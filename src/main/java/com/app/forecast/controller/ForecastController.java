package com.app.forecast.controller;

import com.app.forecast.dto.LocationInfoResponseDaysList;
import com.app.forecast.service.ForecastService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("forecast/v1/last-week")
@RequiredArgsConstructor
public class ForecastController {

    private final ForecastService forecastService;

    @GetMapping
    public ResponseEntity<LocationInfoResponseDaysList> getForecastByCoordinates(
            @RequestParam String latitude,
            @RequestParam String longitude) {
        return new ResponseEntity<>(forecastService.getForecastByCoordinates(latitude, longitude), HttpStatus.OK);
    }
}
