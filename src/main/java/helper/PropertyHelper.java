package helper;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyHelper {

    private final static Logger logger = Logger.getLogger(PropertyHelper.class);

    private static final String propertyPath = "config.properties";
    private static final String PROPERTY_ERROR = "Could not find property";

    public static final String PROPERTY_GENERAL_IP = "GENERAL_IP";
    public static final String PROPERTY_GENERAL_PORT = "GENERAL_PORT";

    public static final String PROPERTY_ANIMATION_PORT = "ANIMATION_PORT";
    public static final String PROPERTY_ANIMATION_IP = "ANIMATION_IP";



    public static String readProperty(String key) {
        String value;

        //Read in properties
        Properties prop = new Properties();
        InputStream inputStream = null;
        try {

            inputStream = PropertyHelper.class.getClassLoader().getResourceAsStream(propertyPath);
            prop.load(inputStream);

            value = prop.getProperty(key);

        } catch(NullPointerException npe) {
            logger.error("Could not find properties file", npe);
            value = PROPERTY_ERROR;
        } catch(IOException ex) {
            logger.error("Could not access properties file", ex);
            value = PROPERTY_ERROR;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("Could not close properties file", e);
                }
            }
        }
        //logger.info("Read value: " + key + " = " + value);
        return value;
    }
}
