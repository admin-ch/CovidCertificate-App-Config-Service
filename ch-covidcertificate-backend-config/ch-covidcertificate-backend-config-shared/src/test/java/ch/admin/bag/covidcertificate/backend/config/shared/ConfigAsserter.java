package ch.admin.bag.covidcertificate.backend.config.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import ch.admin.bag.covidcertificate.backend.config.shared.model.ConfigResponse;
import ch.admin.bag.covidcertificate.backend.config.shared.model.Faq;
import ch.admin.bag.covidcertificate.backend.config.shared.model.FaqEntry;
import ch.admin.bag.covidcertificate.backend.config.shared.model.Language;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import org.springframework.mock.web.MockHttpServletResponse;

public class ConfigAsserter {
    public final ObjectMapper objectMapper;

    public ConfigAsserter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void assertNoUpdate(MockHttpServletResponse result) throws Exception {
        ConfigResponse resp =
                objectMapper.readValue(
                        result.getContentAsString(Charset.forName("utf-8")), ConfigResponse.class);
        assertNull(resp.getInfoBox());
    }

    public void assertNormalUpdate(MockHttpServletResponse result) throws Exception {
        ConfigResponse resp =
                objectMapper.readValue(
                        result.getContentAsString(Charset.forName("utf-8")), ConfigResponse.class);
        assertNotNull(resp);
        assertNotNull(resp.getInfoBox());
        for (Language language : Language.values()) {
            assertNotNull(resp.getInfoBox().get(language));
        }
        assertEquals("App-Update verfügbar", resp.getInfoBox().get(Language.DE).getTitle());
    }

    public void assertTestflightUpdate(MockHttpServletResponse result) throws Exception {
        ConfigResponse resp =
                objectMapper.readValue(
                        result.getContentAsString(Charset.forName("utf-8")), ConfigResponse.class);
        assertNotNull(resp);
        assertNotNull(resp.getInfoBox());
        for (Language language : Language.values()) {
            assertNotNull(resp.getInfoBox().get(language));
        }
        assertEquals("App-Update im App Store", resp.getInfoBox().get(Language.DE).getTitle());
    }

    public void assertIsNoForceUpdate(MockHttpServletResponse result) throws Exception {
        ConfigResponse resp =
                objectMapper.readValue(
                        result.getContentAsString(Charset.forName("utf-8")), ConfigResponse.class);
        assertFalse(resp.isForceUpdate());
    }

    public void assertInfoBox(MockHttpServletResponse result) throws Exception {
        ConfigResponse resp =
                objectMapper.readValue(
                        result.getContentAsString(Charset.forName("utf-8")), ConfigResponse.class);
        for (Language language : Language.values()) {
            assertNotNull(resp.getInfoBox().get(language));
        }
    }

    public void assertFaq(
            Map<Language, String> expectedFaqTitle,
            int faqEntryCount,
            List<String> expectedFaqEntryTitlesEn,
            List<Boolean> isLinkSetList,
            Map<Language, Faq> faqByLanguage) {
        for (Language language : Language.values()) {
            Faq faq = faqByLanguage.get(language);
            assertEquals(expectedFaqTitle.get(language), faq.getFaqTitle());
            assertNotNull(faq.getFaqSubTitle());
            assertEquals(faqEntryCount, faq.getFaqEntries().size());

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
