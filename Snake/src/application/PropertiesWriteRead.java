package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
/**
 * Class to read the properties file
 * @author Boldi
 *
 */
public class PropertiesWriteRead {

	private final static String PROPERTIES_PATH="src/resources/game.properties";

	/**
	 * Get value from the properties file
	 * @return GamePreferencies class
	 */
	public static GamePreferencies getproperites(){
		return readPropertiesFile(PROPERTIES_PATH);
	}

	/**
	 * Read the game.properties file
	 * @param fileName The class contains the final classpath
	 * @return GamePreferencies class
	 */
	private static GamePreferencies readPropertiesFile(String fileName) {
	      Properties prop = null;
	      GamePreferencies gp=new GamePreferencies();
	      try(FileInputStream fis = new FileInputStream(fileName)) {	         
	         prop = new Properties();
	         prop.load(fis);
	         gp.setUsername(prop.getProperty("username"));
	         gp.setSize(GamePreferencies.boardSize.valueOf(prop.getProperty("boardSize")));
	         gp.setDif(GamePreferencies.difficulty.valueOf(prop.getProperty("difficulty")));		         
	         
	      } catch(FileNotFoundException fnfe) {
	         fnfe.printStackTrace();
	      } catch(IOException ioe) {
	         ioe.printStackTrace();
	      } finally {
	         
	      }
	      return gp;
	}
	/**
	 * Write the current settings value into the game.properties
	 * @param gp Gamepreferencies class
	 */
	public static void writePropertiesFile(GamePreferencies gp) {
		 try (OutputStream output = new FileOutputStream(PROPERTIES_PATH)) {

	            Properties prop = new Properties();

	            // set the properties value
	            prop.setProperty("username", gp.getUsername());
	            prop.setProperty("boardSize", gp.getSize().toString());
	            prop.setProperty("difficulty",gp.getDif().toString());

	            // save properties to project root folder
	            prop.store(output, null);


	        } catch (IOException io) {
	            io.printStackTrace();
	        }
	}
	
}
