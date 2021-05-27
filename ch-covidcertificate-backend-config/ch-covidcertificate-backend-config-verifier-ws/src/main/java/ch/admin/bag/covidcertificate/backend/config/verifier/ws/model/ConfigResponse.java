/*
 * Copyright (c) 2021 Ubique Innovation AG <https://www.ubique.ch>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package ch.admin.bag.covidcertificate.backend.config.verifier.ws.model;

import ch.ubique.openapi.docannotations.Documentation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Documentation(description = "ConfigResponse description")
public class ConfigResponse {

    @Documentation(
            description =
                    "Blocks the app and shows a link to the app-store. The user can only continue once "
                            + "she updated the app")
    private boolean forceUpdate = false;

    @Documentation(description = "Holds a message translated in different languages")
    private InfoBoxCollection infoBox = null;

    @Documentation(description = "Holds covid-certificate FAQ parameters")
    private FaqCollection questions = null;

    @Documentation(description = "Holds how-it-works FAQ parameters")
    private FaqCollection works = null;

    @Documentation(
            description =
                    "list of ISO 3166-1 alpha-2 country codes describing the available interops countries")
    private List<String> interOpsCountries = new ArrayList<>();

    public boolean isForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public InfoBoxCollection getInfoBox() {
        return infoBox;
    }

    public void setInfoBox(InfoBoxCollection infoBox) {
        this.infoBox = infoBox;
    }

    public List<String> getInterOpsCountries() {
        return interOpsCountries;
    }

    public void setInterOpsCountries(List<String> interOpsCountries) {
        this.interOpsCountries = interOpsCountries;
    }

    public FaqCollection getQuestions() {
        return questions;
    }

    public void setQuestions(FaqCollection questions) {
        this.questions = questions;
    }

    public FaqCollection getWorks() {
        return works;
    }

    public void setWorks(FaqCollection works) {
        this.works = works;
    }
}
