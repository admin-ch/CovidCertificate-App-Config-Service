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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ch.admin.bag.covidcertificate.backend.config.shared.ConfigAsserter;
import ch.admin.bag.covidcertificate.backend.config.shared.TestHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.model.Canton;
import ch.admin.bag.covidcertificate.backend.config.shared.model.ConfigResponse;
import ch.admin.bag.covidcertificate.backend.config.shared.model.Language;
import ch.admin.bag.covidcertificate.backend.config.shared.model.VaccinationBookingCanton;
import ch.admin.bag.covidcertificate.backend.config.shared.model.VaccinationBookingInfo;
import ch.admin.bag.covidcertificate.backend.config.shared.model.VaccinationHint;
import ch.admin.bag.covidcertificate.backend.config.shared.model.WalletConfigResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"cloud-dev"})
@TestPropertySource("classpath:application-local.properties")
public abstract class BaseControllerTest {
    @Autowired protected Validator validator;
    @Autowired protected ObjectMapper objectMapper;
    protected MockMvc mockMvc;
    @Autowired protected WebApplicationContext webApplicationContext;
    protected TestHelper testHelper;
    protected MediaType acceptMediaType;

    protected static final String BASE_URL = "/app/wallet/v1";

    protected String json(Object o) throws IOException {
        return objectMapper.writeValueAsString(o);
    }

    @Test
    public void testHello() throws Exception {
        final MockHttpServletResponse response =
                mockMvc.perform(get(BASE_URL).accept(MediaType.TEXT_PLAIN))
                        .andExpect(status().is2xxSuccessful())
                        .andReturn()
                        .getResponse();

        assertNotNull(response);
        assertEquals(
                "Hello from CH Covidcertificate Config Wallet WS", response.getContentAsString());
    }

    @Test
    public void testSecurityHeaders() throws Exception {
        final MockHttpServletResponse response =
                mockMvc.perform(get(BASE_URL).accept(MediaType.TEXT_PLAIN))
                        .andExpect(status().is2xxSuccessful())
                        .andReturn()
                        .getResponse();
        for (final var header : SECURITY_HEADERS.keySet()) {
            assertTrue(response.containsHeader(header));
            assertEquals(SECURITY_HEADERS.get(header), response.getHeader(header));
        }
    }

    @Test
    public void testGetConfig() throws Exception {
        mockMvc.perform(get(BASE_URL + "/config").accept(acceptMediaType))
                .andExpect(status().is4xxClientError());
        mockMvc.perform(
                        get(BASE_URL + "/config")
                                .accept(acceptMediaType)
                                .param("osversion", "ios12")
                                .param("appversion", "1.0")
                                .param("buildnr", "2020.0145asdfa34"))
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse();
    }

    @Test
    public void testRefreshButtonDisabled() throws Exception {
        MockHttpServletResponse result =
                mockMvc.perform(
                                get(BASE_URL + "/config")
                                        .accept(acceptMediaType)
                                        .param("osversion", "ios12")
                                        .param("appversion", "ios-2.7.0")
                                        .param("buildnr", "ios-2020.0145asdfa34"))
                        .andExpect(status().is2xxSuccessful())
                        .andReturn()
                        .getResponse();
        WalletConfigResponse resp =
                testHelper.toWalletConfigResponse(
                        result, acceptMediaType, TestHelper.PATH_TO_CA_PEM);
        assertFalse(resp.getRefreshButtonDisabled());
    }

    @Test
    public void testForUpdateNote() throws Exception {
        MockHttpServletResponse result;
        ConfigResponse resp;
        // no update info box
        /*result =
                mockMvc.perform(
                                get(BASE_URL + "/config")
                                        .accept(acceptMediaType)
                                        .param("osversion", "ios12")
                                        .param("appversion", "ios-2.7.0")
                                        .param("buildnr", "ios-2020.0145asdfa34"))
                        .andExpect(status().is2xxSuccessful())
                        .andReturn()
                        .getResponse();
        resp =
                testHelper.toConfigResponse(result, acceptMediaType, TestHelper.PATH_TO_CA_PEM);
        ConfigAsserter.assertNoUpdate(resp);*/

        // info box for everyone
        result =
                mockMvc.perform(
                                get(BASE_URL + "/config")
                                        .accept(acceptMediaType)
                                        .param("osversion", "android9")
                                        .param("appversion", "android-3.5.0")
                                        .param("buildnr", "1622464850983"))
                        .andExpect(status().is2xxSuccessful())
                        .andReturn()
                        .getResponse();
        resp = testHelper.toConfigResponse(result, acceptMediaType, TestHelper.PATH_TO_CA_PEM);
        ConfigAsserter.assertInfoBox(resp);
    }

    @Test
    public void testFeatureFlagsSet() throws Exception {
        MockHttpServletResponse result =
                mockMvc.perform(
                                get(BASE_URL + "/config")
                                        .accept(acceptMediaType)
                                        .header("accept", "application/json")
                                        .param("osversion", "android9")
                                        .param("appversion", "android-2.2.0")
                                        .param("buildnr", "1622464850983"))
                        .andExpect(status().is2xxSuccessful())
                        .andReturn()
                        .getResponse();
        WalletConfigResponse resp =
                testHelper.toWalletConfigResponse(
                        result, acceptMediaType, TestHelper.PATH_TO_CA_PEM);
        assertTrue(resp.getLightCertificateActive());
        assertTrue(resp.getPdfGenerationActive());
    }

    @Test
    public void testPdfFeatureDisabled() throws Exception {
        MockHttpServletResponse result =
                mockMvc.perform(
                                get(BASE_URL + "/config")
                                        .accept(acceptMediaType)
                                        .header("accept", "application/json")
                                        .param("osversion", "android9")
                                        .param("appversion", "android-2.1.0")
                                        .param("buildnr", "1622464850983"))
                        .andExpect(status().is2xxSuccessful())
                        .andReturn()
                        .getResponse();
        WalletConfigResponse resp =
                testHelper.toWalletConfigResponse(
                        result, acceptMediaType, TestHelper.PATH_TO_CA_PEM);
        assertTrue(resp.getLightCertificateActive());
        assertFalse(resp.getPdfGenerationActive());
    }

    @Test
    public void testNoForceUpdate() throws Exception {
        MockHttpServletResponse result =
                mockMvc.perform(
                                get(BASE_URL + "/config")
                                        .accept(acceptMediaType)
                                        .header("accept", "application/json")
                                        .param("osversion", "ios14.0")
                                        .param("appversion", "ios-1.2.0")
                                        .param("buildnr", "ios-2020.0145asdfa34"))
                        .andExpect(status().is2xxSuccessful())
                        .andReturn()
                        .getResponse();
        ConfigResponse resp =
                testHelper.toConfigResponse(result, acceptMediaType, TestHelper.PATH_TO_CA_PEM);
        ConfigAsserter.assertIsNoForceUpdate(resp);
    }

    @Test
    public void testForceUpdate() throws Exception {
        MockHttpServletResponse result =
                mockMvc.perform(
                                get(BASE_URL + "/config")
                                        .accept(acceptMediaType)
                                        .header("accept", "application/json")
                                        .param("osversion", "ios14.0")
                                        .param("appversion", "ios-1.1.0")
                                        .param("buildnr", "ios-2020.0145asdfa34"))
                        .andExpect(status().is2xxSuccessful())
                        .andReturn()
                        .getResponse();
        ConfigResponse resp =
                testHelper.toConfigResponse(result, acceptMediaType, TestHelper.PATH_TO_CA_PEM);
        ConfigAsserter.assertIsForceUpdate(resp);
    }

    @Test
    public void testFaqTexts() throws Exception {
        MockHttpServletResponse result =
                mockMvc.perform(
                                get(BASE_URL + "/config")
                                        .accept(acceptMediaType)
                                        .param("osversion", "ios12")
                                        .param("appversion", "ios-1.0.9")
                                        .param("buildnr", "ios-2020.0145asdfa34"))
                        .andExpect(status().is2xxSuccessful())
                        .andReturn()
                        .getResponse();

        ConfigResponse resp =
                testHelper.toConfigResponse(result, acceptMediaType, TestHelper.PATH_TO_CA_PEM);

        assertQuestions(resp);
        assertWorks(resp);
    }

    private void assertQuestions(ConfigResponse resp) {
        Map<Language, String> expectedFaqTitle =
                Map.of(
                        Language.EN,
                        "What are COVID certificates?",
                        Language.IT,
                        "Cos'è il certificato COVID?",
                        Language.DE,
                        "Was sind Covid-Zertifikate?",
                        Language.FR,
                        "Le certificat COVID en bref",
                        Language.RM,
                        "Tge è il certificat COVID?");

        final int faqEntryCount = 8;

        // test english faq entry titles. this also verifies the correct entry order
        List<String> expectedFaqEntryTitlesEn =
                List.of(
                        "When and where can I get a COVID certificate?",
                        "How do I get a COVID certificate after I have recovered from COVID-19?",
                        "How can I present a COVID certificate?",
                        "How long is the COVID certificate valid?",
                        "Where is my data stored?",
                        "How are misuse and forgery prevented?",
                        "What happens if I lose my COVID certificate?",
                        "What is the 'certificate light'");
        assertEquals(faqEntryCount, expectedFaqEntryTitlesEn.size());

        // true for those faq entries where a link is set
        List<Boolean> isLinkSetList =
                List.of(false, false, false, true, false, false, false, false);
        assertEquals(faqEntryCount, isLinkSetList.size());

        ConfigAsserter.assertFaq(
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
                        "Come funziona \nl'app?",
                        Language.DE,
                        "Wie funktioniert \ndie App?",
                        Language.FR,
                        "Comment l'app \nfonctionne-t-elle?",
                        Language.RM,
                        "Co funcziuna \nl'app?");

        final int faqEntryCount = 9;

        // test english faq entry titles. this also verifies the correct entry order
        List<String> expectedFaqEntryTitlesEn =
                List.of(
                        "How can I add a COVID certificate to the app?",
                        "Can several COVID certificates be added?",
                        "What is a transfer code?",
                        "How can I see if my COVID certificate is valid?",
                        "Can I use the app offline?",
                        "How is my data protected?",
                        "What data is contained in the QR code?",
                        "I only have my COVID certificate electronically in the app. How can I get the certificate as a PDF or on paper?",
                        "What do I need to do if I delete the COVID certificate or the app?");
        assertEquals(faqEntryCount, expectedFaqEntryTitlesEn.size());

        // true for those faq entries where a link is set
        List<Boolean> isLinkSetList =
                List.of(false, false, false, false, false, false, false, false, false);
        assertEquals(faqEntryCount, isLinkSetList.size());

        ConfigAsserter.assertFaq(
                expectedFaqTitle,
                faqEntryCount,
                expectedFaqEntryTitlesEn,
                isLinkSetList,
                resp.getWorks());
    }

    @Test
    public void testVaccinationHints() throws Exception {
        MockHttpServletResponse result =
                mockMvc.perform(
                                get(BASE_URL + "/config")
                                        .accept(acceptMediaType)
                                        .header("accept", "application/json")
                                        .param("osversion", "ios14.0")
                                        .param("appversion", "ios-1.1.0")
                                        .param("buildnr", "ios-2020.0145asdfa34"))
                        .andExpect(status().is2xxSuccessful())
                        .andReturn()
                        .getResponse();
        WalletConfigResponse resp =
                testHelper.toWalletConfigResponse(
                        result, acceptMediaType, TestHelper.PATH_TO_CA_PEM);

        assertFalse(resp.getShowVaccinationHintDetail());
        assertTrue(resp.getShowVaccinationHintHomescreen());
        assertFalse(resp.getShowVaccinationHintTransfer());

        validateVaccinationBookingCantons(resp);
        validateVaccinationBookingInfo(resp);
        validateVaccionationHints(resp);
    }

    private void validateVaccionationHints(WalletConfigResponse resp) {
        Map<Language, List<VaccinationHint>> byLanguage = resp.getVaccinationHints();
        assertNotNull(byLanguage);
        assertEquals(Language.values().length, byLanguage.size());
        for (Entry<Language, List<VaccinationHint>> forLanguage : byLanguage.entrySet()) {
            List<VaccinationHint> hints = forLanguage.getValue();
            assertEquals(9, hints.size());
            for (VaccinationHint hint : hints) {
                assertTrue(validator.validate(hint).isEmpty());
            }
        }
    }

    private void validateVaccinationBookingInfo(WalletConfigResponse resp) {
        Map<Language, VaccinationBookingInfo> byLanguage = resp.getVaccinationBookingInfo();
        assertNotNull(byLanguage);
        assertEquals(Language.values().length, byLanguage.size());
        for (Entry<Language, VaccinationBookingInfo> forLanguage : byLanguage.entrySet()) {
            VaccinationBookingInfo info = forLanguage.getValue();
            assertTrue(validator.validate(info).isEmpty());
        }
    }

    private void validateVaccinationBookingCantons(WalletConfigResponse resp) {
        Map<Language, List<VaccinationBookingCanton>> byLanguage =
                resp.getVaccinationBookingCantons();
        assertNotNull(byLanguage);
        assertEquals(Language.values().length, byLanguage.size());
        for (Entry<Language, List<VaccinationBookingCanton>> forLanguage : byLanguage.entrySet()) {
            List<VaccinationBookingCanton> vaccinationBookingCantons = forLanguage.getValue();
            assertEquals(Canton.values().length, vaccinationBookingCantons.size());
            for (VaccinationBookingCanton vaccinationBookingCanton : vaccinationBookingCantons) {
                assertTrue(validator.validate(vaccinationBookingCanton).isEmpty());
            }
        }
    }
}
