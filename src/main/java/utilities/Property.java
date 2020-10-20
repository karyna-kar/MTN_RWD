package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


public class Property {
    public static String getAuthProperty(String propKey) {
        FileInputStream fis;
        String prop = "";
        try {
            Properties property = new Properties();
            fis = new FileInputStream("src/main/resources/application.properties");
            property.load(fis);
            prop = property.getProperty(propKey);

        } catch (FileNotFoundException e) {
            Logs.error("Properties file is absent", e);
        } catch (IOException e) {
            Logs.error(e);
            System.out.println(e.getMessage());
        }
        return prop;
    }

    public static String getDBProperty(String propKey) {
        FileInputStream fis;
        String prop = "";
        try {
            Properties property = new Properties();
            fis = new FileInputStream("src/main/resources/application.properties");
            property.load(fis);
            prop = property.getProperty(propKey);

        } catch (FileNotFoundException e) {
            Logs.error("Properties file is absent", e);
        } catch (IOException e) {
            Logs.error(e);
            System.out.println(e.getMessage());
        }
        return prop;
    }
}
