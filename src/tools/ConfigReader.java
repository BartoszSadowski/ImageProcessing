package tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigReader {

    public enum Methods {THREADS, THREADS_LIMITED, NO_THREADS, EXECUTOR}

    public static Methods getMethod(){
        String method ="";
        File configFile = new File("src/config/config.properties");

        try {
            FileReader reader = new FileReader(configFile);
            Properties props = new Properties();
            props.load(reader);

            method = props.getProperty("method");

            System.out.println("method is: " + method);
            reader.close();
        } catch (FileNotFoundException ex) {
            // file does not exist
        } catch (IOException ex) {
            // I/O error
        }

        Methods methodValue = Methods.valueOf(method);
        return methodValue;
    }
}
