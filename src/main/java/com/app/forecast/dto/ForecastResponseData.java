package com.app.forecast.dto;

public record ForecastResponseData(

        DayData daily,
        HourData hourly
) {
}