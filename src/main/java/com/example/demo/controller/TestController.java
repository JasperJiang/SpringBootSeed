package com.example.demo.controller;

import com.example.demo.service.TokenService;
import com.example.demo.util.AuthUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    @Autowired
    TokenService tokenService;

    @GetMapping("/test")
    public String getMessage() {
        return "Hello from private API controller";
    }

    @GetMapping("/revokeToken")
    public void revokeToken(){
        log.info(AuthUtils.getToken());
        tokenService.revokeToken(AuthUtils.getToken());
    }
}
