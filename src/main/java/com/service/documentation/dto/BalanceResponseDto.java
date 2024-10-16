package com.service.documentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO for balance response")
public class BalanceResponseDto {

    @Schema(description = "Current balance", example = "1939.93891947")
    private Double balance;
}