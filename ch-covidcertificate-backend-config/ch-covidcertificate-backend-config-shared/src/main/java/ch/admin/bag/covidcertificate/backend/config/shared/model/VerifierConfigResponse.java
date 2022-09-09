package ch.admin.bag.covidcertificate.backend.config.shared.model;

import ch.ubique.openapi.docannotations.Documentation;
import java.util.Map;

public class VerifierConfigResponse extends ConfigResponse {
    @Documentation(description = "Holds texts, icons and colors for modes by language")
    private Map<Language, VerifierModesInfos> checkModesInfos = null;

    @Documentation(
            description = "describes after how many hours the check mode has to be reselected",
            example = "48")
    private Integer checkModeReselectAfterHours;
    private Map<Language, String> covidCertificateNewsText;
    private Map<Language, InfoCovidCertificateNews> infoCovidCertificateNews;

    public Map<Language, VerifierModesInfos> getCheckModesInfos() {
        return checkModesInfos;
    }

    public void setCheckModesInfos(Map<Language, VerifierModesInfos> checkModesInfos) {
        this.checkModesInfos = checkModesInfos;
    }

    public Integer getCheckModeReselectAfterHours() {
        return checkModeReselectAfterHours;
    }

    public void setCheckModeReselectAfterHours(Integer checkModeReselectAfterHours) {
        this.checkModeReselectAfterHours = checkModeReselectAfterHours;
    }

    public Map<Language, String> getCovidCertificateNewsText() {
        return covidCertificateNewsText;
    }

    public void setCovidCertificateNewsText(Map<Language, String> covidCertificateNewsText) {
        this.covidCertificateNewsText = covidCertificateNewsText;
    }

    public Map<Language, InfoCovidCertificateNews> getInfoCovidCertificateNews() {
        return infoCovidCertificateNews;
    }

    public void setInfoCovidCertificateNews(
            Map<Language, InfoCovidCertificateNews> infoCovidCertificateNews) {
        this.infoCovidCertificateNews = infoCovidCertificateNews;
    }
}
