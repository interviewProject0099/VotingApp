package com.app.forecast.validator;

import com.app.forecast.exception.InvalidCoordinatesException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CoordinatesValidatorTest {

    @Test
    void shouldValidateValidCoordinates() {
        // given
        String validLatitude = "12.345";
        String validLongitude = "67.890";

        // then
        assertDoesNotThrow(() -> CoordinatesValidator.validateCoordinates(validLatitude, validLongitude));
    }

    @Test
    void shouldThrowExceptionForInvalidLatitude() {
        // given
        String invalidLatitude = "100.0";
        String validLongitude = "67.890";

        // then
        assertThrows(InvalidCoordinatesException.class, () -> CoordinatesValidator.validateCoordinates(invalidLatitude, validLongitude));
    }

    @Test
    void shouldThrowExceptionForInvalidLongitude() {
        // given
        String validLatitude = "12.345";
        String invalidLongitude = "200.0";

        // then
        assertThrows(InvalidCoordinatesException.class, () -> CoordinatesValidator.validateCoordinates(validLatitude, invalidLongitude));
    }

    @Test
    void shouldThrowExceptionForInvalidCoordinateFormat() {
        // given
        String invalidLatitude = "12.345";
        String invalidLongitude = "invalid";

        // then
        assertThrows(InvalidCoordinatesException.class, () -> CoordinatesValidator.validateCoordinates(invalidLatitude, invalidLongitude));
    }
}
