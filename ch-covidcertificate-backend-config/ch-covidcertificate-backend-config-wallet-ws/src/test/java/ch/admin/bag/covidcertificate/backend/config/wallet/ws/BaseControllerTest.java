/*
 * Copyright (c) 2020 Ubique Innovation AG <https://www.ubique.ch>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package ch.admin.bag.covidcertificate.backend.config.wallet.ws;

import static ch.admin.bag.covidcertificate.backend.config.shared.TestHelper.SECURITY_HEADERS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ch.admin.bag.covidcertificate.backend.config.shared.ConfigAsserter;
import ch.admin.bag.covidcertificate.backend.config.shared.model.ConfigResponse;
import ch.admin.bag.covidcertificate.backend.config.shared.model.Language;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"cloud-dev"})
public abstract class BaseControllerTest {
    @Autowired protected ObjectMapper objectMapper;
    protected MockMvc mockMvc;
    @Autowired protected WebApplicationContext webApplicationContext;
    protected ConfigAsserter configAsserter;

    private static final String BASE_URL = "/app/wallet/v1";

    protected String json(Object o) throws IOException {
        return objectMapper.writeValueAsString(o);
    }

    @Test
    public void testHello() throws Exception {
        final MockHttpServletResponse response =
                mockMvc.perform(get(BASE_URL + ""))
                        .andExpect(status().is2xxSuccessful())
                        .andReturn()
                        .getResponse();

        assertNotNull(response);
        assertEquals(
                "Hello from CH Covidcertificate Config Wallet WS", response.getContentAsString());
    }

    @Test
    public void testGetConfig() throws Exception {
        mockMvc.perform(get(BASE_URL + "/config")).andExpect(status().is4xxClientError());
        final MockHttpServletResponse result =
                mockMvc.perform(
                                get(BASE_URL + "/config")
                                        .param("osversion", "ios12")
                                        .param("appversion", "1.0")
                                        .param("buildnr", "2020.0145asdfa34"))
                        .andExpect(status().is2xxSuccessful())
                        .andReturn()
                        .getResponse();
    }

    @Test
    public void testSecurityHeaders() throws Exception {
        final MockHttpServletResponse response =
                mockMvc.perform(get(BASE_URL + ""))
                        .andExpect(status().is2xxSuccessful())
                        .andReturn()
                        .getResponse();
        for (final var header : SECURITY_HEADERS.keySet()) {
            assertTrue(response.containsHeader(header));
            assertEquals(SECURITY_HEADERS.get(header), response.getHeader(header));
        }
    }

    @Test
    public void testForUpdateNote() throws Exception {
        MockHttpServletResponse result =
                mockMvc.perform(
                                get(BASE_URL + "/config")
                                        .param("osversion", "ios12")
                                        .param("appversion", "ios-1.0.9")
                                        .param("buildnr", "ios-2020.0145asdfa34"))
                        .andExpect(status().is2xxSuccessful())
                        .andReturn()
                        .getResponse();
        configAsserter.assertNoUpdate(result);
    }

    @Test
    public void testNoForceUpdate() throws Exception {
        MockHttpServletResponse result =
                mockMvc.perform(
                                get(BASE_URL + "/config")
                                        .param("osversion", "ios14.0")
                                        .param("appversion", "ios-1.0.8")
                                        .param("buildnr", "ios-2020.0145asdfa34"))
                        .andExpect(status().is2xxSuccessful())
                        .andReturn()
                        .getResponse();
        configAsserter.assertIsNoForceUpdate(result);
    }

    @Test
    public void testFaqTexts() throws Exception {
        MockHttpServletResponse result =
                mockMvc.perform(
                                get(BASE_URL + "/config")
                                        .param("osversion", "ios12")
                                        .param("appversion", "ios-1.0.9")
                                        .param("buildnr", "ios-2020.0145asdfa34"))
                        .andExpect(status().is2xxSuccessful())
                        .andReturn()
                        .getResponse();

        ConfigResponse resp =
                objectMapper.readValue(
                        result.getContentAsString(Charset.forName("utf-8")), ConfigResponse.class);

        assertQuestions(resp);
        assertWorks(resp);
    }

    private void assertQuestions(ConfigResponse resp) {
        Map<Language, String> expectedFaqTitle =
                Map.of(
                        Language.EN,
                        "What are COVID certificates?",
                        Language.IT,
                        "Cos’è il certificato COVID?",
                        Language.DE,
                        "Was sind Covid-Zertifikate?",
                        Language.FR,
                        "Le certificat COVID en bref");

        final int faqEntryCount = 5;

        // test english faq entry titles. this also verifies the correct entry order
        List<String> expectedFaqEntryTitlesEn =
                List.of(
                        "When and where can I get a COVID certificate?",
                        "How can I present a COVID certificate?",
                        "Where is my data stored?",
                        "How are misuse and forgery prevented?",
                        "What happens if I lose my COVID certificate?");
        assertEquals(faqEntryCount, expectedFaqEntryTitlesEn.size());

        // true for those faq entries where a link is set
        List<Boolean> isLinkSetList = List.of(false, false, false, false, false);
        assertEquals(faqEntryCount, isLinkSetList.size());

        configAsserter.assertFaq(
                expectedFaqTitle,
                faqEntryCount,
                expectedFaqEntryTitlesEn,
                isLinkSetList,
                resp.getQuestions());
    }

    private void assertWorks(ConfigResponse resp) {
        Map<Language, String> expectedFaqTitle =
                Map.of(
                        Language.EN,
                        "How the app works",
                        Language.IT,
                        "Come funziona \nl’app?",
                        Language.DE,
                        "Wie funktioniert \ndie App?",
                        Language.FR,
                        "Comment l'app \nfonctionne-t-elle?");

        final int faqEntryCount = 6;

        // test english faq entry titles. this also verifies the correct entry order
        List<String> expectedFaqEntryTitlesEn =
                List.of(
                        "How can I add a COVID certificate to the app?",
                        "Can several COVID certificates be added?",
                        "How can I see if my COVID certificate is valid?",
                        "How is my data protected?",
                        "What data is contained in the QR code?",
                        "What if I delete the COVID certificate or the app?");
        assertEquals(faqEntryCount, expectedFaqEntryTitlesEn.size());

        // true for those faq entries where a link is set
        List<Boolean> isLinkSetList = List.of(false, false, false, false, false, false);
        assertEquals(faqEntryCount, isLinkSetList.size());

        configAsserter.assertFaq(
                expectedFaqTitle,
                faqEntryCount,
                expectedFaqEntryTitlesEn,
                isLinkSetList,
                resp.getWorks());
    }
}
