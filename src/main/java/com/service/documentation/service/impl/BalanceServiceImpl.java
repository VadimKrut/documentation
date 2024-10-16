package com.service.documentation.service.impl;

import com.service.documentation.dto.BalanceResponseDto;
import com.service.documentation.service.BalanceService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

@Service
public class BalanceServiceImpl implements BalanceService {

    private final Random random = new Random();

    @Override
    public BalanceResponseDto getBalance() {
        BalanceResponseDto response = new BalanceResponseDto();

        // Генерация случайного баланса
        double balance = 1000 + (5000 - 1000) * random.nextDouble();
        response.setBalance(BigDecimal.valueOf(balance).setScale(8, BigDecimal.ROUND_HALF_UP).doubleValue());

        return response;
    }
}