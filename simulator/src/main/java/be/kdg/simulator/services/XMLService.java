package be.kdg.simulator.services;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class XMLService {
    public static String marshel(Object cameraMessage) {
        XmlMapper xmlMapper = new XmlMapper();
        String xml = "";
        try {
            xmlMapper.registerModule(new JavaTimeModule());
            xmlMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            xml =  xmlMapper.writeValueAsString(cameraMessage);
        } catch (IOException e) {
            e.printStackTrace();
            //TODO: log exception
            //TODO: throw exception
        }
        return xml;
    }
}
