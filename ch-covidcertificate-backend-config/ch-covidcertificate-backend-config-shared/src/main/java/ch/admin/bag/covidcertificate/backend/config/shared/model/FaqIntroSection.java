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

import javax.validation.constraints.NotNull;

public class FaqIntroSection {

    @NotNull private String iconAndroid;
    @NotNull private String iconIos;
    @NotNull private String text;

    public String getIconAndroid() {
        return iconAndroid;
    }

    public void setIconAndroid(String iconAndroid) {
        this.iconAndroid = iconAndroid;
    }

    public String getIconIos() {
        return iconIos;
    }

    public void setIconIos(String iconIos) {
        this.iconIos = iconIos;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
