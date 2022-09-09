/*
 * Copyright (c) 2021 Ubique Innovation AG <https://www.ubique.ch>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package ch.admin.bag.covidcertificate.backend.config.verifier.ws.config;

import ch.admin.bag.covidcertificate.backend.config.shared.helper.CheckModeInfoHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.helper.CovidCertNewsHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.helper.FaqHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.poeditor.Messages;
import ch.admin.bag.covidcertificate.backend.config.verifier.ws.controller.VerifierConfigController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public abstract class VerifierWsBaseConfig {

    @Bean
    public VerifierConfigController verifierConfigController(
            Messages messages,
            CheckModeInfoHelper checkModeInfoHelper,
            CovidCertNewsHelper covidCertNewsHelper,
            FaqHelper faqHelper,
            @Value("${ws.verifier.timeshiftDetection.enabled:false}")
                    boolean timeShiftDetectionEnabled,
            @Value("${ws.verifier.checkModeReselectionAfterHours:48}")
                    int checkModeReselectionAfterHours) {
        return new VerifierConfigController(
                messages, checkModeInfoHelper, faqHelper, covidCertNewsHelper, timeShiftDetectionEnabled, checkModeReselectionAfterHours);
    }
}
