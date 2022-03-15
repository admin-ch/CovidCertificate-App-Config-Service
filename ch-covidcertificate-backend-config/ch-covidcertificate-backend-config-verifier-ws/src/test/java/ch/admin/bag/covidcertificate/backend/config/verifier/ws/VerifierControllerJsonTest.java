package ch.admin.bag.covidcertificate.backend.config.verifier.ws;

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
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class VerifierControllerJsonTest extends BaseControllerTest {
    @BeforeAll
    @Override
    public void setup() {
        super.setup();
        this.acceptMediaType = MediaType.APPLICATION_JSON;
    }
}
