package ch.admin.bag.covidcertificate.backend.config.shared.model;

import java.util.List;

public enum CertRenewalInfoType {
    INFO(
            "info",
            List.of(
                    new EntryIconConfig("1", Icon.CLOCK_X),
                    new EntryIconConfig("2", Icon.QR_CERT),
                    new EntryIconConfig("3", Icon.CLOUD),
                    new EntryIconConfig("4", Icon.INFO))),
    EXPIRED(
            "expired",
            List.of(
                    new EntryIconConfig("1", Icon.CLOCK_X),
                    new EntryIconConfig("2", Icon.QR_CERT),
                    new EntryIconConfig("3", Icon.CLOUD),
                    new EntryIconConfig("4", Icon.INFO))),
    RENEWED(
            "renewed",
            List.of(new EntryIconConfig("1", Icon.PDF), new EntryIconConfig("2", Icon.GLOBE)));

    private String poeditorIdentifier;
    private final List<EntryIconConfig> config;

    CertRenewalInfoType(String poeditorIdentifier, List<EntryIconConfig> config) {
        this.poeditorIdentifier = poeditorIdentifier;
        this.config = config;
    }

    public String getPoeditorIdentifier() {
        return poeditorIdentifier;
    }

    public List<EntryIconConfig> getConfig() {
        return config;
    }
}
