package com.service.documentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TrackingDto {

    @Schema(description = "Number of order")
    private String orderId;

    @Schema(
            description = "status"
    )
    @Column(name = "status")
    private String status;

    @Schema(description = "Money")
    private Double money;
}