package com.service.documentation.service;

import com.service.documentation.dto.WithdrawalRequestDto;
import com.service.documentation.dto.WithdrawalResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface WithdrawalService {

    WithdrawalResponseDto createWithdrawal(WithdrawalRequestDto withdrawalRequest);
}