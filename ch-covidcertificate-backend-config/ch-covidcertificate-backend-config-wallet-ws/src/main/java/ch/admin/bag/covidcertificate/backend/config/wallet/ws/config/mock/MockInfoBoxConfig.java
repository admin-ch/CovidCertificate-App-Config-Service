package ch.admin.bag.covidcertificate.backend.config.wallet.ws.config.mock;

import ch.admin.bag.covidcertificate.backend.config.shared.helper.FaqHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.helper.MockHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.model.ConfigResponse;
import ch.admin.bag.covidcertificate.backend.config.shared.poeditor.Messages;
import ch.admin.bag.covidcertificate.backend.config.wallet.ws.controller.WalletConfigController;
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
    public WalletConfigController walletConfigController(Messages messages, FaqHelper faqHelper) {
        return new MockConfigController(messages, faqHelper);
    }

    public class MockConfigController extends WalletConfigController {

        public MockConfigController(Messages messages, FaqHelper faqHelper) {
            super(messages, faqHelper);
        }

        @Override
        public String hello() {
            return super.hello() + " (mock-infobox)";
        }

        @Override
        public ResponseEntity<ConfigResponse> getConfig(
                String appversion, String osversion, String buildnr) {
            ResponseEntity<ConfigResponse> response =
                    super.getConfig(appversion, osversion, buildnr);
            response.getBody().setInfoBox(MockHelper.getInfoBoxMock());
            return response;
        }
    }
}
