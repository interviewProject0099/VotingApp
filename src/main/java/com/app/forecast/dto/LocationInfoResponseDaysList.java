package com.app.forecast.dto;

import java.util.List;

public record LocationInfoResponseDaysList(

        List<LocationInfoResponseDayItem> locationInfoParDayList
) {
}
