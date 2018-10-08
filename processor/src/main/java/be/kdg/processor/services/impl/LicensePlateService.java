package be.kdg.processor.services.impl;

import be.kdg.processor.models.licensePlates.LicensePlateInfoDTO;
import be.kdg.processor.persistence.VehicleOwnerRepository;
import be.kdg.processor.persistence.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LicensePlateService {
    private final VehicleRepository vehicleRepository;
    private final VehicleOwnerRepository vehicleOwnerRepository;

    @Autowired
    public LicensePlateService(VehicleRepository vehicleRepository, VehicleOwnerRepository vehicleOwnerRepository) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleOwnerRepository = vehicleOwnerRepository;
    }

    public void extractInformation(LicensePlateInfoDTO licensePlateInfoDTO) {
        extractVehicleData(licensePlateInfoDTO.getPlateId(), licensePlateInfoDTO.getEuroNumber());
        extractVehicleOwnerData(licensePlateInfoDTO.getNationalNumber());
    }

    private void extractVehicleData(String plateId, int euroNumber) {
        //TODO implement logic
    }

    private void extractVehicleOwnerData(String nationalNumber) {
        //TODO: implement logic
    }
}
