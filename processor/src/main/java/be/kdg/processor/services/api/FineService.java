package be.kdg.processor.services.api;

import be.kdg.processor.models.cameras.CameraMessage;
import be.kdg.processor.models.fines.Fine;
import be.kdg.processor.services.exceptions.ServiceException;

import java.util.List;

public interface FineService {
    void createEmissionFine(int ownerEuroNorm, int legalEuroNorm, CameraMessage emmisionMessage, String plateId) throws ServiceException;

    void createSpeedFine(double carSpeed, double legalSpeed, CameraMessage enterCamera, CameraMessage exitCamera, String plateId) throws ServiceException;

    boolean checkIfAlreadyHasEmissionfine(String plateId) throws ServiceException;

    Fine saveFine(Fine fine);

    List<Fine> getFines() throws ServiceException;

    Fine getFine(int id) throws ServiceException;

    Fine approveFine(int id) throws ServiceException;

    Fine changeAmount(int id, double amount, String motivation) throws ServiceException;

    Fine deleteFine(int id) throws ServiceException;
}
