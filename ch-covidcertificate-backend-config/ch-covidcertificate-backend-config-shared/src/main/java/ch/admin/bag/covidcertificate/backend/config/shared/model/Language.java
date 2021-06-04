package ch.admin.bag.covidcertificate.backend.config.shared.model;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Locale;

public enum Language {
    DE("de"),
    EN("en"),
    FR("fr"),
    IT("it");

    private String key;

    Language(String key) {
        this.key = key;
    }

    @JsonValue
    public String getKey() {
        return key;
    }

    public Locale toLocale() {
        return Locale.forLanguageTag(this.getKey());
    }
}
