package ch.admin.bag.covidcertificate.backend.config.shared.model;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;

public class CertRenewalInfos {

    @NotNull private String heading;
    @NotNull private final List<CertRenewalInfoEntry> infos = new ArrayList<>();
    @NotNull private String faqLinkText;
    @NotNull private String faqLinkUrl;

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

    public String getFaqLinkText() {
        return faqLinkText;
    }

    public void setFaqLinkText(String faqLinkText) {
        this.faqLinkText = faqLinkText;
    }

    public String getFaqLinkUrl() {
        return faqLinkUrl;
    }

    public void setFaqLinkUrl(String faqLinkUrl) {
        this.faqLinkUrl = faqLinkUrl;
    }
}
