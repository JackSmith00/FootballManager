package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;

import events.Result;
import interfaces.HasResults;
import interfaces.StatisticsCalculator;
import leagueComponents.CoachingStaffMember;
import leagueComponents.League;
import leagueComponents.Player;
import leagueComponents.Referee;
import leagueComponents.Team;

public class FootballManagerGUI implements MouseListener, ActionListener {
	
	// Attributes
	private JFrame frame;
	private JPanel leaguePage;
	private JTable leagueTable;
	private JTable statsTable;
	private JTable resultsTable;
	private JPanel teamPage;
	private JTable teamInfoTable;
	private JTable playerTable;
	private JTable coachingStaffTable;
	private JTable refereeTable;
	
	private League currentLeague;

	public static void main(String[] args) {
		
		new FootballManagerGUI();
	}
	
	// Constructor
	public FootballManagerGUI() {
		frame = new JFrame("League Manager");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Makes the app quit when frame is closed
		
		currentLeague = new League();
		  
		try {
			currentLeague.load("./data/saveData");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		  
		setUpLeaguePage(); // Separate method to set up all components for the GUI
		setUpTeamPage(currentLeague.getTeams().get(0));
		frame.getContentPane().add(teamPage); // Adds all components to the frame
		frame.setBounds(100, 100, 960, 600);
		/*
		 * The line below makes the frame appear in the centre of the
		 * screen rather than in the top left, for a nicer user experience
		 * 
		 * I found how to do this at the following site:
		 * http://www.fredosaurus.com/notes-java/GUI/containers/10windows/15framesize.html
		 */
		frame.setLocationRelativeTo(null);
		frame.setResizable(false); // Stops the user from resizing the app, ruining the appearance
		frame.setVisible(true); // Allows the user to see the frame and interact with it
	}
	
	public void setUpLeaguePage() {
		
		GridBagLayout layout = new GridBagLayout();
		layout.rowHeights = new int[] {120, 280};
		layout.columnWeights = new double[]{3.0, 2.0};
		leaguePage = new JPanel(layout); // create a panel with a gridbag layout
		GridBagConstraints constraints = new GridBagConstraints(); // Constraints will be used for all items but with updated values
		
		updateLeagueTable(currentLeague);
		leagueTable.addMouseListener(this);
		
		updateStatsTableFor(currentLeague);
		updateResultsTableFor(currentLeague);
		JScrollPane resultsPane = new JScrollPane(resultsTable);
		
		
		constraints.gridx = 0; // Sets the column for the grid space
		constraints.gridy = 0; // Sets the row for the grid space
		constraints.insets = new Insets(10, 10, 10, 10); // code found at https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html
		/*
		 * Spaces are numbered starting at 0 for the top, leftmost
		 * cell in the grid and each column to the right and row below
		 * has a higher x/y value
		 */
		constraints.gridheight = 2; // Sets the item to span 4 grid spaces
		constraints.fill = GridBagConstraints.BOTH; // Allows the component to resize to fill the required space for its cells
		JScrollPane leagueScrollPane = new JScrollPane(leagueTable);
		leaguePage.add(leagueScrollPane, constraints); // reference https://stackoverflow.com/questions/2320812/jtable-wont-show-column-headers
		
		constraints.gridheight = 1;
		constraints.gridx = 1;
		leaguePage.add(statsTable, constraints);
		
		constraints.gridy = 1;
		leaguePage.add(resultsPane, constraints);		
		
	}
	
	public void updateLeagueTable(League league) {
		String[] columnNames = {"Team Name", "Wins", "Draws", "Losses", "Goal Difference"};
		Object[][] data = new Object[league.getTeams().size()][columnNames.length];
		Team[] teams = new Team[league.getTeams().size()];
		league.getTeams().toArray(teams);
		
		for(int i = 0; i < league.getTeams().size(); i++) {
			Team currentTeam = league.getTeams().get(i);
			
			data[i][0] = currentTeam.getTeamName();
			data[i][1] = currentTeam.getGamesWon();
			data[i][2] = currentTeam.getGamesDrew();
			data[i][3] = currentTeam.getGamesLost();
			data[i][4] = currentTeam.getGoalDifference();
		}
		
		leagueTable = new UneditableTableWithRowObjectReturn(data, columnNames, teams);
		leagueTable.getColumn("Team Name").setMinWidth(200);
		leagueTable.getColumn("Goal Difference").setMinWidth(100);
		
	}
	
	public void addNewTeam(League league) {
		
	}
	
	public void setUpTeamPage(Team team) {
		GridBagLayout layout = new GridBagLayout();
		layout.rowHeights = new int[] {120, 150, 150};
		layout.columnWidths = new int[] {200, 360, 260};
		teamPage = new JPanel(layout); // create a panel with a gridbag layout
		GridBagConstraints constraints = new GridBagConstraints(); // Constraints will be used for all items but with updated values
		
		updateTeamInfoTable(team);
		teamInfoTable.setTableHeader(null); // https://stackoverflow.com/questions/2528643/jtable-without-a-header
		//teamInfoTable.setPreferredScrollableViewportSize(new Dimension(200, 100)); // https://coderanch.com/t/336316/java/JScrollPane-packed-content
		TablePaneWithTitle teamInfoPanel = new TablePaneWithTitle(teamInfoTable, team.getTeamName());
		
		updateCoachingStaffTable(team);
		//coachingStaffTable.setPreferredScrollableViewportSize(new Dimension(176, 50));
		TablePaneWithTitle coachPanel = new TablePaneWithTitle(coachingStaffTable, "Coaching Staff", "Add Coaching Staff", this);		
		
		updateRefereeTable(team);
		//refereeTable.setPreferredScrollableViewportSize(new Dimension(176, 50));
		TablePaneWithTitle refereePanel = new TablePaneWithTitle(refereeTable, "Referees", "Add Referee", this);
		
		
		updatePlayerTable(team);
		//playerTable.setPreferredScrollableViewportSize(new Dimension(176, 50));
		playerTable.addMouseListener(this);
		TablePaneWithTitle playerPanel = new TablePaneWithTitle(playerTable, "Players", "Add Player", this);
		
		updateStatsTableFor(team);
		//statsTable.setPreferredScrollableViewportSize(new Dimension(176, 50));
		statsTable.setTableHeader(null);
		TablePaneWithTitle statsPanel = new TablePaneWithTitle(statsTable, "Statistics");
		
		updateResultsTableFor(team);
		//resultsTable.setPreferredScrollableViewportSize(new Dimension(176, 50));
		TablePaneWithTitle resultsPanel = new TablePaneWithTitle(resultsTable, "Results");
		
		
		constraints.gridx = 0; // Sets the column for the grid space
		constraints.gridy = 0; // Sets the row for the grid space
		constraints.insets = new Insets(10, 10, 10, 10); // code found at https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html
		/*
		 * Spaces are numbered starting at 0 for the top, leftmost
		 * cell in the grid and each column to the right and row below
		 * has a higher x/y value
		 */
		constraints.fill = GridBagConstraints.BOTH; // Allows the component to resize to fill the required space for its cells
		teamPage.add(teamInfoPanel, constraints);
		
		constraints.gridy = 1;
		teamPage.add(coachPanel, constraints);
		
		constraints.gridy = 2;
		teamPage.add(refereePanel, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridheight = 3;
		teamPage.add(playerPanel, constraints); // reference https://stackoverflow.com/questions/2320812/jtable-wont-show-column-headers
		
		constraints.gridheight = 1;
		constraints.gridx = 2;
		teamPage.add(statsPanel, constraints);
		
		constraints.gridheight = 2;
		constraints.gridy = 1;
		teamPage.add(resultsPanel, constraints);
	}

	public void updateTeamInfoTable(Team team) {
		String[] columnNames = {"Title", "Info"};
		Object[][] data = {
				{"Home Ground", team.getHomeGround().getName()},
				{"Capacity", team.getHomeGround().getCapacity()}
				};
		teamInfoTable = new UneditableTable(data, columnNames);

	}
	
	public void updatePlayerTable(Team team) {
		String[] columnNames = {"Player Name", "Position"};
		Object[][] data = new Object[team.getPlayers().size()][columnNames.length];
		Player[] players = new Player[team.getPlayers().size()];
		team.getPlayers().toArray(players);
		
		for(int i = 0; i < team.getPlayers().size(); i++) {
			Player currentPlayer = team.getPlayers().get(i);
			
			data[i][0] = currentPlayer.getName();
			data[i][1] = currentPlayer.getPosition();
		}
		
		playerTable = new UneditableTableWithRowObjectReturn(data, columnNames, players);
	}
	
	public void updateCoachingStaffTable(Team team) {
		String[] columnNames = {"Name", "Role"};
		Object[][] data = new Object[team.getCoachingStaff().size()][columnNames.length];
		CoachingStaffMember[] coachingStaff = new CoachingStaffMember[team.getCoachingStaff().size()];
		team.getCoachingStaff().toArray(coachingStaff);
		
		for(int i = 0; i < team.getCoachingStaff().size(); i++) {
			CoachingStaffMember currentCoach = team.getCoachingStaff().get(i);
			
			data[i][0] = currentCoach.getName();
			data[i][1] = currentCoach.getRole();
		}
		
		coachingStaffTable = new UneditableTableWithRowObjectReturn(data, columnNames, coachingStaff);
	}
	
	public void updateRefereeTable(Team team) {
		String[] columnNames = {"Name"};
		Object[][] data = new Object[team.getReferees().size()][columnNames.length];
		for(int i = 0; i < team.getReferees().size(); i++) {
			Referee currentRef = team.getReferees().get(i);
			
			data[i][0] = currentRef.getName();
		}
		
		coachingStaffTable = new UneditableTable(data, columnNames);
	}
	
	public void updateStatsTableFor(StatisticsCalculator e) {
		String[] columnNames = {"Stat", "Result"};
		Object[][] data = {
				{"Top Goal Scorer", e.topGoalScorer().getName()},
				{"Top Assister", e.topAssister().getName()},
				{"Total Goals Scored", e.totalGoalsScored()},
				{"Total Cards Given", e.totalCardsGiven()}
				};
		statsTable = new UneditableTable(data, columnNames);
	}

	public void updateResultsTableFor(HasResults e) {
		String[] columnNames = {"Home Team", "Score", "Away Team"};
		Object[][] data = new Object[e.getResults().size()][3];
		
		for(int i = 0; i < e.getResults().size(); i++) {
			Result currentResult = e.getResults().get(0);
			data[e.getResults().size() - (1 + i)][0] = currentResult.getHomeTeam().getTeamName();
			data[e.getResults().size() - (1 + i)][1] = currentResult.scoreString();
			data[e.getResults().size() - (1 + i)][2] = currentResult.getAwayTeam().getTeamName();
		}
		
		resultsTable = new UneditableTable(data, columnNames);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		JTable table = (JTable) e.getSource();
		Point point = e.getPoint();
		int row = table.rowAtPoint(point);
		if (e.getClickCount() == 2 && row != -1) {
			UneditableRowObjectReturnTableModel model = (UneditableRowObjectReturnTableModel) table.getModel();
			System.out.println(model.getRowObject(row).getClass());
		}		
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


}
