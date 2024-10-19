package com.service.documentation.controller;

import com.service.documentation.entity.Session;
import com.service.documentation.security.JwtFilter;
import com.service.documentation.security.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Path("/auth")
@Tag(name = "auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface AuthController {

    @Operation(summary = "Авторизация пользователя", operationId = "auth", description = "Метод для получения нового access token'а.\n\n"
            + "Для ресурсов требующих авторизации, access token должен передаваться в заголовке "
            + HttpHeaders.AUTHORIZATION + " со схемой установления подлинности Bearer, " + "либо в заголовке "
            + HttpHeaders.COOKIE + " с именем " + JwtFilter.COOKIE_NAME + ". "
            + "Если в оба этих заголовка установлены значения, то " + HttpHeaders.AUTHORIZATION
            + " имеет приоритет.\n\n"
            + "**ВАЖНО:** в Swagger UI кука с токеном не отображается, так как у неё установлен флаг HttpOnly, но тем не менее она корректно запоминается браузером и участвует в последующих запросах.")
    @ApiResponse(responseCode = ""
            + HttpServletResponse.SC_OK, description = "В случае успешного выполнения запроса, сервер вернёт ответ с кодом "
            + HttpServletResponse.SC_OK + ", cookie с именем " + JwtFilter.COOKIE_NAME
            + " в заголовке и сессию пользователя в теле ответа. Access токен имеет срок годности "
            + JwtTokenUtil.ACCESS_TOKEN_VALIDITY
            + " минут. Refresh токен имеет неограниченный срок действия.", content = @Content(mediaType = "application/json"), headers = @Header(name = HttpHeaders.SET_COOKIE, description = JwtFilter.COOKIE_NAME
            + ": access token пользователя", schema = @Schema(type = "string")))
    @ApiResponse(responseCode = "" + HttpServletResponse.SC_NOT_FOUND, description = "Пользователь не найден")
    @POST
    @Path("/login")
    Session login(
            @Parameter(required = true, description = "логин пользователя", schema = @Schema(example = "admin"))
            @QueryParam("login") String login,
            @Parameter(required = true, description = "пароль пользователя", schema = @Schema(example = "admin"))
            @QueryParam("password") String password);

    @Operation(summary = "Обновление access token", operationId = "refresh", description = "Метод получения нового access token для указанного refresh token")
    @ApiResponse(responseCode = ""
            + HttpServletResponse.SC_OK, description = "В случае успешного выполнения запроса, сервер вернёт обновлённую сессию пользователя", content = @Content(mediaType = "application/json"), headers = @Header(name = HttpHeaders.SET_COOKIE, description = JwtFilter.COOKIE_NAME
            + ": access token пользователя", schema = @Schema(type = "string")))
    @ApiResponse(responseCode = "" + HttpServletResponse.SC_BAD_REQUEST, description = "Refresh token не задан")
    @ApiResponse(responseCode = ""
            + HttpServletResponse.SC_NOT_FOUND, description = "Сессия с данным refresh token'ом не найдена")
    @POST
    @Path("/refresh")
    Session refreshToken(
            @Parameter(required = true, description = "Refresh token")
            @QueryParam("refreshToken") String refreshToken);

    @Operation(summary = "Закрытие пользовательской сессии",
            operationId = "logout",
            description = "Завершение текущей пользовательской сессии. После выполнения данного запроса текущий api_token удаляется из БД и все последующие запросы с этим токеном будут возвращать код ошибки "
                    + HttpServletResponse.SC_FORBIDDEN + " и сообщение 'Invalid token'")
    @ApiResponse(responseCode = "" + HttpServletResponse.SC_NO_CONTENT, description = "В случае успешного выполнения запроса, сервер вернёт просроченный cookie с именем "
            + JwtFilter.COOKIE_NAME + " и с пустым значением.",
            headers = @Header(name = HttpHeaders.SET_COOKIE, description = JwtFilter.COOKIE_NAME + ": пустая просроченная кука для очистки значения на клиенте", schema = @Schema(type = "string")))
    @ApiResponse(responseCode = "" + HttpServletResponse.SC_UNAUTHORIZED, description = "Не авторизован")
    @DELETE
    @Path("/logout")
    void logout();
}