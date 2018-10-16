package be.kdg.processor.services.api;

import be.kdg.processor.models.cameras.CameraMessage;
import be.kdg.processor.models.fines.Fine;
import be.kdg.processor.services.exceptions.ServiceException;

import java.util.List;

public interface FineService {
    void createEmissionFine(double amount, int ownerEuroNorm, int legalEuroNorm, CameraMessage emmisionMessage, String plateId) throws ServiceException;

    void createSpeedFine(double amount, double carSpeed, double legalSpeed, CameraMessage enterCamera, CameraMessage exitCamera, String plateId) throws ServiceException;

    boolean checkIfAlreadyHasEmissionfine(String plateId) throws ServiceException;

    List<Fine> getFines() throws ServiceException;

    Fine getFine(int id) throws ServiceException;

    Fine approveFine(int id) throws ServiceException;
}
