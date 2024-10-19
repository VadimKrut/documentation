package com.service.documentation.entity;

import com.service.documentation.entity.auditor.LoggedEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "not del")
@ToString(callSuper = true)
@Schema(description = "Role")
@EqualsAndHashCode(callSuper = true)
@Table(schema = "documentation", name = "roles")
public class Role extends LoggedEntity {

    @Column(name = "name")
    @Schema(description = "Name", required = true)
    private String name;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Fetch(FetchMode.JOIN)
    @ManyToMany(targetEntity = Permission.class)
    @JoinTable(schema = "documentation", name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    @Schema(description = "Role permissions. When assigning permissions, provide only the id.")
    private Set<Permission> permissions;
}