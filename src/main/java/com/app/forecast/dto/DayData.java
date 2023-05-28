package com.app.forecast.dto;

import java.time.LocalDateTime;
import java.util.List;

public record DayData(

        List<LocalDateTime> sunrise
) {
}