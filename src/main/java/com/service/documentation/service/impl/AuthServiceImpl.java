package com.service.documentation.service.impl;

import com.service.documentation.entity.Permission;
import com.service.documentation.entity.Session;
import com.service.documentation.entity.User;
import com.service.documentation.exception.ApiException;
import com.service.documentation.repository.PersonRepository;
import com.service.documentation.repository.SessionRepository;
import com.service.documentation.security.JwtTokenUtil;
import com.service.documentation.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.WebUtils;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.service.documentation.security.JwtFilter.COOKIE_NAME;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final JwtTokenUtil jwtTokenUtil;
    private final PersonRepository personRepository;
    private final SessionRepository sessionRepository;

    @Override
    @Transactional
    public Session login(String login, String password) {
        if (login == null) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "Логин не можеть быть пустым");
        }
        if (password == null) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "Пароль не можеть быть пустым");
        }
        if (!personRepository.existsByLogin(login)) {
            throw new ApiException(HttpServletResponse.SC_NOT_FOUND, "Пользователь не найден");
        }
        User user = personRepository.findByLogin(login);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (passwordEncoder.matches(password, user.getPassword())) {
            Session session = new Session();
            session.setUserId(user.getId());
            refreshSession(session, user);
            return session;
        } else {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "Неправильный пароль");
        }
    }

    @Override
    @Transactional
    public Session refreshToken(String refreshToken) {
        if ((refreshToken == null) || refreshToken.isEmpty())
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "Refresh token не задан");
        Optional<Session> session = sessionRepository.findSessionByRefreshToken(refreshToken);
        if (session.isEmpty()) {
            throw new ApiException(HttpServletResponse.SC_NOT_FOUND, "Сессия с данным refresh token'ом не найдена");
        }
        refreshSession(session.get(), personRepository.findById(session.get().getUserId()).get());
        return session.get();
    }

    @Override
    @Transactional
    public void logout() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() == null)
            throw new ApiException(HttpServletResponse.SC_UNAUTHORIZED, "Не авторизован");
        Cookie cookie = WebUtils.getCookie(request, COOKIE_NAME);
        String accessToken = (cookie != null) ? cookie.getValue() : null;
        Optional<Session> session = sessionRepository.findSessionByAccessToken(accessToken);
        if (!session.isEmpty()) {
            session.get().setDel(true);
        }
        cookie = new Cookie(COOKIE_NAME, "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        if ("https".equalsIgnoreCase(request.getScheme()))
            cookie.setSecure(true);
        response.addCookie(cookie);
    }

    private void refreshSession(Session session, User usr) {
        session.setAccessToken(jwtTokenUtil.generateToken(usr.getId(), usr.getLogin(), request.getRequestURL().toString(),
                ((usr.getRole() != null) && (usr.getRole().getPermissions() != null))
                        ? usr.getRole().getPermissions().stream().map(Permission::getPerm).collect(Collectors.toSet())
                        : null));
        session.setRefreshToken(UUID.randomUUID().toString());
        session.setLastLogin(LocalDate.now(ZoneOffset.UTC));
        sessionRepository.save(session);
        Cookie cookie = new Cookie(COOKIE_NAME, session.getAccessToken());
        cookie.setPath("/");
        cookie.setMaxAge(JwtTokenUtil.ACCESS_TOKEN_VALIDITY * 60);
        cookie.setHttpOnly(true);
        if ("https".equalsIgnoreCase(request.getScheme()))
            cookie.setSecure(true);
        response.addCookie(cookie);
    }
}