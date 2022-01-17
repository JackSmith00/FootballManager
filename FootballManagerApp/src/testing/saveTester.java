package testing;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import enums.EmploymentStatus;
import enums.EventType;
import events.GameEvent;
import events.Result;
import events.TwoPlayerGameEvent;
import leagueComponents.*;

/**
 * A class used to provide a demo league for testing purposes,
 * making sure that all components save correctly and saving newly
 * implemented attributes for classes that have been altered
 * 
 * @author Jack
 *
 */
public class saveTester {

	/**
	 * Main entry point for execution
	 * @param args any arguments passed in before executing the application
	 */
	public static void main(String[] args) {		
		/*
		 * The following formations where researched using the following resource:
		 * 
		 * Weiyang, L., n.d. Understanding football formations. [Online] 
		 * Available at: https://www.myactivesg.com/sports/football/training-methods/tactics/understanding-football-formations
		 * [Accessed 28 November 2021].
		 * 
		 * This was used to find existing formations in football to populate the
		 * FormationManager of the application, no code was taken from the resource
		 */
		Formation form1 = new Formation(4, 4, 2);
		Formation form2 = new Formation(4, 3, 3);
		Formation form3 = new Formation(4, 5, 1);
		
		
		FormationManager.add(form1);
		FormationManager.add(form2);
		FormationManager.add(form3);
		
		/*
		 * The following positions where researched using the following resource:
		 * 
		 * Samba Soccer Schools, 2021. Football Positions UK Guide 2021. [Online] 
		 * Available at: https://sambasoccerschools.com/football-positions-uk-guide/
		 * [Accessed 28 November 2021].
		 * 
		 * This was used to find existing positions in football to populate the
		 * PositionManager of the application, no code was taken from the resource
		 */
		Position position1 = new Position("Goalkeeper", "GK");
		Position position2 = new Position("Central Midielder", "CM");
		Position position3 = new Position("Striker", "ST");
		
		PositionManager.add(position1);
		PositionManager.add(position2);
		PositionManager.add(position3);

		Player p1 = new Player("John", position1, form1, EmploymentStatus.FULL_TIME, 10_000);
		Player p2 = new Player("Ben", position2, form1, EmploymentStatus.FULL_TIME, 20_000);

		Player p3 = new Player("Paul", position2, form2, EmploymentStatus.PART_TIME, 10_000);
		Player p4 = new Player("Roger", position3, form3, EmploymentStatus.PART_TIME, 20_000);

		Team t1 = new Team("Liverpool", new Stadium("Anfield", 100));

		t1.addPlayer(p1); t1.addPlayer(p2);

		Team t2 = new Team("Manchester", new Stadium("Old T", 200));

		t2.addPlayer(p3); t2.addPlayer(p4);

		League l = new League("Premier League");

		l.addTeam(t1); l.addTeam(t2);

		GameEvent[] events = {new TwoPlayerGameEvent(EventType.GOAL, p1, p2, 15), new TwoPlayerGameEvent(EventType.SUBSTITUTION, p3, p4, 40)};

		Player[] team1Players = {p1, p2}; Player[] team2Players = {p3, p4};

		Result game1 = new Result(t1, 1, t2, 0, new Date(2021 - 1900, 11, 23), events,
				team1Players, team2Players);

		l.addResult(game1);

		System.out.println(t1.getPlayers()); System.out.println(l.getTeams());
		
		

		try {
			l.save("./data/saveData");
			FormationManager.save();
			PositionManager.save();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}



		League f = new League();

		try { f.load("./data/saveData"); } catch (FileNotFoundException e) {
			e.printStackTrace(); } catch (ClassNotFoundException e) {
				e.printStackTrace(); } catch (IOException e) { e.printStackTrace(); }
	}

}
