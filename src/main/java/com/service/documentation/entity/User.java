package com.service.documentation.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.service.documentation.entity.auditor.LoggedEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

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
}