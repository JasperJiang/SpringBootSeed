/*
 * Licensed Materials - Property of PwC
 * (c) Copyright Pwc Corp.  2019. All Rights Reserved.
 * US Government Users Restricted Rights - Use, duplication or
 * disclosure restricted by GSA ADP Schedule Contract with PwC Corp.
 */

package com.example.demo.conf;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@ConfigurationProperties(prefix = "app")
@ToString
@Setter
@Getter
@Slf4j
public class AppProps {

    private String signingKey;

    private Oauth2 oauth2;

    @Getter
    @Setter
    public static class Oauth2 {
        private String clientId;

        private String passwd;

        private String[] grantTypes;

        private String resourceIds;

        private String[] scopes;

    }


    @PostConstruct
    private void init() {
        log.debug("========== config: {}", this);

    }
}