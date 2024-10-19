package com.service.documentation.controller.impl;

import com.service.documentation.controller.AuthController;
import com.service.documentation.entity.Session;
import com.service.documentation.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    @Override
    public Session login(String login, String password) {
        return authService.login(login, password);
    }

    @Override
    public Session refreshToken(String refreshToken) {
        return authService.refreshToken(refreshToken);
    }

    @Override
    public void logout() {
        authService.logout();
    }
}