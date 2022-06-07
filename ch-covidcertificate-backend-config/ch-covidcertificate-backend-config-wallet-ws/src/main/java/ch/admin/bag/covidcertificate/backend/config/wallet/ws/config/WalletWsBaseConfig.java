/*
 * Copyright (c) 2021 Ubique Innovation AG <https://www.ubique.ch>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package ch.admin.bag.covidcertificate.backend.config.wallet.ws.config;

import ch.admin.bag.covidcertificate.backend.config.shared.helper.CertRenewalInfoHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.helper.CheckModeInfoHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.helper.EolBannerInfoHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.helper.FaqHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.helper.ForeignRulesHintHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.helper.InfoBoxHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.helper.RefreshButtonInfoHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.helper.VaccinationHintHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.poeditor.Messages;
import ch.admin.bag.covidcertificate.backend.config.wallet.ws.controller.WalletConfigController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public abstract class WalletWsBaseConfig {

    @Bean
    public WalletConfigController walletConfigController(
            Messages messages,
            CheckModeInfoHelper checkModeInfoHelper,
            FaqHelper faqHelper,
            InfoBoxHelper infoBoxHelper,
            @Value("${ws.wallet.light-certificate.active:false}") boolean lightCertificateActive,
            @Value("${ws.wallet.pdf-generation.active:false}") boolean pdfGenerationActive,
            VaccinationHintHelper vaccinationHintHelper,
            @Value("${ws.wallet.vaccination-hints.homescreen.show:false}")
                    boolean showVaccinationHintHomescreen,
            @Value("${ws.wallet.vaccination-hints.detail.show:false}")
                    boolean showVaccinationHintDetail,
            @Value("${ws.wallet.vaccination-hints.transfer.show:false}")
                    boolean showVaccinationHintTransfer,
            @Value("${ws.wallet.timeshiftDetection.enabled:true}")
                    boolean timeshiftDetectionEnabled,
            @Value("${ws.wallet.refreshButton.disabled}") boolean refreshButtonDisabled,
            @Value("${ws.wallet.foreignRules.enabled:false}") boolean foreignRulesEnabled,
            @Value("${ws.wallet.ratConversion.enabled}") boolean ratConversionEnabled,
            @Value("${ws.wallet.ratConversion.url}") String ratFormUrl,
            RefreshButtonInfoHelper refreshButtonInfoHelper,
            EolBannerInfoHelper eolBannerInfoHelper,
            ForeignRulesHintHelper foreignRulesHintHelper,
            CertRenewalInfoHelper certRenewalInfoHelper) {
        return new WalletConfigController(
                messages,
                checkModeInfoHelper,
                faqHelper,
                infoBoxHelper,
                lightCertificateActive,
                pdfGenerationActive,
                vaccinationHintHelper,
                showVaccinationHintHomescreen,
                showVaccinationHintDetail,
                showVaccinationHintTransfer,
                timeshiftDetectionEnabled,
                refreshButtonDisabled,
                foreignRulesEnabled,
                ratConversionEnabled,
                ratFormUrl,
                refreshButtonInfoHelper,
                eolBannerInfoHelper,
                foreignRulesHintHelper,
                certRenewalInfoHelper);
    }
}
