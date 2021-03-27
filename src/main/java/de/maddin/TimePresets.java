package de.maddin;

public enum TimePresets {

    DAY(1000), NOON(6000), NIGHT(13000), MIDNIGHT(18000);

    private final int ticks;

    TimePresets(int ticks) {
        this.ticks = ticks;
    }

    public int getTicks() {
        return ticks;
    }

    public static boolean contains(String test) {

        for (TimePresets t : TimePresets.values()) {
            if (t.name().equalsIgnoreCase(test)) {
                return true;
            }
        }
        return false;
    }
}
