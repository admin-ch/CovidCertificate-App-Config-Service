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

    private static final String FOREIGN_RULES_HINTS_PREFIX = "foreign_rules_hints_";

    private final Messages msg;

    public ForeignRulesHintHelper(Messages msg) {
        this.msg = msg;
    }


    public Map<Language, List<ForeignRulesHint>> getForeignRulesHints() {
        Map<Language, List<ForeignRulesHint>> result = new EnumMap<>(Language.class);
        for (Language language : Language.values()) {
            result.put(language, new ArrayList<>());
            var hint = new ForeignRulesHint();
            hint.setText("text here");
            hint.setIconAndroid("android-icon");
            hint.setIconIos("ios-icon");
            result.get(language).add(hint);
        }
        return result;
    }

    public Map<Language, String> getForeignRulesLinkText(){
        Map<Language, String> result = new EnumMap<>(Language.class);
        for (Language language : Language.values()) {
            result.put(language, "Blubb");
        }
        return result;
    }

    public Map<Language, String> getForeignRulesLinkUrl(){
        Map<Language, String> result = new EnumMap<>(Language.class);
        for (Language language : Language.values()) {
            result.put(language, "https://bag.admin.ch");
        }
        return result;
    }

}
