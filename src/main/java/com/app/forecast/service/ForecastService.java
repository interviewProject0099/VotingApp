package com.app.forecast.service;

import com.app.forecast.apiclient.ForecastWebClient;
import com.app.forecast.dto.ForecastResponseData;
import com.app.forecast.dto.LocationInfoResponseDayItem;
import com.app.forecast.dto.LocationInfoResponseDaysList;
import com.app.forecast.entity.EndpointCall;
import com.app.forecast.repository.EndpointCallsLoggerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

import static com.app.forecast.validator.CoordinatesValidator.validateCoordinates;

@Service
@Slf4j
@RequiredArgsConstructor
public class ForecastService {

    private static final int NUMBER_OF_MEASUREMENTS = 24;
    private static final int NUMBER_OF_DAYS = 7;

    private final ForecastWebClient forecastWebClient;
    private final EndpointCallsLoggerRepository endpointCallsLoggerRepository;

    @Cacheable(cacheNames = "ForecastByCoordinates")
    public LocationInfoResponseDaysList getForecastByCoordinates(String latitude, String longitude) {
        endpointCallsLoggerRepository.save(new EndpointCall(latitude, longitude));
        validateCoordinates(latitude, longitude);

        final ForecastResponseData forecastData = forecastWebClient.getForecastByCoordinates(latitude, longitude);
        return new LocationInfoResponseDaysList(countAveragePrecipitation(
                        forecastData.daily().sunrise(),
                        forecastData.hourly().precipitation()
                ));
    }

    private List<LocationInfoResponseDayItem> countAveragePrecipitation(
            List<LocalDateTime> sunriseList,
            List<Double> precipitationList) {

        return IntStream.range(0, NUMBER_OF_DAYS)
                .mapToObj(day -> {
                    final LocalDateTime sunrise = sunriseList.get(day);
                    final List<Double> precipitationPerDay = precipitationList.subList(
                            day * NUMBER_OF_MEASUREMENTS, (day + 1) * NUMBER_OF_MEASUREMENTS);

                    double precipitationSum = 0;
                    try {
                        precipitationSum = precipitationPerDay.stream().mapToDouble(Double::doubleValue).sum();
                    } catch (NullPointerException exception) {
                        log.warn("Precipitation per day contains null for date: {}, setting 0 for precipitationSum",
                                sunrise.toLocalDate());
                    }
                    double precipitationAverage = Math.round((precipitationSum / NUMBER_OF_MEASUREMENTS) * 1000.0) / 1000.0;
                    return new LocationInfoResponseDayItem(sunrise, precipitationAverage);
                })
                .toList();
    }
}
