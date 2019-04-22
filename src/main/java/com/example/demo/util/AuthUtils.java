package com.example.demo.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

public class AuthUtils {
    public static String getToken() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (null != auth && auth.getDetails() instanceof OAuth2AuthenticationDetails) {
            return ((OAuth2AuthenticationDetails) auth.getDetails()).getTokenValue();
        } else return null;
    }
}
