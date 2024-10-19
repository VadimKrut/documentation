package com.service.documentation.service.impl;

import com.service.documentation.dto.BalanceResponseDto;
import com.service.documentation.entity.User;
import com.service.documentation.repository.PersonRepository;
import com.service.documentation.service.BalanceService;
import com.service.documentation.util.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService {

    private final Random random = new Random();
    private final PersonRepository personRepository;

    @Override
    @Transactional
    public BalanceResponseDto getBalance() {
        BalanceResponseDto response = new BalanceResponseDto();
        User user = personRepository.findById(CurrentUser.getCurrentUser()).orElseThrow();
        response.setBalance(user.getBalance());
//        // Генерация случайного баланса
//        double balance = 1000 + (5000 - 1000) * random.nextDouble();
//        response.setBalance(BigDecimal.valueOf(balance).setScale(8, BigDecimal.ROUND_HALF_UP).doubleValue());

        return response;
    }
}