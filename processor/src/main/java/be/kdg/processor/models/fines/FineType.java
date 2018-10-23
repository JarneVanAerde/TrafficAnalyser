package be.kdg.processor.models.fines;

/**
 * Enum that is used for declaring the different fine types.
 */
public enum FineType {
    SPEED_FINE("Speed fine"), EMISSION_FINE("Emission fine");

    private String type;

    FineType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
