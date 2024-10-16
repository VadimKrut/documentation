package com.service.documentation.service;

import com.service.documentation.dto.BalanceResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface BalanceService {

    BalanceResponseDto getBalance();
}