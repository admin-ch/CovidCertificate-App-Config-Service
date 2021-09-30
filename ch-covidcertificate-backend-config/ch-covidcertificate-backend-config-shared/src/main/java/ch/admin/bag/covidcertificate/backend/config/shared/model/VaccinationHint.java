package ch.admin.bag.covidcertificate.backend.config.shared.model;

import javax.validation.constraints.NotNull;

public class VaccinationHint {
    @NotNull private String title;
    @NotNull private String text;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
