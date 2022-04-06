package ch.admin.bag.covidcertificate.backend.config.shared.helper;

import ch.admin.bag.covidcertificate.backend.config.shared.model.CheckMode;
import ch.admin.bag.covidcertificate.backend.config.shared.model.EntryIconConfig;
import ch.admin.bag.covidcertificate.backend.config.shared.model.ForeignRulesHint;
import ch.admin.bag.covidcertificate.backend.config.shared.model.Icon;
import ch.admin.bag.covidcertificate.backend.config.shared.model.Language;
import ch.admin.bag.covidcertificate.backend.config.shared.model.VerifierModeInfoEntries;
import ch.admin.bag.covidcertificate.backend.config.shared.model.VerifierModeInfoEntry;
import ch.admin.bag.covidcertificate.backend.config.shared.model.VerifierModeInfos;
import ch.admin.bag.covidcertificate.backend.config.shared.model.VerifierModesInfos;
import ch.admin.bag.covidcertificate.backend.config.shared.model.WalletCheckModeInfo;
import ch.admin.bag.covidcertificate.backend.config.shared.model.WalletModesInfo;
import ch.admin.bag.covidcertificate.backend.config.shared.model.WalletModesInfoEntry;
import ch.admin.bag.covidcertificate.backend.config.shared.poeditor.Messages;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ForeignRulesHintHelper {

    private static final String FOREIGN_RULES_HINTS_PREFIX = "wallet_foreign_rules_check_hint_";

    private static final String[] FOREIGN_RULES_HINT_ICONS = { "ic_timelapse", "ic_travel", "ic_info_outline", "ic_question_outline"};

    private final Messages msg;

    public ForeignRulesHintHelper(Messages msg) {
        this.msg = msg;
    }


    public Map<Language, List<ForeignRulesHint>> getForeignRulesHints() {
        Map<Language, List<ForeignRulesHint>> result = new EnumMap<>(Language.class);
        for (Language language : Language.values()) {
            result.put(language, new ArrayList<>());
            for(int i = 0; i < FOREIGN_RULES_HINT_ICONS.length; i++){
                var hint = new ForeignRulesHint();
                hint.setText(msg.getMessage(String.format("%s%d", FOREIGN_RULES_HINTS_PREFIX, i+1), language.toLocale()));
                hint.setIconAndroid(FOREIGN_RULES_HINT_ICONS[i]);
                hint.setIconIos(FOREIGN_RULES_HINT_ICONS[i].replace("_", "-"));
                result.get(language).add(hint);
            }
        }
        return result;
    }

    public Map<Language, String> getForeignRulesLinkText(){
        Map<Language, String> result = new EnumMap<>(Language.class);
        for (Language language : Language.values()) {
            result.put(language, msg.getMessage("wallet_foreign_rules_check_hints_more_info_link_text", language.toLocale()));
        }
        return result;
    }

    public Map<Language, String> getForeignRulesLinkUrl(){
        Map<Language, String> result = new EnumMap<>(Language.class);
        for (Language language : Language.values()) {
            result.put(language, msg.getMessage("wallet_foreign_rules_check_hints_more_info_link_url", language.toLocale()));
        }
        return result;
    }

}
