package ch.admin.bag.covidcertificate.backend.config.shared.model;

public class EntryIconConfig {
    private String entryId;
    private String iconIos;
    private String iconAndroid;

    public EntryIconConfig(String entryId, String iconIos, String iconAndroid) {
        this.entryId = entryId;
        this.iconIos = iconIos;
        this.iconAndroid = iconAndroid;
    }

    public String getEntryId() {
        return entryId;
    }

    public String getIconIos() {
        return iconIos;
    }

    public String getIconAndroid() {
        return iconAndroid;
    }
}
