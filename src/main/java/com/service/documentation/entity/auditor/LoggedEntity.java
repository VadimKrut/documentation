package com.service.documentation.entity.auditor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Data
@MappedSuperclass
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder(builderMethodName = "", toBuilder = true)
@NoArgsConstructor
public abstract class LoggedEntity extends BaseEntity {

    @CreatedDate
    @JsonIgnore
    @Schema(description = "Date and time the record was created", accessMode = Schema.AccessMode.READ_ONLY)
    @Column(updatable = false)
    private Date dateCreate;

    @LastModifiedDate
    @JsonIgnore
    @Schema(description = "Date and time the record was last modified", accessMode = Schema.AccessMode.READ_ONLY)
    private Date dateUpdate;

    @LastModifiedBy
    @JsonIgnore
    @Schema(description = "ID of the user who modified the entry", accessMode = Schema.AccessMode.READ_ONLY)
    @Column(name = "last_user_id")
    private Long lastUserId;

    @JsonIgnore
    @Schema(description = "Label for deleted entries", accessMode = Schema.AccessMode.READ_ONLY)
    private boolean del = false;
}