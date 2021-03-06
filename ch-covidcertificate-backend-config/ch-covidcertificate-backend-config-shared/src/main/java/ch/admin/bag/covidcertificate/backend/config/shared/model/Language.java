package ch.admin.bag.covidcertificate.backend.config.shared.model;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.List;
import java.util.Locale;

public enum Language {
    DE("de"),
    EN("en"),
    FR("fr"),
    IT("it"),
    RM("rm");

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

    /**
     * for use during development when missing translations
     * @return
     */
    public static List<Language> deOnly() {
        return List.of(Language.DE);
    }
}
