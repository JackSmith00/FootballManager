package interfaces;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Allows an object to read a saved version of itself from storage and take on its data
 * @author Jack
 *
 */
public interface Reader {

	/**
	 * Allows an object and its contents to be loaded from a saved location when application is ran
	 * @param path : A string representation of the path to the location to load the object from
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void load(String path) throws FileNotFoundException, IOException, ClassNotFoundException;
	
}
