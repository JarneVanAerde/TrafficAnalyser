package be.kdg.processor.services.api;

import be.kdg.processor.models.licensePlates.LicensePlateInfo;
import be.kdg.sa.services.InvalidLicensePlateException;
import be.kdg.sa.services.LicensePlateNotFoundException;

import java.io.IOException;

public interface LicensePlateServiceAdatpter {
    LicensePlateInfo get(String plateId) throws IOException, LicensePlateNotFoundException, InvalidLicensePlateException;
}
