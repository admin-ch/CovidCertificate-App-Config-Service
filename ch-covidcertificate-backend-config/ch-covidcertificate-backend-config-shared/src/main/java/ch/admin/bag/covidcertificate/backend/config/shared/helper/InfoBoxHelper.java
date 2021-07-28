package ch.admin.bag.covidcertificate.backend.config.shared.helper;

import ch.admin.bag.covidcertificate.backend.config.shared.model.InfoBox;
import ch.admin.bag.covidcertificate.backend.config.shared.model.Language;
import ch.admin.bag.covidcertificate.backend.config.shared.poeditor.Messages;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

public class InfoBoxHelper {

    private final Messages msg;

    public InfoBoxHelper(Messages msg) {
        this.msg = msg;
    }

    public Map<Language, InfoBox> getUpdateInfoBox(boolean forAndroid) {
        Map<Language, InfoBox> result = new EnumMap<>(Language.class);
        for (Language language : Language.values()) {
            Locale l = language.toLocale();
            InfoBox infoBox = new InfoBox();
            infoBox.setTitle(msg.getMessage("force_update_title", l));
            infoBox.setMsg(msg.getMessage("force_update_text", l));
            if (forAndroid) {
                infoBox.setUrl(msg.getMessage("wallet_android_app_google_play_store_url", l));
            } else { // iOS
                infoBox.setUrl(msg.getMessage("wallet_apple_app_store_url", l));
            }
            infoBox.setUrlTitle(msg.getMessage("force_update_button", l));
            infoBox.setIsDismissible(false);
            result.put(language, infoBox);
        }
        return result;
    }
}
