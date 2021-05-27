package ch.admin.bag.covidcertificate.backend.config.shared.helper;

import ch.admin.bag.covidcertificate.backend.config.shared.model.Faq;
import ch.admin.bag.covidcertificate.backend.config.shared.model.FaqEntry;
import ch.admin.bag.covidcertificate.backend.config.shared.model.Language;
import ch.admin.bag.covidcertificate.backend.config.shared.poeditor.Messages;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class FaqHelper {

    private static final String VERIFIER_KEY_PREFIX_WORKS = "verifier_faq_works_";

    private static final String WALLET_KEY_PREFIX_QUESTIONS = "wallet_faq_questions_";
    private static final String WALLET_KEY_PREFIX_WORKS = "wallet_faq_works_";

    private static final List<String> WALLET_FAQ_QUESTIONS_ENTRIES =
            List.of("1", "2", "3", "4", "5");
    private static final List<String> WALLET_FAQ_WORKS_ENTRIES =
            List.of("1", "2", "3", "4", "5", "6");

    private static final List<String> VERIFIER_WORKS_FAQ_ENTRIES =
            List.of("1", "2", "3", "4", "5", "6");

    private final Messages msg;

    public FaqHelper(Messages msg) {
        this.msg = msg;
    }

    public Map<Language, Faq> getVerifierFaqWorks() {
        return getFaq(VERIFIER_KEY_PREFIX_WORKS, VERIFIER_WORKS_FAQ_ENTRIES);
    }

    public Map<Language, Faq> getWalletFaqWorks() {
        return getFaq(WALLET_KEY_PREFIX_WORKS, WALLET_FAQ_WORKS_ENTRIES);
    }

    public Map<Language, Faq> getWalletFaqQuestions() {
        return getFaq(WALLET_KEY_PREFIX_QUESTIONS, WALLET_FAQ_QUESTIONS_ENTRIES);
    }

    private Map<Language, Faq> getFaq(String prefix, List<String> entries) {
        Map<Language, Faq> result = new HashMap<Language, Faq>();
        for (Language language : Language.values()) {
            Locale l = getLocaleForLanguage(language);
            Faq f = new Faq();
            f.setFaqTitle(msg.getMessage(prefix + "title", l));
            f.setFaqSubTitle(msg.getMessage(prefix + "subtitle", l));
            f.setFaqIconAndroid("ic_faq_image");
            f.setFaqIconIos("ic-faq-image");
            List<FaqEntry> faqEntries = new ArrayList<>();
            for (String entry : entries) {
                FaqEntry e = new FaqEntry();
                e.setTitle(msg.getMessage(prefix + "question_" + entry, l));
                e.setText(msg.getMessage(prefix + "answer_" + entry, l));
                e.setLinkTitle(msg.getNullableMessage(prefix + "linktitle_" + entry, l));
                e.setLinkUrl(msg.getNullableMessage(prefix + "linkurl_" + entry, l));
                faqEntries.add(e);
            }
            f.setFaqEntries(faqEntries);
            result.put(language, f);
        }
        return result;
    }

    private Locale getLocaleForLanguage(Language l) {
        return Locale.forLanguageTag(l.getKey());
    }
}
