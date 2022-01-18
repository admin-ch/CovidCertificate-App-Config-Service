package ch.admin.bag.covidcertificate.backend.config.shared.model;

import javax.validation.constraints.NotNull;

public class EolBannerInfo {

    // homescreen banner
    @NotNull private String homescreenHexColor;
    @NotNull private String homescreenTitle;

    // detail banner
    @NotNull private String detailHexColor;
    @NotNull private String detailTitle;
    @NotNull private String detailText;
    @NotNull private String detailMoreInfo;

    // popup
    @NotNull private String popupTitle;
    private String popupText;
    private String popupBoldText;
    private String popupLinkText;
    private String popupLinkUrl;

    public String getHomescreenHexColor() {
        return homescreenHexColor;
    }

    public void setHomescreenHexColor(String homescreenHexColor) {
        this.homescreenHexColor = homescreenHexColor;
    }

    public String getHomescreenTitle() {
        return homescreenTitle;
    }

    public void setHomescreenTitle(String homescreenTitle) {
        this.homescreenTitle = homescreenTitle;
    }

    public String getDetailHexColor() {
        return detailHexColor;
    }

    public void setDetailHexColor(String detailHexColor) {
        this.detailHexColor = detailHexColor;
    }

    public String getDetailTitle() {
        return detailTitle;
    }

    public void setDetailTitle(String detailTitle) {
        this.detailTitle = detailTitle;
    }

    public String getDetailText() {
        return detailText;
    }

    public void setDetailText(String detailText) {
        this.detailText = detailText;
    }

    public String getDetailMoreInfo() {
        return detailMoreInfo;
    }

    public void setDetailMoreInfo(String detailMoreInfo) {
        this.detailMoreInfo = detailMoreInfo;
    }

    public String getPopupTitle() {
        return popupTitle;
    }

    public void setPopupTitle(String popupTitle) {
        this.popupTitle = popupTitle;
    }

    public String getPopupText() {
        return popupText;
    }

    public void setPopupText(String popupText) {
        this.popupText = popupText;
    }

    public String getPopupBoldText() {
        return popupBoldText;
    }

    public void setPopupBoldText(String popupBoldText) {
        this.popupBoldText = popupBoldText;
    }

    public String getPopupLinkText() {
        return popupLinkText;
    }

    public void setPopupLinkText(String popupLinkText) {
        this.popupLinkText = popupLinkText;
    }

    public String getPopupLinkUrl() {
        return popupLinkUrl;
    }

    public void setPopupLinkUrl(String popupLinkUrl) {
        this.popupLinkUrl = popupLinkUrl;
    }
}
