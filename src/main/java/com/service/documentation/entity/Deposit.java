package com.service.documentation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.service.documentation.entity.auditor.LoggedEntity;
import com.service.documentation.enums.TransactionStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "not del")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Deposit")
@Table(schema = "documentation", name = "deposit")
public class Deposit extends LoggedEntity {

    @Schema(description = "Number of order")
    @Column(name = "order_id")
    private String orderId;

    @Schema(
            description = "status",
            allowableValues = "COMPLETED, CANCELED",
            type = "string", enumAsRef = true, implementation = TransactionStatus.class,
            example = "COMPLETED"
    )
    @Column(name = "status")
    private String status;

    @Schema(description = "Money")
    @Column(name = "money")
    private Double money;

    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "user_id")
    @Fetch(FetchMode.JOIN)
    @Schema(description = "User model", requiredMode = Schema.RequiredMode.REQUIRED)
    @ManyToOne(targetEntity = User.class, cascade = CascadeType.DETACH)
    private User user;
}