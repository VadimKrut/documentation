package com.service.documentation.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@Component
public class JwtTokenUtil {

    public static final int ACCESS_TOKEN_VALIDITY = 120;

    @Value("${jwt.secret}")
    public String secret;

    public Long getUserIdFromToken(String token) {
        return Long.valueOf((Integer) getAllClaimsFromToken(token).get("uid"));
    }

    @SuppressWarnings("unchecked")
    public Collection<String> getPermsFromToken(String token) {
        return (Collection<String>) getAllClaimsFromToken(token).get("perms");
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public String generateToken(long userId, String login, String issuer, Set<String> perms) {
        Claims claims = Jwts.claims();
        claims.setSubject(login);
        claims.setIssuer(issuer);
        claims.setIssuedAt(new Date());
        claims.setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY * 60 * 1000));
        claims.put("uid", userId);
        claims.put("perms", (perms != null) ? perms : Collections.emptySet());

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}