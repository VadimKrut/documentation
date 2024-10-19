package com.service.documentation.controller.impl;

import com.service.documentation.controller.TrackingController;
import com.service.documentation.dto.TrackingDto;
import com.service.documentation.dto.TypeTracking;
import com.service.documentation.service.TrackingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrackingControllerImpl implements TrackingController {

    private final TrackingService trackingService;

    @Override
    public TrackingDto getTracking(String number, TypeTracking typeTracking) {
        return trackingService.getTracking(number, typeTracking);
    }
}