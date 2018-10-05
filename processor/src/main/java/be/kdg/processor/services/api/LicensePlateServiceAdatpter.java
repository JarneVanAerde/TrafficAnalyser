package be.kdg.processor.services.api;

import be.kdg.processor.models.licensePlates.LicensePlateInfo;

public interface LicensePlateServiceAdatpter {
    LicensePlateInfo get(String plateId);
}
