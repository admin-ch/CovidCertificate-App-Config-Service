package ch.admin.bag.covidcertificate.backend.config.wallet.ws.config.mock;

import ch.admin.bag.covidcertificate.backend.config.shared.helper.FaqHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.model.Faq;
import ch.admin.bag.covidcertificate.backend.config.shared.model.FaqEntry;
import ch.admin.bag.covidcertificate.backend.config.shared.model.Language;
import ch.admin.bag.covidcertificate.backend.config.shared.poeditor.Messages;
import ch.admin.bag.covidcertificate.backend.config.wallet.ws.controller.WalletConfigController;
import ch.admin.bag.covidcertificate.backend.config.wallet.ws.model.WalletConfigResponse;
import java.util.Map;
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
    public WalletConfigController walletConfigController(Messages messages, FaqHelper faqHelper) {
        return new MockConfigController(messages, faqHelper);
    }

    public class MockConfigController extends WalletConfigController {

        public MockConfigController(Messages messages, FaqHelper faqHelper) {
            super(messages, faqHelper);
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