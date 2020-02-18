package com.client.authorizationcode.oauth;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.resource.UserApprovalRequiredException;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;

import java.util.HashMap;
import java.util.Map;

public class CustomAuthorizationCodeAccessTokenProvider implements AccessTokenProvider {

    @Override
    public OAuth2AccessToken obtainAccessToken(OAuth2ProtectedResourceDetails details, AccessTokenRequest request) throws UserRedirectRequiredException, UserApprovalRequiredException, AccessDeniedException {
        AuthorizationCodeResourceDetails resource = (AuthorizationCodeResourceDetails) details;
        //ImplicitResourceDetails resource = (ImplicitResourceDetails) details;

        Map<String, String> requestParameters = getParametersForTokenRequest(
                resource, request);

        UserRedirectRequiredException redirectException = new UserRedirectRequiredException(
                resource.getUserAuthorizationUri(), requestParameters);

        throw redirectException;
    }

    @Override
    public boolean supportsResource(OAuth2ProtectedResourceDetails resource) {
        return resource instanceof AuthorizationCodeResourceDetails
                && "authorization_code".equals(resource.getGrantType());
    }

    @Override
    public OAuth2AccessToken refreshAccessToken(OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails, OAuth2RefreshToken oAuth2RefreshToken, AccessTokenRequest accessTokenRequest) throws UserRedirectRequiredException {
        return null;
    }

    @Override
    public boolean supportsRefresh(OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails) {
        return true;
    }

    private Map<String, String> getParametersForTokenRequest(
            AuthorizationCodeResourceDetails resource, AccessTokenRequest request) {

        Map<String, String> queryString = new HashMap<String, String>();
        queryString.put("response_type", "token");
        queryString.put("client_id", resource.getClientId());

        if (resource.isScoped()) {
            queryString.put("scope",
                    resource.getScope().stream().reduce((a, b) -> a + " " + b)
                            .get());
        }

        String redirectUri = resource.getRedirectUri(request);
        if (redirectUri == null) {
            throw new IllegalStateException("No redirect URI available in request");
        }
        queryString.put("redirect_uri", redirectUri);

        return queryString;

    }

}
