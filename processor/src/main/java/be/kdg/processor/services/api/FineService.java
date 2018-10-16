package be.kdg.processor.services.api;

import be.kdg.processor.models.cameras.CameraMessage;
import be.kdg.processor.models.fines.EmissionFine;
import be.kdg.processor.models.fines.Fine;
import be.kdg.processor.models.fines.SpeedFine;
import be.kdg.processor.services.exceptions.PersistenceException;

import java.util.List;

public interface FineService {
    void createEmissionFine(double amount, int ownerEuroNorm, int legalEuroNorm, CameraMessage emmisionMessage, String plateId) throws PersistenceException;

    void createSpeedFine(double amount, double carSpeed, double legalSpeed, CameraMessage enterCamera, CameraMessage exitCamera, String plateId) throws PersistenceException;

    boolean checkIfAlreadyHasEmissionfine(String plateId) throws PersistenceException;

    List<Fine> getFines();

    Fine getFine(int id) throws PersistenceException;
}
