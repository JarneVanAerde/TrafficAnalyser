package be.kdg.processor.services.parsingServices;

import be.kdg.processor.models.cameras.Camera;
import be.kdg.processor.models.licensePlateInfos.LicensePlateInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class JSONService {
    public static <T> Object unmarshal(String jsonString, Class<T> objectClass) {
        Object object = null;

        try {
            object = new ObjectMapper().readValue(jsonString, objectClass);
        } catch (IOException e) {
            e.printStackTrace();
            e.printStackTrace();
            //TODO: log exception
            //TODO: throw exception
        }

        return object;
    }
}
