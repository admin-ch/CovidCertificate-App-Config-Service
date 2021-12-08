package ch.admin.bag.covidcertificate.backend.config.shared.model;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;

public class VerifierModeInfos {
    @NotNull private String title;
    @NotNull private String hexColor;
    @NotNull private final List<VerifierModeInfoEntry> infos = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHexColor() {
        return hexColor;
    }

    public void setHexColor(String hexColor) {
        this.hexColor = hexColor;
    }

    public List<VerifierModeInfoEntry> getInfos() {
        return infos;
    }

    public void addInfo(VerifierModeInfoEntry info) {
        this.infos.add(info);
    }
}
