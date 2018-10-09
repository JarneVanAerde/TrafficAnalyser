package be.kdg.processor.services.api;

import be.kdg.processor.models.licensePlates.LicensePlateInfoDTO;
import be.kdg.sa.services.InvalidLicensePlateException;
import be.kdg.sa.services.LicensePlateNotFoundException;

import java.io.IOException;

public interface LicensePlateServiceAdatpter {
    LicensePlateInfoDTO get(String plateId) throws IOException, LicensePlateNotFoundException, InvalidLicensePlateException;
}
