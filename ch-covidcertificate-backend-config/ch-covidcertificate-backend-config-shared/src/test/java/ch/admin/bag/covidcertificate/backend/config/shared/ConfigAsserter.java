package ch.admin.bag.covidcertificate.backend.config.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ch.admin.bag.covidcertificate.backend.config.shared.model.ConfigResponse;
import ch.admin.bag.covidcertificate.backend.config.shared.model.Faq;
import ch.admin.bag.covidcertificate.backend.config.shared.model.FaqEntry;
import ch.admin.bag.covidcertificate.backend.config.shared.model.InfoBox;
import ch.admin.bag.covidcertificate.backend.config.shared.model.Language;
import java.util.List;
import java.util.Map;

public class ConfigAsserter {
    public static void assertNoUpdate(ConfigResponse resp) throws Exception {
        assertNull(resp.getInfoBox());
    }

    public static void assertNormalUpdate(ConfigResponse resp, boolean isAndroid) throws Exception {
        assertNotNull(resp);
        assertNotNull(resp.getInfoBox());
        for (Language language : Language.values()) {
            assertNotNull(resp.getInfoBox().get(language));
        }
        InfoBox deInfoBox = resp.getInfoBox().get(Language.DE);
        assertEquals("Update benötigt", deInfoBox.getTitle());
        assertEquals("Laden Sie die neue Version der App.", deInfoBox.getMsg());
        if (isAndroid) {
            assertEquals(
                    "market://details?id=ch.admin.bag.covidcertificate.wallet", deInfoBox.getUrl());
        } else { // iOS
            assertEquals("http://itunes.apple.com/app/id1565917320", deInfoBox.getUrl());
        }
        assertEquals("Aktualisieren", deInfoBox.getUrlTitle());
        assertFalse(deInfoBox.getIsDismissible());
    }

    public static void assertTestflightUpdate(ConfigResponse resp) throws Exception {
        assertNotNull(resp);
        assertNotNull(resp.getInfoBox());
        for (Language language : Language.values()) {
            assertNotNull(resp.getInfoBox().get(language));
        }
        assertEquals("App-Update im App Store", resp.getInfoBox().get(Language.DE).getTitle());
    }

    public static void assertIsNoForceUpdate(ConfigResponse resp) throws Exception {
        assertFalse(resp.isForceUpdate());
    }

    public static void assertIsForceUpdate(ConfigResponse resp) throws Exception {
        assertTrue(resp.isForceUpdate());
    }

    public static void assertInfoBox(ConfigResponse resp) throws Exception {
        for (Language language : Language.values()) {
            assertNotNull(resp.getInfoBox().get(language));
        }
    }

    public static void assertFaq(
            Map<Language, String> expectedFaqTitle,
            int faqEntryCount,
            List<String> expectedFaqEntryTitlesEn,
            List<Boolean> isLinkSetList,
            Map<Language, Faq> faqByLanguage) {
        for (Language language : Language.values()) {
            Faq faq = faqByLanguage.get(language);
            assertEquals(expectedFaqTitle.get(language), faq.getFaqTitle());
            assertNotNull(faq.getFaqSubTitle());
            assertEquals(faqEntryCount, faq.getFaqEntries().size(), "language: " + language.name());

            for (int i = 0; i < faqEntryCount; i++) {
                FaqEntry faqEntry = faq.getFaqEntries().get(i);
                assertNotNull(faqEntry.getTitle());
                assertNotNull(faqEntry.getText());
                assertEquals(isLinkSetList.get(i), faqEntry.getLinkUrl() != null);
                assertEquals(isLinkSetList.get(i), faqEntry.getLinkTitle() != null);

                if (Language.EN.equals(language)) {
                    assertEquals(expectedFaqEntryTitlesEn.get(i), faqEntry.getTitle());
                }
            }
        }
    }
}
