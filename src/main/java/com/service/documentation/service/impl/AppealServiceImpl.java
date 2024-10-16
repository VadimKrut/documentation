package com.service.documentation.service.impl;

import com.service.documentation.dto.AppealRequestDto;
import com.service.documentation.dto.AppealResponseDto;
import com.service.documentation.exception.ApiException;
import com.service.documentation.service.AppealService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;

@Service
public class AppealServiceImpl implements AppealService {

    @Override
    public AppealResponseDto createAppeal(AppealRequestDto appealRequest, InputStream fileInputStream) {
        if (appealRequest.getAmount() == null) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "amount is null");
        }
        if (appealRequest.getOrder_id() == null) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "order_id is null");
        }

        AppealResponseDto response = new AppealResponseDto();

        // Генерация случайного appeal_id
        response.setAppeal_id(UUID.randomUUID().toString());

        return response;
    }
}