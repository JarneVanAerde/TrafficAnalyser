package be.kdg.processor.services.api;

import be.kdg.processor.models.licensePlates.LicensePlateInfo;
import be.kdg.processor.services.exceptions.ServiceException;

public interface LicensePlateServiceAdatpter {
    LicensePlateInfo get(String plateId) throws ServiceException;
}
