package com.service.documentation.repository;

import com.service.documentation.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    Optional<Session> findSessionByRefreshToken(String refreshToken);

    Optional<Session> findSessionByAccessToken(String accessToken);
}