package com.app.forecast.dto;

import java.util.List;

public record HourData(

        List<Double> precipitation
) {
}
