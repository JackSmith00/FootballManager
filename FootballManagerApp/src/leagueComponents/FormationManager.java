package leagueComponents;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.TreeSet;

public final class FormationManager implements Serializable {

	private static TreeSet<Formation> formations = load();
	
	public static void save() throws FileNotFoundException, IOException {
		FileOutputStream fileStream = new FileOutputStream("./data/formationData");
		ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
		objectStream.writeObject(formations);
		objectStream.close();
	}

	private static TreeSet<Formation> load() {
		try {
			FileInputStream fileStream = new FileInputStream("./data/formationData");
			ObjectInputStream objectStream = new ObjectInputStream(fileStream);
			TreeSet<Formation> tempTreeSet = (TreeSet<Formation>) objectStream.readObject();
			objectStream.close();
			return tempTreeSet;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new TreeSet<Formation>();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new TreeSet<Formation>();
		}
	}
	
	public static void add(Formation formation) {
		formations.add(formation);
	}
	
	public static void remove(Formation formation) {
		formations.remove(formation);
	}
	
	public static TreeSet<Formation> getFormations() {
		return formations;
	}
	
}
