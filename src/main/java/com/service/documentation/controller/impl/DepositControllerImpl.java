package com.service.documentation.controller.impl;

import com.service.documentation.controller.DepositController;
import com.service.documentation.dto.DepositRequestDto;
import com.service.documentation.dto.DepositResponseDto;
import com.service.documentation.service.DepositService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DepositControllerImpl implements DepositController {

    private final DepositService depositService;

    @Override
    public DepositResponseDto createDeposit(DepositRequestDto depositRequest) {
        return depositService.createDeposit(depositRequest);
    }
}