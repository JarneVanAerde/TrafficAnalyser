package be.kdg.processor.services;

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

    public static CameraMessage unmarshel(String xmlString) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        CameraMessage cameraMessage;

        try {
            xmlMapper.registerModule(new JavaTimeModule());
            cameraMessage = xmlMapper.readValue(xmlString, CameraMessage.class);
        } catch (IOException ioe) {
            LOGGER.error("Someting went wrong while unmarsheling an xml string to an object.");
            throw ioe;
        }

        return cameraMessage;
    }
}
