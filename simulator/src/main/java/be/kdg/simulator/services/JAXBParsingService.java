package be.kdg.simulator.services;

import be.kdg.simulator.model.CameraMessage;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

@Component
public class JAXBParsingService {
    public static String marshel(CameraMessage objectToParse){
        try {
            JAXBContext context = JAXBContext.newInstance(objectToParse.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter sw = new StringWriter();
            marshaller.marshal(objectToParse, sw);
            return sw.toString();
        } catch (JAXBException e) {
            //TODO: log exception
            //TODO: Throw exception
        }
        return null;
    }
}
