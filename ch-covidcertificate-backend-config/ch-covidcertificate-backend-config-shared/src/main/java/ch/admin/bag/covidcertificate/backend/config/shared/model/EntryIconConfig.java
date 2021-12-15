package ch.admin.bag.covidcertificate.backend.config.shared.model;

public class EntryIconConfig {
    private String entryId;
    private Icon icon;

    public EntryIconConfig(String entryId, Icon icon) {
        this.entryId = entryId;
        this.icon = icon;
    }

    public String getEntryId() {
        return entryId;
    }

    public String getIconIos() {
        return icon.getIos();
    }

    public String getIconAndroid() {
        return icon.getAndroid();
    }
}
