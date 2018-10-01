package be.kdg.processor.models.fines;

public enum FineType {
    SPEED_FINE("Speed fine"), EMMISSON_FINE("Emission fine");

    private String type;

    FineType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
