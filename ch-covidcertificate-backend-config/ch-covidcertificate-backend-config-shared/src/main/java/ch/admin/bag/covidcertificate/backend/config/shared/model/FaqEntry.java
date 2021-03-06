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

public class FaqEntry {

    @NotNull private String title;
    @NotNull private String text;

    /* optional */
    private String iconAndroid;
    private String iconIos;
    private String linkTitle;
    private String linkUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

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

    public String getLinkTitle() {
        return linkTitle;
    }

    public void setLinkTitle(String linkTitle) {
        this.linkTitle = linkTitle;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }
}
