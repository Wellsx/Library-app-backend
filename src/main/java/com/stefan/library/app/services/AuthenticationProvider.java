package com.stefan.library.app.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

@Component
public class AuthenticationProvider {

    public Integer getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken) {
            JwtAuthenticationToken jwtAuthentication = (JwtAuthenticationToken) authentication;
            Jwt jwt = jwtAuthentication.getToken();
            Long userIdLong = jwt.getClaim("user_id");
            return userIdLong.intValue();
        } else {
            throw new AuthenticationCredentialsNotFoundException("User not authenticated");
        }
    }
}
