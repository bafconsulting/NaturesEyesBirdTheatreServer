package com.natureseyes.birdtheatre.client.birdtheatre;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.oltu.oauth2.client.request.OAuthClientRequest.AuthenticationRequestBuilder;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest.TokenRequestBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Feign;
import feign.RequestInterceptor;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.slf4j.Slf4jLogger;
import com.natureseyes.birdtheatre.client.birdtheatre.api.*;
import com.natureseyes.birdtheatre.client.birdtheatre.auth.*;
import com.natureseyes.birdtheatre.client.birdtheatre.auth.OAuth.AccessTokenListener;

@Configuration(value = "birdtheatreApiClientConfiguration")
public class ApiClient {

    private static final Logger log = LoggerFactory.getLogger(ApiClient.class);

    @Autowired
    private Encoder feignEncoder;

    @Autowired
    private Decoder feignDecoder;

    @Autowired(required=false)
    private ApiClientProperties birdtheatreApiClientProperties;

    @Bean
    public FeignApiClient birdtheatreApiClient() {
        return createApiClient(birdtheatreApiClientProperties, feignEncoder, feignDecoder);
    }
    
    @Bean
    public AuditresourceApi birdtheatreAuditresourceApi() {
        return birdtheatreApiClient().buildClient(AuditresourceApi.class);
    }
    
    @Bean
    public LogsresourceApi birdtheatreLogsresourceApi() {
        return birdtheatreApiClient().buildClient(LogsresourceApi.class);
    }
    
    @Bean
    public BroadcaststreamresourceApi birdtheatreBroadcaststreamresourceApi() {
        return birdtheatreApiClient().buildClient(BroadcaststreamresourceApi.class);
    }
    
    @Bean
    public AccountresourceApi birdtheatreAccountresourceApi() {
        return birdtheatreApiClient().buildClient(AccountresourceApi.class);
    }
    
    @Bean
    public UserresourceApi birdtheatreUserresourceApi() {
        return birdtheatreApiClient().buildClient(UserresourceApi.class);
    }
    
    public static FeignApiClient createApiClient(ApiClientProperties props, Encoder feignEncoder, Decoder feignDecoder) {
        Feign.Builder feignBuilder = Feign.builder()
                .encoder(feignEncoder)
                .decoder(feignDecoder)
                .logger(new Slf4jLogger());

        FeignApiClient apiClient = new FeignApiClient(feignBuilder);
        if (props != null) {
            if (props.getUrl() != null) {
                apiClient.setBasePath(props.getUrl());
            }
            if (props.getTokenUrl() != null) {
                if (props.getClientId() != null && props.getClientSecret() != null) {
                    OAuth oauth;
                    if (props.getPassword() != null && props.getUsername() != null) {
                        // OAuth password flow
                        oauth = new OAuth(OAuthFlow.password, "", props.getTokenUrl(), String.join(" ", props.getScopes()));
                        oauth.getTokenRequestBuilder()
                                .setUsername(props.getUsername())
                                .setPassword(props.getPassword());
                    } else {
                        // OAuth credentials flow
                        oauth = new OAuth(OAuthFlow.application, "", props.getTokenUrl(), String.join(" ", props.getScopes()));
                    }
                    oauth.getTokenRequestBuilder()
                            .setClientId(props.getClientId())
                            .setClientSecret(props.getClientSecret());
                    apiClient.addAuthorization("oauth", oauth);
                } else {
                    log.warn("A token URL was found but client ID or client secret is missing. Skipping authorization...");
                }
            } else if (props.getPassword() != null && props.getUsername() != null) {
                // Basic auth
                apiClient.getFeignBuilder().requestInterceptor(new BasicAuthRequestInterceptor(props.getUsername(), props.getPassword()));
            }
        }
        return apiClient;
    }

    public static class FeignApiClient {

        public static class FeignApiClientException extends RuntimeException {
            public FeignApiClientException(String string) {
                super(string);
            }
        }

        private String basePath = "https://localhost:9000/";
        private Map<String, RequestInterceptor> apiAuthorizations;
        private Feign.Builder feignBuilder;

        public FeignApiClient() {
            apiAuthorizations = new LinkedHashMap<String, RequestInterceptor>();
        }

        public FeignApiClient(Feign.Builder feignBuilder) {
            this();
            this.feignBuilder = feignBuilder;
        }

        public String getBasePath() {
            return basePath;
        }

        public FeignApiClient setBasePath(String basePath) {
            this.basePath = basePath;
            return this;
        }

        public Map<String, RequestInterceptor> getApiAuthorizations() {
            return apiAuthorizations;
        }

        public void setApiAuthorizations(Map<String, RequestInterceptor> apiAuthorizations) {
            this.apiAuthorizations = apiAuthorizations;
        }

        public Feign.Builder getFeignBuilder() {
            return feignBuilder;
        }

        public FeignApiClient setFeignBuilder(Feign.Builder feignBuilder) {
            this.feignBuilder = feignBuilder;
            return this;
        }


        /**
        * Creates a feign client for given API interface.
        *
        * Usage:
        * ApiClient apiClient = new ApiClient();
        * apiClient.setBasePath("http://localhost:8080");
        * XYZApi api = apiClient.buildClient(XYZApi.class);
        * XYZResponse response = api.someMethod(...);
        */
        public <T> T buildClient(Class<T> clientClass) {
            return feignBuilder.target(clientClass, basePath);
        }

        /**
        * Helper method to configure the first api key found
        * @param apiKey
        */
        public void setApiKey(String apiKey) {
            for(RequestInterceptor apiAuthorization : apiAuthorizations.values()) {
                if (apiAuthorization instanceof ApiKeyAuth) {
                    ApiKeyAuth keyAuth = (ApiKeyAuth) apiAuthorization;
                    keyAuth.setApiKey(apiKey);
                    return ;
                }
            }
            throw new FeignApiClientException("No API key authentication configured!");
        }

        /**
        * Helper method to configure the username/password for basic auth or password OAuth
        * @param username
        * @param password
        */
        public void setCredentials(String username, String password) {
            for(RequestInterceptor apiAuthorization : apiAuthorizations.values()) {
                if (apiAuthorization instanceof HttpBasicAuth) {
                    HttpBasicAuth basicAuth = (HttpBasicAuth) apiAuthorization;
                    basicAuth.setCredentials(username, password);
                    return;
                }
                if (apiAuthorization instanceof OAuth) {
                    OAuth oauth = (OAuth) apiAuthorization;
                    oauth.getTokenRequestBuilder().setUsername(username).setPassword(password);
                    return;
                }
            }
            throw new FeignApiClientException("No Basic authentication or OAuth configured!");
        }

        /**
        * Helper method to configure the token endpoint of the first oauth found in the apiAuthorizations (there should be only one)
        * @return
        */
        public TokenRequestBuilder getTokenEndPoint() {
            for(RequestInterceptor apiAuthorization : apiAuthorizations.values()) {
                if (apiAuthorization instanceof OAuth) {
                    OAuth oauth = (OAuth) apiAuthorization;
                    return oauth.getTokenRequestBuilder();
                }
            }
            return null;
        }

        /**
        * Helper method to configure authorization endpoint of the first oauth found in the apiAuthorizations (there should be only one)
        * @return
        */
        public AuthenticationRequestBuilder getAuthorizationEndPoint() {
            for(RequestInterceptor apiAuthorization : apiAuthorizations.values()) {
                if (apiAuthorization instanceof OAuth) {
                    OAuth oauth = (OAuth) apiAuthorization;
                    return oauth.getAuthenticationRequestBuilder();
                }
            }
            return null;
        }

        /**
        * Helper method to pre-set the oauth access token of the first oauth found in the apiAuthorizations (there should be only one)
        * @param accessToken
        * @param expiresIn : validity period in seconds
        */
        public void setAccessToken(String accessToken, Long expiresIn) {
            for(RequestInterceptor apiAuthorization : apiAuthorizations.values()) {
                if (apiAuthorization instanceof OAuth) {
                    OAuth oauth = (OAuth) apiAuthorization;
                    oauth.setAccessToken(accessToken, expiresIn);
                    return;
                }
            }
        }

        /**
        * Helper method to configure the oauth accessCode/implicit flow parameters
        * @param clientId
        * @param clientSecret
        * @param redirectURI
        */
        public void configureAuthorizationFlow(String clientId, String clientSecret, String redirectURI) {
            for(RequestInterceptor apiAuthorization : apiAuthorizations.values()) {
                if (apiAuthorization instanceof OAuth) {
                    OAuth oauth = (OAuth) apiAuthorization;
                    oauth.getTokenRequestBuilder()
                            .setClientId(clientId)
                            .setClientSecret(clientSecret)
                            .setRedirectURI(redirectURI);
                    oauth.getAuthenticationRequestBuilder()
                            .setClientId(clientId)
                            .setRedirectURI(redirectURI);
                    return;
                }
            }
        }

        /**
        * Configures a listener which is notified when a new access token is received.
        * @param accessTokenListener
        */
        public void registerAccessTokenListener(AccessTokenListener accessTokenListener) {
            for(RequestInterceptor apiAuthorization : apiAuthorizations.values()) {
                if (apiAuthorization instanceof OAuth) {
                    OAuth oauth = (OAuth) apiAuthorization;
                    oauth.registerAccessTokenListener(accessTokenListener);
                    return;
                }
            }
        }

        public RequestInterceptor getAuthorization(String authName) {
            return apiAuthorizations.get(authName);
        }

        /**
        * Adds an authorization to be used by the client
        * @param authName
        * @param authorization
        */
        public void addAuthorization(String authName, RequestInterceptor authorization) {
            if (apiAuthorizations.containsKey(authName)) {
                throw new FeignApiClientException("auth name \"" + authName + "\" already in api authorizations");
            }
            apiAuthorizations.put(authName, authorization);
            feignBuilder.requestInterceptor(authorization);
        }

    }
}
