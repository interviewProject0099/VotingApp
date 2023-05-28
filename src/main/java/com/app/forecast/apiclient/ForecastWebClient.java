package com.app.forecast.apiclient;

import com.app.forecast.dto.ForecastResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class ForecastWebClient {

    private final WebClient webClient;

    @Value("${forecast.api.url}")
    private String apiUrl;
    private static final String URI_TEMPLATE = "%s&latitude=%s&longitude=%s&start_date=%s&end_date=%s";

    public ForecastResponseData getForecastByCoordinates(String latitude, String longitude) {
        final String startDate = LocalDate.now().minusDays(8L).toString();
        final String endDate = LocalDate.now().minusDays(1L).toString();
        final String uri = String.format(URI_TEMPLATE, apiUrl, latitude, longitude, startDate, endDate);

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(ForecastResponseData.class)
                .block();
    }
}
