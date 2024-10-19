package com.service.documentation.entity;

import com.service.documentation.entity.auditor.LoggedEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "not del")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "User Session")
@Table(schema = "documentation", name = "sessions")
public class Session extends LoggedEntity {

    @Column(name = "user_id")
    @Schema(description = "User identifier", required = true)
    private Long userId;

    @Column(name = "access_token")
    @Schema(description = "Access token", required = true)
    private String accessToken;

    @Column(name = "refresh_token")
    @Schema(description = "UUID for token refresh", required = true)
    private String refreshToken;

    @Column(name = "last_login")
    @Schema(description = "Last login date")
    private LocalDate lastLogin;

    @Column(name = "useragent")
    @Schema(description = "User's entry point")
    private String useragent;

    @Column(name = "user_ip")
    @Schema(description = "User IP")
    private String userIp;
}