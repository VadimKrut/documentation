package com.service.documentation.controller;

import com.service.documentation.dto.BalanceResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Path("/balance")
@Tag(name = "Balance", description = "API responsible for checking the user's balance.")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApiResponse(responseCode = "" + HttpServletResponse.SC_INTERNAL_SERVER_ERROR, description = "An error occurred, this functionality is temporarily unavailable")
public interface BalanceController {

    @Operation(summary = "Get current balance",
            description = "Returns the current balance of the user.")
    @GET
    @ApiResponses(value = {
            @ApiResponse(responseCode = "" + HttpServletResponse.SC_OK, description = "The request was successful",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BalanceResponseDto.class)))
    })
    BalanceResponseDto getBalance();
}