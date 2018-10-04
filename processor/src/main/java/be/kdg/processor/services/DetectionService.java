package be.kdg.processor.services;

import be.kdg.sa.services.CameraNotFoundException;
import be.kdg.sa.services.LicensePlateNotFoundException;

import java.io.IOException;

public interface DetectionService<E> {
    void detectFine(E message) throws IOException, LicensePlateNotFoundException, CameraNotFoundException;
}
