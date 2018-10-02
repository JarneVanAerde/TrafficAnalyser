package be.kdg.processor.services.parsingServices;

import be.kdg.processor.models.cameras.Camera;
import be.kdg.processor.models.licensePlateInfos.LicensePlateInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class JSONService {
    public static LicensePlateInfo jsonToLicensePlateInfo(String jsonString) {
        LicensePlateInfo licensePlateInfo = null;

        try {
            licensePlateInfo = new ObjectMapper().readValue(jsonString, LicensePlateInfo.class);
        } catch (IOException e) {
            e.printStackTrace();
            //TODO: log exception
            //TODO: throw exception
        }

        return licensePlateInfo;
    }

    public static Camera jsonToCamera(String jsonString) {
        Camera camera = null;

        try {
            camera = new ObjectMapper().readValue(jsonString, Camera.class);
        } catch (IOException e) {
            e.printStackTrace();
            //TODO: log exception
            //TODO: throw exception
        }

        return camera;
    }
}
