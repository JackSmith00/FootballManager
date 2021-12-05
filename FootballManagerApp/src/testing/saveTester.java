package testing;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import enums.EmploymentStatus;
import enums.EventType;
import events.GameEvent;
import events.Result;
import events.TwoPlayerGameEvent;
import leagueComponents.*;

public class saveTester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
		
		  Player p1 = new Player("John", "GK", null, EmploymentStatus.FULL_TIME, 10_000);
		  Player p2 = new Player("Ben", "RW", null, EmploymentStatus.FULL_TIME, 20_000);
		  
		  Player p3 = new Player("Paul", "LW", null, EmploymentStatus.PART_TIME, 10_000);
		  Player p4 = new Player("Roger", "ST", null, EmploymentStatus.PART_TIME, 20_000);
		  
		  Team t1 = new Team("Liverpool", new Stadium("Anfield", 100));
		  
		  t1.addPlayer(p1); t1.addPlayer(p2);
		  
		  Team t2 = new Team("Manchester", new Stadium("Old T", 200));
		  
		  t2.addPlayer(p3); t2.addPlayer(p4);
		  
		  League l = new League("Premier League");
		  
		  l.addTeam(t1); l.addTeam(t2);
		  
		  GameEvent[] events = {new GameEvent(EventType.GOAL, p1, 15), new TwoPlayerGameEvent(EventType.SUBSTITUTION, p3, p4, 40)};
		  
		  Player[] team1Players = {p1, p2}; Player[] team2Players = {p3, p4};
		  
		  Result game1 = new Result(t1, 3, t2, 2, new Date(2021, 11, 23), events,
		  team1Players, team2Players);
		  
		  l.addResult(game1);
		  
		  System.out.println(t1.getPlayers()); System.out.println(l.getTeams());
		  
		  try { l.save("./data/saveData"); } catch (FileNotFoundException e) {
		  e.printStackTrace(); } catch (IOException e) { e.printStackTrace(); }
		 
		 
		 		

		
		
		
		  League f = new League();
		  
		  try { f.load("./data/saveData"); } catch (FileNotFoundException e) {
		  e.printStackTrace(); } catch (ClassNotFoundException e) {
		  e.printStackTrace(); } catch (IOException e) { e.printStackTrace(); }
		  
		  System.out.println(f.getName());
		  System.out.println(f.getTeams().get(0).getTeamName());
		  System.out.println(f.getTeams().get(0).getPlayers());
		  System.out.println(f.getTeams().get(0).getPlayers().get(0).getName());
		  System.out.println(f.getTeams().get(1).getPlayers().get(0).getTeam().
		  getHomeGround());

		 
		 
	}

}
