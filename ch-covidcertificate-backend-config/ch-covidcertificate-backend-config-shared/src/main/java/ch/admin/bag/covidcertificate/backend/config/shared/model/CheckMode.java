package ch.admin.bag.covidcertificate.backend.config.shared.model;

import java.util.List;

public enum CheckMode {
    THREE_G(
            "#86c5d9",
            "3g",
            "ic_3g",
            "ic_no3g",
            List.of(
                    new EntryIconConfig("1", "ic-expire-i", "ic_expire_i"),
                    new EntryIconConfig("2", "ic_3g", "ic_3g"),
                    new EntryIconConfig(
                            "3", "ic-qr-certificate-light", "ic_qr_certificate_light"))),
    TWO_G(
            "#c2d076",
            "2g",
            "ic_2g",
            "ic_no2g",
            List.of(
                    new EntryIconConfig("1", "ic-expire-i", "ic_expire_i"),
                    new EntryIconConfig("2", "ic_2g", "ic_2g"),
                    new EntryIconConfig(
                            "3", "ic-qr-certificate-light-no", "ic_qr_certificate_light_no")));

    private String color;
    private String poeditorIdentifier;
    private String icon;
    private String strikedIcon;
    private List<EntryIconConfig> verifierInfoEntries;

    CheckMode(
            String color,
            String poeditorIdentifier,
            String icon,
            String strikedIcon,
            List<EntryIconConfig> verifierInfoEntries) {
        this.color = color;
        this.icon = icon;
        this.strikedIcon = strikedIcon;
        this.poeditorIdentifier = poeditorIdentifier;
        this.verifierInfoEntries = verifierInfoEntries;
    }

    public String getColor() {
        return color;
    }

    public String getPoeditorIdentifier() {
        return poeditorIdentifier;
    }

    public String getIcon() {
        return icon;
    }

    public String getStrikedIcon() {
        return strikedIcon;
    }

    public List<EntryIconConfig> getVerifierInfoEntries() {
        return verifierInfoEntries;
    }
}
