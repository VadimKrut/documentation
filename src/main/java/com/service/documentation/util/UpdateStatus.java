package com.service.documentation.util;

import com.service.documentation.service.impl.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateStatus {

    private final TransactionService transactionService;

    // Задача, которая выполняется каждый час
    @Scheduled(cron = "0 0 * * * ?")
    public void updateDepositAndWithdrawEveryHour() {
        transactionService.updateDeposit();
        transactionService.updateWithdraw();
    }

    // Задача, которая выполняется каждые полчаса
    @Scheduled(cron = "0 0,30 * * * ?")
    public void removeContainsEveryHalfHour() {
        transactionService.randomStatusAppeal();
    }

    //    // Задача, которая выполняется каждые два часа
//    @Scheduled(cron = "0 0 */2 * * ?")
//    public void removeContainsEveryTwoHours() {
//        // Ваш код
//    }
//
//    // Задача, которая выполняется каждую минуту
//    @Scheduled(cron = "0 * * * * ?")
//    public void removeContainsEveryMinute() {
//        System.out.println("Начало");
//        long startTime = System.currentTimeMillis();
//        transactionService.updateDeposit();
//        transactionService.updateWithdraw();
//        transactionService.randomStatusAppeal();
//        System.out.println("Кончало " + (System.currentTimeMillis() - startTime) + " ms");
//    }
}