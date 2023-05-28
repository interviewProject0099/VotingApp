package com.app.forecast.controller;

import com.app.forecast.dto.LocationInfoResponseDayItem;
import com.app.forecast.dto.LocationInfoResponseDaysList;
import com.app.forecast.exception.InvalidCoordinatesException;
import com.app.forecast.service.ForecastService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ForecastController.class)
public class ForecastControllerTest {

    public static final LocalDateTime LOCAL_DATE_TIME = LocalDateTime.now();
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS");
    public static final String LATITUDE = "12.34";
    public static final String LONGITUDE = "56.78";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ForecastService forecastServiceMock;

    @Test
    void shouldReturnForecastByCoordinates() throws Exception {
        //given
        String formattedDateTime = LOCAL_DATE_TIME.format(DATE_TIME_FORMATTER);

        List<LocationInfoResponseDayItem> locationInfoList = List.of(
                new LocationInfoResponseDayItem(LOCAL_DATE_TIME, 0.304),
                new LocationInfoResponseDayItem(LOCAL_DATE_TIME, 0.113),
                new LocationInfoResponseDayItem(LOCAL_DATE_TIME, 0.0),
                new LocationInfoResponseDayItem(LOCAL_DATE_TIME, 0.0),
                new LocationInfoResponseDayItem(LOCAL_DATE_TIME, 0.0),
                new LocationInfoResponseDayItem(LOCAL_DATE_TIME, 0.0),
                new LocationInfoResponseDayItem(LOCAL_DATE_TIME, 0.0)
        );

        when(forecastServiceMock.getForecastByCoordinates(LATITUDE, LONGITUDE))
                .thenReturn(new LocationInfoResponseDaysList(locationInfoList));

        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/forecast/v1/last-week")
                        .param("latitude", LATITUDE)
                        .param("longitude", LONGITUDE)
                        .contentType(MediaType.APPLICATION_JSON))

                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.locationInfoParDayList", hasSize(locationInfoList.size())))
                .andExpect(jsonPath("$.locationInfoParDayList[0].sunrise", is(formattedDateTime)))
                .andExpect(jsonPath("$.locationInfoParDayList[0].averagePrecipitation", is(0.304)))
                .andExpect(jsonPath("$.locationInfoParDayList[1].sunrise", is(formattedDateTime)))
                .andExpect(jsonPath("$.locationInfoParDayList[1].averagePrecipitation", is(0.113)))
                .andExpect(jsonPath("$.locationInfoParDayList[2].sunrise", is(formattedDateTime)))
                .andExpect(jsonPath("$.locationInfoParDayList[2].averagePrecipitation", is(0.0)))
                .andExpect(jsonPath("$.locationInfoParDayList[3].sunrise", is(formattedDateTime)))
                .andExpect(jsonPath("$.locationInfoParDayList[3].averagePrecipitation", is(0.0)))
                .andExpect(jsonPath("$.locationInfoParDayList[4].sunrise", is(formattedDateTime)))
                .andExpect(jsonPath("$.locationInfoParDayList[4].averagePrecipitation", is(0.0)))
                .andExpect(jsonPath("$.locationInfoParDayList[5].sunrise", is(formattedDateTime)))
                .andExpect(jsonPath("$.locationInfoParDayList[5].averagePrecipitation", is(0.0)))
                .andExpect(jsonPath("$.locationInfoParDayList[6].sunrise", is(formattedDateTime)))
                .andExpect(jsonPath("$.locationInfoParDayList[6].averagePrecipitation", is(0.0)));
    }

    @Test
    void shouldReturnBadRequestWhenThrowInvalidCoordinatesException() throws Exception {
        // given
        when(forecastServiceMock.getForecastByCoordinates(LATITUDE, LONGITUDE))
                .thenThrow(new InvalidCoordinatesException("Invalid coordinates"));

        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/forecast/v1/last-week")
                        .param("latitude", LATITUDE)
                        .param("longitude", LONGITUDE)
                        .contentType(MediaType.APPLICATION_JSON))

                //then
                .andExpect(status().isBadRequest());
    }

}