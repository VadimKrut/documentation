package com.service.documentation.service.impl;

import com.service.documentation.dto.WithdrawalRequestDto;
import com.service.documentation.dto.WithdrawalResponseDto;
import com.service.documentation.exception.ApiException;
import com.service.documentation.service.WithdrawalService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class WithdrawalServiceImpl implements WithdrawalService {

    private final Random random = new Random();

    @Override
    public WithdrawalResponseDto createWithdrawal(WithdrawalRequestDto withdrawalRequest) {

        if (withdrawalRequest == null) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "withdrawalRequest is null");
        }

        // Проверка обязательных полей
        if (withdrawalRequest.getAmount() == null || withdrawalRequest.getAmount() <= 0) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "amount is missing or invalid");
        }
        if (withdrawalRequest.getCurrency() == null || withdrawalRequest.getCurrency().isEmpty()) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "currency is missing");
        }
        if (withdrawalRequest.getWallet() == null || withdrawalRequest.getWallet().isEmpty()) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "wallet address is missing");
        }

        WithdrawalResponseDto response = new WithdrawalResponseDto();

        // Установка значения суммы
        response.setAmount(withdrawalRequest.getAmount());

        // Установка валюты
        response.setCurrency(withdrawalRequest.getCurrency());

        // Генерация случайного transaction_id
        response.setTransaction_id(generateTransactionId());

        return response;
    }

    // Метод для генерации transaction_id
    private String generateTransactionId() {
        return "trc20 " + UUID.randomUUID().toString();
    }
}