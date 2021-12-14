package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import events.Result;
import guiInputForms.CoachingStaffInputForm;
import guiInputForms.PlayerInputForm;
import guiInputForms.RefereeInputForm;
import guiInputForms.ResultInputForm;
import guiInputForms.TeamInputForm;
import guiOutputDialogs.CoachingStaffMemberOutputDialog;
import guiOutputDialogs.PlayerOutputDialog;
import guiOutputDialogs.RefereeOutputDialog;
import guiOutputDialogs.ResultOutputDialog;
import interfaces.HasResults;
import interfaces.StatisticsCalculator;
import leagueComponents.CoachingStaffMember;
import leagueComponents.FormationManager;
import leagueComponents.League;
import leagueComponents.Player;
import leagueComponents.Referee;
import leagueComponents.Team;

/**
 * Class responsible for displaying the main GUI of the application.
 * 
 * <br><b>Implements:</b>
 * 	<br><b>MouseListener</b> - to listen for double clicks on certain tables in the GUI
 * 	<br><b>ActionListener</b> - to listen for buttons in the GUI being pressed
 * 	<br><b>WindowListener</b> - to allow for custom close operation of the main GUI window
 * 
 * @author Jack
 *
 */
public class FootballManagerGUI extends JFrame implements MouseListener, ActionListener, WindowListener {
	
	// Attributes
	private JPanel leaguePage; // the page that will display league information
	private JTable leagueTable; // the table that will hold teams and their W/L/D in the league
	private JTable statsTable; // shows stats for the current team/league displayed
	private JTable resultsTable; // shows results for the current team or all results for the league
	private JPanel teamPage; // the page that will display information on the current team
	private JTable teamInfoTable; // table that will show general info on a team: stadium name and capacity and league position
	private JTable playerTable; // shows all players and their positions in the team
	private JTable coachingStaffTable; // shows all coaching staff and their roles in the team
	private JTable refereeTable; // shows all referees for the team
	
	private League currentLeague; // the league currently used in the app
	private Team currentTeam; // the team currently displayed to the user

	/**
	 * Main entry point for execution of the GUI application.
	 * 
	 * @param args any arguments passed in before executing the application
	 */
	public static void main(String[] args) {
		new FootballManagerGUI();
	}
	
	// Constructor
	/**
	 * Allows a GUI for the Football Manager App to be created.
	 */
	public FootballManagerGUI() {
		setTitle("League Manager"); // set the frame title
		
		/*
		 * I used code found at the following resource to write the
		 * code seen below
		 * 
		 * Oracle, n.d. How to Write Window Listeners. [Online] 
		 * Available at: https://docs.oracle.com/javase/tutorial/uiswing/events/windowlistener.html
		 * [Accessed 7 December 2021].
		 * 
		 * I did not copy the code verbatim, it was used as a guide to find
		 * that I needed to add and implement a WindowListener to this class
		 * to get it to handle its own close operation
		 */
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // allows this class to handle its own close operation
		addWindowListener(this); // makes this class listen for any changes to the GUI window
		
		File data = new File("./data/saveData"); // file containing the save data of the existing league
		
		if(data.exists()) {	// check if the file exists at the given location
			currentLeague = new League(); // initialise an empty league that can have the contents of an existing league loaded into it
			try { // try to load in the saved league and catch any errors that may occur
				currentLeague.load("./data/saveData");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else { // if the file doesn't exist
			
			/*
			 * I researched how to use the code below at the following resource
			 * 
			 * Oracle, n.d. Class JOptionPane. [Online] 
			 * Available at: https://docs.oracle.com/javase/7/docs/api/javax/swing/JOptionPane.html
			 * [Accessed 7 December 2021].
			 * 
			 * I did not copy the code verbatim, I simply used it to understand how
			 * to create a JOptionPane of my own
			 */
			String leagueName = JOptionPane.showInputDialog(this, "League Name:"); // creates an option dialog that asks the user for a string to name the new league
			currentLeague = new League(leagueName); // uses user input league name to create a new league
		}
		  
		setUpLeaguePage(currentLeague); // separate method that sets up all components for the GUI for the current league
		add(leaguePage); // add the set up league page to the GUI, as the league page will be the home page for the app
		setBounds(0, 0, 900, 650); // set the size off the application
		/*
		 * The line below makes the frame appear in the centre of the
		 * screen rather than in the top left, for a nicer user experience
		 * 
		 * I found how to do this at the following site:
		 * 
		 * Swartz, F., 2006. Java Notes: Window Size and Position. [Online] 
		 * Available at: http://www.fredosaurus.com/notes-java/GUI/containers/10windows/15framesize.html
		 * [Accessed 1 May 2021].
		 */
		setLocationRelativeTo(null); // centre the frame on the screen
		setResizable(false); // stops the user from resizing the app, ruining the appearance
		setVisible(true); // allows the user to see the frame and interact with it
	}
	
	/**
	 * Sets up all components contained within the `leaguePage` which
	 * can then be added to the frame.
	 * 
	 * Accepts a parameter for the league so that at a later point the application
	 * can be expanded to support multiple leagues in the same execution of the app
	 * 
	 * @param league the league to set up the components for
	 */
	public void setUpLeaguePage(League league) {
		if(leaguePage == null) { // if the leaguePage hasn't been initialised yet
			GridBagLayout layout = new GridBagLayout(); // GridBagLayout variable so that its attributes can be altered before it is added to the JPanel
			layout.rowHeights = new int[] {30, 25, 120, 45, 225, 25}; // set fixed heights for the rows
			layout.columnWidths = new int[]{473, 312}; // set fixed widths for the columns
			leaguePage = new JPanel(layout); // create a panel with a gridbag layout
		} else { // the leaguePage has already been initialised
			leaguePage.removeAll(); // get rid of all current components from the panel so new ones can be added
		}
		/*
		 * The code used throughout this method I researched with the following resource
		 * 
		 * Oracle, n.d. How to Use GridBagLayout. [Online] 
		 * Available at: https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html
		 * [Accessed 23 November 2021].
		 * 
		 * I did not copy the code verbatim, I used it to understand how
		 * to position items and add padding whilst using the GridBagLayout
		 */
		GridBagConstraints constraints = new GridBagConstraints(); // constraints will be used for all items but with updated values
		
		// first, set up components
		
		JLabel leagueName = new JLabel(league.getName()); // label containing the name of the league
		leagueName.setHorizontalAlignment(JLabel.CENTER); // position league name in the centre

		updateLeagueTable(league); // update contents of the league table
		leagueTable.addMouseListener(this); // make this class listen for mouse events on the league table
		JLabel leagueTableTitle = new JLabel("League Table"); // label for the league table
		leagueTableTitle.setHorizontalAlignment(JLabel.CENTER); // align the league table label in the centre
		/*
		 * The code below was influenced by the following resource
		 * 
		 * Haspulat, E., 2010. JTable won't show column headers. [Online] 
		 * Available at: https://stackoverflow.com/questions/2320812/jtable-wont-show-column-headers
		 * [Accessed 24 November 2021].
		 * 
		 * I did not copy the code verbatim, it was used as a guide to fix an issue
		 * where tables weren't displaying their column names. The code has been adapted
		 * to suit this application.
		 */
		JScrollPane leagueScrollPane = new JScrollPane(leagueTable); // add the league table to a JScrollPane
		JButton addTeamButton = new JButton("Add Team"); // create the button for adding new teams
		addTeamButton.addActionListener(this); // make this class listen for when the `addTeamButton` is pressed
		
		updateStatsTableFor(league); // update contents of the stats table for the whole league
		JLabel statsTableTitle = new JLabel("Stats"); // label for the stats table
		statsTableTitle.setHorizontalAlignment(JLabel.CENTER); // align the stats table label in the centre
		JScrollPane statsScrollPane = new JScrollPane(statsTable); // add the stats table to a JScrollPane
		
		updateResultsTableFor(league); // update contents of the results table for the whole league
		JLabel resultsTableTitle = new JLabel("Results"); // label for the results table
		resultsTableTitle.setHorizontalAlignment(JLabel.CENTER); // align the results table label in the centre
		JScrollPane resultsScrollPane = new JScrollPane(resultsTable); // add the results table to a JScrollPane
		JButton addResultsButton = new JButton("Add Results"); // create the button for adding new results
		addResultsButton.addActionListener(this); // make this class listen for when the `addResultsButton` is pressed
		
		// now, add the components to the `leaguePage`
		
		// add the league title at the top across both grid columns
		constraints.gridwidth = 2; // make the label span both columns
		constraints.fill = GridBagConstraints.HORIZONTAL; // make the label fill the columns
		leaguePage.add(leagueName, constraints); // add the league title with the constraints
		
		constraints.insets = new Insets(0, 5, 0, 5); // add some padding to the right and left of items
		constraints.gridwidth = 1; // lower the gridwidth back to 1 so each coming item only spans 1 column
		/*
		 * Grid cells are numbered starting at 0 for the top, leftmost
		 * cell in the grid and each column to the right and row below
		 * has a higher x/y value
		 */
		constraints.gridy = 1; // next row
		constraints.fill = GridBagConstraints.BOTH; // allows the component to resize to fill the required space for its cells
		leaguePage.add(leagueTableTitle, constraints); // add the leagueTable title
		
		constraints.gridy = 2; // next row
		constraints.gridheight = 3; // span 3 rows
		leaguePage.add(leagueScrollPane, constraints); // add the league table in its scroll pane
		
		constraints.gridy = 5; // next row (taking previous row sizing into consideration
		constraints.gridheight = 2; // span 2 rows
		leaguePage.add(addTeamButton, constraints); //  add the `addTeamButton` to the league page
		
		constraints.gridheight = 1; // reset row height to 1
		constraints.gridy = 1; // 2nd row (1st row has the league title already here)
		constraints.gridx = 1; // next column
		leaguePage.add(statsTableTitle, constraints); // add stats table title here
		
		constraints.gridy = 2; // next row
		leaguePage.add(statsScrollPane, constraints); // add stats table here in its scroll pane
		
		constraints.gridy = 3; // next row
		leaguePage.add(resultsTableTitle, constraints);	// add results table title here
		
		constraints.gridy = 4; // next row
		leaguePage.add(resultsScrollPane, constraints); // add results table here in its scroll pane
		
		constraints.gridy = 5; // next row
		leaguePage.add(addResultsButton, constraints); // add the `addResulltsButton` to the league page
		
	}
	
	/**
	 * Updates values shown in the league table.
	 * 
	 * The league table shows each team in the league, their W/L/D,
	 * points and goal difference. The table is organsied in order
	 * of league position.
	 * 
	 * Takes a league as input for a future version where there may be
	 * multiple leagues in the same execution of the application.
	 * 
	 * @param league the league to create the table for
	 */
	public void updateLeagueTable(League league) {
		String[] columnNames = {"Team Name", "Wins", "Draws", "Losses", "Points", "Goal Difference"};
		// initialise the data array to have enough rows for each team in the league and enough columns for each heading
		Object[][] data = new Object[league.getTeams().size()][columnNames.length];
		Team[] teams = new Team[league.getTeams().size()]; // initialise the team array to have enough room for all team in the league
		league.getTeams().toArray(teams); // add all the teams from the league to the league array
		
		for(int i = 0; i < teams.length; i++) { // loop every team
			Team currentTeam = teams[i]; // holds the current team
			
			currentTeam.setLeaguePosition(i + 1); // update the league position of the team
			
			// add the value for each team that matches the column heading
			data[i][0] = currentTeam.getTeamName();
			data[i][1] = currentTeam.getGamesWon();
			data[i][2] = currentTeam.getGamesDrew();
			data[i][3] = currentTeam.getGamesLost();
			data[i][4] = currentTeam.getPoints();
			data[i][5] = currentTeam.getGoalDifference();
		}
		// here, the data array now holds all the relevant data for all teams
		
		// the row return table has been used so the team from each row can be accessed to navigate the app
		if(leagueTable == null) { // if no table exists, initialise one
			leagueTable = new UneditableTableWithRowObjectReturn(data, columnNames, teams); // make a table to display the league
		} else { // table exists already, so just update values
			((UneditableTableWithRowObjectReturn) leagueTable).setData(data, teams);
		}
		
		// set the minimum sizes for columns with longer headings and values so they are always legible
		leagueTable.getColumn("Team Name").setMinWidth(150);
		leagueTable.getColumn("Goal Difference").setMinWidth(100);
		
	}
	
	/**
	 * Creates the input form and passes the input to the current
	 * league to create a new team.
	 * 
	 * @param league the league to add the team to
	 */
	public void addNewTeam(League league) {
		TeamInputForm inputForm = new TeamInputForm(this); // create a team input form
		if(inputForm.getNewTeam() != null) { // make sure that input was submitted by the form before accessing
			league.addTeam(inputForm.getNewTeam()); // add the new team into the league
		}
		updateLeagueTable(league); // update the league table with the new team
		leagueTable.revalidate(); // revalidate the league table to show the new team
	}
	
	/**
	 * Sets up all components contained within the `teamPage` which
	 * can then be added to the frame.
	 *
	 * @param team the team to set the page up for
	 */
	public void setUpTeamPage(Team team) {
		if(teamPage == null) { // check if the teamPage has been initialised or not
			GridBagLayout layout = new GridBagLayout();
			layout.rowHeights = new int[] {25, 25, 120, 45, 130, 25, 45, 130, 25};
			layout.columnWidths = new int[] {200, 360, 260};
			teamPage = new JPanel(layout); // create a panel with a gridbag layout
		} else {
			teamPage.removeAll();
		}
		GridBagConstraints constraints = new GridBagConstraints(); // Constraints will be used for all items but with updated values
		
		JButton returnToLeaguePage = new JButton("< Back to league view");
		returnToLeaguePage.addActionListener(this);

		JLabel teamName = new JLabel(team.getTeamName());
		teamName.setHorizontalAlignment(JLabel.CENTER); // https://www.youtube.com/watch?v=Kmgo00avvEw&t=1698

		JButton deleteTeam = new JButton("Delete Team");
		deleteTeam.addActionListener(this);
		
		updateTeamInfoTable(team);
		JLabel teamInfoTableTitle = new JLabel("Info");
		teamInfoTableTitle.setHorizontalAlignment(JLabel.CENTER); // https://www.youtube.com/watch?v=Kmgo00avvEw&t=1698
		teamInfoTable.setTableHeader(null); // https://stackoverflow.com/questions/2528643/jtable-without-a-header
		JScrollPane teamInfoScrollPane = new JScrollPane(teamInfoTable);
		
		updateCoachingStaffTable(team);
		coachingStaffTable.addMouseListener(this);
		JLabel coachTableTitle = new JLabel("Coaching Staff");
		coachTableTitle.setHorizontalAlignment(JLabel.CENTER); // https://www.youtube.com/watch?v=Kmgo00avvEw&t=1698s
		JScrollPane coachScrollPane = new JScrollPane(coachingStaffTable);
		JButton addCoachStaffButton = new JButton("Add Coaching Staff");
		addCoachStaffButton.addActionListener(this);
		
		updateRefereeTable(team);
		JLabel refereeTableTitle = new JLabel("Referees");
		refereeTableTitle.setHorizontalAlignment(JLabel.CENTER); // https://www.youtube.com/watch?v=Kmgo00avvEw&t=1698s
		JScrollPane refereeScrollPane = new JScrollPane(refereeTable);
		JButton addRefereeButton = new JButton("Add Referee");
		addRefereeButton.addActionListener(this);
		
		updatePlayerTable(team);
		playerTable.addMouseListener(this);
		JLabel playerTableTitle = new JLabel("Players");
		playerTableTitle.setHorizontalAlignment(JLabel.CENTER); // https://www.youtube.com/watch?v=Kmgo00avvEw&t=1698s
		JScrollPane playerScrollPane = new JScrollPane(playerTable);
		JButton addPlayerButton = new JButton("Add Player");
		addPlayerButton.addActionListener(this);
		
		updateStatsTableFor(team);
		JLabel statsTableTitle = new JLabel("Statistics");
		statsTableTitle.setHorizontalAlignment(JLabel.CENTER); // https://www.youtube.com/watch?v=Kmgo00avvEw&t=1698s
		JScrollPane statsPane = new JScrollPane(statsTable);
		
		updateResultsTableFor(team);
		resultsTable.addMouseListener(this);
		JLabel resultsTableTitle = new JLabel("Results");
		resultsTableTitle.setHorizontalAlignment(JLabel.CENTER); // https://www.youtube.com/watch?v=Kmgo00avvEw&t=1698s
		JScrollPane resultsPane = new JScrollPane(resultsTable);
		
		teamPage.add(returnToLeaguePage, constraints);
		//constraints.insets = new Insets(10, 10, 10, 10); // code found at https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html
		constraints.gridx = 1;
		teamPage.add(teamName, constraints);
		
		constraints.gridx = 2;
		teamPage.add(deleteTeam, constraints);
		/*
		 * Spaces are numbered starting at 0 for the top, leftmost
		 * cell in the grid and each column to the right and row below
		 * has a higher x/y value
		 */
		constraints.insets = new Insets(0, 0, 0, 5);
		constraints.fill = GridBagConstraints.BOTH; // Allows the component to resize to fill the required space for its cells
		constraints.gridy = 1;
		constraints.gridx = 0;
		teamPage.add(teamInfoTableTitle, constraints);
		
		constraints.gridy = 2;
		teamPage.add(teamInfoScrollPane, constraints);
		
		constraints.gridy = 3;
		teamPage.add(coachTableTitle, constraints);
		
		constraints.gridy = 4;
		teamPage.add(coachScrollPane, constraints);
		
		constraints.gridy = 5;
		teamPage.add(addCoachStaffButton, constraints);
		
		constraints.gridy = 6;
		teamPage.add(refereeTableTitle, constraints);
		
		constraints.gridy = 7;
		teamPage.add(refereeScrollPane, constraints);
		
		constraints.gridy = 8;
		teamPage.add(addRefereeButton, constraints);
		
		constraints.gridx = 1;
		constraints.insets = new Insets(0, 5, 0, 5);
		constraints.gridy = 1;
		teamPage.add(playerTableTitle, constraints);
		
		constraints.gridheight = 6;
		constraints.gridy = 2;
		teamPage.add(playerScrollPane, constraints); // reference https://stackoverflow.com/questions/2320812/jtable-wont-show-column-headers
		
		constraints.gridheight = 1;
		constraints.gridy = 8;
		teamPage.add(addPlayerButton, constraints);
		
		constraints.gridx = 2;
		constraints.insets = new Insets(0, 5, 0, 0);
		constraints.gridy = 1;
		teamPage.add(statsTableTitle, constraints);
		
		constraints.gridy = 2;
		teamPage.add(statsPane, constraints);
		
		constraints.gridy = 3;
		teamPage.add(resultsTableTitle, constraints);
		
		constraints.gridy = 4;
		constraints.gridheight = 4;
		teamPage.add(resultsPane, constraints);
		
		
	}

	public void updateTeamInfoTable(Team team) {
		String[] columnNames = {"Title", "Info"};
		Object[][] data = {
				{"Home Ground", team.getHomeGround().getName()},
				{"Capacity", team.getHomeGround().getCapacity()}, 
				{"League Position", team.getLeaguePosition()}
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
			data[i][1] = currentPlayer.getPosition().getPositionAbbreviation();
		}
		
		playerTable = new UneditableTableWithRowObjectReturn(data, columnNames, players);
	}
	
	public void addNewPlayer(Team team) {
		PlayerInputForm inputForm = new PlayerInputForm(this);
		if(inputForm.getNewPlayer() != null) {
			team.addPlayer(inputForm.getNewPlayer());
		}
		setUpTeamPage(team);
		getContentPane().revalidate();	
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
	
	public void addNewCoachingStaffMember(Team team) {
		CoachingStaffInputForm inputForm = new CoachingStaffInputForm(this);
		if(inputForm.getNewCoachingStaffMember() != null) {
			team.addCoachingStaffMember(inputForm.getNewCoachingStaffMember());
		}
		setUpTeamPage(team);
		getContentPane().revalidate();
	}
	
	public void updateRefereeTable(Team team) {
		String[] columnNames = {"Name"};
		Object[][] data = new Object[team.getReferees().size()][columnNames.length];
		Referee[] referees = new Referee[team.getReferees().size()];
		team.getReferees().toArray(referees);
		for(int i = 0; i < team.getReferees().size(); i++) {
			Referee currentRef = team.getReferees().get(i);
			
			data[i][0] = currentRef.getName();
		}
		
		refereeTable = new UneditableTableWithRowObjectReturn(data, columnNames, referees);
		refereeTable.addMouseListener(this);
	}
	
	public void addNewReferee(Team team) {
		RefereeInputForm inputForm = new RefereeInputForm(this);
		if(inputForm.getNewReferee() != null) {
			team.addReferee(inputForm.getNewReferee());
		}
		setUpTeamPage(team);
		getContentPane().revalidate();
	}
	
	public void updateStatsTableFor(StatisticsCalculator e) {
		int rows;
		if(e.getClass() == Team.class) {
			rows = 5;
		} else {
			rows = 4;
		}
		String[] columnNames = {"Stat", "Result"};
		Object[] statResults = new Object[rows]; // makes stats null initially
		if(e.topGoalScorer() != null) { // only find stats if there are stats to be found, otherwise an error occurs
			statResults[0] = e.topGoalScorer().getName();
			statResults[1] = e.topAssister().getName();
			statResults[2] = e.totalGoalsScored();
			statResults[3] = e.totalCardsGiven();
		}
		Object[][] data = new Object[rows][2];
		data[0] = new Object[] {"Top Goal Scorer", statResults[0]};
		data[1] = new Object[] {"Top Assister", statResults[1]};
		data[2] = new Object[] {"Total Goals Scored", statResults[2]};
		data[3] = new Object[] {"Total Cards Given", statResults[3]};
		if(e.getClass() == Team.class) {
			data[4] = new Object[] {"Clean Sheets", ((Team) e).getCleanSheets()};
		}
		statsTable = new UneditableTable(data, columnNames);
	}

	public void updateResultsTableFor(HasResults e) {
		String[] columnNames = {"Home Team", "Score", "Away Team"};
		Object[][] data = new Object[e.getResults().size()][3];
		Result[] results = new Result[e.getResults().size()];
		
		for(int i = 0; i < e.getResults().size(); i++) {
			Result currentResult = e.getResults().get(i);
			data[e.getResults().size() - (1 + i)][0] = currentResult.getHomeTeam().getTeamName();
			data[e.getResults().size() - (1 + i)][1] = currentResult.scoreString();
			data[e.getResults().size() - (1 + i)][2] = currentResult.getAwayTeam().getTeamName();
			
			results[e.getResults().size() - (1 + i)] = currentResult; // allows the rowObject to match the reversed row display
		}
		
		resultsTable = new UneditableTableWithRowObjectReturn(data, columnNames, results);
		resultsTable.addMouseListener(this);
	}
	
	public void addNewResult(League league) {
		ResultInputForm inputForm = new ResultInputForm(this, league);
		if(inputForm.getNewResult() != null) {
			league.addResult(inputForm.getNewResult());
		}
		setUpLeaguePage(league);
		getContentPane().revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "Add Team":
			addNewTeam(currentLeague);
			break;
		case "Add Results":
			addNewResult(currentLeague);
			break;
		case "Add Player":
			addNewPlayer(currentTeam);
			break;
		case "Add Coaching Staff":
			addNewCoachingStaffMember(currentTeam);
			break;
		case "Add Referee":
			addNewReferee(currentTeam);
			break;
		//case "< Back to league view": no longer needed as this is performed in the default clause to save code reuse in case "Delete Team:
		case "Delete Team":
			int input = JOptionPane.showConfirmDialog(getOwner(), "Are you sure you want to delete this Team?\nThis cannot be undone.", "Delete Team", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if(input == JOptionPane.YES_OPTION) {
				currentLeague.removeTeam(currentTeam);
			} else {
				break;
			}
		default:
			setUpLeaguePage(currentLeague);
			getContentPane().removeAll();
			getContentPane().add(leaguePage);
			revalidate();
			repaint();
			break;
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		JTable table = (JTable) e.getSource();
		Point point = e.getPoint();
		int row = table.rowAtPoint(point);
		if(e.getClickCount() == 2 && row != -1) { // if double clicked on an actual row
			UneditableRowObjectReturnTableModel model = (UneditableRowObjectReturnTableModel) table.getModel();
			Object rowObject = model.getRowObject(row);
			if(rowObject.getClass() == Team.class) { // If the selected row is a team
				currentTeam = (Team) rowObject;
				setUpTeamPage(currentTeam);
				getContentPane().removeAll();
				getContentPane().add(teamPage);
				revalidate();
				repaint();
			}
			if(rowObject.getClass() == Referee.class) {
				new RefereeOutputDialog(this, (Referee) rowObject);
			}
			if(rowObject.getClass() == CoachingStaffMember.class) {
				new CoachingStaffMemberOutputDialog(this, (CoachingStaffMember) rowObject);
			}
			if(rowObject.getClass() == Player.class) {
				new PlayerOutputDialog(this, (Player) rowObject);
			}
			if(rowObject.getClass() == Result.class) {
				new ResultOutputDialog(this, (Result) rowObject);
			}
			
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

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		try {
			currentLeague.save("./data/saveData");
			System.out.println("League saved");
			FormationManager.save();
			System.exit(0);
		} catch (FileNotFoundException e1) {
			System.out.println("File was not found");
			e1.printStackTrace();
			int input = JOptionPane.showConfirmDialog(this, "Saving failed, are you sure you want to exit?", "Save failure", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			// https://mkyong.com/swing/java-swing-how-to-make-a-confirmation-dialog/
			switch(input) {
			case JOptionPane.YES_OPTION:
				System.exit(0);
				break;
			case JOptionPane.NO_OPTION:
				break;
			default:
				break;
			}
		} catch (IOException e1) {
			System.out.println("An I/O Error has occured");
			e1.printStackTrace();
			int input = JOptionPane.showConfirmDialog(this, "Saving failed, are you sure you want to exit?", "Save failure", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			// https://mkyong.com/swing/java-swing-how-to-make-a-confirmation-dialog/
			switch(input) {
			case JOptionPane.YES_OPTION:
				System.exit(0);
				break;
			case JOptionPane.NO_OPTION:
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public League getCurrentLeague() {
		return currentLeague;
	}
	
	public Team getCurrentTeam() {
		return currentTeam;
	}

}
