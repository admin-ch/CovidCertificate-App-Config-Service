package ch.admin.bag.covidcertificate.backend.config.shared.helper;

import ch.admin.bag.covidcertificate.backend.config.shared.model.Faq;
import ch.admin.bag.covidcertificate.backend.config.shared.model.FaqEntry;
import ch.admin.bag.covidcertificate.backend.config.shared.model.FaqIntroSection;
import ch.admin.bag.covidcertificate.backend.config.shared.model.Language;
import ch.admin.bag.covidcertificate.backend.config.shared.poeditor.Messages;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class FaqHelper {

    private final FaqConfig walletQuestionsFaqConfig = new FaqConfig();
    private final FaqConfig walletWorksFaqConfig = new FaqConfig();
    private final FaqConfig walletTransferQuestionsFaqConfig = new FaqConfig();
    private final FaqConfig walletTransferWorksFaqConfig = new FaqConfig();
    private final FaqConfig verifierWorksFaqConfig = new FaqConfig();

    private final Messages msg;

    private static final List<String> WALLET_FAQ_QUESTIONS_ENTRIES =
            List.of("1", "1_1", "2", "2_1", "3", "4", "5", "6");
    private static final List<String> WALLET_FAQ_WORKS_ENTRIES =
            List.of("1", "2", "3", "3_1", "4", "5", "6");
    private static final List<String> VERIFIER_WORKS_FAQ_ENTRIES =
        List.of("1", "2", "2_1", "3", "5", "6", "7", "8");
    private static final List<String> TRANSFER_WORKS_FAQ_ENTRIES =
        List.of("1", "2", "3");
    private static final List<String> TRANSFER_QUESTIONS_FAQ_ENTRIES =
        List.of("1", "2", "3", "4");

    public FaqHelper(Messages msg) {
        this.msg = msg;

        walletQuestionsFaqConfig.setKeyPrefix("wallet_faq_questions_");
        walletQuestionsFaqConfig.setIosIcon("ic-faq-image");
        walletQuestionsFaqConfig.setAndroidIcon("ic_faq_image");
        walletQuestionsFaqConfig.setFaqEntries(WALLET_FAQ_QUESTIONS_ENTRIES);

        walletWorksFaqConfig.setKeyPrefix("wallet_faq_works_");
        walletWorksFaqConfig.setIosIcon("ic-faq-image");
        walletWorksFaqConfig.setAndroidIcon("ic_faq_image");
        walletWorksFaqConfig.setFaqEntries(WALLET_FAQ_WORKS_ENTRIES);

        walletTransferQuestionsFaqConfig.setKeyPrefix("wallet_transfer_code_faq_questions_");
        walletTransferQuestionsFaqConfig.setIosIcon("illu-faq-transfer-code");
        walletTransferQuestionsFaqConfig.setAndroidIcon("illu_faq_transfer_code");
        walletTransferQuestionsFaqConfig.setFaqEntries(TRANSFER_QUESTIONS_FAQ_ENTRIES);

        walletTransferWorksFaqConfig.setKeyPrefix("wallet_transfer_code_faq_works_");
        walletTransferWorksFaqConfig.setIosIcon(null);
        walletTransferWorksFaqConfig.setAndroidIcon(null);
        walletTransferWorksFaqConfig.setFaqEntries(TRANSFER_WORKS_FAQ_ENTRIES);
        walletTransferWorksFaqConfig.setIntroEntries(
                List.of(
                        new FaqIntroEntryConfig("1", "ic-one", "ic_one"),
                        new FaqIntroEntryConfig("2", "ic-two", "ic_two"),
                        new FaqIntroEntryConfig("3", "ic-three", "ic_three")));

        verifierWorksFaqConfig.setKeyPrefix("verifier_faq_works_");
        verifierWorksFaqConfig.setIosIcon("ic-faq-image");
        verifierWorksFaqConfig.setAndroidIcon("ic_faq_image");
        verifierWorksFaqConfig.setFaqEntries(VERIFIER_WORKS_FAQ_ENTRIES);
    }

    public Map<Language, Faq> getVerifierFaqWorks() {
        return getFaq(verifierWorksFaqConfig);
    }

    public Map<Language, Faq> getWalletFaqWorks() {
        return getFaq(walletWorksFaqConfig);
    }

    public Map<Language, Faq> getWalletFaqQuestions() {
        return getFaq(walletQuestionsFaqConfig);
    }

    public Map<Language, Faq> getWalletTransferFaqWorks() {
        return getFaq(walletTransferWorksFaqConfig);
    }

    public Map<Language, Faq> getWalletTransferFaqQuestions() {
        return getFaq(walletTransferQuestionsFaqConfig);
    }

    private Map<Language, Faq> getFaq(FaqConfig faqConfig) {
        Map<Language, Faq> result = new EnumMap<>(Language.class);
        for (Language language : Language.values()) {
            String prefix = faqConfig.getKeyPrefix();
            Locale l = language.toLocale();
            Faq f = new Faq();
            f.setFaqTitle(msg.getMessage(prefix + "title", l));
            if (walletTransferWorksFaqConfig.equals(faqConfig)) {
                f.setFaqIntroSections(getFaqIntroSections(faqConfig, l));
            } else {
                f.setFaqSubTitle(msg.getMessage(prefix + "subtitle", l));
            }
            f.setFaqIconIos(faqConfig.getIosIcon());
            f.setFaqIconAndroid(faqConfig.getAndroidIcon());
            List<FaqEntry> faqEntries = new ArrayList<>();
            for (String entry : faqConfig.getFaqEntries()) {
                FaqEntry e = new FaqEntry();
                e.setTitle(msg.getMessage(prefix + "question_" + entry, l));
                e.setText(msg.getMessage(prefix + "answer_" + entry, l));
                e.setLinkTitle(msg.getNullableMessage(prefix + "linktext_" + entry, l));
                e.setLinkUrl(msg.getNullableMessage(prefix + "linkurl_" + entry, l));
                faqEntries.add(e);
            }
            f.setFaqEntries(faqEntries);
            result.put(language, f);
        }
        return result;
    }

    private List<FaqIntroSection> getFaqIntroSections(FaqConfig faqConfig, Locale l) {
        List<FaqIntroSection> faqIntroSections = new ArrayList<>();

        for (FaqIntroEntryConfig introEntry : faqConfig.getIntroEntries()) {
            FaqIntroSection faqIntroSection = new FaqIntroSection();
            faqIntroSection.setIconIos(introEntry.getIosIcon());
            faqIntroSection.setIconAndroid(introEntry.getAndroidIcon());
            faqIntroSection.setText(
                    msg.getMessage(
                            faqConfig.getKeyPrefix() + "intro_" + introEntry.getIntroEntry(), l));
            faqIntroSections.add(faqIntroSection);
        }
        return faqIntroSections;
    }

    /** internal config classes */
    private class FaqConfig {
        private String keyPrefix;
        private String iosIcon;
        private String androidIcon;
        private List<String> faqEntries;
        private List<FaqIntroEntryConfig> introEntries;

        public String getKeyPrefix() {
            return keyPrefix;
        }

        public void setKeyPrefix(String keyPrefix) {
            this.keyPrefix = keyPrefix;
        }

        public String getIosIcon() {
            return iosIcon;
        }

        public void setIosIcon(String iosIcon) {
            this.iosIcon = iosIcon;
        }

        public String getAndroidIcon() {
            return androidIcon;
        }

        public void setAndroidIcon(String androidIcon) {
            this.androidIcon = androidIcon;
        }

        public List<String> getFaqEntries() {
            return faqEntries;
        }

        public void setFaqEntries(List<String> faqEntries) {
            this.faqEntries = faqEntries;
        }

        public List<FaqIntroEntryConfig> getIntroEntries() {
            return introEntries;
        }

        public void setIntroEntries(List<FaqIntroEntryConfig> introEntries) {
            this.introEntries = introEntries;
        }
    }

    private class FaqIntroEntryConfig {
        private String introEntry;
        private String iosIcon;
        private String androidIcon;

        public FaqIntroEntryConfig(String introEntry, String iosIcon, String androidIcon) {
            this.introEntry = introEntry;
            this.iosIcon = iosIcon;
            this.androidIcon = androidIcon;
        }

        public String getIntroEntry() {
            return introEntry;
        }

        public void setIntroEntry(String introEntry) {
            this.introEntry = introEntry;
        }

        public String getIosIcon() {
            return iosIcon;
        }

        public void setIosIcon(String iosIcon) {
            this.iosIcon = iosIcon;
        }

        public String getAndroidIcon() {
            return androidIcon;
        }

        public void setAndroidIcon(String androidIcon) {
            this.androidIcon = androidIcon;
        }
    }
}
