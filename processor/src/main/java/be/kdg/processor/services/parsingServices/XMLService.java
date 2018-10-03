package be.kdg.processor.services.parsingServices;

import be.kdg.processor.models.cameras.CameraMessage;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class XMLService {
    private static final Logger LOGGER = LoggerFactory.getLogger(XMLService.class);

    public static <T> Object unmarshel(String xmlString, Class<T> objectClass) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        Object object;

        try {
            xmlMapper.registerModule(new JavaTimeModule());
            object = xmlMapper.readValue(xmlString, objectClass);
        } catch (IOException ioe) {
            LOGGER.error("Someting went wrong while unmarsheling an xml string to an object " + objectClass.getSimpleName() + ".");
            throw ioe;
        }

        return object;
    }
}
