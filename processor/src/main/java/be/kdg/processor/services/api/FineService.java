package be.kdg.processor.services.api;

import be.kdg.processor.models.cameras.CameraMessage;
import be.kdg.processor.models.fines.EmissionFine;
import be.kdg.processor.models.fines.SpeedFine;
import be.kdg.processor.services.exceptions.ObjectNotFoundException;

public interface FineService {
    EmissionFine createEmissionFine(double amount, int ownerEuroNorm, int legalEuroNorm, CameraMessage emmisionMessage, String plateId) throws ObjectNotFoundException;

    SpeedFine createSpeedFine(double amount, double carSpeed, double legalSpeed, CameraMessage enterCamera, CameraMessage exitCamera);

    boolean checkIfAlreadyHasEmissionfine(String plateId) throws ObjectNotFoundException;
}
