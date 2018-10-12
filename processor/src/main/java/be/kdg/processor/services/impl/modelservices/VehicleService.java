package be.kdg.processor.services.impl.modelservices;

import be.kdg.processor.models.licensePlates.LicensePlateInfoDTO;
import be.kdg.processor.models.vehicles.Vehicle;
import be.kdg.processor.models.vehicles.VehicleOwner;
import be.kdg.processor.persistence.VehicleOwnerRepository;
import be.kdg.processor.persistence.VehicleRepository;
import be.kdg.processor.services.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final VehicleOwnerRepository vehicleOwnerRepository;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository, VehicleOwnerRepository vehicleOwnerRepository) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleOwnerRepository = vehicleOwnerRepository;
    }

    public void extractPlateInfo(LicensePlateInfoDTO licensePlateInfoDTO) throws PersistenceException {
        Vehicle vehicle;
        VehicleOwner vehicleOwner;
        boolean ownerEx = vehicleOwnerRepository.existsById(licensePlateInfoDTO.getNationalNumber());
        boolean vehicleEx = vehicleRepository.existsById(licensePlateInfoDTO.getPlateId());

        if (!ownerEx && !vehicleEx) {
            vehicle = new Vehicle(licensePlateInfoDTO.getPlateId(), licensePlateInfoDTO.getEuroNumber());
            vehicleOwner = new VehicleOwner(licensePlateInfoDTO.getNationalNumber());
            vehicleOwner.addVehicle(vehicle);
            vehicleOwnerRepository.save(vehicleOwner);
        } else if (ownerEx && !vehicleEx) {
            vehicle = new Vehicle(licensePlateInfoDTO.getPlateId(), licensePlateInfoDTO.getEuroNumber());
            vehicleOwner = getOwner(licensePlateInfoDTO.getNationalNumber());
            vehicleOwner.addVehicle(vehicle);
            vehicleOwnerRepository.save(vehicleOwner);
        }
    }

    public Vehicle getVehicle(String plateId) throws PersistenceException {
        return vehicleRepository.findById(plateId)
                .orElseThrow(() -> new PersistenceException("Vehicle with plate id " + plateId + " wasn't found in the database"));
    }

    public VehicleOwner getOwner(String nationalId) throws PersistenceException {
        return vehicleOwnerRepository.findById(nationalId)
                .orElseThrow(() -> new PersistenceException("VehicleOwner with plate id " + nationalId + " wasn't found in the database"));
    }

    public Vehicle saveVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }
}
