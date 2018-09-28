package be.kdg.processor.services;

import be.kdg.processor.models.CameraMessage;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class XMLService {
    public static CameraMessage unmarshel(String xmlString) {
        XmlMapper xmlMapper = new XmlMapper();
        CameraMessage cameraMessage = null;

        try {
            xmlMapper.registerModule(new JavaTimeModule());
            cameraMessage = xmlMapper.readValue(xmlString, CameraMessage.class);
        } catch (IOException e) {
            e.printStackTrace();
            //TODO: throw eception
            //TODO: log exception
        }

        return cameraMessage;
    }
}
