package interfaces;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface Reader {

	public void load(String path) throws FileNotFoundException, IOException, ClassNotFoundException;
	
}
