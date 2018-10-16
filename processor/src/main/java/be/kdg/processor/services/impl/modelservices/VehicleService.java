package be.kdg.processor.services.impl.modelservices;

import be.kdg.processor.models.licensePlates.LicensePlateInfo;
import be.kdg.processor.models.vehicles.Vehicle;
import be.kdg.processor.models.vehicles.VehicleOwner;
import be.kdg.processor.persistence.VehicleOwnerRepository;
import be.kdg.processor.persistence.VehicleRepository;
import be.kdg.processor.services.exceptions.ServiceException;
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

    public void extractPlateInfo(LicensePlateInfo licensePlateInfo) throws ServiceException {
        Vehicle vehicle;
        VehicleOwner vehicleOwner;
        boolean ownerEx = vehicleOwnerRepository.existsById(licensePlateInfo.getNationalNumber());
        boolean vehicleEx = vehicleRepository.existsById(licensePlateInfo.getPlateId());

        if (!ownerEx && !vehicleEx) {
            vehicle = new Vehicle(licensePlateInfo.getPlateId(), licensePlateInfo.getEuroNumber());
            vehicleOwner = new VehicleOwner(licensePlateInfo.getNationalNumber());
            vehicleOwner.addVehicle(vehicle);
            vehicleOwnerRepository.save(vehicleOwner);
        } else if (ownerEx && !vehicleEx) {
            vehicle = new Vehicle(licensePlateInfo.getPlateId(), licensePlateInfo.getEuroNumber());
            vehicleOwner = getOwner(licensePlateInfo.getNationalNumber());
            vehicleOwner.addVehicle(vehicle);
            vehicleOwnerRepository.save(vehicleOwner);
        }
    }

    public Vehicle getVehicle(String plateId) throws ServiceException {
        return vehicleRepository.findById(plateId)
                .orElseThrow(() -> new ServiceException(getClass().getSimpleName() + ": Vehicle with plate id " + plateId + " wasn't found in the database"));
    }

    public VehicleOwner getOwner(String nationalId) throws ServiceException {
        return vehicleOwnerRepository.findById(nationalId)
                .orElseThrow(() -> new ServiceException(getClass().getSimpleName() + ": VehicleOwner with plate id " + nationalId + " wasn't found in the database"));
    }

    public Vehicle saveVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }
}
