package ch.admin.bag.covidcertificate.backend.config.shared.model;

import javax.validation.constraints.NotNull;

public class ForeignRulesHint {
    @NotNull private String iconIos;
    @NotNull private String iconAndroid;
    @NotNull private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIconIos() {
        return iconIos;
    }

    public void setIconIos(String iconIos) {
        this.iconIos = iconIos;
    }

    public String getIconAndroid() {
        return iconAndroid;
    }

    public void setIconAndroid(String iconAndroid) {
        this.iconAndroid = iconAndroid;
    }
}
