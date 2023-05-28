package com.app.forecast.service;


import com.app.forecast.apiclient.ForecastWebClient;
import com.app.forecast.dto.DayData;
import com.app.forecast.dto.ForecastResponseData;
import com.app.forecast.dto.HourData;
import com.app.forecast.dto.LocationInfoResponseDayItem;
import com.app.forecast.repository.EndpointCallsLoggerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ForecastServiceTest {

    private static final int NUMBER_OF_DAYS = 7;
    public static final LocalDateTime NOW = LocalDateTime.now();
    public static final String LATITUDE = "12.34";
    public static final String LONGITUDE = "56.78";


    @Mock
    private ForecastWebClient forecastWebClientMock;

    @Mock
    private EndpointCallsLoggerRepository endpointCallsLoggerRepositoryMock;

    @InjectMocks
    private ForecastService classUnderTest;

    @Test
    void shouldReturnLocationInfoResponseDaysList() {
        // given
        List<LocalDateTime> sunriseList = getSunrisesList();
        List<Double> precipitationList = getPrecipitationsList();
        ForecastResponseData forecastData = new ForecastResponseData(new DayData(sunriseList), new HourData(precipitationList));

        when(forecastWebClientMock.getForecastByCoordinates(LATITUDE, LONGITUDE)).thenReturn(forecastData);

        // when
        List<LocationInfoResponseDayItem> result = classUnderTest.getForecastByCoordinates(LATITUDE, LONGITUDE).locationInfoParDayList();

        // then
        assertThat(result).hasSize(7);
        assertThat(result.get(0).sunrise()).isEqualTo(NOW.minusDays(NUMBER_OF_DAYS));
        assertThat(result.get(0).averagePrecipitation()).isEqualTo(0.054);
        assertThat(result.get(1).sunrise()).isEqualTo(NOW.minusDays(NUMBER_OF_DAYS - 1));
        assertThat(result.get(1).averagePrecipitation()).isEqualTo(0.05);
        assertThat(result.get(6).sunrise()).isEqualTo(NOW.minusDays(NUMBER_OF_DAYS - 6));
        assertThat(result.get(6).averagePrecipitation()).isEqualTo(0.0);
        verify(endpointCallsLoggerRepositoryMock, times(1)).save(any());
    }

    private List<LocalDateTime> getSunrisesList() {
        List<LocalDateTime> sunriseList = new ArrayList<>();
        LocalDateTime currentDate = NOW.minusDays(NUMBER_OF_DAYS);

        for (int i = 0; i < NUMBER_OF_DAYS; i++) {
            sunriseList.add(currentDate);
            currentDate = currentDate.plusDays(1);
        }

        return sunriseList;
    }

    private List<Double> getPrecipitationsList() {
        return Arrays.asList(
                0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.1, 0.4, 0.5, 0.1, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 0.2, 0.2, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.2, 0.3, 0.1, 0.0, 0.0, 0.0, 0.1, 0.0, 0.0,
                0.1, 0.1, 0.0, 0.1, 0.0, 0.0, 0.0, 0.0, 0.0, 0.1, 0.1, 0.1, 0.1, 0.1, 0.0, 0.0, 0.1, 0.1, 0.1, 0.1,
                0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.1, 0.0, 0.0, 0.1, 0.0, 0.0, 0.0, 0.1, 0.1, 0.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                0.0,0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null
        );
    }
}