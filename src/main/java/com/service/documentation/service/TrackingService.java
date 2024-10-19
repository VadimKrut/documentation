package com.service.documentation.service;

import com.service.documentation.dto.TrackingDto;
import com.service.documentation.dto.TypeTracking;
import org.springframework.stereotype.Service;

@Service
public interface TrackingService {

    TrackingDto getTracking(String number, TypeTracking typeTracking);
}