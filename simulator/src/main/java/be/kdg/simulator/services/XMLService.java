package be.kdg.simulator.services;

import be.kdg.simulator.model.CameraMessage;
import com.fasterxml.jackson.xml.XmlMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class XMLService {
    public static String marshel(CameraMessage cameraMessage) {
        XmlMapper xmlMapper = new XmlMapper();
        String xml = "";
        try {
            xml =  xmlMapper.writeValueAsString(cameraMessage);
        } catch (IOException e) {
            e.printStackTrace();
            //TODO: log exception
            //TODO: throw exception
        }
        return xml;
    }
}
