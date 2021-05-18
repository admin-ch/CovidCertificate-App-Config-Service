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

import ch.admin.bag.covidcertificate.backend.config.shared.config.ActuatorSecurity;
import ch.admin.bag.covidcertificate.backend.config.shared.config.BaseSecurity;
import ch.admin.bag.covidcertificate.backend.config.shared.config.WSProdConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("cloud-prod")
@Import({ActuatorSecurity.class, BaseSecurity.class, WSProdConfig.class})
public class WalletWsProdConfig extends WalletWsBaseConfig {}
