package ch.admin.bag.covidcertificate.backend.config.shared.helper;

import ch.admin.bag.covidcertificate.backend.config.shared.model.Faq;
import ch.admin.bag.covidcertificate.backend.config.shared.model.FaqEntry;
import ch.admin.bag.covidcertificate.backend.config.shared.model.InfoBox;
import ch.admin.bag.covidcertificate.backend.config.shared.model.Language;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockHelper {
    public static Map<Language, Faq> getWorksMock() {
        Map<Language, Faq> works = new HashMap<>();
        for (Language language : Language.values()) {
            Faq faq = new Faq();
            faq.setFaqTitle("Wie funktioniert die App?");
            faq.setFaqSubTitle(
                    "Hier steht eine kurze Zusammenfassung. Das ist aber erst mal nur Blindtext. Das muss man dann noch ausformulieren und noch ein bisschen.");
            faq.setFaqIconIos("ic-how-it-works-image");
            faq.setFaqIconAndroid("ic_how_it_works_image");
            faq.setFaqEntries(getWorksFaqEntriesMock());
            works.put(language, faq);
        }
        return works;
    }

    private static List<FaqEntry> getWorksFaqEntriesMock() {
        return List.of(
                getFaqEntryMock("Wie kann ich ein Covid-Zertifikat zur App hinzufügen?"),
                getFaqEntryMock("Können auch mehrere Zertifikate hinzugefügt werden?"),
                getFaqEntryMock("Wie sehe ich, ob mein Zertifikat gültig ist?"),
                getFaqEntryMock("Wie sind meine Daten geschützt?"),
                getFaqEntryMock("Welche Daten sind im QR-Code enthalten?"));
    }

    private static FaqEntry getFaqEntryMock(String title) {
        FaqEntry faqEntry = new FaqEntry();
        faqEntry.setTitle(title);
        faqEntry.setText("Hier steht ein Text");
        faqEntry.setIconAndroid(null);
        faqEntry.setIconIos(null);
        faqEntry.setLinkTitle(null);
        faqEntry.setLinkUrl(null);
        return faqEntry;
    }

    public static Map<Language, Faq> getQuestionsMock() {
        Map<Language, Faq> questions = new HashMap<>();
        for (Language language : Language.values()) {
            Faq faq = new Faq();
            faq.setFaqTitle("Was sind Covid-Zertifikate?");
            faq.setFaqSubTitle(
                    "Das Covid-Zertifikat ist eine Möglichkeit eine Covid-19-Impfung, eine durchgemachte Erkrankung oder ein negatives Testergebnis fälschungssicher zu dokumentieren.");
            faq.setFaqIconIos("ic-faq-image");
            faq.setFaqIconAndroid("ic_faq_image");
            faq.setFaqEntries(getQuestionFaqEntriesMock());
            questions.put(language, faq);
        }
        return questions;
    }

    private static List<FaqEntry> getQuestionFaqEntriesMock() {
        return List.of(
                getFaqEntry0Mock(),
                getFaqEntry1Mock(),
                getFaqEntry2Mock(),
                getFaqEntry3Mock(),
                getFaqEntry4Mock());
    }

    private static FaqEntry getFaqEntry0Mock() {
        FaqEntry faqEntry = new FaqEntry();
        faqEntry.setTitle("Wann und wo kann ich ein Covid-Zertifikat erhalten?");
        faqEntry.setText("Hier steht ein Text");
        faqEntry.setIconAndroid(null);
        faqEntry.setIconIos(null);
        faqEntry.setLinkTitle(null);
        faqEntry.setLinkUrl(null);
        return faqEntry;
    }

    private static FaqEntry getFaqEntry1Mock() {
        FaqEntry faqEntry = new FaqEntry();
        faqEntry.setTitle("Wie kann ich ein Covid-Zertifikat vorweisen?");
        faqEntry.setText("Hier steht ein Text");
        faqEntry.setIconAndroid(null);
        faqEntry.setIconIos(null);
        faqEntry.setLinkTitle(null);
        faqEntry.setLinkUrl(null);
        return faqEntry;
    }

    private static FaqEntry getFaqEntry2Mock() {
        FaqEntry faqEntry = new FaqEntry();
        faqEntry.setTitle("Wo sind meine Daten gespeichert?");
        faqEntry.setText("Hier steht ein Text");
        faqEntry.setIconAndroid(null);
        faqEntry.setIconIos(null);
        faqEntry.setLinkTitle(null);
        faqEntry.setLinkUrl(null);
        return faqEntry;
    }

    private static FaqEntry getFaqEntry3Mock() {
        FaqEntry faqEntry = new FaqEntry();
        faqEntry.setTitle("Wie werden Missbrauch und Fälschung verhindert?");
        faqEntry.setText("Hier steht ein Text");
        faqEntry.setIconAndroid(null);
        faqEntry.setIconIos(null);
        faqEntry.setLinkTitle(null);
        faqEntry.setLinkUrl(null);
        return faqEntry;
    }

    private static FaqEntry getFaqEntry4Mock() {
        FaqEntry faqEntry = new FaqEntry();
        faqEntry.setTitle("Was passiert, wenn ich mein Covid-Zertifikat verliere?");
        faqEntry.setText(
                "Ihr Covid-Zertifikat wird in keinem zentralen System gespeichert. Es befindet sich ausschliesslich in Ihrem Besitz. Bewahren Sie das Covid-Zertifikat auf Papier deshalb sorgfältig auf.");
        faqEntry.setIconAndroid(null);
        faqEntry.setIconIos(null);
        faqEntry.setLinkTitle(null);
        faqEntry.setLinkUrl(null);
        return faqEntry;
    }

    public static Map<Language, InfoBox> getInfoBoxMock() {
        Map<Language, InfoBox> infoBoxes = new HashMap<>();
        for (Language language : Language.values()) {
            InfoBox infoBox = new InfoBox();
            infoBox.setTitle("Noch keinen Covidcode?");
            infoBox.setMsg(
                    "Sie wurden positiv getestet (PCR-Test oder Antigen-Schnelltest) und haben nach 4h noch keinen Covidcode erhalten?\nDann kontaktieren Sie die Infoline Coronavirus:");
            infoBox.setUrl("tel:+41583877780");
            infoBox.setUrlTitle("+41 58 387 77 80");
            infoBox.setIsDismissible(false);
            infoBoxes.put(language, infoBox);
        }
        return infoBoxes;
    }
}
