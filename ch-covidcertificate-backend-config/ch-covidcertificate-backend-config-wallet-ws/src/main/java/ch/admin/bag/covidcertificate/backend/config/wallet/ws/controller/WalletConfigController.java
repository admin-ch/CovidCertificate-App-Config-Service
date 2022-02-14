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
import ch.admin.bag.covidcertificate.backend.config.shared.helper.CheckModeInfoHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.helper.EolBannerInfoHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.helper.FaqHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.helper.InfoBoxHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.helper.RefreshButtonInfoHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.helper.VaccinationHintHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.model.Faq;
import ch.admin.bag.covidcertificate.backend.config.shared.model.WalletConfigResponse;
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
@RequestMapping("/app/wallet/v1")
public class WalletConfigController {

    private static final Integer LIGHT_CERT_DURATION_IN_HOURS = 24;

    protected final Messages messages;
    private final CheckModeInfoHelper checkModeInfoHelper;
    protected final FaqHelper faqHelper;
    private final InfoBoxHelper infoBoxHelper;
    private final RefreshButtonInfoHelper refreshButtonInfoHelper;
    private final EolBannerInfoHelper eolBannerInfoHelper;

    private static final Version FORCE_UPDATE_BELOW_1_2_0 = new Version("1.2.0");
    private static final Version INFOBOX_BELOW_2_7_0 = new Version("2.7.0");
    private static final Version DEACTIVATE_PDF_BELOW_2_2_0 = new Version("2.2.0");
    private static final Version TRANSFER_CODE_VALIDITY_30_DAYS_2_7_0 = new Version("2.7.0");

    private static final long ANDROID_TRANSFER_CHECK_INTERVAL_MS = 2 * 60 * 60 * 1000l;
    private static final long ANDROID_TRANSFER_CHECK_BACKOFF_MS = 30 * 1000l;
    private final boolean lightCertificateActive;
    private final boolean pdfGenerationActive;

    private final VaccinationHintHelper vaccinationHintHelper;
    private final boolean showVaccinationHintHomescreen;
    private final boolean showVaccinationHintDetail;
    private final boolean showVaccinationHintTransfer;
    private final boolean refreshButtonDisabled;

    private final boolean timeShiftDetectionEnabled;

    private static final Logger logger = LoggerFactory.getLogger(WalletConfigController.class);

    public WalletConfigController(
            Messages messages,
            CheckModeInfoHelper checkModeInfoHelper,
            FaqHelper faqHelper,
            InfoBoxHelper infoBoxHelper,
            boolean lightCertificateActive,
            boolean pdfGenerationActive,
            VaccinationHintHelper vaccinationHintHelper,
            boolean showVaccinationHintHomescreen,
            boolean showVaccinationHintDetail,
            boolean showVaccinationHintTransfer,
            boolean timeShiftDetectionEnabled,
            boolean refreshButtonDisabled,
            RefreshButtonInfoHelper refreshButtonInfoHelper,
            EolBannerInfoHelper eolBannerInfoHelper) {
        this.messages = messages;
        this.checkModeInfoHelper = checkModeInfoHelper;
        this.faqHelper = faqHelper;
        this.infoBoxHelper = infoBoxHelper;
        this.lightCertificateActive = lightCertificateActive;
        this.pdfGenerationActive = pdfGenerationActive;
        this.vaccinationHintHelper = vaccinationHintHelper;
        this.showVaccinationHintHomescreen = showVaccinationHintHomescreen;
        this.showVaccinationHintDetail = showVaccinationHintDetail;
        this.showVaccinationHintTransfer = showVaccinationHintTransfer;
        this.timeShiftDetectionEnabled = timeShiftDetectionEnabled;
        this.refreshButtonDisabled = refreshButtonDisabled;
        this.refreshButtonInfoHelper = refreshButtonInfoHelper;
        this.eolBannerInfoHelper = eolBannerInfoHelper;
    }

    @Documentation(
            description = "Echo endpoint",
            responses = {"200 => Hello from CH Covidcertificate Config Wallet WS"})
    @CrossOrigin(origins = {"https://editor.swagger.io"})
    @GetMapping(value = "")
    public @ResponseBody String hello() {
        logger.info("Hello from CH Covidcertificate Config Wallet WS");
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
        configResponse.setVaccinationHints(vaccinationHintHelper.getVaccinationHints());
        configResponse.setVaccinationBookingCantons(
                vaccinationHintHelper.getVaccinationBookingCantons());
        configResponse.setVaccinationBookingInfo(vaccinationHintHelper.getVaccinationBookingInfo());
        configResponse.setShowVaccinationHintHomescreen(showVaccinationHintHomescreen);
        configResponse.setShowVaccinationHintDetail(showVaccinationHintDetail);
        configResponse.setShowVaccinationHintTransfer(showVaccinationHintTransfer);
        configResponse.setTimeshiftDetectionEnabled(timeShiftDetectionEnabled);
        configResponse.setCheckModesInfo(checkModeInfoHelper.getWalletCheckModesInfo());
        configResponse.setLightCertDurationInHours(LIGHT_CERT_DURATION_IN_HOURS);
        configResponse.setRefreshButtonDisabled(refreshButtonDisabled);
        configResponse.setRefreshButtonInfo(refreshButtonInfoHelper.getInfo());
        configResponse.setEolBannerInfo(eolBannerInfoHelper.getInfo());

        if (clientAppVersion.isSmallerVersionThan(DEACTIVATE_PDF_BELOW_2_2_0)) {
            configResponse.setPdfGenerationActive(false);
        } else {
            configResponse.setPdfGenerationActive(pdfGenerationActive);
        }

        if (clientAppVersion.isSmallerVersionThan(FORCE_UPDATE_BELOW_1_2_0)) {
            configResponse.setForceUpdate(true);
        }

        configResponse.setInfoBox(infoBoxHelper.getUpdateInfoBox(clientAppVersion.isAndroid()));

        replacePoeditorPlaceHolders(configResponse, clientAppVersion);

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(CacheUtil.CONFIG_MAX_AGE))
                .body(configResponse);
    }

    private void replacePoeditorPlaceHolders(
            WalletConfigResponse configResponse, Version clientAppVersion) {
        replaceTransferCodeValidityPlaceHolder(configResponse, clientAppVersion);
        replaceLightCertValidityPlaceHolder(configResponse);
    }

    private void replaceLightCertValidityPlaceHolder(WalletConfigResponse configResponse) {
        for (Faq faq : configResponse.getQuestions().values()) {
            faq.getFaqEntries()
                    .forEach(
                            entry ->
                                    entry.setText(
                                            entry.getText()
                                                    .replace(
                                                            "{LIGHT_CERT_VALIDITY_IN_H}",
                                                            LIGHT_CERT_DURATION_IN_HOURS
                                                                    .toString())));
        }
    }

    private void replaceTransferCodeValidityPlaceHolder(
            WalletConfigResponse configResponse, Version clientAppVersion) {
        String transferCodeValidity;
        if (clientAppVersion.isSmallerVersionThan(TRANSFER_CODE_VALIDITY_30_DAYS_2_7_0)) {
            transferCodeValidity = "7";
        } else {
            transferCodeValidity = "30";
        }
        for (var faqs : configResponse.getTransferWorks().values()) {
            faqs.getFaqIntroSections()
                    .forEach(
                            entry ->
                                    entry.setText(
                                            entry.getText()
                                                    .replace(
                                                            "{TRANSFER_CODE_VALIDITY}",
                                                            transferCodeValidity)));
        }
    }
}
