package be.kdg.processor.services;

import be.kdg.processor.models.cameras.CameraMessage;
import be.kdg.processor.models.fines.EmissionFine;
import be.kdg.processor.models.fines.FineType;
import be.kdg.processor.models.fines.SpeedFine;
import org.springframework.stereotype.Service;

@Service
public class BelgiumFineService implements FineService {
    @Override
    public EmissionFine createEmissionFine(double amount, int ownerEuroNorm, int legalEuroNorm, CameraMessage emmisionMessage) {
        EmissionFine emissionFine = new EmissionFine(FineType.EMISSiON_FINE, amount, ownerEuroNorm, legalEuroNorm, emmisionMessage);
        
    }

    @Override
    public SpeedFine createSpeedFine(double amount, double carSpeed, double legalSpeed, CameraMessage enterCamera, CameraMessage exitCamera) {
        return null;
    }
}
