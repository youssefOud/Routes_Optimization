package entities;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Parser for the xml document which contains the city plan or the document xml
 * which contains the delivery points
 *
 * @author PLD-HEXA-301
 */
public class Parser {

    /**
     * Default constructor of the class Parser
     */
    public Parser() {
    }

    /**
     * Parses the xml document located in the path given in parameter. Parses
     * the xml document which represents a city plan only.
     *
     * @param xmlFileName the path of the xml file (.xml) to parse
     * @return the object Network which is the name of the root element of the
     * document xml. The object contains all the data of the xml document.
     */
    public Network parseCityPlan(String xmlFileName) {
        if (xmlFileName.endsWith(".xml")) {
            ObjectMapper objectMapper = new XmlMapper();

            objectMapper.enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            objectMapper.enable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);

            Network res = null;
            try {
                res = objectMapper.readValue(StringUtils.toEncodedString(Files.readAllBytes(Paths.get(xmlFileName)), StandardCharsets.UTF_8), Network.class);
            } catch (JsonParseException ex) {
                Logger.getLogger(Parser.class.getName()).log(Level.FINE, null, ex);
                return null;
            } catch (JsonMappingException ex) {
                Logger.getLogger(Parser.class.getName()).log(Level.FINE, null, ex);
                return null;
            } catch (IOException ex) {
                Logger.getLogger(Parser.class.getName()).log(Level.FINE, null, ex);
                return null;
            }
            return res;
        } else {
            return null;
        }
    }

    /**
     * Parses the xml document located in the path given in parameter. Parses
     * the xml document which represents delivery requests only
     *
     * @param xmlFileName the path of the xml (.xml) file to parse
     * @return the object DeliveryRequest which is the name of the root
     * element of the document xml. The object contains all the data of the xml
     * document.
     */
    public DeliveryRequest parseDelivery(String xmlFileName) {
        if (xmlFileName.endsWith(".xml")) {
            ObjectMapper objectMapper = new XmlMapper();
            objectMapper.enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            objectMapper.enable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);

            DeliveryRequest ddl = null;
            try {
                ddl = objectMapper.readValue(StringUtils.toEncodedString(Files.readAllBytes(Paths.get(xmlFileName)), StandardCharsets.UTF_8), DeliveryRequest.class);
            } catch (JsonParseException ex) {
                Logger.getLogger(Parser.class.getName()).log(Level.FINE, null, ex);
                return null;
            } catch (JsonMappingException ex) {
                Logger.getLogger(Parser.class.getName()).log(Level.FINE, null, ex);
                return null;
            } catch (IOException ex) {
                Logger.getLogger(Parser.class.getName()).log(Level.FINE, null, ex);
                return null;
            }
            return ddl;
        } else {
            return null;
        }
    }
}
