package be.kdg.processor.services;

import be.kdg.processor.models.cameras.CameraMessage;
import be.kdg.processor.models.fines.EmissionFine;
import be.kdg.processor.models.fines.SpeedFine;

public class BelgiumFineService implements FineService {
    @Override
    public EmissionFine createEmissionFine(double amount, int ownerEuroNorm, int legalEuroNorm, CameraMessage emmisionMessage) {
        return null;
    }

    @Override
    public SpeedFine createSpeedFine(double amount, double carSpeed, double legalSpeed, CameraMessage enterCamera, CameraMessage exitCamera) {
        return null;
    }
}
