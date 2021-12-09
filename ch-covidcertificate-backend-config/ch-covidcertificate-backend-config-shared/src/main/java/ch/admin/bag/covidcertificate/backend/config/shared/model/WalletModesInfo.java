package ch.admin.bag.covidcertificate.backend.config.shared.model;

import java.util.Map;
import javax.validation.constraints.NotNull;

public class WalletModesInfo {
    @NotNull private String title;
    @NotNull private Map<CheckMode, WalletCheckModeInfo> modes;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<CheckMode, WalletCheckModeInfo> getModes() {
        return modes;
    }

    public void setModes(Map<CheckMode, WalletCheckModeInfo> modes) {
        this.modes = modes;
    }
}
