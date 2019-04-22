package com.example.demo.security;

public class SecurityConstants {
    public static final String AUTH_LOGIN_URL = "/login";

    public static final String JWT_SECRET = "n2r5u8x/A%D*G-KaPdSgVkYp3s6v9y$B&E(H+MbQeThWmZq4t7w!z%C*F-J@NcRf";

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    public static final long EXPIRATION_TIME = 7200000; // 2hr
}
