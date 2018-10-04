package be.kdg.processor.services.parsingServices;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class JSONService {
    private static final Logger LOGGER = LoggerFactory.getLogger(JSONService.class);

    public static <T> Object unmarshal(String jsonString, Class<T> objectClass) throws IOException {
        Object object;

        try {
            object = new ObjectMapper().readValue(jsonString, objectClass);
        } catch (IOException ioe) {
            LOGGER.error("Someting went wrong while unmarsheling an json string to an object " + objectClass.getSimpleName() + ".");
            throw ioe;
        }

        return object;
    }
}
