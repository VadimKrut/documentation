package com.service.documentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO for deposit request")
public class DepositRequestDto {

    @Schema(description = "Deposit amount", example = "10000", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long amount;

    @Schema(description = "Currency of the deposit", example = "RUB", requiredMode = Schema.RequiredMode.REQUIRED)
    private String currency;

    @Schema(description = "Type of payment", example = "TO_CARD", requiredMode = Schema.RequiredMode.REQUIRED)
    private String payment_type;

    @Schema(description = "Notification URL", example = "api.something.callback.url", requiredMode = Schema.RequiredMode.REQUIRED)
    private String notification_url;

    @Schema(description = "Order ID", example = "1a0c6e8b-8974-4062-9cea-2b34a6412226", requiredMode = Schema.RequiredMode.REQUIRED)
    private String order_id;
}