package com.example.demo.service.impl;

import com.example.demo.domain.UserEntity;
import com.example.demo.exception.AuthenticationException;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.ReqUtils;
import com.example.demo.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        Optional<UserEntity> userEntityOptional = userRepository.findByUserNameAndPassword(name, password);

        if (userEntityOptional.isPresent()) {

            ReqUtils.setUserAttribute(userEntityOptional.get().toUserVo());

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