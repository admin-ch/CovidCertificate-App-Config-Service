package ch.admin.bag.covidcertificate.backend.config.wallet.ws;

import ch.admin.bag.covidcertificate.backend.config.shared.TestHelper;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
public class WalletControllerJsonTest extends BaseControllerTest {
    @BeforeAll
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.objectMapper = new ObjectMapper(new JsonFactory());
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.registerModule(new JodaModule());
        // this makes sure, that the objectmapper does not fail, when a filter is not provided.
        this.objectMapper.setFilterProvider(new SimpleFilterProvider().setFailOnUnknownId(false));
        this.testHelper = new TestHelper(objectMapper);
        this.acceptMediaType = MediaType.APPLICATION_JSON;
    }
}
