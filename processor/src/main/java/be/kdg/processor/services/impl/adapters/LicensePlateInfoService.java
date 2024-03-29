package be.kdg.processor.services.impl.adapters;

import be.kdg.processor.models.licensePlates.LicensePlateInfo;
import be.kdg.processor.services.api.LicensePlateServiceAdatpter;
import be.kdg.processor.services.exceptions.ServiceException;
import be.kdg.sa.services.InvalidLicensePlateException;
import be.kdg.sa.services.LicensePlateNotFoundException;
import be.kdg.sa.services.LicensePlateServiceProxy;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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

    /**
     * License plates are cached to save time
     *
     * @param plateId for the requested license plate
     * @return a license plate form the service
     * @throws ServiceException wrapper-exception
     */
    @Cacheable(value = "licensePlates")
    @Override
    public LicensePlateInfo get(String plateId) throws ServiceException {
        try {
            String licenseJson = licensePlateServiceProxy.get(plateId);
            return objectMapper.readValue(licenseJson, LicensePlateInfo.class);
        } catch (IOException | LicensePlateNotFoundException | InvalidLicensePlateException e) {
            throw new ServiceException(getClass().getSimpleName() + ": " + e.getMessage());
        }
    }
}
