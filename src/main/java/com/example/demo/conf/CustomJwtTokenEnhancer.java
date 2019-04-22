package com.example.demo.conf;

import com.example.demo.util.ReqUtils;
import com.example.demo.vo.UserVo;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

public class CustomJwtTokenEnhancer implements TokenEnhancer {


    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {


        final Map<String, Object> additionalInfo = new HashMap<>();
        UserVo userVo = ReqUtils.removeUserAttribute();

        setUserInfo(additionalInfo, userVo);


        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;

    }

    private void setUserInfo(Map<String, Object> additionalInfo, UserVo userVo) {
        additionalInfo.put("user", userVo);
    }

}
