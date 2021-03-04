package com.tib.worklogger.clients.cli;

import java.time.temporal.ChronoUnit;

public class CliDurationParser {

    public static CliDuration parse(String durationString) {
        String number = durationString.replaceAll("[\\D+]", "");
        String unit = durationString.replaceAll("[^A-Za-z]", "");
        return new CliDuration(Integer.parseInt(number), DurationUnit.getSupportedUnit(unit).unit);
    }

    public static class CliDuration {
        public final int duration;
        public final ChronoUnit unit;

        public CliDuration(int duration, ChronoUnit unit) {
            this.duration = duration;
            this.unit = unit;
        }
    }

    private enum DurationUnit {
        SECONDS("s", ChronoUnit.SECONDS),
        MINUTES("m", ChronoUnit.MINUTES),
        HOURS("h", ChronoUnit.HOURS),
        DAYS("d", ChronoUnit.DAYS);

        final String supportedValue;
        final ChronoUnit unit;

        DurationUnit(String supportedValue, ChronoUnit unit) {
            this.supportedValue = supportedValue;
            this.unit = unit;
        }

        static DurationUnit getSupportedUnit(String value) {
            for(DurationUnit unit : DurationUnit.values()) {
                if (value.equalsIgnoreCase(unit.supportedValue)) {
                    return unit;
                }
            }
            throw new RuntimeException("Unsupported duration unit: "+ value);
        }
    }
}
