package be.kdg.processor.services;

import be.kdg.sa.services.CameraNotFoundException;
import be.kdg.sa.services.InvalidLicensePlateException;
import be.kdg.sa.services.LicensePlateNotFoundException;

import java.io.IOException;

public interface DetectionService<T> {
    void detectFine(T message) throws IOException, LicensePlateNotFoundException, CameraNotFoundException, InvalidLicensePlateException;
}
