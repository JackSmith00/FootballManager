package leagueComponents;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.TreeSet;

/**
 * Used to manage the storage and retrieval of
 * positions, regardless of the league they
 * are being used in
 * 
 * @author Jack
 *
 */
public final class PositionManager {

private static TreeSet<Position> positions = load(); // holds all positions created
	
	/**
	 * Allows created positions to be saved for future use
	 * 
	 * @throws FileNotFoundException when the given file cannot be found
	 * @throws IOException when any error occurs in the input/output process
	 */
	public static void save() throws FileNotFoundException, IOException {
		FileOutputStream fileStream = new FileOutputStream("./data/positionData");
		ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
		objectStream.writeObject(positions);
		objectStream.close();
	}

	/**
	 * Loads any saved positions from a predefined location
	 * 
	 * @return a TreeSet of all Position retrieved
	 */
	private static TreeSet<Position> load() {
		try {
			FileInputStream fileStream = new FileInputStream("./data/positionData");
			ObjectInputStream objectStream = new ObjectInputStream(fileStream);
			TreeSet<Position> tempTreeSet = (TreeSet<Position>) objectStream.readObject();
			objectStream.close();
			return tempTreeSet;
		} catch (IOException e) {
			e.printStackTrace();
			return new TreeSet<Position>();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return new TreeSet<Position>();
		}
	}
	
	/**
	 * Adds a new Position to be saved for future use
	 * 
	 * @param position the Position to save for use elsewhere
	 */
	public static void add(Position position) {
		positions.add(position);
	}
	
	/**
	 * Removes an existing Position from future use
	 * 
	 * @param position the Position to remove, preventing use elsewhere
	 */
	public static void remove(Position position) {
		positions.remove(position);
	}
	
	/**
	 * Retrieves all Position stored in the application
	 * 
	 * @return a TreeSet of all existing Position stored
	 */
	public static TreeSet<Position> getPositions() {
		return positions;
	}
}
