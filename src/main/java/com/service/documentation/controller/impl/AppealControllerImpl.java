package com.service.documentation.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.documentation.controller.AppealController;
import com.service.documentation.dto.AppealRequestDto;
import com.service.documentation.dto.AppealResponseDto;
import com.service.documentation.exception.ApiException;
import com.service.documentation.service.AppealService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class AppealControllerImpl implements AppealController {

    private final AppealService appealService;

//    @Override
//    public AppealResponseDto createAppeal(InputStream fileInputStream, FormDataContentDisposition fileMetaData, AppealRequestDto appealRequest) {
//        return appealService.createAppeal(appealRequest, fileInputStream);
//    }

    @Override
    public AppealResponseDto createAppeal(InputStream fileInputStream, FormDataContentDisposition fileMetaData, String appealRequestJson) {
        // Преобразование JSON строки в объект AppealRequestDto
        ObjectMapper objectMapper = new ObjectMapper();
        AppealRequestDto appealRequest;
        try {
            appealRequest = objectMapper.readValue(appealRequestJson, AppealRequestDto.class);
        } catch (Exception e) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "Invalid appealRequest format");
        }

        // Логика обработки
        return appealService.createAppeal(appealRequest, fileInputStream);
    }
}