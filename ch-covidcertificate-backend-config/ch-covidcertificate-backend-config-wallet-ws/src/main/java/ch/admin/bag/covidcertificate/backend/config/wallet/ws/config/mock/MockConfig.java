package ch.admin.bag.covidcertificate.backend.config.wallet.ws.config.mock;

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
@Profile("mock")
public class MockConfig {

    @Bean
    @Primary
    public WalletConfigController walletConfigController(Messages messages) {
        return new MockConfigController(messages);
    }

    public class MockConfigController extends WalletConfigController {

        public MockConfigController(Messages messages) {
            super(messages);
        }

        @Override
        public String hello() {
            return super.hello() + " (mock)";
        }

        @Override
        public ResponseEntity<ConfigResponse> getConfig(
                String appversion, String osversion, String buildnr) {
            ConfigResponse response = new ConfigResponse();
            response.setForceUpdate(false);
            response.setInfoBox(MockHelper.getInfoBoxMock());
            response.setQuestions(MockHelper.getQuestionsMock());
            response.setWorks(MockHelper.getWorksMock());
            return ResponseEntity.ok(response);
        }
    }
}
