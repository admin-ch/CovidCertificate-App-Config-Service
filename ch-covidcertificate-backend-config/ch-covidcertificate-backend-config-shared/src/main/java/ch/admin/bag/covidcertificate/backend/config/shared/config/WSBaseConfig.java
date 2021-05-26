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

import ch.admin.bag.covidcertificate.backend.config.shared.helper.CacheUtil;
import ch.admin.bag.covidcertificate.backend.config.shared.helper.FaqHelper;
import ch.admin.bag.covidcertificate.backend.config.shared.interceptor.HeaderInjector;
import ch.admin.bag.covidcertificate.backend.config.shared.poeditor.Messages;
import ch.admin.bag.covidcertificate.backend.config.shared.security.signature.JWSMessageConverter;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.time.Duration;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public abstract class WSBaseConfig implements WebMvcConfigurer {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Value(
            "#{${ws.security.headers: {'X-Content-Type-Options':'nosniff', 'X-Frame-Options':'DENY','X-Xss-Protection':'1; mode=block'}}}")
    Map<String, String> additionalHeaders;

    @Value("${ws.config.max-age:PT1M}")
    public void setConfigMaxAge(Duration configMaxAge) {
        CacheUtil.CONFIG_MAX_AGE = configMaxAge;
    }

    @Value("${ws.jws.p12:}")
    public String p12KeyStore;
    @Value("${ws.jws.password:}")
    public String p12KeyStorePassword;

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
        return new Messages(messageSource);
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource =
                new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setFallbackToSystemLocale(false);
        messageSource.setDefaultLocale(null);
        return messageSource;
    }

    @Bean
    public JWSMessageConverter jwsMessageConverter() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException {
        return new JWSMessageConverter(jwsKeyStore(), p12KeyStorePassword.toCharArray());
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        try {
            converters.add(jwsMessageConverter());
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException | UnrecoverableKeyException e) {
            logger.error("Could not load key store", e);
            throw new RuntimeException("Could not add jws Converter");
        }
    }

    public KeyStore jwsKeyStore() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
        var keyStore = KeyStore.getInstance("pkcs12");
        var bais = new ByteArrayInputStream(Base64.getDecoder().decode(p12KeyStore));
        keyStore.load(bais, p12KeyStorePassword.toCharArray());
        return keyStore;
    }
}
