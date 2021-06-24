/*
 * Copyright (c) 2021 Ubique Innovation AG <https://www.ubique.ch>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package ch.admin.bag.covidcertificate.backend.config.wallet.ws.controller;

import ch.admin.bag.covidcertificate.backend.config.shared.helper.CacheUtil;
import ch.admin.bag.covidcertificate.backend.config.shared.helper.FaqHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.poeditor.Messages;
import ch.admin.bag.covidcertificate.backend.config.shared.semver.Version;
import ch.admin.bag.covidcertificate.backend.config.wallet.ws.model.WalletConfigResponse;
import ch.ubique.openapi.docannotations.Documentation;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/app/wallet/v1")
public class WalletConfigController {

    protected final Messages messages;
    protected final FaqHelper faqHelper;

    private static final Version FORCE_UPDATE_BELOW_1_2_0 = new Version("1.2.0");

    private static final long ANDROID_TRANSFER_CHECK_INTERVAL_MS = 2 * 60 * 60 * 1000l;
    private static final long ANDROID_TRANSFER_CHECK_BACKOFF_MS = 30 * 1000l;

    public WalletConfigController(Messages messages, FaqHelper faqHelper) {
        this.messages = messages;
        this.faqHelper = faqHelper;
    }

    @Documentation(
            description = "Echo endpoint",
            responses = {"200 => Hello from CH Covidcertificate Config Wallet WS"})
    @CrossOrigin(origins = {"https://editor.swagger.io"})
    @GetMapping(value = "")
    public @ResponseBody String hello() {
        return "Hello from CH Covidcertificate Config Wallet WS";
    }

    @Documentation(
            description =
                    "Read latest configuration and messages, depending on the version of the phone and the app.",
            responses = {"200 => ConfigResponse structure with dynamic infos and forceupdate"})
    @CrossOrigin(origins = {"https://editor.swagger.io"})
    @GetMapping(value = "/config")
    public @ResponseBody ResponseEntity<WalletConfigResponse> getConfig(
            @Documentation(description = "Version of the App installed", example = "ios-1.0.7")
                    @RequestParam
                    String appversion,
            @Documentation(description = "Version of the OS", example = "ios13.6") @RequestParam
                    String osversion,
            @Documentation(description = "Build number of the app", example = "ios-200619.2333.175")
                    @RequestParam
                    String buildnr) {
        WalletConfigResponse configResponse = new WalletConfigResponse();
        configResponse.setQuestions(faqHelper.getWalletFaqQuestions());
        configResponse.setWorks(faqHelper.getWalletFaqWorks());
        configResponse.setTransferQuestions(faqHelper.getWalletTransferFaqQuestions());
        configResponse.setTransferWorks(faqHelper.getWalletTransferFaqWorks());
        configResponse.setAndroidTransferCheckBackoffMs(ANDROID_TRANSFER_CHECK_BACKOFF_MS);
        configResponse.setAndroidTransferCheckIntervalMs(ANDROID_TRANSFER_CHECK_INTERVAL_MS);

        Version clientAppVersion = new Version(appversion);
        if (clientAppVersion.isSmallerVersionThan(FORCE_UPDATE_BELOW_1_2_0) && clientAppVersion
            .isIOS()) {
            configResponse.setForceUpdate(true);
        }

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(CacheUtil.CONFIG_MAX_AGE))
                .body(configResponse);
    }
}
