package com.service.documentation.entity;

import com.service.documentation.entity.auditor.LoggedEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "not del")
@Schema(description = "Permission")
@ToString(callSuper = true)
@Table(schema = "documentation", name = "permissions")
@EqualsAndHashCode(callSuper = true)
public class Permission extends LoggedEntity {

    @Column(name = "perm")
    @Schema(description = "Permission", required = true)
    private String perm;

    @Column(name = "description")
    @Schema(description = "description")
    private String description;
}