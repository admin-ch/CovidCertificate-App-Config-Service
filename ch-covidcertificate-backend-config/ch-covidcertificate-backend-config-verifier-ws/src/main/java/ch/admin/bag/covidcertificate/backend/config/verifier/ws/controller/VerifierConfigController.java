/*
 * Copyright (c) 2021 Ubique Innovation AG <https://www.ubique.ch>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package ch.admin.bag.covidcertificate.backend.config.verifier.ws.controller;

import ch.admin.bag.covidcertificate.backend.config.shared.helper.CacheUtil;
import ch.admin.bag.covidcertificate.backend.config.shared.helper.CheckModeInfoHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.helper.FaqHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.model.VerifierConfigResponse;
import ch.admin.bag.covidcertificate.backend.config.shared.poeditor.Messages;
import ch.admin.bag.covidcertificate.backend.config.shared.semver.Version;
import ch.ubique.openapi.docannotations.Documentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/app/verifier/v1")
public class VerifierConfigController {

    protected final Messages messages;
    protected final FaqHelper faqHelper;
    protected final CheckModeInfoHelper checkModeInfoHelper;

    private static final Version FORCE_UPDATE_BELOW_1_2_0 = new Version("1.2.0");

    private static final Logger logger = LoggerFactory.getLogger(VerifierConfigController.class);

    private final boolean timeshiftDetectionEnabled;

    public VerifierConfigController(
            Messages messages,
            CheckModeInfoHelper checkModeInfoHelper,
            FaqHelper faqHelper,
            boolean timeshiftDetectionEnabled) {
        this.messages = messages;
        this.checkModeInfoHelper = checkModeInfoHelper;
        this.faqHelper = faqHelper;
        this.timeshiftDetectionEnabled = timeshiftDetectionEnabled;
    }

    @Documentation(
            description = "Echo endpoint",
            responses = {"200 => Hello from CH Covidcertificate Config Verifier WS"})
    @CrossOrigin(origins = {"https://editor.swagger.io"})
    @GetMapping(value = "")
    public @ResponseBody String hello() {
        logger.info("Hello from CH Covidcertificate Config Verifier WS");
        return "Hello from CH Covidcertificate Config Verifier WS";
    }

    @Documentation(
            description =
                    "Read latest configuration and messages, depending on the version of the phone and the app.",
            responses = {"200 => ConfigResponse structure with dynamic infos and forceupdate"})
    @CrossOrigin(origins = {"https://editor.swagger.io"})
    @GetMapping(value = "/config")
    public @ResponseBody ResponseEntity<VerifierConfigResponse> getConfig(
            @Documentation(description = "Version of the App installed", example = "ios-1.0.7")
                    @RequestParam
                    String appversion,
            @Documentation(description = "Version of the OS", example = "ios13.6") @RequestParam
                    String osversion,
            @Documentation(description = "Build number of the app", example = "ios-200619.2333.175")
                    @RequestParam
                    String buildnr) {
        var configResponse = new VerifierConfigResponse();
        configResponse.setWorks(faqHelper.getVerifierFaqWorks());
        configResponse.setCheckModesInfos(checkModeInfoHelper.getVerifierCheckModesInfos());
        configResponse.setCheckModeReselectAfterHours(48);
        configResponse.setTimeshiftDetectionEnabled(timeshiftDetectionEnabled);

        Version clientAppVersion = new Version(appversion);
        if (clientAppVersion.isSmallerVersionThan(FORCE_UPDATE_BELOW_1_2_0)) {
            configResponse.setForceUpdate(true);
        }

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(CacheUtil.CONFIG_MAX_AGE))
                .body(configResponse);
    }
}
