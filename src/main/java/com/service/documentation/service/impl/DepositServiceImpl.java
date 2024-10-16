package com.service.documentation.service.impl;

import com.service.documentation.dto.DepositRequestDto;
import com.service.documentation.dto.DepositResponseDto;
import com.service.documentation.exception.ApiException;
import com.service.documentation.service.DepositService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class DepositServiceImpl implements DepositService {

    private static final List<String> CURRENCIES = Arrays.asList("USDT", "USD", "RUB");
    private static final List<String> BANKS = Arrays.asList("Sberbank", "Tinkoff", "VTB", "Gazprombank", "Alfa-Bank");
    private static final List<String> NAMES = Arrays.asList("Ivanov I.I.", "Petrov P.P.", "Sidorov S.S.", "Smirnov S.V.", "Kuznetsov K.K.", "Popov P.A.", "Volkov V.V.", "Zaytsev Z.Z.", "Fedorov F.F.", "Nikolaev N.N.");
    private final Random random = new Random();

    @Override
    public DepositResponseDto createDeposit(DepositRequestDto depositRequest) {
        if (depositRequest == null) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "depositRequest is null");
        }
        // Проверка обязательных полей
        if (depositRequest.getAmount() == null || depositRequest.getAmount() <= 0) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "amount is missing or invalid");
        }
        if (depositRequest.getCurrency() == null || depositRequest.getCurrency().isEmpty()) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "currency is missing");
        }
        if (depositRequest.getPayment_type() == null || depositRequest.getPayment_type().isEmpty()) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "payment_type is missing");
        }
        if (depositRequest.getNotification_url() == null || depositRequest.getNotification_url().isEmpty()) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "notification_url is missing");
        }
        if (depositRequest.getOrder_id() == null || depositRequest.getOrder_id().isEmpty()) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "order_id is missing");
        }

        DepositResponseDto response = new DepositResponseDto();

        // Генерация UUID для invoice_id и order_id
        response.setInvoice_id(UUID.randomUUID().toString());
        response.setOrder_id(UUID.randomUUID().toString());

        // Генерация времени с текущей даты
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        response.setTime_opening(now.format(formatter));
        response.setTime_completed(now.plusHours(4).format(formatter)); // Завершение через 4 часа
        response.setTime_expire(now.plusHours(5).format(formatter)); // Истекает через 5 часов

        // Генерация случайной суммы
        double amountByCurrency = 10 + (1000 - 10) * random.nextDouble();
        response.setAmount_by_currency(BigDecimal.valueOf(amountByCurrency).setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue());

        // Генерация случайной валюты
        response.setCurrency(CURRENCIES.get(random.nextInt(CURRENCIES.size())));

        // Генерация случайного курса валюты (на 5-10% меньше суммы)
        double currencyRate = amountByCurrency * (0.90 + (0.95 - 0.90) * random.nextDouble());
        response.setCurrency_rate(BigDecimal.valueOf(currencyRate).setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue());

        // Генерация случайного банка
        response.setIssuer(BANKS.get(random.nextInt(BANKS.size())));

        // Генерация случайного имени
        response.setHolder_name(NAMES.get(random.nextInt(NAMES.size())));

        // Генерация случайного номера карты
        response.setCard_number(generateCardNumber());

        // Генерация случайного номера телефона
        response.setPhone_number("+7 904 " + generatePhoneNumber());

        return response;
    }

    // Метод для генерации номера карты
    private String generateCardNumber() {
        return String.format("%04d %04d %04d %04d",
                random.nextInt(10000),
                random.nextInt(10000),
                random.nextInt(10000),
                random.nextInt(10000));
    }

    // Метод для генерации номера телефона
    private String generatePhoneNumber() {
        return String.format("%03d %02d %02d",
                random.nextInt(1000),
                random.nextInt(100),
                random.nextInt(100));
    }
}