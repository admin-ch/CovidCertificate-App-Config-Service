package ch.admin.bag.covidcertificate.backend.config.verifier.ws.config.mock;

import ch.admin.bag.covidcertificate.backend.config.shared.helper.FaqHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.model.ConfigResponse;
import ch.admin.bag.covidcertificate.backend.config.shared.poeditor.Messages;
import ch.admin.bag.covidcertificate.backend.config.shared.semver.Version;
import ch.admin.bag.covidcertificate.backend.config.verifier.ws.controller.VerifierConfigController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;

@Configuration
@Profile("mock-forceupdate")
public class MockForceUpdateConfig {

    @Value("${mock.forceupdate.buildnr.ios}")
    private String iosBuildNrForceUpdate;

    @Value("${mock.forceupdate.buildnr.android}")
    private String androidBuildNrForceUpdate;

    @Bean
    @Primary
    public VerifierConfigController verifierConfigController(
            Messages messages, FaqHelper faqHelper) {
        return new MockConfigController(messages, faqHelper);
    }

    public class MockConfigController extends VerifierConfigController {

        public MockConfigController(Messages messages, FaqHelper faqHelper) {
            super(messages, faqHelper);
        }

        @Override
        public String hello() {
            return super.hello() + " (mock-forceupdate)";
        }

        @Override
        public ResponseEntity<ConfigResponse> getConfig(
                String appversion, String osversion, String buildnr) {
            ResponseEntity<ConfigResponse> response =
                    super.getConfig(appversion, osversion, buildnr);
            Version version = new Version(appversion);
            if ((version.isIOS() && buildnr.equals(iosBuildNrForceUpdate))
                    || (version.isAndroid() && buildnr.equals(androidBuildNrForceUpdate))) {
                response.getBody().setForceUpdate(true);
            }
            return response;
        }
    }
}
