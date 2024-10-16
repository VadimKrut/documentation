package com.service.documentation.controller.impl;

import com.service.documentation.controller.BalanceController;
import com.service.documentation.dto.BalanceResponseDto;
import com.service.documentation.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BalanceControllerImpl implements BalanceController {

    private final BalanceService balanceService;

    @Override
    public BalanceResponseDto getBalance() {
        return balanceService.getBalance();
    }
}