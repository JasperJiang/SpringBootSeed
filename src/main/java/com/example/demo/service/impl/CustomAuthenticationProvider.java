package com.example.demo.service.impl;

import com.example.demo.exception.AuthenticationException;
import com.example.demo.util.ReqUtils;
import com.example.demo.vo.UserVo;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        if (name.equalsIgnoreCase("123") && password.equalsIgnoreCase("123")) {

            ReqUtils.setUserAttribute(UserVo.builder().userName("abc").build());

            return new UsernamePasswordAuthenticationToken(name, password, new ArrayList<>());
        } else {
            throw new AuthenticationException("Wrong username or password");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
                UsernamePasswordAuthenticationToken.class);
    }
}