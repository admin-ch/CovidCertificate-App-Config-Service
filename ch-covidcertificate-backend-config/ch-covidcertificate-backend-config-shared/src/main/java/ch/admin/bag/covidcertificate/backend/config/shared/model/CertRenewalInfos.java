package ch.admin.bag.covidcertificate.backend.config.shared.model;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;

public class CertRenewalInfos {

    @NotNull private String heading;
    @NotNull private final List<CertRenewalInfoEntry> infos = new ArrayList<>();

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public List<CertRenewalInfoEntry> getInfos() {
        return infos;
    }

    public void addInfo(CertRenewalInfoEntry infoEntry) {
        this.infos.add(infoEntry);
    }
}
