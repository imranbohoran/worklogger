package com.tib.worklogger.clients.cli;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CliDurationParserTest {

    @ParameterizedTest(name = "{index} => value={0}, duration={1}, unit={2}")
    @MethodSource("durationData")
    void shouldParseDurationAmountAndUnit(String value, int duration, ChronoUnit unit) {
        CliDurationParser.CliDuration parsedDuration = CliDurationParser.parse(value);
        assertThat(parsedDuration).isNotNull();
        assertThat(parsedDuration.duration).isEqualTo(duration);
        assertThat(parsedDuration.unit).isEqualTo(unit);
    }

    private static Stream<Arguments> durationData() {
        return Stream.of(
            Arguments.of("12m", 12, ChronoUnit.MINUTES),
            Arguments.of("12M", 12, ChronoUnit.MINUTES),
            Arguments.of("13h", 13, ChronoUnit.HOURS),
            Arguments.of("13H", 13, ChronoUnit.HOURS),
            Arguments.of("13d", 13, ChronoUnit.DAYS),
            Arguments.of("10s", 10, ChronoUnit.SECONDS)
        );
    }

    @Test
    void shouldThrowRuntimeExceptionForUnsupportedDurationUnit() {
        RuntimeException exception =
            assertThrows(RuntimeException.class, () -> CliDurationParser.parse("12f"));

        assertThat(exception.getMessage()).isEqualTo("Unsupported duration unit: f");
    }
}
