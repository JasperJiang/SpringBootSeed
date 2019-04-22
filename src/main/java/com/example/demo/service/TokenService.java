package com.example.demo.service;

import org.springframework.security.oauth2.provider.OAuth2Authentication;

public interface TokenService {

	void revokeToken(String token);

	String getAccessToken(OAuth2Authentication auth2Authentication);
}
