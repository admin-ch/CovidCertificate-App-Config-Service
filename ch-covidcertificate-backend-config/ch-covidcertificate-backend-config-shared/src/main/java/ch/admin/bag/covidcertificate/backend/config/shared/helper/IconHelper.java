package ch.admin.bag.covidcertificate.backend.config.shared.helper;

public class IconHelper {
    public static String getAndroidConformIcon(String icon) {
        return icon.replace('-', '_').toLowerCase();
    }
}
