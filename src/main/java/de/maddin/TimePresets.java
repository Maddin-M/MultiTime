package de.maddin;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum TimePresets {

    DAY(1000), NOON(6000), NIGHT(13000), MIDNIGHT(18000);

    private final int ticks;

    TimePresets(int ticks) {
        this.ticks = ticks;
    }

    public int getTicks() {
        return ticks;
    }

    public static List<String> getAllNames() {
        return Arrays
                .stream(TimePresets.values())
                .map(e -> e.name().toLowerCase())
                .collect(Collectors.toList());
    }
}
