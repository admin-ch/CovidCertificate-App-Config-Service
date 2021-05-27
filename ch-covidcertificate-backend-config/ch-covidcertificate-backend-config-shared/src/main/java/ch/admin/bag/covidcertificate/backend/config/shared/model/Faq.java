/*
 * Copyright (c) 2021 Ubique Innovation AG <https://www.ubique.ch>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package ch.admin.bag.covidcertificate.backend.config.shared.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Faq {

    private String faqTitle;
    private String faqSubTitle;
    private String faqIconIos;
    private String faqIconAndroid;

    private List<FaqEntry> faqEntries;

    public String getFaqTitle() {
        return faqTitle;
    }

    public void setFaqTitle(String faqTitle) {
        this.faqTitle = faqTitle;
    }

    public String getFaqSubTitle() {
        return faqSubTitle;
    }

    public void setFaqSubTitle(String faqSubTitle) {
        this.faqSubTitle = faqSubTitle;
    }

    public String getFaqIconIos() {
        return faqIconIos;
    }

    public void setFaqIconIos(String faqIconIos) {
        this.faqIconIos = faqIconIos;
    }

    public String getFaqIconAndroid() {
        return faqIconAndroid;
    }

    public void setFaqIconAndroid(String faqIconAndroid) {
        this.faqIconAndroid = faqIconAndroid;
    }

    public List<FaqEntry> getFaqEntries() {
        return faqEntries;
    }

    public void setFaqEntries(List<FaqEntry> faqEntries) {
        this.faqEntries = faqEntries;
    }
}
