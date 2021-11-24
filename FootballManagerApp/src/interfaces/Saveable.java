package interfaces;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Allows an object to save its data to a storage location to be used in a later execution
 * @author Jack
 *
 */
public interface Saveable {
	
	/**
	 * Allows an object and its contents to be saved for use the next time the application is ran
	 * @param path : A string representation of the path to the location to save the object
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void save(String path) throws FileNotFoundException, IOException;
	
}
