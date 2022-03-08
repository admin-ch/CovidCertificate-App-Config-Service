package ch.admin.bag.covidcertificate.backend.config.wallet.ws;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ch.admin.bag.covidcertificate.backend.config.shared.TestHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.model.ConfigResponse;
import ch.admin.bag.covidcertificate.backend.config.shared.model.Language;
import ch.admin.bag.covidcertificate.backend.config.shared.model.WalletConfigResponse;
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
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ActiveProfiles({"actuator-security"})
@SpringBootTest(
        properties = {
            "ws.monitor.prometheus.user=prometheus",
            "management.endpoints.enabled-by-default=true",
            "management.endpoints.web.exposure.include=*"
        })
@TestInstance(Lifecycle.PER_CLASS)
public class WalletControllerJsonTest extends BaseControllerTest {
    @BeforeAll
    @Override
    public void setup() {
        super.setup();
        this.acceptMediaType = MediaType.APPLICATION_JSON;
    }

    @Test
    public void testTransferCodeValidity() throws Exception {
        MockHttpServletResponse result =
            mockMvc.perform(
                    get(BASE_URL + "/config")
                        .accept(acceptMediaType)
                        .param("osversion", "ios12")
                        .param("appversion", "ios-2.6.1")
                        .param("buildnr", "ios-2020.0145asdfa34"))
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse();

        WalletConfigResponse resp =
            testHelper.toWalletConfigResponse(result, acceptMediaType, TestHelper.PATH_TO_CA_PEM);

        assertTrue(resp.getTransferWorks().get(Language.EN).getFaqIntroSections().stream().anyMatch(entry -> entry.getText().contains("transfer code expires after 7 days")));

        MockHttpServletResponse result2 =
            mockMvc.perform(
                    get(BASE_URL + "/config")
                        .accept(acceptMediaType)
                        .param("osversion", "ios12")
                        .param("appversion", "ios-2.7.0")
                        .param("buildnr", "ios-2020.0145asdfa34"))
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse();

        WalletConfigResponse resp2 =
            testHelper.toWalletConfigResponse(result2, acceptMediaType, TestHelper.PATH_TO_CA_PEM);

        assertTrue(resp2.getTransferWorks().get(Language.EN).getFaqIntroSections().stream().anyMatch(entry -> entry.getText().contains("transfer code expires after 30 days")));
    }
}
