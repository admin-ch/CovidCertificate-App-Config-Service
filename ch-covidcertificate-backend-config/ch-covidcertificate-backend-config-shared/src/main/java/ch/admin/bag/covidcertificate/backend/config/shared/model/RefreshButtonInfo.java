package ch.admin.bag.covidcertificate.backend.config.shared.model;

public class RefreshButtonInfo {
    private String text1;
    private String text2;
    private String fatTitle;
    private String text3;
    private String linkText;
    private String linkUrl;

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public String getFatTitle() {
        return fatTitle;
    }

    public void setFatTitle(String fatTitle) {
        this.fatTitle = fatTitle;
    }

    public String getText3() {
        return text3;
    }

    public void setText3(String text3) {
        this.text3 = text3;
    }

    public String getLinkText() {
        return linkText;
    }

    public void setLinkText(String linkText) {
        this.linkText = linkText;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }
}
