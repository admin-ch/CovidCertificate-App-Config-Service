package ch.admin.bag.covidcertificate.backend.config.shared.helper;

import ch.admin.bag.covidcertificate.backend.config.shared.model.CheckMode;
import ch.admin.bag.covidcertificate.backend.config.shared.model.EntryIconConfig;
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
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CheckModeInfoHelper {

    private static final String VERIFIER_CHECK_MODE_INFO_PREFIX = "verifier_check_mode_info_";
    private static final List<EntryIconConfig> VERIFIER_CHECK_MODE_INFO_UNSELECTED_ENTRIES =
            List.of(new EntryIconConfig("1", Icon.INFO), new EntryIconConfig("2", Icon.SETTINGS));

    private static final String WALLET_CHECK_MODE_INFO_PREFIX = "wallet_check_mode_info_";

    private final Messages msg;

    public CheckModeInfoHelper(Messages msg) {
        this.msg = msg;
    }

    public Map<Language, VerifierModesInfos> getVerifierCheckModesInfos() {
        Map<Language, VerifierModesInfos> result = new EnumMap<>(Language.class);
        for (Language language : Language.values()) {
            Locale l = language.toLocale();

            var unselected = new VerifierModeInfoEntries();
            for (EntryIconConfig entry : VERIFIER_CHECK_MODE_INFO_UNSELECTED_ENTRIES) {
                final VerifierModeInfoEntry info = new VerifierModeInfoEntry();
                info.setIconAndroid(entry.getIconAndroid());
                info.setIconIos(entry.getIconIos());
                info.setText(
                        msg.getMessage(
                                VERIFIER_CHECK_MODE_INFO_PREFIX
                                        + "unselected_text_"
                                        + entry.getEntryId(),
                                l));
                unselected.addInfo(info);
            }

            final EnumMap<CheckMode, VerifierModeInfos> modeToInfos =
                    new EnumMap<>(CheckMode.class);
            for (CheckMode checkMode : CheckMode.getVerifierModes()) {
                final VerifierModeInfos modeInfos = new VerifierModeInfos();
                modeToInfos.put(checkMode, modeInfos);
                modeInfos.setTitle(
                        msg.getMessage(
                                VERIFIER_CHECK_MODE_INFO_PREFIX
                                        + checkMode.getPoeditorIdentifier()
                                        + "_title",
                                l));
                modeInfos.setHexColor(checkMode.getColor());

                for (EntryIconConfig entry : checkMode.getVerifierInfoEntries()) {
                    var infoEntry = new VerifierModeInfoEntry();
                    modeInfos.addInfo(infoEntry);
                    infoEntry.setText(
                            msg.getMessage(
                                    VERIFIER_CHECK_MODE_INFO_PREFIX
                                            + checkMode.getPoeditorIdentifier()
                                            + "_text_"
                                            + entry.getEntryId(),
                                    l));
                    infoEntry.setIconIos(entry.getIconIos());
                    infoEntry.setIconAndroid(entry.getIconAndroid());
                }
            }

            result.put(language, new VerifierModesInfos(modeToInfos, unselected));
        }
        return result;
    }

    public Map<Language, WalletModesInfo> getWalletCheckModesInfo() {
        Map<Language, WalletModesInfo> result = new EnumMap<>(Language.class);
        for (Language language : Language.values()) {
            Locale l = language.toLocale();
            final WalletModesInfo walletModesInfo = new WalletModesInfo();
            result.put(language, walletModesInfo);
            walletModesInfo.setTitle(msg.getMessage(WALLET_CHECK_MODE_INFO_PREFIX + "title", l));

            final EnumMap<CheckMode, WalletCheckModeInfo> modes = new EnumMap<>(CheckMode.class);
            walletModesInfo.setModes(modes);
            for (CheckMode checkMode : CheckMode.getWalletModes()) {
                var modeInfo = new WalletCheckModeInfo();
                modes.put(checkMode, modeInfo);

                var ok = new WalletModesInfoEntry();
                ok.setText(
                        msg.getMessage(
                                WALLET_CHECK_MODE_INFO_PREFIX
                                        + checkMode.getPoeditorIdentifier()
                                        + "_ok_text",
                                l));
                ok.setIconIos(checkMode.getIconIos());
                ok.setIconAndroid(checkMode.getIconAndroid());
                modeInfo.setOk(ok);

                var notOk = new WalletModesInfoEntry();
                notOk.setText(
                        msg.getMessage(
                                WALLET_CHECK_MODE_INFO_PREFIX
                                        + checkMode.getPoeditorIdentifier()
                                        + "_not_ok_text",
                                l));
                notOk.setIconIos(checkMode.getNokIconIos());
                notOk.setIconAndroid(checkMode.getNokIconAndroid());
                modeInfo.setNotOk(notOk);
            }
        }
        return result;
    }
}
