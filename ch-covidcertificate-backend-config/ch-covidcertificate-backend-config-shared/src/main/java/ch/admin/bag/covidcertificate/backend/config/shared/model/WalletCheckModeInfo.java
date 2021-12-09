package ch.admin.bag.covidcertificate.backend.config.shared.model;

import javax.validation.constraints.NotNull;

public class WalletCheckModeInfo {
    @NotNull private WalletModesInfoEntry ok;
    @NotNull private WalletModesInfoEntry notOk;

    public WalletModesInfoEntry getOk() {
        return ok;
    }

    public void setOk(WalletModesInfoEntry ok) {
        this.ok = ok;
    }

    public WalletModesInfoEntry getNotOk() {
        return notOk;
    }

    public void setNotOk(WalletModesInfoEntry notOk) {
        this.notOk = notOk;
    }
}
