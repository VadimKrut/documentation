package com.service.documentation.service;

import com.service.documentation.dto.DepositRequestDto;
import com.service.documentation.dto.DepositResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface DepositService {

    DepositResponseDto createDeposit(DepositRequestDto depositRequest);
}