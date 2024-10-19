package com.service.documentation.controller;

import com.service.documentation.entity.User;
import com.service.documentation.enums.SortDirection;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Path("/person")
@Tag(name = "person")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApiResponse(responseCode = "" + HttpServletResponse.SC_FORBIDDEN, description = "Access denied")
public interface PersonController {

    @Operation(summary = "Получение пользователя по id",
            description = "Получение пользователя по id, доступно для роли ADMIN")
    @GET
    @Path("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "" + HttpServletResponse.SC_OK, description = "the request was successful",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "" + HttpServletResponse.SC_BAD_REQUEST, description = "id user is null"),
            @ApiResponse(responseCode = "" + HttpServletResponse.SC_NOT_FOUND, description = "User not found"),
    })
    @PreAuthorize("hasAuthority('ADMIN')")
    User findById(
            @Parameter(description = "id user", required = true) @PathParam("id") Long id
    );

    @Operation(summary = "Получение пользователей",
            description = "Получение пользователей, доступно для роли ADMIN")
    @GET
    @ApiResponses(value = {
            @ApiResponse(responseCode = "" + HttpServletResponse.SC_OK, description = "the request was successful",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(type = "array", implementation = User.class)))),
    })
    @PreAuthorize("hasAuthority('ADMIN')")
    List<User> findAllByParams(
            @Parameter(description = "Page number")
            @QueryParam("pageNum") Integer pageNum,
            @Parameter(description = "Page Size")
            @QueryParam("pageSize") Integer pageSize,
            @Parameter(description = "Sorting field")
            @QueryParam("sortField") String sortField,
            @Parameter(description = "Sort order (default ASC)")
            @QueryParam("sortDirection") SortDirection sortDirection,
            @Parameter(description = "login user")
            @QueryParam("login") String login
    );

    @Operation(summary = "Получение текущего пользователя",
            description = "Получение текущего пользователя, доступно для роли USER, ADMIN")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GET
    @Path("/mySelf")
    User findMySelf();

    @Operation(summary = "Создание пользователя", description = "Создание пользователя, доступно для роли ADMIN")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "" + HttpServletResponse.SC_OK, description = "the request was successful",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "" + HttpServletResponse.SC_BAD_REQUEST, description = """
                    * Поле login не должно быть пустым
                    * Поле login имеет диапозон от 5 до 50
                    * Поле login должно быть уникальным
                    * Поле password не должно быть пустым
                    """)
    })
    @POST
    User create(
            @Parameter(description = "UserDto") @RequestBody User user
    );

    @Operation(summary = "Обновление пользователя", description = "Обновление пользователя, доступно для роли ADMIN")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "" + HttpServletResponse.SC_OK, description = "the request was successful",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "" + HttpServletResponse.SC_BAD_REQUEST, description = """
                    * id user is null
                    * Поле login не должно быть пустым
                    * Поле login имеет диапозон от 5 до 50
                    * Поле login должно быть уникальным
                    * Поле password не должно быть пустым
                    """),
            @ApiResponse(responseCode = "" + HttpServletResponse.SC_NOT_FOUND, description = "User not found"),
            @ApiResponse(responseCode = "" + HttpServletResponse.SC_CONFLICT, description = "Нельзя обновить админа")
    })
    @PUT
    User update(
            @Parameter(description = "UserDto") @RequestBody User user
    );

    @Operation(summary = "Удаление текущего пользователя",
            description = "Удаление текущего пользователя, доступно для роли USER")
    @PreAuthorize("hasAuthority('USER')")
    @DELETE
    void delete();

    @Operation(summary = "Удаление пользователя по id",
            description = "Удаление пользователя по id, доступно для роли ADMIN")
    @DELETE
    @Path("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "" + HttpServletResponse.SC_OK, description = "the request was successful",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "" + HttpServletResponse.SC_BAD_REQUEST, description = "id user is null"),
            @ApiResponse(responseCode = "" + HttpServletResponse.SC_NOT_FOUND, description = "User not found"),
            @ApiResponse(responseCode = "" + HttpServletResponse.SC_CONFLICT, description = "Нельзя удалить админа")
    })
    @PreAuthorize("hasAuthority('ADMIN')")
    void deleteById(
            @Parameter(description = "id user", required = true) @PathParam("id") Long id
    );
}