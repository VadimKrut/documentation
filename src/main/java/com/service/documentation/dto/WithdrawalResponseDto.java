package com.service.documentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO for withdrawal response")
public class WithdrawalResponseDto {

    @Schema(description = "Withdrawal amount", example = "10000")
    private Double amount;

    @Schema(description = "Currency for withdrawal", example = "USDT")
    private String currency;

    @Schema(description = "Transaction ID", example = "trc20 transaction id")
    private String transaction_id;
}