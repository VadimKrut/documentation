package com.service.documentation.service.impl;

import com.service.documentation.dto.AppealRequestDto;
import com.service.documentation.dto.AppealResponseDto;
import com.service.documentation.entity.Appeal;
import com.service.documentation.enums.AppealStatus;
import com.service.documentation.exception.ApiException;
import com.service.documentation.repository.AppealRepository;
import com.service.documentation.repository.PersonRepository;
import com.service.documentation.service.AppealService;
import com.service.documentation.util.CurrentUser;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.UUID;

@Service
public class AppealServiceImpl implements AppealService {

    private final AppealRepository appealRepository;
    private final PersonRepository personRepository;

    public AppealServiceImpl(AppealRepository appealRepository, PersonRepository personRepository) {
        this.appealRepository = appealRepository;
        this.personRepository = personRepository;
    }

    @Override
    @Transactional
    public AppealResponseDto createAppeal(AppealRequestDto appealRequest, InputStream fileInputStream) {
        if (appealRequest.getAmount() == null) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "amount is null");
        }
        if (appealRequest.getOrder_id() == null) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "order_id is null");
        }
        if (appealRepository.existsByOrderId(appealRequest.getOrder_id())) {
            throw new ApiException(HttpServletResponse.SC_CONFLICT, "Заявка с таким order_id уже существует");
        }

        String appealId = appealRequest.getOrder_id();

        AppealResponseDto response = new AppealResponseDto();

        response.setAppeal_id(appealId);

        Appeal appeal = new Appeal();
        appeal.setStatus(AppealStatus.NEW.name());
        appeal.setOrderId(appealId);
        appeal.setMoney(appealRequest.getAmount());
        appeal.setUser(personRepository.findById(CurrentUser.getCurrentUser()).orElseThrow());
        appealRepository.save(appeal);
        return response;
    }
}