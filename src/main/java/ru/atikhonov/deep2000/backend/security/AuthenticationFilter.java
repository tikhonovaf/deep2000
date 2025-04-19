package ru.atikhonov.deep2000.backend.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    private String usernameParameter;
    private String passwordParameter;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        Map<String, String> requestMap;

        try (BufferedReader reader = request.getReader()) {
            requestMap = new ObjectMapper().readValue(reader, Map.class);
        } catch (IOException e) {
            throw new AuthenticationCredentialsNotFoundException("Cannot convert request!");
        }

        String username = requestMap.getOrDefault(usernameParameter, null);
        String password = requestMap.getOrDefault(passwordParameter, null);

        logger.info("TRY TO LOGIN BY " + username);

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                username, password);

        Authentication auth = this.getAuthenticationManager().authenticate(authRequest);

        return auth;
    }

    public void setUsernameParameter(String usernameParameter) {
        this.usernameParameter = usernameParameter;
    }

    public void setPasswordParameter(String passwordParameter) {
        this.passwordParameter = passwordParameter;
    }
}