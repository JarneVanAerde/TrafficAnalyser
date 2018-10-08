package be.kdg.processor.services.impl;

import be.kdg.processor.models.licensePlates.LicensePlateInfo;
import be.kdg.processor.services.api.LicensePlateServiceAdatpter;
import be.kdg.sa.services.InvalidLicensePlateException;
import be.kdg.sa.services.LicensePlateNotFoundException;
import be.kdg.sa.services.LicensePlateServiceProxy;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Adapter used for the LicensePlateServiceProxy.
 */
@Service
public class LicensePlateInfoService implements LicensePlateServiceAdatpter {
    private final LicensePlateServiceProxy licensePlateServiceProxy;
    private final ObjectMapper objectMapper;

    @Autowired
    public LicensePlateInfoService(LicensePlateServiceProxy licensePlateServiceProxy, ObjectMapper objectMapper) {
        this.licensePlateServiceProxy = licensePlateServiceProxy;
        this.objectMapper = objectMapper;
    }

    @Override
    public LicensePlateInfo get(String plateId) throws IOException, LicensePlateNotFoundException, InvalidLicensePlateException {
        String licenseJson = licensePlateServiceProxy.get(plateId);
        return objectMapper.readValue(licenseJson, LicensePlateInfo.class);
    }
}
