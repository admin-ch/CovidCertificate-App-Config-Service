package ch.admin.bag.covidcertificate.backend.config.shared.model;

public enum Canton {
    AG("ag"),
    AR("ar"),
    AI("ai"),
    BL("bl"),
    BS("bs"),
    BE("be"),
    FR("fr"),
    GE("ge"),
    GL("gl"),
    GR("gr"),
    JU("ju"),
    LU("lu"),
    NE("ne"),
    NW("nw"),
    OW("ow"),
    SG("sg"),
    SH("sh"),
    SZ("sz"),
    SO("so"),
    TG("tg"),
    TI("ti"),
    UR("ur"),
    VS("vs"),
    VD("vd"),
    ZG("zg"),
    ZH("zh");

    private String id;

    Canton(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getAndroidIconAssetName() {
        return "icon_" + id;
    }

    public String getIosIconAssetName() {
        return "icon-" + id;
    }
}
