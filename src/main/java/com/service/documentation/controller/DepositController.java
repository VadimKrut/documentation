package com.service.documentation.controller;

import com.service.documentation.dto.DepositRequestDto;
import com.service.documentation.dto.DepositResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Path("/deposit")
@Tag(name = "Deposit", description = "API responsible for deposit operations, allowing clients to create deposit requests and retrieve transaction details such as amounts, currency, time, and other payment information.")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApiResponse(responseCode = "" + HttpServletResponse.SC_INTERNAL_SERVER_ERROR, description = "An error occurred, this functionality is temporarily unavailable")
public interface DepositController {

    @Operation(summary = "Create a deposit",
            description = "Initiates a deposit transaction.")
    @POST
    @ApiResponses(value = {
            @ApiResponse(responseCode = "" + HttpServletResponse.SC_OK, description = "The deposit was successful",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DepositResponseDto.class))),
            @ApiResponse(responseCode = "" + HttpServletResponse.SC_BAD_REQUEST, description = """
                * amount is null or invalid
                * currency is missing
                * payment_type is missing
                * notification_url is missing
                * order_id is missing
                * depositRequest is null
                """)
    })
    DepositResponseDto createDeposit(
            @Parameter(description = "Deposit request details", required = true) @RequestBody DepositRequestDto depositRequest
    );
}