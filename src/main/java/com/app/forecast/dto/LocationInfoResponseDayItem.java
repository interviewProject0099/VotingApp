package com.app.forecast.dto;

import java.time.LocalDateTime;

public record LocationInfoResponseDayItem(

        LocalDateTime sunrise,
        double averagePrecipitation
        ) {
}
