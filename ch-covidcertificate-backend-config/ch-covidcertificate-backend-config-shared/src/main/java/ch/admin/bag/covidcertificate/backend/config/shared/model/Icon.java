package ch.admin.bag.covidcertificate.backend.config.shared.model;

public enum Icon {
    ONE("ic-one", "ic_one"),
    TWO("ic-two", "ic_two"),
    THREE("ic-three", "ic_three"),

    SETTINGS("ic-settings", "ic_settings"),

    INFO("ic-expire-i", "ic_expire_i"),
    ALERT("ic-info-alert", "ic_info_alert"),

    THREE_G("ic_3g", "ic_3g"),
    THREE_G_NOK("ic_no3g", "ic_no3g"),
    TWO_G("ic_2g", "ic_2g"),
    TWO_G_NOK("ic_no2g", "ic_no2g"),
    TWO_G_PLUS("ic-2-g-plus", "ic_2g_plus"),
    TWO_G_PLUS_NOK("ic-no-2-g-plus-height", "ic_no_2_g_plus_height"),
    TEST_CERT("ic-t", "ic_t"),

    CERT_LIGHT_YES("ic-qr-certificate-light", "ic_qr_certificate_light"),
    CERT_LIGHT_NO("ic-qr-certificate-light-no", "ic_qr_certificate_light_no"),

    CLOCK_X("ic-timeerror", "ic_timeerror"),
    QR_CERT("ic-qr-certificate", "ic_qr_certificate"),
    CLOUD("ic-online", "ic_online"),
    GLOBE("ic-travel", "ic_travel"),
    PDF("ic-pdf", "ic_pdf");

    private String ios;
    private String android;

    Icon(String ios, String android) {
        this.ios = ios;
        this.android = android;
    }

    public String getIos() {
        return ios;
    }

    public String getAndroid() {
        return android;
    }
}
