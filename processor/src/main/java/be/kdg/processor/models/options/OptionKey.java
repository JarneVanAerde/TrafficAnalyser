package be.kdg.processor.models.options;

public enum OptionKey {
    EMISSION_FAC("Emission faculty"), SPEED_FAC("Speed faculty");

    private String name;

    OptionKey(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
