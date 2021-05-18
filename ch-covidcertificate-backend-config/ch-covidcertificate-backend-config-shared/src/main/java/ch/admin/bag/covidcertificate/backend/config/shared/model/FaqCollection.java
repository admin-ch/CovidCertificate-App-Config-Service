package ch.admin.bag.covidcertificate.backend.config.shared.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class FaqCollection {

    private Faq de;
    private Faq en;
    private Faq fr;
    private Faq it;

    public Faq getDe() {
        return de;
    }

    public void setDe(Faq de) {
        this.de = de;
    }

    public Faq getEn() {
        return en;
    }

    public void setEn(Faq en) {
        this.en = en;
    }

    public Faq getFr() {
        return fr;
    }

    public void setFr(Faq fr) {
        this.fr = fr;
    }

    public Faq getIt() {
        return it;
    }

    public void setIt(Faq it) {
        this.it = it;
    }
}
