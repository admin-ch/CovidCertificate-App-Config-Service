package ch.admin.bag.covidcertificate.backend.config.verifier.ws.config.mock;

import ch.admin.bag.covidcertificate.backend.config.shared.helper.CheckModeInfoHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.helper.FaqHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.helper.MockHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.model.VerifierConfigResponse;
import ch.admin.bag.covidcertificate.backend.config.shared.poeditor.Messages;
import ch.admin.bag.covidcertificate.backend.config.verifier.ws.controller.VerifierConfigController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;

@Configuration
@Profile("mock-infobox")
public class MockInfoBoxConfig {

    @Bean
    @Primary
    public VerifierConfigController verifierConfigController(
            Messages messages,
            CheckModeInfoHelper checkModeInfoHelper,
            FaqHelper faqHelper,
            @Value("${ws.verifier.timeshiftDetection.enabled:false}")
                    boolean timeshiftDetectionEnabled) {
        return new MockConfigController(
                messages, checkModeInfoHelper, faqHelper, timeshiftDetectionEnabled);
    }

    public class MockConfigController extends VerifierConfigController {

        public MockConfigController(
                Messages messages,
                CheckModeInfoHelper checkModeInfoHelper,
                FaqHelper faqHelper,
                boolean timeshiftDetectionEnabled) {
            super(messages, checkModeInfoHelper, faqHelper, timeshiftDetectionEnabled);
        }

        @Override
        public String hello() {
            return super.hello() + " (mock-infobox)";
        }

        @Override
        public ResponseEntity<VerifierConfigResponse> getConfig(
                String appversion, String osversion, String buildnr) {
            var response = super.getConfig(appversion, osversion, buildnr);
            response.getBody().setInfoBox(MockHelper.getInfoBoxMock());
            return response;
        }
    }
}
