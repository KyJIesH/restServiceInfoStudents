package ru.kuleshov.restserviceinfostudents.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для выполнения OAuth2.0 авторизации и
 * получения Access Token
 */
@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping
public class LoginController {
    private static final String TAG = "LOGIN CONTROLLER";

    @GetMapping(value = "/success", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> successfulLogin(
            @RegisteredOAuth2AuthorizedClient("github") OAuth2AuthorizedClient authorizedClient) {
        log.info("{} - Отображение успешной авторизации)", TAG);
        OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
        log.info("{}", accessToken.getTokenValue());
        StringBuilder sb = new StringBuilder();
        sb.append("Вы успешно авторизованы!").append("\n");
        sb.append("Id: ").append(authorizedClient.getPrincipalName()).append("\n");
        sb.append("Access Token: ").append(accessToken.getTokenValue()).append("\n");
        sb.append("Refresh Token: ").append(authorizedClient.getClientRegistration());
        return new ResponseEntity<>(sb, HttpStatus.OK);
    }
}
