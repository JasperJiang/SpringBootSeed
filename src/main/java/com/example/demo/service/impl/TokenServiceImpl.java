package com.example.demo.service.impl;

import com.example.demo.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TokenServiceImpl implements TokenService {

	@Autowired
	private DefaultTokenServices tokenServices;


	@Override
	public void revokeToken(String token){
		try {
			tokenServices.revokeToken(token);
		}catch (Exception e){
			log.info("no token found");
		}
	}

	@Override
	public String getAccessToken(OAuth2Authentication auth2Authentication){
		try {
			return tokenServices.getAccessToken(auth2Authentication).getValue();
		}catch (Exception e){
			log.info("user is not logged in");
			return null;
		}
	}

}
