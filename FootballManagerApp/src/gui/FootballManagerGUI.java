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
import leagueComponents.PositionManager;
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
		
		// first, set up components
		
		JLabel leagueName = new JLabel(league.getName()); // label containing the name of the league
		leagueName.setHorizontalAlignment(JLabel.CENTER); // position league name in the centre

		updateLeagueTable(league); // update contents of the league table
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
		
		leagueTable.addMouseListener(this); // make this class listen for mouse events on the league table
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
			GridBagLayout layout = new GridBagLayout(); // GridBagLayout variable so that its attributes can be altered before it is added to the JPanel
			layout.rowHeights = new int[] {25, 25, 120, 45, 130, 25, 45, 130, 25}; // set fixed heights for the rows
			layout.columnWidths = new int[] {200, 360, 260}; // set fixed widths for the columns
			teamPage = new JPanel(layout); // create a panel with a the above layout
		} else { // teamPage already initialised
			teamPage.removeAll(); // remove all existing components from the page
		}
		
		// first, set up components
		
		JButton returnToLeaguePage = new JButton("< Back to league view"); // create button to return to league screen
		returnToLeaguePage.addActionListener(this); // make this class listen for this button being pressed

		JLabel teamName = new JLabel(team.getTeamName());
		/*
		 * The code below I found at the following resource:
		 * 
		 * Bro Code, 2020. Java GUI ☕(Free). [Online] 
		 * Available at: https://www.youtube.com/watch?v=Kmgo00avvEw&t=1698s
		 * [Accessed 7 December 2021].
		 * 
		 * I used the code to find how to align the label and then adapted this
		 * to suit this code
		 */
		teamName.setHorizontalAlignment(JLabel.CENTER); // centre the label in the cell

		JButton deleteTeam = new JButton("Delete Team"); // create button to delete team
		deleteTeam.addActionListener(this); // make this class listen for the button being pressed
		
		updateTeamInfoTable(team); // update the team info table
		JLabel teamInfoTableTitle = new JLabel("Info"); // create title for team info table
		teamInfoTableTitle.setHorizontalAlignment(JLabel.CENTER); // centre the label in the cell
		/*
		 * The code below was influenced by the following resource
		 * 
		 * Lang, P., 2010. JTable without a header. [Online] 
		 * Available at: https://stackoverflow.com/questions/2528643/jtable-without-a-header
		 * [Accessed 25 November 2021].
		 * 
		 * From this resource I found that I needed to add the table to
		 * a JScrollPane and then can use `setTableHeader(null)` to remove
		 * the header columns
		 */
		teamInfoTable.setTableHeader(null); // remove column headings from this table
		JScrollPane teamInfoScrollPane = new JScrollPane(teamInfoTable); // add the team info table to the scroll pane
		
		updateCoachingStaffTable(team); // update the coaching staff table for the team
		JLabel coachTableTitle = new JLabel("Coaching Staff"); // create label for coach table
		coachTableTitle.setHorizontalAlignment(JLabel.CENTER); // centre the label in the middle of the cell
		JScrollPane coachScrollPane = new JScrollPane(coachingStaffTable); // add the table to its new scroll pane
		JButton addCoachStaffButton = new JButton("Add Coaching Staff"); // create button to add coaching staff
		addCoachStaffButton.addActionListener(this); // make this class listen for this button clicks
		
		updateRefereeTable(team); // update referee table for the team
		JLabel refereeTableTitle = new JLabel("Referees"); // create label for referee table
		refereeTableTitle.setHorizontalAlignment(JLabel.CENTER); // https://www.youtube.com/watch?v=Kmgo00avvEw&t=1698s
		JScrollPane refereeScrollPane = new JScrollPane(refereeTable); // add the table to its new scroll pane
		JButton addRefereeButton = new JButton("Add Referee"); // create button to add referees
		addRefereeButton.addActionListener(this); // make this class listen for this button clicks
		
		updatePlayerTable(team); // update player table for the team
		JLabel playerTableTitle = new JLabel("Players"); // create label for player table
		playerTableTitle.setHorizontalAlignment(JLabel.CENTER); // centre the label in the middle of the cell
		JScrollPane playerScrollPane = new JScrollPane(playerTable); // add the table to its new scroll pane
		JButton addPlayerButton = new JButton("Add Player"); // create button to add players
		addPlayerButton.addActionListener(this); // make this class listen for this button clicks
		
		updateStatsTableFor(team); // update stats table for the team
		JLabel statsTableTitle = new JLabel("Statistics"); // create label for stats table
		statsTableTitle.setHorizontalAlignment(JLabel.CENTER); // centre the label in the middle of the cell
		JScrollPane statsPane = new JScrollPane(statsTable); // add the table to its new scroll pane
		
		updateResultsTableFor(team); // update the results table for this team
		JLabel resultsTableTitle = new JLabel("Results"); // create label for results table
		resultsTableTitle.setHorizontalAlignment(JLabel.CENTER); // centre the label in the middle of the cell
		JScrollPane resultsPane = new JScrollPane(resultsTable); // add the table to its new scroll pane
		
		// now add the components to the `teamPage`
		GridBagConstraints constraints = new GridBagConstraints(); // Constraints will be used for all items but with updated values
		
		teamPage.add(returnToLeaguePage, constraints); // add return button to the top left
		
		constraints.gridx = 1; // next column
		teamPage.add(teamName, constraints); // add team name
		
		constraints.gridx = 2; // next column
		teamPage.add(deleteTeam, constraints); // add delete button
		
		constraints.insets = new Insets(0, 5, 0, 5); // create left/right padding for following items
		
		constraints.fill = GridBagConstraints.BOTH; // allows the component to resize to fill the required space for its cells
		constraints.gridx = 0; // first column
		constraints.gridy = 1; // next row
		teamPage.add(teamInfoTableTitle, constraints);
		
		constraints.gridy = 2; // next row
		teamPage.add(teamInfoScrollPane, constraints);
		
		constraints.gridy = 3; // next row
		teamPage.add(coachTableTitle, constraints);
		
		constraints.gridy = 4; // next row
		teamPage.add(coachScrollPane, constraints);
		
		constraints.gridy = 5; // next row
		teamPage.add(addCoachStaffButton, constraints);
		
		constraints.gridy = 6; // next row
		teamPage.add(refereeTableTitle, constraints);
		
		constraints.gridy = 7; // next row
		teamPage.add(refereeScrollPane, constraints);
		
		constraints.gridy = 8; // next row
		teamPage.add(addRefereeButton, constraints);
		
		constraints.gridx = 1; // middle column
		constraints.gridy = 1; // 2nd row
		teamPage.add(playerTableTitle, constraints);
		
		constraints.gridy = 2; // next row
		constraints.gridheight = 6; // update row span for next column
		teamPage.add(playerScrollPane, constraints);
		
		constraints.gridheight = 1; // reset row span to 1
		constraints.gridy = 8; // next row
		teamPage.add(addPlayerButton, constraints);
		
		constraints.gridx = 2; // right column
		constraints.gridy = 1; // second row
		teamPage.add(statsTableTitle, constraints);
		
		constraints.gridy = 2; // next row
		teamPage.add(statsPane, constraints);
		
		constraints.gridy = 3; // next row
		teamPage.add(resultsTableTitle, constraints);
		
		constraints.gridy = 4; // next row
		constraints.gridheight = 4; // update row span for next column
		teamPage.add(resultsPane, constraints);
		
	}

	/**
	 * Updates the values shown in the team info table.
	 * 
	 * Team info consists of the home stadium name, the
	 * capacity of the home stadium and the position of
	 * the team in the league.
	 * 
	 * @param team the team to display info for
	 */
	public void updateTeamInfoTable(Team team) {
		String[] columnNames = {"Title", "Info"}; // headings for the table columns
		Object[][] data = { // data to display in the table
				{"Home Ground", team.getHomeGround().getName()},
				{"Capacity", team.getHomeGround().getCapacity()}, 
				{"League Position", team.getLeaguePosition()}
				};

		if(teamInfoTable == null) { // if no table exists, initialise one
			teamInfoTable = new UneditableTable(data, columnNames); // create a table that can't be edited to display the info
		} else { // table exists already, so just update values
			((UneditableTableModel) teamInfoTable.getModel()).setData(data);
		}
	}
	
	/**
	 * Updates the values shown in the player table
	 * 
	 * This table only displays the player name and their
	 * position abbreviation. The table is clickable so
	 * player information can be accessed in more detail elsewhere.
	 * 
	 * @param team the team to show the players for
	 */
	public void updatePlayerTable(Team team) {
		String[] columnNames = {"Player Name", "Position"}; // headings for the table columns
		// initialise the data array to have enough rows for each player in the team and enough columns for each heading
		Object[][] data = new Object[team.getPlayers().size()][columnNames.length];
		Player[] players = new Player[team.getPlayers().size()]; // initialise the players array to have enough room for all players in the team
		team.getPlayers().toArray(players); // add players from the team to the player array
		
		for(int i = 0; i < players.length; i++) { // loop every player
			Player currentPlayer = players[i]; // hold the current player
			
			// add attributes of player to the relevant column
			data[i][0] = currentPlayer.getName();
			data[i][1] = currentPlayer.getPosition().getPositionAbbreviation();
		}

		if(playerTable == null) { // if no table exists, initialise one
			// create the table with the calculated data
			playerTable = new UneditableTableWithRowObjectReturn(data, columnNames, players);
			playerTable.addMouseListener(this); // make this class listen for mouse clicks on the table
		} else { // table exists already, so just update values
			((UneditableRowObjectReturnTableModel) playerTable.getModel()).setData(data);
			((UneditableRowObjectReturnTableModel) playerTable.getModel()).setRowObjects(players);
		}
	}
	
	/**
	 * Creates the input form and passes the input to the current
	 * team to create a new player.
	 * 
	 * @param team the team to create the new player in
	 */
	public void addNewPlayer(Team team) {
		PlayerInputForm inputForm = new PlayerInputForm(this); // create the player input form
		if(inputForm.getNewPlayer() != null) { // check there has been input submitted to the form
			team.addPlayer(inputForm.getNewPlayer()); // add the new player to the team
		}
		updatePlayerTable(team); // update the player table with the new player
		playerTable.revalidate(); // revalidate the player table to show the new player
	}
	
	/**
	 * Updates the values shown in the coaching staff table
	 * 
	 * This table displays the coaching staffs name and their
	 * role. The table is clickable so coaching staff information
	 * can be accessed in more detail elsewhere.
	 * 
	 * @param team the team to update the coaching staff table for
	 */
	public void updateCoachingStaffTable(Team team) {
		String[] columnNames = {"Name", "Role"}; // headings for table columns
		// initialise the data array to have enough rows for each coach in the team and enough columns for each heading
		Object[][] data = new Object[team.getCoachingStaff().size()][columnNames.length];
		// initialise the coachingStaff array to have enough room for all coaches in the team
		CoachingStaffMember[] coachingStaff = new CoachingStaffMember[team.getCoachingStaff().size()];
		team.getCoachingStaff().toArray(coachingStaff); // add coaches from the team to the coaching staff array
		
		for(int i = 0; i < coachingStaff.length; i++) { // loop through every coach in the array
			CoachingStaffMember currentCoach = coachingStaff[i]; // hold the current coach
			
			// add attributes of coach to the relevant column
			data[i][0] = currentCoach.getName();
			data[i][1] = currentCoach.getRole();
		}

		if(coachingStaffTable == null) { // if no table exists, initialise one
			// create the table with the calculated data
			coachingStaffTable = new UneditableTableWithRowObjectReturn(data, columnNames, coachingStaff);
			coachingStaffTable.addMouseListener(this); // makes this class listen for mouse clicks on this table
		} else { // table exists already, so just update values
			((UneditableRowObjectReturnTableModel) coachingStaffTable.getModel()).setData(data);
			((UneditableRowObjectReturnTableModel) coachingStaffTable.getModel()).setRowObjects(coachingStaff);
		}

	}
	
	/**
	 * Creates the input form and passes the input to the current
	 * team to create a new coach.
	 * 
	 * @param team the team to create the new coaching staff in
	 */
	public void addNewCoachingStaffMember(Team team) {
		CoachingStaffInputForm inputForm = new CoachingStaffInputForm(this); // create an input form for a new coaching staff
		if(inputForm.getNewCoachingStaffMember() != null) { // make sure a new coaching staff has been submitted
			team.addCoachingStaffMember(inputForm.getNewCoachingStaffMember()); // add the new coaching staff to the team
		}
		updateCoachingStaffTable(team); // update the coaching staff table with the new coach
		coachingStaffTable.revalidate(); // revalidate the coaching staff table to show the new coaching staff
	}
	
	/**
	 * Updates the values shown in the referee table
	 * 
	 * This table displays the referee names. The table 
	 * is clickable so referee information can be 
	 * accessed in more detail elsewhere.
	 * 
	 * @param team the team to update the referee table for
	 */
	public void updateRefereeTable(Team team) {
		String[] columnNames = {"Name"}; // heading for table column
		// initialise the data array to have enough rows for each referee in the team and enough columns for each heading
		Object[][] data = new Object[team.getReferees().size()][columnNames.length];
		// initialise the referees array to have enough room for all referees in the team
		Referee[] referees = new Referee[team.getReferees().size()];
		team.getReferees().toArray(referees); // add all referees from the team to the referees array
		for(int i = 0; i < referees.length; i++) {
			Referee currentRef = referees[i]; // hold the current referee
			
			// add attributes of the referee to the relevant column
			data[i][0] = currentRef.getName();
		}
		
		if(refereeTable == null) { // if no table exists, initialise one
			// create the table with the calculated data
			refereeTable = new UneditableTableWithRowObjectReturn(data, columnNames, referees);
			refereeTable.addMouseListener(this); // makes this class listen for mouse clicks on this table
		} else { // table exists already, so just update values
			((UneditableRowObjectReturnTableModel) refereeTable.getModel()).setData(data);
			((UneditableRowObjectReturnTableModel) refereeTable.getModel()).setRowObjects(referees);
		}
	}
	
	/**
	 * Creates the input form and passes the input to the current
	 * team to create a new referee.
	 * 
	 * @param team the team to create the new referee in
	 */
	public void addNewReferee(Team team) {
		RefereeInputForm inputForm = new RefereeInputForm(this); // create an input form for a new referee
		if(inputForm.getNewReferee() != null) { // make sure a new referee has been submitted
			team.addReferee(inputForm.getNewReferee()); // add the new referee to the team
		}
		updateRefereeTable(team); // update the referee table with the new referee
		refereeTable.revalidate(); // refactor the refereeTable to show the new referee
	}
	
	/**
	 * Updates the values shown in the stats table.
	 * 
	 * Takes in any class that implements the
	 * `StatisticsCalculator` interface, with a special
	 * condition for a team class.
	 * 
	 * @param e the StatisticsCalculator to update the statistics for
	 */
	public void updateStatsTableFor(StatisticsCalculator e) {
		int rows; // how many rows the table will need
		if(e.getClass() == Team.class) { // check for special case of a team class
			rows = 5; // team class has 5 rows
		} else { 
			rows = 4; // any other class will only need 4 rows
		}
		String[] columnNames = {"Stat", "Result"}; // headings for table columns
		Object[] statResults = new Object[4]; // makes stats null initially
		if(e.topGoalScorer() != null) { // only find stats if there are stats to be found, otherwise an error occurs
			// add relevant data for stat headings
			statResults[0] = e.topGoalScorer().getName();
			statResults[1] = e.topAssister().getName();
			statResults[2] = e.totalGoalsScored();
			statResults[3] = e.totalCardsGiven();
		}
		Object[][] data = new Object[rows][columnNames.length]; // initialise data array with enough room for each row and each column
		// add relevant data to each row in the data array
		data[0] = new Object[] {"Top Goal Scorer", statResults[0]};
		data[1] = new Object[] {"Top Assister", statResults[1]};
		data[2] = new Object[] {"Total Goals Scored", statResults[2]};
		data[3] = new Object[] {"Total Cards Given", statResults[3]};
		
		if(e.getClass() == Team.class) { // special case for a team
			// add the extra stat for a team class
			data[4] = new Object[] {"Clean Sheets", ((Team) e).getCleanSheets()};
		}
		// the row return table has been used so the team from each row can be accessed to navigate the app
		if(statsTable == null) { // if no table exists, initialise one
			statsTable = new UneditableTable(data, columnNames); // create a table that can't be edited to display the info
		} else { // table exists already, so just update values
			((UneditableTableModel) statsTable.getModel()).setData(data);
		}
	}

	/**
	 * Update the values shown in the results table for
	 * the given class with results.
	 * 
	 * Will work with any class that implements the
	 * `HasResults` interface
	 * 
	 * @param e the object with results to update the table for
	 */
	public void updateResultsTableFor(HasResults e) {
		String[] columnNames = {"Home Team", "Score", "Away Team"}; // headings for table columns
		Object[][] data = new Object[e.getResults().size()][columnNames.length]; // initialise the data array to be big enough for all results and columns
		Result[] results = new Result[e.getResults().size()]; // initialise the results array to be big enough to hold every result
		
		for(int i = 0; i < e.getResults().size(); i++) { // loop every stored result
			Result currentResult = e.getResults().get(i); // holds the current result
			
			// add data to the relevant column
			// reverse the order results are processed into the data array so that most recent are first
			data[e.getResults().size() - (1 + i)][0] = currentResult.getHomeTeam().getTeamName();
			data[e.getResults().size() - (1 + i)][1] = currentResult.scoreString();
			data[e.getResults().size() - (1 + i)][2] = currentResult.getAwayTeam().getTeamName();
			
			results[e.getResults().size() - (1 + i)] = currentResult; // allows the rowObject to match the reversed row display
		}
		if(resultsTable == null) { // if no table exists, initialise one
			// create a uneditable table that can be clicked to give the result
			resultsTable = new UneditableTableWithRowObjectReturn(data, columnNames, results);
			resultsTable.addMouseListener(this);
		} else { // table exists already, so just update values
			((UneditableRowObjectReturnTableModel) resultsTable.getModel()).setData(data);
			((UneditableRowObjectReturnTableModel) resultsTable.getModel()).setRowObjects(results);
		}
	}
	
	/**
	 * Creates the input form and passes the input to the current
	 * league to create a new result.
	 * 
	 * Takes a league as a parameter so that in future, if the app
	 * works with multiple leagues at once, this method does not need
	 * to change.
	 * 
	 * @param league the league to create the result for
	 */
	public void addNewResult(League league) {
		ResultInputForm inputForm = new ResultInputForm(this, league); // create an input form for the league
		if(inputForm.getNewResult() != null) { // makes sure a result has been submitted
			league.addResult(inputForm.getNewResult()); // add the result to the league
		}
		
		updateResultsTableFor(league); // updates the results table with the new result
		resultsTable.revalidate(); // revalidates the result table to show the new result
		updateLeagueTable(league); // updates the league table with new W/L/D values etc
		leagueTable.revalidate(); // revalidate the league table to show new W/L/D values
		updateStatsTableFor(league); // updates the stats table to get current stats after new result
		statsTable.revalidate(); // show stat changes on page by revalidating
	}
	
	/**
	 * Used to check with user what they wish to do when
	 * an error occurs whilst saving data and then takes
	 * the appropriate action.
	 * 
	 * If the user wishes to quit regardless of the error,
	 * this method will close the application, otherwise,
	 * this method will let execution continue
	 */
	private void saveFailWarning() {
		/*
		 * The code below was researched at the following resource:
		 * 
		 * Marilena, 2016. Java Swing – JOptionPane showConfirmDialog example. [Online] 
		 * Available at: https://mkyong.com/swing/java-swing-how-to-make-a-confirmation-dialog/
		 * [Accessed 7 December 2021].
		 * 
		 * I did not copy the code verbatim, I used it to understand how to make a pop-up
		 * JOptionPane where the user has to confirm their choice, I adapted it to
		 * suit the needs of this app.
		 */
		int input = JOptionPane.showConfirmDialog(this, "Saving failed, are you sure you want to exit?", "Save failure", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		if(input == JOptionPane.YES_OPTION) {
			// if the user still wishes to exit after receiving the warning then do so
			System.exit(0);				
		} // otherwise do nothing
	}

	/**
	 * Invoked when an action occurs.
	 * 
	 * These actions are created by buttons in the frame
	 * and this method will respond differently based on the
	 * button that was pressed.
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) { // get the label for the button and take different actions based on this
		case "Add Team": // if the add team button is pressed
			addNewTeam(currentLeague); // add a new team to the current league
			break; // prevent other cases running for this action
		case "Add Results": // if the add results button is pressed
			addNewResult(currentLeague); // add a new result to the current league
			break; // prevent other cases running for this action
		case "Add Player": // if the add player button is pressed
			addNewPlayer(currentTeam); // add a new player to the current team
			break; // prevent other cases running for this action
		case "Add Coaching Staff": // if the add coaching staff button is pressed
			addNewCoachingStaffMember(currentTeam); // add a new coaching staff member to the current team
			break; // prevent other cases running for this action
		case "Add Referee": // if the add referee button is pressed
			addNewReferee(currentTeam); // add a new referee to the current team
			break; // prevent other cases running for this action
		//case "< Back to league view": no longer needed as this is performed in the default clause to save code reuse in case "Delete Team:
		case "Delete Team": // if the delete team button is pressed
			// create an input dialog to make the user confirm the deletion
			int input = JOptionPane.showConfirmDialog(getOwner(), "Are you sure you want to delete this Team?\nThis cannot be undone.", "Delete Team", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if(input == JOptionPane.YES_OPTION) { // if the user still wishes to delete
				currentLeague.removeTeam(currentTeam); // remove the team from the league
			} else { // user wishes to keep the team
				break; // prevent other cases running for this action
			}
			// no break when the user deletes the team so the default case also runs
		default: // takes place when '< back to league page' is pressed and when team is deleted
			setUpLeaguePage(currentLeague); // set the league page up
			getContentPane().removeAll(); // clear the frame
			getContentPane().add(leaguePage); // add the league page to the frame
			// update the frame display
			revalidate();
			repaint();
			break;
		}
	}
	
	/**
	 * Invoked when the mouse button has been clicked (pressed and released) on a component.
	 * 
	 * These actions are created by tables in the frame and all require
	 * a double click to have their action processed.
	 * 
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		JTable table = (JTable) e.getSource(); // get the table the click occured on
		Point point = e.getPoint(); // get the point in the table that was clicked
		int row = table.rowAtPoint(point); // find which row was clicked so the rowObject can be retrieved
		if(e.getClickCount() == 2 && row != -1) { // if double clicked on an actual row
			UneditableRowObjectReturnTableModel model = (UneditableRowObjectReturnTableModel) table.getModel(); // gets the model that will have row return
			Object rowObject = model.getRowObject(row); // gets the object associated with the row
			
			// different cases depending on the class of the rowObject
			if(rowObject.getClass() == Team.class) { // if the selected row holds a team
				/*
				 * When a team is double clicked, the desired action is
				 * to navigate to the team page of that team
				 */
				currentTeam = (Team) rowObject; // set the currentTeam to the selected team
				setUpTeamPage(currentTeam); // set the team page up for this team
				getContentPane().removeAll(); // remove existing components from the frame
				getContentPane().add(teamPage); // add the teamPage to the frame
				// update the frame display to show the teamPage
				revalidate();
				repaint();
			}
			if(rowObject.getClass() == Referee.class) { // if the selected row holds a referee 
				/*
				 * When a referee is double clicked, a pop-out dialog
				 * containing information about the referee should
				 * be created.
				 */
				new RefereeOutputDialog(this, (Referee) rowObject); // create an output dialog for the selected referee
			}
			if(rowObject.getClass() == CoachingStaffMember.class) { // if the selected row holds a coaching staff member
				/*
				 * When a coaching staff member is double clicked,
				 * a pop-out dialog containing information about 
				 * the coaching staff member should be created.
				 */
				new CoachingStaffMemberOutputDialog(this, (CoachingStaffMember) rowObject); // create an output dialog for the selected coach
			}
			if(rowObject.getClass() == Player.class) { // if the selected row holds a player
				/*
				 * When a player is double clicked, a pop-out dialog
				 * containing information about the player should
				 * be created.
				 */
				new PlayerOutputDialog(this, (Player) rowObject); // create an output dialog for the selected player
			}
			if(rowObject.getClass() == Result.class) { // if the selected row holds a result
				/*
				 * When a result is double clicked, a pop-out dialog
				 * containing all result details should be created.
				 */
				new ResultOutputDialog(this, (Result) rowObject); // create an output dialog for the result
			}
			
		}
	}
	
	/*
	 * The remaining method belonging to the MouseListener
	 * interface are unimplemented as they are not currently
	 * needed for the GUIs operations.
	 */

	@Override
	public void mousePressed(MouseEvent e) {
		// unimplemented as not needed
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// unimplemented as not needed		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// unimplemented as not needed		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// unimplemented as not needed		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// unimplemented as not needed		
	}

	/**
	 * Invoked when the user attempts to close the window from the window's system menu.
	 * 
	 * Used to create a custom close operation for the app, where
	 * the league is saved before exiting the program.
	 * 
	 * This method will also warn the user if the save fails and give them
	 * the opportunity to cancel the exit to not lose their data.
	 */
	@Override
	public void windowClosing(WindowEvent e) {
		try { // code here may give an error
			currentLeague.save("./data/saveData"); // try to save the league data at the given location
			System.out.println("League saved"); // shows that the league saves successfully
			FormationManager.save(); // try to save newly created formations
			PositionManager.save(); // try to save newly created positions
			System.exit(0); // exit the application
		} catch (FileNotFoundException e1) { // error where files can't be found
			System.out.println("File was not found"); // inform which error took place
			e1.printStackTrace();
			saveFailWarning(); // ask the user what they wish to do about the save fail
		} catch (IOException e1) {
			System.out.println("An I/O Error has occured"); // inform which error took place
			e1.printStackTrace();
			saveFailWarning(); // ask the user what they wish to do about the save fail
		}
	}
	
	/*
	 * The remaining method belonging to the WindowListener
	 * interface are unimplemented as they are not currently
	 * needed for the GUIs operations.
	 */

	@Override
	public void windowClosed(WindowEvent e) {
		// unimplemented as not needed		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// unimplemented as not needed		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// unimplemented as not needed		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// unimplemented as not needed		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// unimplemented as not needed		
	}

	// Getters
	
	/**
	 * @return the currentLeague
	 */
	public League getCurrentLeague() {
		return currentLeague;
	}

	/**
	 * @return the currentTeam
	 */
	public Team getCurrentTeam() {
		return currentTeam;
	}
	
	/**
	 * @return the leaguePage
	 */
	public JPanel getLeaguePage() {
		return leaguePage;
	}

	/**
	 * @return the leagueTable
	 */
	public JTable getLeagueTable() {
		return leagueTable;
	}

	/**
	 * @return the statsTable
	 */
	public JTable getStatsTable() {
		return statsTable;
	}

	/**
	 * @return the resultsTable
	 */
	public JTable getResultsTable() {
		return resultsTable;
	}

	/**
	 * @return the teamPage
	 */
	public JPanel getTeamPage() {
		return teamPage;
	}

	/**
	 * @return the teamInfoTable
	 */
	public JTable getTeamInfoTable() {
		return teamInfoTable;
	}

	/**
	 * @return the playerTable
	 */
	public JTable getPlayerTable() {
		return playerTable;
	}

	/**
	 * @return the coachingStaffTable
	 */
	public JTable getCoachingStaffTable() {
		return coachingStaffTable;
	}

	/**
	 * @return the refereeTable
	 */
	public JTable getRefereeTable() {
		return refereeTable;
	}
	
	// Setters

	/**
	 * @param currentLeague the currentLeague to set
	 */
	public void setCurrentLeague(League currentLeague) {
		this.currentLeague = currentLeague;
	}
	
	/**
	 * @param currentTeam the currentTeam to set
	 */
	public void setCurrentTeam(Team currentTeam) {
		this.currentTeam = currentTeam;
	}

}
