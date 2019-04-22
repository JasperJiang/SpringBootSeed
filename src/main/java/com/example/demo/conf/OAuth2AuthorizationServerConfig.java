package com.example.demo.conf;

import com.example.demo.enums.ErrorCodesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultThrowableAnalyzer;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.web.util.ThrowableAnalyzer;

import java.util.Arrays;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    AppProps appProps;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        final TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();

        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter()));

        endpoints.exceptionTranslator(new ExcepTranslator());

        endpoints.authenticationManager(this.authenticationManager)
                .tokenStore(tokenStore()).reuseRefreshTokens(false)
                .tokenEnhancer(tokenEnhancerChain);
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(appProps.getSigningKey());
        return converter;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(appProps.getOauth2().getClientId())
                .secret(passwordEncoder().encode(appProps.getOauth2().getPasswd()))
                .authorizedGrantTypes(appProps.getOauth2().getGrantTypes())
                .scopes(appProps.getOauth2().getScopes())
                .resourceIds(appProps.getOauth2().getResourceIds());
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new CustomJwtTokenEnhancer();
    }


    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private class ExcepTranslator extends DefaultWebResponseExceptionTranslator {

        private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();

        @Override
        public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {


            ResponseEntity<OAuth2Exception> resp = super.translate(e);

            Throwable[] causeChain = throwableAnalyzer.determineCauseChain(e);


            Exception ase = (org.springframework.security.core.AuthenticationException) throwableAnalyzer
                    .getFirstThrowableOfType(org.springframework.security.core.AuthenticationException.class,
                            causeChain);
            if (ase != null) {
                ErrorCodesEnum errorCodes = ErrorCodesEnum.ERR_WHILE_AUTHENTICATING;
                resp.getBody().addAdditionalInformation("errorCode", errorCodes.toString());
            }
            return resp;
        }
    }
}
