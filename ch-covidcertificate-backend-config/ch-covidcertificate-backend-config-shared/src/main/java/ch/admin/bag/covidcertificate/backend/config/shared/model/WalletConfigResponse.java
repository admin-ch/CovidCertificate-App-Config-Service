package ch.admin.bag.covidcertificate.backend.config.shared.model;

import ch.ubique.openapi.docannotations.Documentation;
import java.util.Map;

public class WalletConfigResponse extends ConfigResponse {
    @Documentation(description = "Holds transfer FAQ parameters by language")
    private Map<Language, Faq> transferQuestions = null;

    @Documentation(description = "Holds transfer how-it-works FAQ parameters by language")
    private Map<Language, Faq> transferWorks = null;

    @Documentation(
            description =
                    "feature flag. when set to true the light certificate feature is available")
    private Boolean lightCertificateActive;

    @Documentation(
            description = "feature flag. when set to true the pdf generation feature is available")
    private Boolean pdfGenerationActive;

    public Map<Language, Faq> getTransferQuestions() {
        return transferQuestions;
    }

    public void setTransferQuestions(Map<Language, Faq> transferQuestions) {
        this.transferQuestions = transferQuestions;
    }

    public Map<Language, Faq> getTransferWorks() {
        return transferWorks;
    }

    public void setTransferWorks(Map<Language, Faq> transferWorks) {
        this.transferWorks = transferWorks;
    }

    public Boolean getLightCertificateActive() {
        return lightCertificateActive;
    }

    public void setLightCertificateActive(Boolean lightCertificateActive) {
        this.lightCertificateActive = lightCertificateActive;
    }

    public Boolean getPdfGenerationActive() {
        return pdfGenerationActive;
    }

    public void setPdfGenerationActive(Boolean pdfGenerationActive) {
        this.pdfGenerationActive = pdfGenerationActive;
    }
}
