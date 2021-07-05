package ch.admin.bag.covidcertificate.backend.config.wallet.ws.config.mock;

import ch.admin.bag.covidcertificate.backend.config.shared.helper.FaqHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.helper.MockHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.poeditor.Messages;
import ch.admin.bag.covidcertificate.backend.config.wallet.ws.controller.WalletConfigController;
import ch.admin.bag.covidcertificate.backend.config.wallet.ws.model.WalletConfigResponse;
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
    public WalletConfigController walletConfigController(
            Messages messages,
            FaqHelper faqHelper,
            @Value("${ws.wallet.light-certificate.active:false}") boolean lightCertificateActive,
            @Value("${ws.wallet.pdf-generation.active:false}") boolean pdfGenerationActive) {
        return new MockConfigController(
                messages, faqHelper, lightCertificateActive, pdfGenerationActive);
    }

    public class MockConfigController extends WalletConfigController {

        public MockConfigController(
                Messages messages,
                FaqHelper faqHelper,
                boolean lightCertificateActive,
                boolean pdfGenerationActive) {
            super(messages, faqHelper, lightCertificateActive, pdfGenerationActive);
        }

        @Override
        public String hello() {
            return super.hello() + " (mock-infobox)";
        }

        @Override
        public ResponseEntity<WalletConfigResponse> getConfig(
                String appversion, String osversion, String buildnr) {
            ResponseEntity<WalletConfigResponse> response =
                    super.getConfig(appversion, osversion, buildnr);
            response.getBody().setInfoBox(MockHelper.getInfoBoxMock());
            return response;
        }
    }
}
