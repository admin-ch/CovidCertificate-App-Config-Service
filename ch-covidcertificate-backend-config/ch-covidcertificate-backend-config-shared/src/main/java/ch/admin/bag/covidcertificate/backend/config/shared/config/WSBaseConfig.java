/*
 * Copyright (c) 2021 Ubique Innovation AG <https://www.ubique.ch>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package ch.admin.bag.covidcertificate.backend.config.shared.config;

import ch.admin.bag.covidcertificate.backend.config.shared.helper.FaqHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.interceptor.HeaderInjector;
import ch.admin.bag.covidcertificate.backend.config.shared.poeditor.Messages;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public abstract class WSBaseConfig implements WebMvcConfigurer {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Value("#{${ws.security.headers: {'X-Content-Type-Options':'nosniff', 'X-Frame-Options':'DENY','X-Xss-Protection':'1; mode=block'}}}")
	Map<String, String> additionalHeaders;

	@Bean
	public HeaderInjector securityHeaderInjector() {
		return new HeaderInjector(additionalHeaders);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(securityHeaderInjector());
	}

	@Bean
	public FaqHelper faqHelper(Messages messages) {
		return new FaqHelper(messages);
	}

	@Bean
	public Messages messages(MessageSource messageSource) {
		Messages messages = new Messages(messageSource);
		return messages;
	}

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

		messageSource.setBasename("classpath:i18n/messages");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setFallbackToSystemLocale(false);
		messageSource.setDefaultLocale(null);
		return messageSource;
	}
}
