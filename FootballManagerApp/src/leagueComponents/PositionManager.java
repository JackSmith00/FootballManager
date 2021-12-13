package leagueComponents;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.TreeSet;

public final class PositionManager {

private static TreeSet<Position> positions = load();
	
	public static void save() throws FileNotFoundException, IOException {
		FileOutputStream fileStream = new FileOutputStream("./data/positionData");
		ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
		objectStream.writeObject(positions);
		objectStream.close();
	}

	private static TreeSet<Position> load() {
		try {
			FileInputStream fileStream = new FileInputStream("./data/positionData");
			ObjectInputStream objectStream = new ObjectInputStream(fileStream);
			TreeSet<Position> tempTreeSet = (TreeSet<Position>) objectStream.readObject();
			objectStream.close();
			return tempTreeSet;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new TreeSet<Position>();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new TreeSet<Position>();
		}
	}
	
	public static void add(Position position) {
		positions.add(position);
	}
	
	public static void remove(Position position) {
		positions.remove(position);
	}
	
	public static TreeSet<Position> getPositions() {
		return positions;
	}
}
