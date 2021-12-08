package ch.admin.bag.covidcertificate.backend.config.shared.model;

import java.util.Map;
import javax.validation.constraints.NotNull;

public class VerifierModesInfos {
    @NotNull private final Map<CheckMode, VerifierModeInfos> infos;
    @NotNull private final VerifierModeInfoEntries unselected;

    public VerifierModesInfos(
            Map<CheckMode, VerifierModeInfos> infos, VerifierModeInfoEntries unselected) {
        this.infos = infos;
        this.unselected = unselected;
    }

    public Map<CheckMode, VerifierModeInfos> getInfos() {
        return infos;
    }

    public VerifierModeInfoEntries getUnselected() {
        return unselected;
    }
}
