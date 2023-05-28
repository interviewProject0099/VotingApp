package com.app.forecast.validator;

import com.app.forecast.exception.InvalidCoordinatesException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CoordinatesValidator {

    public static void validateCoordinates(String latitude, String longitude) {
        try {
            double lat = Double.parseDouble(latitude);
            double lon = Double.parseDouble(longitude);

            if (lat < -90 || lat > 90) {
                throw new InvalidCoordinatesException("Invalid latitude value. Latitude must be between -90 and 90.");
            }

            if (lon < -180 || lon > 180) {
                throw new InvalidCoordinatesException("Invalid longitude value. Longitude must be between -180 and 180.");
            }
        } catch (NumberFormatException exception) {
            throw new InvalidCoordinatesException("Invalid coordinate format. Latitude and longitude must be numeric values.");
        }
    }
}
