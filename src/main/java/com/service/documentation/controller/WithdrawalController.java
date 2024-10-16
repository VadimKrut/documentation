package com.service.documentation.controller;

import com.service.documentation.dto.WithdrawalRequestDto;
import com.service.documentation.dto.WithdrawalResponseDto;
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
@Path("/withdrawal")
@Tag(name = "Withdrawal", description = "Operations related to withdrawals")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApiResponse(responseCode = "" + HttpServletResponse.SC_INTERNAL_SERVER_ERROR, description = "An error occurred, this functionality is temporarily unavailable")
public interface WithdrawalController {

    @Operation(summary = "Create a withdrawal",
            description = "Initiates a withdrawal transaction.")
    @POST
    @ApiResponses(value = {
            @ApiResponse(responseCode = "" + HttpServletResponse.SC_OK, description = "The withdrawal was successful",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = WithdrawalResponseDto.class))),
            @ApiResponse(responseCode = "" + HttpServletResponse.SC_BAD_REQUEST, description = """
                * amount is missing or invalid
                * currency is missing
                * wallet address is missing
                * withdrawalRequest is null
                """)
    })
    WithdrawalResponseDto createWithdrawal(
            @Parameter(description = "Withdrawal request details", required = true) @RequestBody WithdrawalRequestDto withdrawalRequest
    );
}