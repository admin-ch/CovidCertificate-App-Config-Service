package ch.admin.bag.covidcertificate.backend.config.shared.helper;

import ch.admin.bag.covidcertificate.backend.config.shared.model.CertRenewalInfoEntry;
import ch.admin.bag.covidcertificate.backend.config.shared.model.CertRenewalInfoType;
import ch.admin.bag.covidcertificate.backend.config.shared.model.CertRenewalInfos;
import ch.admin.bag.covidcertificate.backend.config.shared.model.EntryIconConfig;
import ch.admin.bag.covidcertificate.backend.config.shared.model.Language;
import ch.admin.bag.covidcertificate.backend.config.shared.poeditor.Messages;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

public class CertRenewalInfoHelper {

    private static final String CERT_RENEWAL_INFO_PREFIX = "cert_renewal_info";

    private final Messages msg;

    public CertRenewalInfoHelper(Messages msg) {
        this.msg = msg;
    }

    public Map<Language, Map<CertRenewalInfoType, CertRenewalInfos>> getInfo() {
        final Map<Language, Map<CertRenewalInfoType, CertRenewalInfos>> result =
                new EnumMap<>(Language.class);

        for (Language language : Language.values()) {
            Locale l = language.toLocale();
            final Map<CertRenewalInfoType, CertRenewalInfos> infosByType =
                    new EnumMap<>(CertRenewalInfoType.class);

            for (CertRenewalInfoType type : CertRenewalInfoType.values()) {
                final CertRenewalInfos infos = new CertRenewalInfos();
                infos.setHeading(
                        msg.getMessage(
                                String.join(
                                        "_",
                                        CERT_RENEWAL_INFO_PREFIX,
                                        type.getPoeditorIdentifier(),
                                        "heading"),
                                l));
                infos.setFaqLinkText(msg.getMessage("wallet_certificate_renewal_faq_link_text", l));
                infos.setFaqLinkUrl(msg.getMessage("wallet_certificate_renewal_faq_link_url", l));

                for (EntryIconConfig entryIconConfig : type.getConfig()) {
                    final CertRenewalInfoEntry info = new CertRenewalInfoEntry();
                    info.setIconAndroid(entryIconConfig.getIconAndroid());
                    info.setIconIos(entryIconConfig.getIconIos());
                    info.setText(
                            msg.getMessage(
                                    String.join(
                                            "_",
                                            CERT_RENEWAL_INFO_PREFIX,
                                            type.getPoeditorIdentifier(),
                                            "text",
                                            entryIconConfig.getEntryId()),
                                    l));

                    infos.addInfo(info);
                }

                infosByType.put(type, infos);
            }

            result.put(language, infosByType);
        }
        return result;
    }
}
