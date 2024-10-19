package com.service.documentation.service.impl;

import com.service.documentation.entity.Appeal;
import com.service.documentation.entity.Deposit;
import com.service.documentation.entity.User;
import com.service.documentation.entity.Withdraw;
import com.service.documentation.enums.AppealStatus;
import com.service.documentation.enums.TransactionStatus;
import com.service.documentation.repository.AppealRepository;
import com.service.documentation.repository.DepositRepository;
import com.service.documentation.repository.PersonRepository;
import com.service.documentation.repository.WithdrawRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final DepositRepository depositRepository;
    private final PersonRepository personRepository;
    private final WithdrawRepository withdrawRepository;
    private final AppealRepository appealRepository;

    @Transactional
    public void updateDeposit() {
        List<Deposit> deposits = depositRepository.findAllForUpdateStatus();
        if (!CollectionUtils.isEmpty(deposits)) {
            deposits.forEach(deposit -> {
                TransactionStatus status = getRandomTransactionStatus();
                deposit.setStatus(status.name());
                if (status.equals(TransactionStatus.COMPLETED)) {
                    User user = personRepository.findById(deposit.getUser().getId()).orElseThrow();
                    if (deposit.getMoney() != null) {
                        user.setBalance(user.getBalance() + deposit.getMoney());
                    }
                    personRepository.save(user);
                }
            });
            depositRepository.saveAll(deposits);
        }
    }

    @Transactional
    public void updateWithdraw() {
        List<Withdraw> withdraws = withdrawRepository.findAllForUpdateStatus();
        if (!CollectionUtils.isEmpty(withdraws)) {
            withdraws.forEach(withdraw -> {
                TransactionStatus status = getRandomTransactionStatus();
                withdraw.setStatus(status.name());
                if (status.equals(TransactionStatus.CANCELED)) {
                    User user = personRepository.findById(withdraw.getUser().getId()).orElseThrow();
                    if (withdraw.getMoney() != null) {
                        user.setBalance(user.getBalance() + withdraw.getMoney());
                    }
                    personRepository.save(user);
                }
            });
            withdrawRepository.saveAll(withdraws);
        }
    }

    @Transactional
    public void randomStatusAppeal() {
        List<Appeal> appeals = appealRepository.findAll();
        if (!CollectionUtils.isEmpty(appeals)) {
            appeals.forEach(appeal -> {
                AppealStatus status = getRandomAppealStatus();
                appeal.setStatus(status.name());
            });
            appealRepository.saveAll(appeals);
        }
    }

    private TransactionStatus getRandomTransactionStatus() {
        return new Random().nextBoolean() ? TransactionStatus.CANCELED : TransactionStatus.COMPLETED;
    }

    private AppealStatus getRandomAppealStatus() {
        AppealStatus[] statuses = AppealStatus.values();
        return statuses[new Random().nextInt(statuses.length)];
    }
}