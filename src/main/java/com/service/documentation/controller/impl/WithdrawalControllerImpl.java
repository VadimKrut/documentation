package com.service.documentation.controller.impl;

import com.service.documentation.controller.WithdrawalController;
import com.service.documentation.dto.WithdrawalRequestDto;
import com.service.documentation.dto.WithdrawalResponseDto;
import com.service.documentation.service.WithdrawalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WithdrawalControllerImpl implements WithdrawalController {

    private final WithdrawalService service;

    @Override
    public WithdrawalResponseDto createWithdrawal(WithdrawalRequestDto withdrawalRequest) {
        return service.createWithdrawal(withdrawalRequest);
    }
}