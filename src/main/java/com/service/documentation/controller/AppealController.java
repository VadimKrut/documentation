package com.service.documentation.controller;

import com.service.documentation.dto.AppealRequestDto;
import com.service.documentation.dto.AppealResponseDto;
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
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;

@RestController
@Path("/appeal")
@Tag(name = "Appeal", description = "API responsible for handling appeals related to transactions.")
@Produces(MediaType.APPLICATION_JSON)
@ApiResponse(responseCode = "" + HttpServletResponse.SC_INTERNAL_SERVER_ERROR, description = "An error occurred, this functionality is temporarily unavailable")
public interface AppealController {

    @Operation(summary = "Create an appeal",
            description = "Initiates an appeal for a specific transaction and accepts an optional file related to the appeal.")
    @POST
    @ApiResponses(value = {
            @ApiResponse(responseCode = "" + HttpServletResponse.SC_OK, description = "The appeal was successfully created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AppealResponseDto.class))),
            @ApiResponse(responseCode = "" + HttpServletResponse.SC_BAD_REQUEST, description = """
                    * amount is null
                    * order_id is null
                    * Invalid appealRequest format
                    """)
    })
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    AppealResponseDto createAppeal(
            @Parameter(description = "File to be uploaded related to the appeal.")
            @FormDataParam("file") InputStream fileInputStream,

            @Parameter(description = "Metadata about the uploaded file.")
            @FormDataParam("file") FormDataContentDisposition fileMetaData,

            @Parameter(description = "Details of the appeal request including the amount and order ID.", schema = @Schema(implementation = AppealRequestDto.class), required = true)
            @FormDataParam("appealRequest") String appealRequestJson
    );
}