package de.maddin.multitime;

/**
 * Class containing all relevant values, that never change.
 */
public class Constants {

    private Constants() {}

    public static final String COMMAND = "time";
    public static final int BSTATS_PLUGIN_ID = 10918;
    public static final int SPIGOT_PLUGIN_ID = 90642;
    public static final String SPIGOT_API_LINK = "https://api.spigotmc.org/legacy/update.php?resource=";

    /**
     * This enum holds the named time parameters and its values.
     */
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
}
