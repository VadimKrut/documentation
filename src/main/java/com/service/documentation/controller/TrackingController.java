package com.service.documentation.controller;

import com.service.documentation.dto.TrackingDto;
import com.service.documentation.dto.TypeTracking;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Path("/tracking")
@Tag(name = "tracking")
@Produces(MediaType.APPLICATION_JSON)
@ApiResponse(responseCode = "" + HttpServletResponse.SC_INTERNAL_SERVER_ERROR, description = "An error occurred, this functionality is temporarily unavailable")
public interface TrackingController {

    @Operation(summary = "Получить результат по номеру (orderId или appleId для заявки)", description = "Получить результат по номеру (orderId или appleId для заявки)")
    @GET
    @Path("/{number}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "" + HttpServletResponse.SC_OK, description = "the request was successful",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TrackingDto.class))),
            @ApiResponse(responseCode = "" + HttpServletResponse.SC_BAD_REQUEST, description = """
                    * Номер (orderId или appleId для заявки) не указан
                    * Тип трекинга не указан
                    """),
            @ApiResponse(responseCode = "" + HttpServletResponse.SC_NOT_FOUND, description = "Документ не найден по заданному номеру"),
    })
    TrackingDto getTracking(
            @Parameter(description = "Номер (orderId или appleId для заявки)", required = true) @PathParam("number") String number,
            @Parameter(description = "Тип", required = true) @QueryParam("typeTracking") TypeTracking typeTracking
    );
}