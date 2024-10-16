package com.service.documentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO for appeal request")
public class AppealRequestDto {

    @Schema(description = "Appeal amount", example = "10000", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long amount;

    @Schema(description = "Order ID", example = "7f723c70-0c00-47ae-8c45-a29d29f88a93", requiredMode = Schema.RequiredMode.REQUIRED)
    private String order_id;
}