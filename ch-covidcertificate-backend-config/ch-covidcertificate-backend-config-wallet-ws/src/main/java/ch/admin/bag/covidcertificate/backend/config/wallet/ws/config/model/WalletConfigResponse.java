package ch.admin.bag.covidcertificate.backend.config.wallet.ws.config.model;

import ch.admin.bag.covidcertificate.backend.config.shared.model.ConfigResponse;
import ch.admin.bag.covidcertificate.backend.config.shared.model.Faq;
import ch.admin.bag.covidcertificate.backend.config.shared.model.Language;
import ch.ubique.openapi.docannotations.Documentation;
import java.util.Map;

public class WalletConfigResponse extends ConfigResponse {
    @Documentation(description = "Holds transfer FAQ parameters by language")
    private Map<Language, Faq> transferQuestions = null;

    @Documentation(description = "Holds transfer how-it-works FAQ parameters by language")
    private Map<Language, Faq> transferWorks = null;

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
}
