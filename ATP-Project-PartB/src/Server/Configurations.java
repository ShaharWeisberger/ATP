package Server;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configurations {
    private static Configurations singleton = new Configurations( );
    //private int mazeRows =20;
    //private int mazeColumns =20;
    private int maxThread = 10;
    private String mazeGeneratingAlgorithm = "DFS";
    private String mazeSearchingAlgorithm = "BEST_FS";

    private Configurations() {
        try (InputStream input = new FileInputStream("resources/config.properties")) {
            Properties prop = new Properties();
            // load a properties file
            prop.load(input);
            //get the property value and print it out
            try {
                //mazeRows = Integer.parseInt(prop.getProperty("mazeRows"));
                //mazeColumns = Integer.parseInt(prop.getProperty("mazeColumns"));
                maxThread = Integer.parseInt(prop.getProperty("threadPoolSize"));
                mazeGeneratingAlgorithm = prop.getProperty("mazeGeneratingAlgorithm");
                mazeSearchingAlgorithm = prop.getProperty("mazeSearchingAlgorithm");
            }
            catch (Exception e){
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /* Static 'instance' method */
    public static Configurations getInstance( ) {
        return singleton;
    }

    public int getMaxThread() {
        return maxThread;
    }

    public String getMazeGeneratingAlgorithm() {
        return mazeGeneratingAlgorithm;
    }

    public String getMazeSearchingAlgorithm() {
        return mazeSearchingAlgorithm;
    }
}
