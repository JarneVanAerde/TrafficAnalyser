package be.kdg.simulator.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class XMLService {
    private static final Logger LOGGER = LoggerFactory.getLogger(XMLService.class);

    /**
     * Uses a Jackson library to convert objects into xml-format
     * DateTime needs to be converted using a JavaTimeModule.
     *
     * @param objectToMarshel message to convert
     * @return converted message
     */
    public static String marshel(Object objectToMarshel) throws JsonProcessingException {
        XmlMapper xmlMapper = new XmlMapper();
        String xml = "";

        try {
            xmlMapper.registerModule(new JavaTimeModule());
            xmlMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            xml =  xmlMapper.writeValueAsString(objectToMarshel);
        } catch (IOException ioe) {
            LOGGER.error("Marsheling of " + objectToMarshel.getClass().getSimpleName() + " has failed.");
            throw ioe;
        }

        return xml;
    }
}
