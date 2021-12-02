package ch.admin.bag.covidcertificate.backend.config.shared.helper;

import ch.admin.bag.covidcertificate.backend.config.shared.model.Canton;
import ch.admin.bag.covidcertificate.backend.config.shared.model.Language;
import ch.admin.bag.covidcertificate.backend.config.shared.model.VaccinationBookingCanton;
import ch.admin.bag.covidcertificate.backend.config.shared.model.VaccinationBookingInfo;
import ch.admin.bag.covidcertificate.backend.config.shared.model.VaccinationHint;
import ch.admin.bag.covidcertificate.backend.config.shared.poeditor.Messages;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class VaccinationHintHelper {

    private static final String VACCINATION_BOOKING_PREFIX = "vaccination_booking_";

    private static final String VACCINATION_BOOKING_INFO_PREFIX = "vaccination_booking_info_";
    private static final String VACCINATION_IMPF_CHECK_PREFIX = "vaccination_impf_check_";

    private static final String VACCINATION_HINT_PREFIX = "vaccination_hint_";
    private static final List<String> VACCINATION_HINT_ENTRIES =
            List.of("1", "2", "3", "4", "5", "6", "7", "8", "9");

    private final Messages msg;

    public VaccinationHintHelper(Messages msg) {
        this.msg = msg;
    }

    public Map<Language, List<VaccinationBookingCanton>> getVaccinationBookingCantons() {
        Map<Language, List<VaccinationBookingCanton>> result = new EnumMap<>(Language.class);
        for (Language language : Language.values()) {
            Locale l = language.toLocale();
            List<VaccinationBookingCanton> cantonInfos = new ArrayList<>();
            for (Canton canton : Canton.values()) {
                VaccinationBookingCanton cantonInfo = new VaccinationBookingCanton();
                cantonInfo.setName(
                        msg.getMessage(VACCINATION_BOOKING_PREFIX + canton.getId() + "_name", l));
                cantonInfo.setIconAndroid(canton.getAndroidIconAssetName());
                cantonInfo.setIconIos(canton.getIosIconAssetName());
                cantonInfo.setLinkUrl(
                        msg.getMessage(VACCINATION_BOOKING_PREFIX + canton.getId() + "_url", l));
                cantonInfos.add(cantonInfo);
            }
            result.put(language, cantonInfos);
        }
        return result;
    }

    public Map<Language, List<VaccinationHint>> getVaccinationHints() {
        Map<Language, List<VaccinationHint>> result = new EnumMap<>(Language.class);
        for (Language language : Language.values()) {
            Locale l = language.toLocale();
            List<VaccinationHint> hints = new ArrayList<>();
            for (String entry : VACCINATION_HINT_ENTRIES) {
                VaccinationHint hint = new VaccinationHint();
                hint.setTitle(msg.getMessage(VACCINATION_HINT_PREFIX + "title_" + entry, l));
                hint.setText(msg.getMessage(VACCINATION_HINT_PREFIX + "text_" + entry, l));
                hints.add(hint);
            }
            result.put(language, hints);
        }
        return result;
    }

    public Map<Language, VaccinationBookingInfo> getVaccinationBookingInfo() {
        Map<Language, VaccinationBookingInfo> result = new EnumMap<>(Language.class);
        for (Language language : Language.values()) {
            Locale l = language.toLocale();
            VaccinationBookingInfo info = new VaccinationBookingInfo();
            info.setTitle(msg.getMessage(VACCINATION_BOOKING_INFO_PREFIX + "title", l));
            info.setText(msg.getMessage(VACCINATION_BOOKING_INFO_PREFIX + "text", l));
            info.setInfo(msg.getMessage(VACCINATION_BOOKING_INFO_PREFIX + "info", l));

            info.setImpfcheckTitle(msg.getMessage(VACCINATION_IMPF_CHECK_PREFIX + "title", l));
            info.setImpfcheckText(msg.getMessage(VACCINATION_IMPF_CHECK_PREFIX + "info_text", l));
            info.setImpfcheckButton(msg.getMessage(VACCINATION_IMPF_CHECK_PREFIX + "action", l));
            info.setImpfcheckUrl(msg.getMessage(VACCINATION_IMPF_CHECK_PREFIX + "url", l));

            result.put(language, info);
        }
        return result;
    }
}
