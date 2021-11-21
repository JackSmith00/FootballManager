package interfaces;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface Saveable {

	public void save(String path) throws FileNotFoundException, IOException;
	
}
