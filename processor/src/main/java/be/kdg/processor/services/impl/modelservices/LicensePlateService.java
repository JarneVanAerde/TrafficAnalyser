package be.kdg.processor.services.impl.modelservices;

import be.kdg.processor.models.licensePlates.LicensePlateInfoDTO;
import be.kdg.processor.models.vehicles.Vehicle;
import be.kdg.processor.models.vehicles.VehicleOwner;
import be.kdg.processor.persistence.VehicleOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LicensePlateService {
    private final VehicleOwnerRepository vehicleOwnerRepository;

    @Autowired
    public LicensePlateService(VehicleOwnerRepository vehicleOwnerRepository) {
        this.vehicleOwnerRepository = vehicleOwnerRepository;
    }

    public void extractInformation(LicensePlateInfoDTO licensePlateInfoDTO) {
        Vehicle vehicle = new Vehicle(licensePlateInfoDTO.getPlateId(), licensePlateInfoDTO.getEuroNumber());
        VehicleOwner vehicleOwner = new VehicleOwner(licensePlateInfoDTO.getNationalNumber());
        vehicleOwner.addVehicle(vehicle);
        vehicleOwnerRepository.save(vehicleOwner);
    }
}
