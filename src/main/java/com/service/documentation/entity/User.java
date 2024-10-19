package com.service.documentation.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.service.documentation.entity.auditor.LoggedEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "not del")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "User")
@Table(schema = "documentation", name = "user")
public class User extends LoggedEntity {

    @NotBlank(message = "Поле login не должно быть пустым")
    @Size(min = 5, max = 50, message = "Поле login имеет диапозон от 5 до 50")
    @Schema(description = "Логин пользователя", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(name = "login")
    private String login;

    @NotBlank(message = "Поле password не должно быть пустым")
    @Schema(description = "Пароль пользователя", requiredMode = Schema.RequiredMode.REQUIRED, accessMode = Schema.AccessMode.WRITE_ONLY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password")
    private String password;

    @Schema(description = "Баланс пользователя")
    @Column(name = "balance")
    private Double balance;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "role_id")
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ManyToOne(targetEntity = Role.class, fetch = FetchType.EAGER)
    @Schema(description = "Роль пользователя", accessMode = Schema.AccessMode.READ_ONLY)
    private Role role;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Fetch(FetchMode.JOIN)
    @Schema(description = "Table of relations with the user", accessMode = Schema.AccessMode.READ_ONLY)
    @OneToMany
    @JoinColumn(name = "user_id", updatable = false)
    private List<Appeal> appeals = new ArrayList<>();

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Fetch(FetchMode.JOIN)
    @Schema(description = "Table of relations with the user", accessMode = Schema.AccessMode.READ_ONLY)
    @OneToMany
    @JoinColumn(name = "user_id", updatable = false)
    private List<Deposit> deposits = new ArrayList<>();

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Fetch(FetchMode.JOIN)
    @Schema(description = "Table of relations with the user", accessMode = Schema.AccessMode.READ_ONLY)
    @OneToMany
    @JoinColumn(name = "user_id", updatable = false)
    private List<Withdraw> withdraws = new ArrayList<>();

    @PreUpdate
    @PrePersist
    void updateBalance() {
        if (balance == null) {
            balance = 0.0;
        }
    }
}