package ch.admin.bag.covidcertificate.backend.config.shared.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum CheckMode {
    THREE_G(
            "#86c5d9",
            "3g",
            Icon.THREE_G,
            Icon.THREE_G_STRIKED,
            List.of(
                    new EntryIconConfig("1", Icon.INFO),
                    new EntryIconConfig("2", Icon.THREE_G),
                    new EntryIconConfig("3", Icon.CERT_LIGHT_YES))),
    TWO_G(
            "#c2d076",
            "2g",
            Icon.TWO_G,
            Icon.TWO_G_STRIKED,
            List.of(
                    new EntryIconConfig("1", Icon.INFO),
                    new EntryIconConfig("2", Icon.TWO_G),
                    new EntryIconConfig("3", Icon.CERT_LIGHT_NO))),
    TWO_G_PLUS(
            "#e6ad8e",
            "2g_plus",
            Icon.TWO_G_PLUS,
            // striked icon is for wallet app only. 2G+ is not supported for wallet app at this time
            null,
            List.of(
                    new EntryIconConfig("1", Icon.INFO),
                    new EntryIconConfig("2", Icon.TWO_G_PLUS),
                    new EntryIconConfig("3", Icon.ALERT),
                    new EntryIconConfig("4", Icon.CERT_LIGHT_NO))),
    TEST_CERT(
            "#facafa",
            "test_cert",
            Icon.TEST_CERT,
            // striked icon is for wallet app only. `t` mode is not supported for wallet app at this
            // time
            null,
            List.of(
                    new EntryIconConfig("1", Icon.INFO),
                    new EntryIconConfig("2", Icon.TEST_CERT),
                    new EntryIconConfig("3", Icon.CERT_LIGHT_NO)));

    private String color;
    private String poeditorIdentifier;
    private Icon icon;
    private Icon strikedIcon;
    private List<EntryIconConfig> verifierInfoEntries;

    CheckMode(
            String color,
            String poeditorIdentifier,
            Icon icon,
            Icon strikedIcon,
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

    public String getIconAndroid() {
        return icon.getAndroid();
    }

    public String getIconIos() {
        return icon.getIos();
    }

    public String getStrikedIconAndroid() {
        return strikedIcon.getAndroid();
    }

    public String getStrikedIconIos() {
        return strikedIcon.getIos();
    }

    public List<EntryIconConfig> getVerifierInfoEntries() {
        return verifierInfoEntries;
    }

    public static List<CheckMode> getVerifierModes() {
        return Arrays.stream(CheckMode.values()).collect(Collectors.toList());
    }

    public static List<CheckMode> getWalletModes() {
        return Arrays.stream(CheckMode.values())
                .filter(m -> !List.of(CheckMode.TWO_G_PLUS, CheckMode.TEST_CERT).contains(m))
                .collect(Collectors.toList());
    }
}
