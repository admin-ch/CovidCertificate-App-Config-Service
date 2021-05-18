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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class InfoBoxCollection {

	private InfoBox deInfoBox;
	private InfoBox frInfoBox;
	private InfoBox itInfoBox;
	private InfoBox enInfoBox;

	public InfoBox getDeInfoBox() {
		return deInfoBox;
	}

	public void setDeInfoBox(InfoBox deInfoBox) {
		this.deInfoBox = deInfoBox;
	}

	public InfoBox getFrInfoBox() {
		return frInfoBox;
	}

	public void setFrInfoBox(InfoBox frInfoBox) {
		this.frInfoBox = frInfoBox;
	}

	public InfoBox getItInfoBox() {
		return itInfoBox;
	}

	public void setItInfoBox(InfoBox itInfoBox) {
		this.itInfoBox = itInfoBox;
	}

	public InfoBox getEnInfoBox() {
		return enInfoBox;
	}

	public void setEnInfoBox(InfoBox enInfoBox) {
		this.enInfoBox = enInfoBox;
	}
}