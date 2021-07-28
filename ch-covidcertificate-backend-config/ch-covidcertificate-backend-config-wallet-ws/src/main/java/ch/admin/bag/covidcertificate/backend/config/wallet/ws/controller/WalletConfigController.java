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
import ch.admin.bag.covidcertificate.backend.config.shared.helper.InfoBoxHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.model.WalletConfigResponse;
import ch.admin.bag.covidcertificate.backend.config.shared.poeditor.Messages;
import ch.admin.bag.covidcertificate.backend.config.shared.semver.Version;
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
    private final InfoBoxHelper infoBoxHelper;

    private static final Version FORCE_UPDATE_BELOW_1_2_0 = new Version("1.2.0");
    private static final Version DEACTIVATE_PDF_BELOW_2_2_0 = new Version("2.2.0");
    private static final Version UPDATE_INFO_BOX_ANDROID_BELOW_2_0_0 = new Version("2.0.0");

    private static final long ANDROID_TRANSFER_CHECK_INTERVAL_MS = 2 * 60 * 60 * 1000l;
    private static final long ANDROID_TRANSFER_CHECK_BACKOFF_MS = 30 * 1000l;
    private final boolean lightCertificateActive;
    private final boolean pdfGenerationActive;

    public WalletConfigController(
            Messages messages,
            FaqHelper faqHelper,
            InfoBoxHelper infoBoxHelper,
            boolean lightCertificateActive,
            boolean pdfGenerationActive) {
        this.messages = messages;
        this.faqHelper = faqHelper;
        this.infoBoxHelper = infoBoxHelper;
        this.lightCertificateActive = lightCertificateActive;
        this.pdfGenerationActive = pdfGenerationActive;
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
        Version clientAppVersion = new Version(appversion);

        WalletConfigResponse configResponse = new WalletConfigResponse();
        configResponse.setQuestions(faqHelper.getWalletFaqQuestions());
        configResponse.setWorks(faqHelper.getWalletFaqWorks());
        configResponse.setTransferQuestions(faqHelper.getWalletTransferFaqQuestions());
        configResponse.setTransferWorks(faqHelper.getWalletTransferFaqWorks());
        configResponse.setAndroidTransferCheckBackoffMs(ANDROID_TRANSFER_CHECK_BACKOFF_MS);
        configResponse.setAndroidTransferCheckIntervalMs(ANDROID_TRANSFER_CHECK_INTERVAL_MS);
        configResponse.setLightCertificateActive(lightCertificateActive);

        if (clientAppVersion.isSmallerVersionThan(DEACTIVATE_PDF_BELOW_2_2_0)) {
            configResponse.setPdfGenerationActive(false);
        } else {
            configResponse.setPdfGenerationActive(pdfGenerationActive);
        }

        if (clientAppVersion.isSmallerVersionThan(FORCE_UPDATE_BELOW_1_2_0)
                && clientAppVersion.isIOS()) {
            configResponse.setForceUpdate(true);
        }

        if (clientAppVersion.isSmallerVersionThan(UPDATE_INFO_BOX_ANDROID_BELOW_2_0_0)
                && clientAppVersion.isAndroid()
                && !configResponse.isForceUpdate()) {
            configResponse.setInfoBox(infoBoxHelper.getUpdateInfoBox(clientAppVersion.isAndroid()));
        }

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(CacheUtil.CONFIG_MAX_AGE))
                .body(configResponse);
    }
}
