package ch.admin.bag.covidcertificate.backend.config.shared.helper;

import ch.admin.bag.covidcertificate.backend.config.shared.model.Language;
import ch.admin.bag.covidcertificate.backend.config.shared.model.RefreshButtonInfo;
import ch.admin.bag.covidcertificate.backend.config.shared.poeditor.Messages;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

public class RefreshButtonInfoHelper {

    private static final String WALLET_REFRESH_BUTTON_INFO_PREFIX = "wallet_refresh_button_info_";

    private final Messages msg;

    public RefreshButtonInfoHelper(Messages msg) {
        this.msg = msg;
    }

    public Map<Language, RefreshButtonInfo> getInfo() {
        Map<Language, RefreshButtonInfo> result = new EnumMap<>(Language.class);
        for (Language language : Language.values()) {
            Locale l = language.toLocale();
            final RefreshButtonInfo info = new RefreshButtonInfo();
            info.setTitle(msg.getMessage(WALLET_REFRESH_BUTTON_INFO_PREFIX + "title", l));
            info.setText1(msg.getMessage(WALLET_REFRESH_BUTTON_INFO_PREFIX + "text_1", l));
            info.setText2(msg.getMessage(WALLET_REFRESH_BUTTON_INFO_PREFIX + "text_2", l));
            info.setFatTitle(msg.getMessage(WALLET_REFRESH_BUTTON_INFO_PREFIX + "fat_title_3", l));
            info.setText3(msg.getMessage(WALLET_REFRESH_BUTTON_INFO_PREFIX + "text_3", l));
            info.setLinkText(msg.getMessage(WALLET_REFRESH_BUTTON_INFO_PREFIX + "link_text", l));
            info.setLinkUrl(msg.getMessage(WALLET_REFRESH_BUTTON_INFO_PREFIX + "link_url", l));

            result.put(language, info);
        }
        return result;
    }
}
