package ch.admin.bag.covidcertificate.backend.config.shared.model;

import ch.ubique.openapi.docannotations.Documentation;
import java.util.List;
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
            description = "feature flag for the travel check feature"
    )
    private Boolean isForeignRulesCheckEnabled;

    @Documentation(
            description = "feature flag. when set to true the pdf generation feature is available")
    private Boolean pdfGenerationActive;

    @Documentation(description = "Holds vaccination hints by language")
    private Map<Language, List<VaccinationHint>>  vaccinationHints = null;

    @Documentation(description = "Hint icons and texts for the travel (aka foreign rules) check")
    private Map<Language, List<ForeignRulesHint>> foreignRulesHints;

    private Map<Language, String> foreignRulesLinkText;

    private Map<Language, String> foreignRulesLinkUrl;

    @Documentation(description = "Holds vaccination booking infos for cantons by language")
    private Map<Language, List<VaccinationBookingCanton>> vaccinationBookingCantons = null;

    @Documentation(description = "Holds general vaccination booking info by language")
    private Map<Language, VaccinationBookingInfo> vaccinationBookingInfo = null;

    @Documentation(description = "Holds info texts and icons for check modes by language")
    private Map<Language, WalletModesInfo> checkModesInfo = null;

    @Documentation(
            description = "describes after how many hours the check mode has to be reselected",
            example = "48")
    private Integer lightCertDurationInHours;

    @Documentation(
            description =
                    "feature flag. when set to true the vaccination hints should be displayed on the homescreen")
    private Boolean showVaccinationHintHomescreen;

    @Documentation(
            description =
                    "feature flag. when set to true the vaccination hints should be displayed in the detail view")
    private Boolean showVaccinationHintDetail;

    @Documentation(
            description =
                    "feature flag. when set to true the vaccination hints should be displayed in the transfer code view")
    private Boolean showVaccinationHintTransfer;

    @Documentation(description = "feature flag. when set to true the refresh button is disabled")
    private Boolean refreshButtonDisabled;

    @Documentation(description = "Holds info texts for refresh button disable by language")
    private Map<Language, RefreshButtonInfo> refreshButtonInfo = null;

    @Documentation(
            description =
                    "Holds info texts for the eol banners by language and `displayRuleId`_`caseId`")
    private Map<Language, Map<String, EolBannerInfo>> eolBannerInfo = null;

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

    public Map<Language, List<VaccinationHint>> getVaccinationHints() {
        return vaccinationHints;
    }

    public void setVaccinationHints(Map<Language, List<VaccinationHint>> vaccinationHints) {
        this.vaccinationHints = vaccinationHints;
    }

    public Map<Language, List<VaccinationBookingCanton>> getVaccinationBookingCantons() {
        return vaccinationBookingCantons;
    }

    public void setVaccinationBookingCantons(
            Map<Language, List<VaccinationBookingCanton>> vaccinationBookingCantons) {
        this.vaccinationBookingCantons = vaccinationBookingCantons;
    }

    public Map<Language, VaccinationBookingInfo> getVaccinationBookingInfo() {
        return vaccinationBookingInfo;
    }

    public Map<Language, List<ForeignRulesHint>> getForeignRulesHints() {
        return foreignRulesHints;
    }

    public void setForeignRulesHints(
            Map<Language, List<ForeignRulesHint>> foreignRulesHints) {
        this.foreignRulesHints = foreignRulesHints;
    }

    public Map<Language, String> getForeignRulesLinkText() {
        return foreignRulesLinkText;
    }

    public void setForeignRulesLinkText(
            Map<Language, String> foreignRulesLinkText) {
        this.foreignRulesLinkText = foreignRulesLinkText;
    }

    public Map<Language, String> getForeignRulesLinkUrl() {
        return foreignRulesLinkUrl;
    }

    public void setForeignRulesLinkUrl(
            Map<Language, String> foreignRulesLinkUrl) {
        this.foreignRulesLinkUrl = foreignRulesLinkUrl;
    }

    public void setVaccinationBookingInfo(
            Map<Language, VaccinationBookingInfo> vaccinationBookingInfo) {
        this.vaccinationBookingInfo = vaccinationBookingInfo;
    }

    public Boolean getShowVaccinationHintHomescreen() {
        return showVaccinationHintHomescreen;
    }

    public void setShowVaccinationHintHomescreen(Boolean showVaccinationHintHomescreen) {
        this.showVaccinationHintHomescreen = showVaccinationHintHomescreen;
    }

    public Boolean getShowVaccinationHintDetail() {
        return showVaccinationHintDetail;
    }

    public void setShowVaccinationHintDetail(Boolean showVaccinationHintDetail) {
        this.showVaccinationHintDetail = showVaccinationHintDetail;
    }

    public Boolean getShowVaccinationHintTransfer() {
        return showVaccinationHintTransfer;
    }

    public void setShowVaccinationHintTransfer(Boolean showVaccinationHintTransfer) {
        this.showVaccinationHintTransfer = showVaccinationHintTransfer;
    }

    public Map<Language, WalletModesInfo> getCheckModesInfo() {
        return checkModesInfo;
    }

    public void setCheckModesInfo(Map<Language, WalletModesInfo> checkModesInfo) {
        this.checkModesInfo = checkModesInfo;
    }

    public Integer getLightCertDurationInHours() {
        return lightCertDurationInHours;
    }

    public void setLightCertDurationInHours(Integer lightCertDurationInHours) {
        this.lightCertDurationInHours = lightCertDurationInHours;
    }

    public Boolean getRefreshButtonDisabled() {
        return refreshButtonDisabled;
    }

    public void setRefreshButtonDisabled(Boolean refreshButtonDisabled) {
        this.refreshButtonDisabled = refreshButtonDisabled;
    }

    public Map<Language, RefreshButtonInfo> getRefreshButtonInfo() {
        return refreshButtonInfo;
    }

    public void setRefreshButtonInfo(Map<Language, RefreshButtonInfo> refreshButtonInfo) {
        this.refreshButtonInfo = refreshButtonInfo;
    }

    public Map<Language, Map<String, EolBannerInfo>> getEolBannerInfo() {
        return eolBannerInfo;
    }

    public void setEolBannerInfo(Map<Language, Map<String, EolBannerInfo>> eolBannerInfo) {
        this.eolBannerInfo = eolBannerInfo;
    }

    public Boolean getForeignRulesCheckEnabled() {
        return isForeignRulesCheckEnabled;
    }

    public void setForeignRulesCheckEnabled(Boolean foreignRulesCheckEnabled) {
        isForeignRulesCheckEnabled = foreignRulesCheckEnabled;
    }
}
