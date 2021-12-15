package ch.admin.bag.covidcertificate.backend.config.shared.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
                            "3", "ic-qr-certificate-light-no", "ic_qr_certificate_light_no"))),
    TWO_G_PLUS(
            "#e6ad8e",
            "2g_plus",
            "ic-2-g-plus",
            // striked icon is for wallet app only. 2G+ is not supported for wallet app at this time
            null,
            List.of(
                    new EntryIconConfig("1", "ic-expire-i", "ic_expire_i"),
                    new EntryIconConfig("2", "ic-2-g-plus", "ic_2g_plus"),
                    new EntryIconConfig("3", "ic-info-alert", "ic_info_alert"),
                    new EntryIconConfig(
                            "4", "ic-qr-certificate-light-no", "ic_qr_certificate_light_no")));

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

    public static List<CheckMode> getVerifierModes() {
        return Arrays.stream(CheckMode.values()).collect(Collectors.toList());
    }

    public static List<CheckMode> getWalletModes() {
        return Arrays.stream(CheckMode.values())
                .filter(m -> !m.equals(CheckMode.TWO_G_PLUS))
                .collect(Collectors.toList());
    }
}
