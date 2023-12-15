package ru.job4j.grabber.utils;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.*;
class HabrCareerDateTimeParserTest {
    @Test
    void dateTimeTestOne() {
        HabrCareerDateTimeParser parser = new HabrCareerDateTimeParser();
        String dateTime = "2023-12-10T10:27:52+03:00";
        assertThat(parser.parse(dateTime))
                .isEqualTo(LocalDateTime.of(2023, 12, 10, 10, 27, 52));
    }

    @Test
    void dateTimeTestTwo() {
        HabrCareerDateTimeParser parser = new HabrCareerDateTimeParser();
        String dateTime = "2021-02-11T15:00:00+03:00";
        assertThat(parser.parse(dateTime))
                .isEqualTo(LocalDateTime.of(2021, 2, 11, 15, 0, 0));
    }

    @Test
    void dateTimeTestThree() {
        HabrCareerDateTimeParser parser = new HabrCareerDateTimeParser();
        String dateTime = "2024-01-21T00:00:00+03:00";
        assertThat(parser.parse(dateTime))
                .isEqualTo(LocalDateTime.of(2024, 1, 21, 0, 0, 0));
    }
}