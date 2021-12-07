/*
 * Copyright (c) 2020 Ubique Innovation AG <https://www.ubique.ch>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package ch.admin.bag.covidcertificate.backend.config.verifier.ws;

import static ch.admin.bag.covidcertificate.backend.config.shared.TestHelper.SECURITY_HEADERS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ch.admin.bag.covidcertificate.backend.config.shared.ConfigAsserter;
import ch.admin.bag.covidcertificate.backend.config.shared.TestHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.model.ConfigResponse;
import ch.admin.bag.covidcertificate.backend.config.shared.model.Language;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import java.util.Map;
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

    @Autowired protected ObjectMapper objectMapper;
    protected MockMvc mockMvc;
    @Autowired protected WebApplicationContext webApplicationContext;
    protected TestHelper testHelper;
    protected MediaType acceptMediaType;

    protected static final String BASE_URL = "/app/verifier/v1";

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
                "Hello from CH Covidcertificate Config Verifier WS", response.getContentAsString());
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
    public void testForUpdateNote() throws Exception {
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
        ConfigAsserter.assertNoUpdate(resp);
    }

    @Test
    public void testNoForceUpdate() throws Exception {
        MockHttpServletResponse result =
                mockMvc.perform(
                                get(BASE_URL + "/config")
                                        .accept(acceptMediaType)
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
        assertNull(resp.getQuestions());
    }

    private void assertWorks(ConfigResponse resp) {
        Map<Language, String> expectedFaqTitle =
                Map.of(
                        Language.EN,
                        "How COVID certificates are checked",
                        Language.IT,
                        "Come avviene la verifica dei certificati COVID?",
                        Language.DE,
                        "So werden Covid-Zertifikate geprüft",
                        Language.FR,
                        "Contrôle de certificats COVID",
                        Language.RM,
                        "Uschia vegnan verifitgads ils certificats COVID");

        final int faqEntryCount = 9;

        // test english faq entry titles. this also verifies the correct entry order
        List<String> expectedFaqEntryTitlesEn =
                List.of(
                        "How can COVID certificates be checked?",
                        "What exactly is checked?",
                        "What are the current validity criteria in Switzerland?",
                        "Which identification documents are valid? Why do personal details have to be checked?",
                        "What data do I see during the verification process?",
                        "Is any data stored in the COVID Certificate Check app or in a central system during the verification process?",
                        "Is it possible to verify the certificates offline?",
                        "What is the 'certificate light'",
                        "How can a 'certificate light' be converted back into an EU/EFTA-compliant COVID certificate?");
        assertEquals(faqEntryCount, expectedFaqEntryTitlesEn.size());

        // true for those faq entries where a link is set
        List<Boolean> isLinkSetList =
                List.of(true, false, true, false, false, false, false, false, false);
        assertEquals(faqEntryCount, isLinkSetList.size());

        ConfigAsserter.assertFaq(
                expectedFaqTitle,
                faqEntryCount,
                expectedFaqEntryTitlesEn,
                isLinkSetList,
                resp.getWorks());
    }
}
