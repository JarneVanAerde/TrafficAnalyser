package be.kdg.processor.models.options;

/**
 * Enum-key to make searching for options in the database easier.
 */
public enum OptionKey {
    EMISSION_FAC("Emission faculty"), SPEED_FAC("Speed faculty"), TIME_FRAME_EMISSION("Time-buffer emission fines (hours)"),
    TIME_FRAME_SPEED_MESSAGE("Time-buffer speed messages (minutes)"), RETRY_DELAY("Delay retry (seconds)"),
    RETRY_ATTEMPTS("Number of retry attempts");

    private String name;

    OptionKey(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
