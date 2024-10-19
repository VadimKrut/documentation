package com.service.documentation.service.impl;

import com.service.documentation.dto.TrackingDto;
import com.service.documentation.dto.TypeTracking;
import com.service.documentation.entity.Appeal;
import com.service.documentation.entity.Deposit;
import com.service.documentation.entity.Withdraw;
import com.service.documentation.exception.ApiException;
import com.service.documentation.repository.AppealRepository;
import com.service.documentation.repository.DepositRepository;
import com.service.documentation.repository.WithdrawRepository;
import com.service.documentation.service.TrackingService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TrackingServiceImpl implements TrackingService {

    private final AppealRepository appealRepository;
    private final DepositRepository depositRepository;
    private final WithdrawRepository withdrawRepository;

    @Override
    @Transactional
    public TrackingDto getTracking(String number, TypeTracking typeTracking) {
        if (number == null || number.isEmpty()) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "Номер (orderId или appleId для заявки) не указан");
        }
        if (typeTracking == null) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "Тип трекинга не указан");
        }
        if (typeTracking.equals(TypeTracking.APPEAL)) {
            Appeal appeal = appealRepository.findByOrderId(number).orElseThrow(
                    () -> new ApiException(HttpServletResponse.SC_NOT_FOUND, "Документ не найден по заданному номеру"
                    ));
            return TrackingDto.builder()
                    .orderId(appeal.getOrderId())
                    .status(appeal.getStatus())
                    .money(appeal.getMoney())
                    .build();
        } else if (typeTracking.equals(TypeTracking.DEPOSIT)) {
            Deposit deposit = depositRepository.findByOrderId(number).orElseThrow(
                    () -> new ApiException(HttpServletResponse.SC_NOT_FOUND, "Документ не найден по заданному номеру"
                    ));
            return TrackingDto.builder()
                    .orderId(deposit.getOrderId())
                    .status(deposit.getStatus())
                    .money(deposit.getMoney())
                    .build();
        } else if (typeTracking.equals(TypeTracking.WITHDRAW)) {
            Withdraw withdraw = withdrawRepository.findByOrderId(number).orElseThrow(
                    () -> new ApiException(HttpServletResponse.SC_NOT_FOUND, "Документ не найден по заданному номеру"
                    ));
            return TrackingDto.builder()
                    .orderId(withdraw.getOrderId())
                    .status(withdraw.getStatus())
                    .money(withdraw.getMoney())
                    .build();
        }
        return null;
    }
}