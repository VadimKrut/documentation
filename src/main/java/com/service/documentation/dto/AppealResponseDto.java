package com.service.documentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO for appeal response")
public class AppealResponseDto {

    @Schema(description = "Appeal ID", example = "02723c70-0c00-47ae-8c45-a29d29f88a93")
    private String appeal_id;
}