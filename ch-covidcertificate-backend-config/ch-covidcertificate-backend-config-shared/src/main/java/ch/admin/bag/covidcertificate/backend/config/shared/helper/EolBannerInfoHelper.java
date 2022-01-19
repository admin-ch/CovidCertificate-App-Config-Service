package ch.admin.bag.covidcertificate.backend.config.shared.helper;

import ch.admin.bag.covidcertificate.backend.config.shared.model.EolBannerInfo;
import ch.admin.bag.covidcertificate.backend.config.shared.model.EolCase;
import ch.admin.bag.covidcertificate.backend.config.shared.model.Language;
import ch.admin.bag.covidcertificate.backend.config.shared.poeditor.Messages;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class EolBannerInfoHelper {

    private static final String WALLET_EOL_BANNER_INFO_PREFIX = "wallet_eol_banner_";

    private final Messages msg;

    public EolBannerInfoHelper(Messages msg) {
        this.msg = msg;
    }

    public Map<Language, Map<String, EolBannerInfo>> getInfo() {
        Map<Language, Map<String, EolBannerInfo>> result = new EnumMap<>(Language.class);
        for (Language language : Language.values()) {
            Locale l = language.toLocale();
            Map<String, EolBannerInfo> infos = new HashMap<>();

            for (EolCase eolCase : EolCase.values()) {
                final EolBannerInfo info = new EolBannerInfo();
                final String poeditorId = eolCase.name().toLowerCase();
                info.setHomescreenHexColor(eolCase.getHomescreenHexColor());
                info.setHomescreenTitle(
                        msg.getMessage(
                                WALLET_EOL_BANNER_INFO_PREFIX + poeditorId + "_homescreen_title",
                                l));
                info.setDetailHexColor(eolCase.getDetailHexColor());
                info.setDetailTitle(
                        msg.getMessage(
                                WALLET_EOL_BANNER_INFO_PREFIX + poeditorId + "_detail_title", l));
                info.setDetailText(
                        msg.getMessage(
                                WALLET_EOL_BANNER_INFO_PREFIX + poeditorId + "_detail_text", l));
                info.setDetailMoreInfo(
                        msg.getMessage(
                                WALLET_EOL_BANNER_INFO_PREFIX + poeditorId + "_detail_more_info",
                                l));
                info.setPopupTitle(
                        msg.getMessage(
                                WALLET_EOL_BANNER_INFO_PREFIX + poeditorId + "_popup_title", l));
                info.setPopupText1(
                        msg.getNullableMessage(
                                WALLET_EOL_BANNER_INFO_PREFIX + poeditorId + "_popup_text1", l));
                info.setPopupBoldText(
                        msg.getNullableMessage(
                                WALLET_EOL_BANNER_INFO_PREFIX + poeditorId + "_popup_bold_text",
                                l));
                info.setPopupText2(
                        msg.getNullableMessage(
                                WALLET_EOL_BANNER_INFO_PREFIX + poeditorId + "_popup_text2", l));
                info.setPopupLinkText(
                        msg.getNullableMessage(
                                WALLET_EOL_BANNER_INFO_PREFIX + poeditorId + "_popup_link_text",
                                l));
                info.setPopupLinkUrl(
                        msg.getNullableMessage(
                                WALLET_EOL_BANNER_INFO_PREFIX + poeditorId + "_popup_link_url", l));
                infos.put(eolCase.getId(), info);
            }
            result.put(language, infos);
        }
        return result;
    }
}
