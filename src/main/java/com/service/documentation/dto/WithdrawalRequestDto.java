package com.service.documentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO for withdrawal request")
public class WithdrawalRequestDto {

    @Schema(description = "Withdrawal amount", example = "10000", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long amount;

    @Schema(description = "Currency for withdrawal", example = "USDT", requiredMode = Schema.RequiredMode.REQUIRED)
    private String currency;

    @Schema(description = "Wallet address", example = "TPAgKFYzRdk83Qocc4gXvEVu4jPKfeuer5", requiredMode = Schema.RequiredMode.REQUIRED)
    private String wallet;
}