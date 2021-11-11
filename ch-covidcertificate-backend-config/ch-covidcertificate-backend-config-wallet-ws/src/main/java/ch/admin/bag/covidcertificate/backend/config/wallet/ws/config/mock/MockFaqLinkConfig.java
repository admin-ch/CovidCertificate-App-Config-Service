package ch.admin.bag.covidcertificate.backend.config.wallet.ws.config.mock;

import ch.admin.bag.covidcertificate.backend.config.shared.helper.FaqHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.helper.InfoBoxHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.helper.VaccinationHintHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.model.Faq;
import ch.admin.bag.covidcertificate.backend.config.shared.model.FaqEntry;
import ch.admin.bag.covidcertificate.backend.config.shared.model.Language;
import ch.admin.bag.covidcertificate.backend.config.shared.model.WalletConfigResponse;
import ch.admin.bag.covidcertificate.backend.config.shared.poeditor.Messages;
import ch.admin.bag.covidcertificate.backend.config.wallet.ws.controller.WalletConfigController;
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
    public WalletConfigController walletConfigController(
            Messages messages,
            FaqHelper faqHelper,
            InfoBoxHelper infoBoxHelper,
            @Value("${ws.wallet.light-certificate.active:false}") boolean lightCertificateActive,
            @Value("${ws.wallet.pdf-generation.active:false}") boolean pdfGenerationActive,
            VaccinationHintHelper vaccinationHintHelper,
            @Value("${ws.wallet.vaccination-hints.homescreen.show:false}")
                    boolean showVaccinationHintHomescreen,
            @Value("${ws.wallet.vaccination-hints.detail.show:false}")
                    boolean showVaccinationHintDetail,
            @Value("${ws.wallet.vaccination-hints.transfer.show:false}")
                    boolean showVaccinationHintTransfer,
            @Value("${ws.wallet.timeshiftDetection.enabled:true}")
                    boolean timeShiftDetectionEnabled
    ) {
        return new MockConfigController(
                messages,
                faqHelper,
                infoBoxHelper,
                lightCertificateActive,
                pdfGenerationActive,
                vaccinationHintHelper,
                showVaccinationHintHomescreen,
                showVaccinationHintDetail,
                showVaccinationHintTransfer,
                timeShiftDetectionEnabled);
    }

    public class MockConfigController extends WalletConfigController {

        public MockConfigController(
                Messages messages,
                FaqHelper faqHelper,
                InfoBoxHelper infoBoxHelper,
                boolean lightCertificateActive,
                boolean pdfGenerationActive,
                VaccinationHintHelper vaccinationHintHelper,
                boolean showVaccinationHintHomescreen,
                boolean showVaccinationHintDetail,
                boolean showVaccinationHintTransfer,
                boolean timeShiftDetectionEnabled) {
            super(
                    messages,
                    faqHelper,
                    infoBoxHelper,
                    lightCertificateActive,
                    pdfGenerationActive,
                    vaccinationHintHelper,
                    showVaccinationHintHomescreen,
                    showVaccinationHintDetail,
                    showVaccinationHintTransfer,
                    timeShiftDetectionEnabled);
        }

        @Override
        public String hello() {
            return super.hello() + " (mock-faqlink)";
        }

        @Override
        public ResponseEntity<WalletConfigResponse> getConfig(
                String appversion, String osversion, String buildnr) {
            ResponseEntity<WalletConfigResponse> response =
                    super.getConfig(appversion, osversion, buildnr);
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
