package com.service.documentation.service;

import com.service.documentation.entity.Session;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    Session login(String login, String password);

    Session refreshToken(String refreshToken);

    void logout();
}