package com.service.documentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO for deposit response")
public class DepositResponseDto {

    @Schema(description = "Invoice ID", example = "818f9561-2cbe-48af-9980-3c2de84f758f")
    private String invoice_id;

    @Schema(description = "Order ID", example = "1a0c6e8b-8974-4062-9cea-2b34a6412226")
    private String order_id;

    @Schema(description = "Time when deposit was opened", example = "2024-10-10 19:05:01")
    private String time_opening;

    @Schema(description = "Time when deposit was completed", example = "2024-10-10 23:05:01")
    private String time_completed;

    @Schema(description = "Time when deposit expires", example = "2024-10-10 23:55:01")
    private String time_expire;

    @Schema(description = "Amount by currency", example = "101.1122345083042")
    private Double amount_by_currency;

    @Schema(description = "Currency type", example = "USDT")
    private String currency;

    @Schema(description = "Currency rate", example = "98.5")
    private Double currency_rate;

    @Schema(description = "Issuer of the payment", example = "Sberbank")
    private String issuer;

    @Schema(description = "Holder name", example = "Ivanov I.I.")
    private String holder_name;

    @Schema(description = "Card number", example = "5432 5123 6125 9842")
    private String card_number;

    @Schema(description = "Phone number", example = "+7 904 141 43 15")
    private String phone_number;
}