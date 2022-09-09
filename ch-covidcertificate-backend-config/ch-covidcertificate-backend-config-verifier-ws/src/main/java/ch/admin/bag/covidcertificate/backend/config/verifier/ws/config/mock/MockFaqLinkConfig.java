package ch.admin.bag.covidcertificate.backend.config.verifier.ws.config.mock;

import ch.admin.bag.covidcertificate.backend.config.shared.helper.CheckModeInfoHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.helper.CovidCertNewsHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.helper.FaqHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.model.Faq;
import ch.admin.bag.covidcertificate.backend.config.shared.model.FaqEntry;
import ch.admin.bag.covidcertificate.backend.config.shared.model.Language;
import ch.admin.bag.covidcertificate.backend.config.shared.model.VerifierConfigResponse;
import ch.admin.bag.covidcertificate.backend.config.shared.poeditor.Messages;
import ch.admin.bag.covidcertificate.backend.config.verifier.ws.controller.VerifierConfigController;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;

@Configuration
@Profile("mock-faqlink")
public class MockFaqLinkConfig {

    @Bean
    @Primary
    public VerifierConfigController verifierConfigController(
            Messages messages,
            CheckModeInfoHelper checkModeInfoHelper,
            FaqHelper faqHelper,
            CovidCertNewsHelper covidCertNewsHelper,
            @Value("${ws.verifier.timeshiftDetection.enabled:false}")
                    boolean timeshiftDetectionEnabled,
            @Value("${ws.verifier.checkModeReselectionAfterHours:48}")
            int checkModeReselectionAfterHours) {
        return new MockConfigController(
                messages, checkModeInfoHelper, faqHelper, covidCertNewsHelper, timeshiftDetectionEnabled, checkModeReselectionAfterHours);
    }

    public class MockConfigController extends VerifierConfigController {

        public MockConfigController(
                Messages messages,
                CheckModeInfoHelper checkModeInfoHelper,
                FaqHelper faqHelper,
                CovidCertNewsHelper covidCertNewsHelper,
                boolean timeShiftDetectionEnabled,
                int checkModeReselectionAfterHours) {
            super(messages, checkModeInfoHelper, faqHelper, covidCertNewsHelper, timeShiftDetectionEnabled, checkModeReselectionAfterHours);
        }

        @Override
        public String hello() {
            return super.hello() + " (mock-faqlink)";
        }

        @Override
        public ResponseEntity<VerifierConfigResponse> getConfig(
                String appversion, String osversion, String buildnr) {
            var response = super.getConfig(appversion, osversion, buildnr);
            Map<Language, Faq> works = response.getBody().getWorks();
            for (Faq faq : works.values()) {
                FaqEntry faqEntry = faq.getFaqEntries().get(0);
                faqEntry.setLinkUrl("https://www.ubique.ch");
                faqEntry.setLinkTitle("mock link");
            }
            return response;
        }
    }
}
