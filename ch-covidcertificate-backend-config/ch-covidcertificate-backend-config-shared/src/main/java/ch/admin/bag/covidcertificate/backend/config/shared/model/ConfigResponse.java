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

import ch.ubique.openapi.docannotations.Documentation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@Documentation(description = "ConfigResponse description")
public class ConfigResponse {

    @Documentation(
            description =
                    "Blocks the app and shows a link to the app-store. The user can only continue once he updated the app")
    private boolean forceUpdate = false;

    @Documentation(description = "Holds an info box for every language")
    private Map<Language, InfoBox> infoBox = null;

    @Documentation(description = "Holds covid-certificate FAQ parameters by language")
    private Map<Language, Faq> questions = null;

    @Documentation(description = "Holds how-it-works FAQ parameters by language")
    private Map<Language, Faq> works = null;

    @Documentation(description = "transfer check interval for android in ms")
    private long androidTransferCheckIntervalMs;

    @Documentation(description = "transfer check backoff for android in ms")
    private long androidTransferCheckBackoffMs;

    public boolean isForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public Map<Language, InfoBox> getInfoBox() {
        return infoBox;
    }

    public void setInfoBox(Map<Language, InfoBox> infoBox) {
        this.infoBox = infoBox;
    }

    public Map<Language, Faq> getQuestions() {
        return questions;
    }

    public void setQuestions(Map<Language, Faq> questions) {
        this.questions = questions;
    }

    public Map<Language, Faq> getWorks() {
        return works;
    }

    public void setWorks(Map<Language, Faq> works) {
        this.works = works;
    }

    public long getAndroidTransferCheckIntervalMs() {
        return androidTransferCheckIntervalMs;
    }

    public void setAndroidTransferCheckIntervalMs(long androidTransferCheckIntervalMs) {
        this.androidTransferCheckIntervalMs = androidTransferCheckIntervalMs;
    }

    public long getAndroidTransferCheckBackoffMs() {
        return androidTransferCheckBackoffMs;
    }

    public void setAndroidTransferCheckBackoffMs(long androidTransferCheckBackoffMs) {
        this.androidTransferCheckBackoffMs = androidTransferCheckBackoffMs;
    }
}
