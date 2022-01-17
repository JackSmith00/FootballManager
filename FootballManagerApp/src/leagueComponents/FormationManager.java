package leagueComponents;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.TreeSet;

/**
 * Used to manage the storage and retrieval of
 * formations, regardless of the league they
 * are being used in
 * 
 * @author Jack
 *
 */
public final class FormationManager implements Serializable {

	private static TreeSet<Formation> formations = load(); // holds all formations created
	
	/**
	 * Allows created formations to be saved for future use
	 * 
	 * @throws FileNotFoundException when the given file cannot be found
	 * @throws IOException when any error occurs in the input/output process
	 */
	public static void save() throws FileNotFoundException, IOException {
		FileOutputStream fileStream = new FileOutputStream("./data/formationData");
		ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
		objectStream.writeObject(formations);
		objectStream.close();
	}

	/**
	 * Loads any saved formations from a predefined location
	 * 
	 * @return a TreeSet of all Formation retrieved
	 */
	private static TreeSet<Formation> load() {
		try {
			FileInputStream fileStream = new FileInputStream("./data/formationData");
			ObjectInputStream objectStream = new ObjectInputStream(fileStream);
			TreeSet<Formation> tempTreeSet = (TreeSet<Formation>) objectStream.readObject();
			objectStream.close();
			return tempTreeSet;
		} catch (IOException e) {
			e.printStackTrace();
			return new TreeSet<Formation>();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return new TreeSet<Formation>();
		}
	}
	
	/**
	 * Adds a new Formation to be saved for future use
	 * 
	 * @param formation the Formation to save for use elsewhere
	 */
	public static void add(Formation formation) {
		formations.add(formation);
	}
	
	/**
	 * Removes an existing Formation from future use
	 * 
	 * @param formation the Formation to remove, preventing use elsewhere
	 */
	public static void remove(Formation formation) {
		formations.remove(formation);
	}
	
	/**
	 * Retrieves all Formation stored in the application
	 * 
	 * @return a TreeSet of all existing Formation stored
	 */
	public static TreeSet<Formation> getFormations() {
		return formations;
	}
	
}
