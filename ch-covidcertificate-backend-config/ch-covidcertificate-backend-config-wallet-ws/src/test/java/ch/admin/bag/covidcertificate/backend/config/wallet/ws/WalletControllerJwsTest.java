package ch.admin.bag.covidcertificate.backend.config.wallet.ws;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ch.admin.bag.covidcertificate.backend.config.shared.InvalidSignatureException;
import ch.admin.bag.covidcertificate.backend.config.shared.TestHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.security.signature.JWSMessageConverter;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ActiveProfiles({"actuator-security"})
@SpringBootTest(
        properties = {
            "ws.monitor.prometheus.user=prometheus",
            "vcap.services.ha_prometheus_dev.credentials.password=prometheus",
            "management.endpoints.enabled-by-default=true",
            "management.endpoints.web.exposure.include=*"
        })
@TestInstance(Lifecycle.PER_CLASS)
public class WalletControllerJwsTest extends BaseControllerTest {
    @BeforeAll
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.objectMapper = new ObjectMapper(new JsonFactory());
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.registerModule(new JodaModule());
        // this makes sure, that the objectmapper does not fail, when a filter is not provided.
        this.objectMapper.setFilterProvider(new SimpleFilterProvider().setFailOnUnknownId(false));
        this.testHelper = new TestHelper(objectMapper);
        this.acceptMediaType = JWSMessageConverter.JWS_MEDIA_TYPE;
    }

    @Test
    public void testJwsCertValidationFail() throws Exception {
        MockHttpServletResponse result =
                mockMvc.perform(
                                get(BASE_URL + "/config")
                                        .accept(acceptMediaType)
                                        .param("osversion", "ios12")
                                        .param("appversion", "ios-1.0.9")
                                        .param("buildnr", "ios-2020.0145asdfa34"))
                        .andExpect(status().is2xxSuccessful())
                        .andReturn()
                        .getResponse();
        assertThrows(
                InvalidSignatureException.class,
                () ->
                        testHelper.toConfigResponse(
                                result, acceptMediaType, TestHelper.PATH_TO_WRONG_CA_PEM));
    }
}
