package ch.admin.bag.covidcertificate.backend.config.shared.model;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;

public class VerifierModeInfoEntries {
    @NotNull private final List<VerifierModeInfoEntry> infos = new ArrayList<>();

    public List<VerifierModeInfoEntry> getInfos() {
        return infos;
    }

    public void addInfo(VerifierModeInfoEntry info) {
        this.infos.add(info);
    }
}
