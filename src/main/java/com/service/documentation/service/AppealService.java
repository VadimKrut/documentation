package com.service.documentation.service;

import com.service.documentation.dto.AppealRequestDto;
import com.service.documentation.dto.AppealResponseDto;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public interface AppealService {

    AppealResponseDto createAppeal(AppealRequestDto appealRequest, InputStream fileInputStream);
}