package ch.admin.bag.covidcertificate.backend.config.wallet.ws.config.mock;

import ch.admin.bag.covidcertificate.backend.config.shared.helper.CertRenewalInfoHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.helper.CheckModeInfoHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.helper.EolBannerInfoHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.helper.FaqHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.helper.ForeignRulesHintHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.helper.InfoBoxHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.helper.RefreshButtonInfoHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.helper.VaccinationHintHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.model.WalletConfigResponse;
import ch.admin.bag.covidcertificate.backend.config.shared.poeditor.Messages;
import ch.admin.bag.covidcertificate.backend.config.shared.semver.Version;
import ch.admin.bag.covidcertificate.backend.config.wallet.ws.controller.WalletConfigController;
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
    public WalletConfigController walletConfigController(
            Messages messages,
            CheckModeInfoHelper checkModeInfoHelper,
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
            @Value("${ws.wallet.timeshiftDetection.enabled:true}") boolean timeshiftDetectionFlag,
            @Value("${ws.wallet.refreshButton.disabled}") boolean refreshButtonDisabled,
            @Value("${ws.wallet.foreignRules.enabled:false}") boolean foreignRulesEnabled,
            @Value("${ws.wallet.ratConversion.enabled}") boolean ratConversionEnabled,
            @Value("${ws.wallet.ratConversion.url}") String ratFormUrl,
            @Value("${ws.wallet.ratConversion.url}") boolean showValidityState,
            RefreshButtonInfoHelper refreshButtonInfoHelper,
            EolBannerInfoHelper eolBannerInfoHelper,
            ForeignRulesHintHelper foreignRulesHintHelper,
            CertRenewalInfoHelper certRenewalInfoHelper) {
        return new MockConfigController(
                messages,
                checkModeInfoHelper,
                faqHelper,
                infoBoxHelper,
                lightCertificateActive,
                pdfGenerationActive,
                vaccinationHintHelper,
                showVaccinationHintHomescreen,
                showVaccinationHintDetail,
                showVaccinationHintTransfer,
                timeshiftDetectionFlag,
                refreshButtonDisabled,
                foreignRulesEnabled,
                ratConversionEnabled,
                ratFormUrl,
                showValidityState,
                refreshButtonInfoHelper,
                eolBannerInfoHelper,
                foreignRulesHintHelper,
                certRenewalInfoHelper);
    }

    public class MockConfigController extends WalletConfigController {

        public MockConfigController(
                Messages messages,
                CheckModeInfoHelper checkModeInfoHelper,
                FaqHelper faqHelper,
                InfoBoxHelper infoBoxHelper,
                boolean lightCertificateActive,
                boolean pdfGenerationActive,
                VaccinationHintHelper vaccinationHintHelper,
                boolean showVaccinationHintHomescreen,
                boolean showVaccinationHintDetail,
                boolean showVaccinationHintTransfer,
                boolean timeshiftDetectionFlag,
                boolean refreshButtonDisabled,
                boolean foreignRulesEnabled,
                boolean ratConversionEnabled,
                String ratFormUrl,
                boolean showValidityState,
                RefreshButtonInfoHelper refreshButtonInfoHelper,
                EolBannerInfoHelper eolBannerInfoHelper,
                ForeignRulesHintHelper foreignRulesHintHelper,
                CertRenewalInfoHelper certRenewalInfoHelper) {
            super(
                    messages,
                    checkModeInfoHelper,
                    faqHelper,
                    infoBoxHelper,
                    lightCertificateActive,
                    pdfGenerationActive,
                    vaccinationHintHelper,
                    showVaccinationHintHomescreen,
                    showVaccinationHintDetail,
                    showVaccinationHintTransfer,
                    timeshiftDetectionFlag,
                    refreshButtonDisabled,
                    foreignRulesEnabled,
                    ratConversionEnabled,
                    ratFormUrl,
                    showValidityState,
                    refreshButtonInfoHelper,
                    eolBannerInfoHelper,
                    foreignRulesHintHelper,
                    certRenewalInfoHelper);
        }

        @Override
        public String hello() {
            return super.hello() + " (mock-forceupdate)";
        }

        @Override
        public ResponseEntity<WalletConfigResponse> getConfig(
                String appversion, String osversion, String buildnr) {
            ResponseEntity<WalletConfigResponse> response =
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
