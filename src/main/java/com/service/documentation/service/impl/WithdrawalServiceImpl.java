package com.service.documentation.service.impl;

import com.service.documentation.dto.WithdrawalRequestDto;
import com.service.documentation.dto.WithdrawalResponseDto;
import com.service.documentation.entity.User;
import com.service.documentation.entity.Withdraw;
import com.service.documentation.enums.MoneyCurrency;
import com.service.documentation.enums.TransactionStatus;
import com.service.documentation.exception.ApiException;
import com.service.documentation.repository.PersonRepository;
import com.service.documentation.repository.WithdrawRepository;
import com.service.documentation.service.WithdrawalService;
import com.service.documentation.util.CheckEnums;
import com.service.documentation.util.CurrentUser;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.UUID;

@Service
public class WithdrawalServiceImpl implements WithdrawalService {

    private final Random random = new Random();
    private final PersonRepository personRepository;
    private final WithdrawRepository withdrawRepository;

    public WithdrawalServiceImpl(PersonRepository personRepository, WithdrawRepository withdrawRepository) {
        this.personRepository = personRepository;
        this.withdrawRepository = withdrawRepository;
    }

    @Override
    @Transactional
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

        CheckEnums.validateMoneyCurrency(withdrawalRequest.getCurrency());// Проверка валюты

        WithdrawalResponseDto response = new WithdrawalResponseDto();

        Double money = 0D;
        String orderId = generateTransactionId();

        if (withdrawalRequest.getCurrency().equalsIgnoreCase(MoneyCurrency.RUB.name())) {
            money = (withdrawalRequest.getAmount() / 90);
        } else {
            money = withdrawalRequest.getAmount();
        }

        Withdraw withdraw = new Withdraw();
        withdraw.setMoney(money);
        withdraw.setStatus(TransactionStatus.NEW.name());
        withdraw.setOrderId(orderId);
        User user = personRepository.findById(CurrentUser.getCurrentUser()).orElseThrow();
        if (user.getBalance() < money) {
            throw new ApiException(HttpServletResponse.SC_CONFLICT, "insufficient balance");
        }
        user.setBalance(user.getBalance() - money);
        personRepository.saveAndFlush(user);
        withdraw.setUser(user);
        withdrawRepository.save(withdraw);

        // Установка значения суммы
        response.setAmount(money);

        // Установка валюты
        response.setCurrency(withdrawalRequest.getCurrency());

        // Генерация случайного transaction_id
        response.setTransaction_id(orderId);

        return response;
    }

    // Метод для генерации transaction_id
    private String generateTransactionId() {
        return UUID.randomUUID().toString();
    }
}