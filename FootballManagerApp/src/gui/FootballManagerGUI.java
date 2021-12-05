package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
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
	private Team currentTeam;

	public static void main(String[] args) {
		new FootballManagerGUI();
	}
	
	// Constructor
	public FootballManagerGUI() {
		frame = new JFrame("League Manager");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Makes the app quit when frame is closed
		
		currentLeague = new League();
		
		File data = new File("./data/saveData");
		
		if(data.exists()) {	  
			try {
				currentLeague.load("./data/saveData");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			String leagueName = JOptionPane.showInputDialog(frame, "League Name:"); // https://docs.oracle.com/javase/7/docs/api/javax/swing/JOptionPane.html
			currentLeague = new League(leagueName);
		}
		  
		setUpLeaguePage(currentLeague); // Separate method to set up all components for the GUI
		frame.add(leaguePage);
		frame.setBounds(100, 100, 900, 650);
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
	
	public void setUpLeaguePage(League league) {
		if(leaguePage == null) {
			GridBagLayout layout = new GridBagLayout();
			layout.rowHeights = new int[] {25, 120, 45, 225, 25};
			layout.columnWidths = new int[]{473, 312};
			leaguePage = new JPanel(layout); // create a panel with a gridbag layout
		} else {
			leaguePage.removeAll();
		}
		GridBagConstraints constraints = new GridBagConstraints(); // Constraints will be used for all items but with updated values
		
		updateLeagueTable(league);
		leagueTable.addMouseListener(this);
		JLabel leagueName = new JLabel(league.getName());
		leagueName.setHorizontalAlignment(JLabel.CENTER);
		JScrollPane leagueScrollPane = new JScrollPane(leagueTable);
		JButton addTeamButton = new JButton("Add Team");
		addTeamButton.addActionListener(this);
		
		updateStatsTableFor(league);
		JLabel statsTableTitle = new JLabel("Stats");
		statsTableTitle.setHorizontalAlignment(JLabel.CENTER);
		JScrollPane statsScrollPane = new JScrollPane(statsTable);
		
		updateResultsTableFor(league);
		JLabel resultsTableTitle = new JLabel("Results");
		resultsTableTitle.setHorizontalAlignment(JLabel.CENTER);
		JScrollPane resultsScrollPane = new JScrollPane(resultsTable);
		JButton addResultsButton = new JButton("Add Results");
		addResultsButton.addActionListener(this);
		
		
		constraints.gridx = 0; // Sets the column for the grid space
		constraints.gridy = 0; // Sets the row for the grid space
		constraints.insets = new Insets(0, 0, 0, 5); // code found at https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html
		/*
		 * Spaces are numbered starting at 0 for the top, leftmost
		 * cell in the grid and each column to the right and row below
		 * has a higher x/y value
		 */
		//constraints.gridheight = 1; // Sets the item to span 4 grid spaces
		constraints.fill = GridBagConstraints.BOTH; // Allows the component to resize to fill the required space for its cells
		leaguePage.add(leagueName, constraints);
		
		constraints.gridy = 1;
		constraints.gridheight = 3;
		leaguePage.add(leagueScrollPane, constraints); // reference https://stackoverflow.com/questions/2320812/jtable-wont-show-column-headers
		
		constraints.gridheight = 1;
		constraints.gridy = 4;
		leaguePage.add(addTeamButton, constraints);
		
		constraints.gridy = 0;
		constraints.gridx = 1;
		constraints.insets = new Insets(0, 5, 0, 0);
		leaguePage.add(statsTableTitle, constraints);
		
		constraints.gridy = 1;
		leaguePage.add(statsScrollPane, constraints);
		
		constraints.gridy = 2;
		leaguePage.add(resultsTableTitle, constraints);		
		
		constraints.gridy = 3;
		leaguePage.add(resultsScrollPane, constraints);
		
		constraints.gridy = 4;
		leaguePage.add(addResultsButton, constraints);
		
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
		TeamInputForm inputForm = new TeamInputForm(frame);
		if(inputForm.getNewTeam() != null) {
			league.addTeam(inputForm.getNewTeam());
		}
		setUpLeaguePage(league);
		frame.getContentPane().revalidate();
	}
	
	public void setUpTeamPage(Team team) {
		if(teamPage == null) {
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

		updateTeamInfoTable(team);
		teamInfoTable.setTableHeader(null); // https://stackoverflow.com/questions/2528643/jtable-without-a-header
		JLabel teamName = new JLabel(team.getTeamName());
		teamName.setHorizontalAlignment(JLabel.CENTER); // https://www.youtube.com/watch?v=Kmgo00avvEw&t=1698
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
		/*
		 * Spaces are numbered starting at 0 for the top, leftmost
		 * cell in the grid and each column to the right and row below
		 * has a higher x/y value
		 */
		constraints.insets = new Insets(0, 0, 0, 5);
		constraints.fill = GridBagConstraints.BOTH; // Allows the component to resize to fill the required space for its cells
		constraints.gridy = 1;
		teamPage.add(teamName, constraints);
		
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
	
	public void addNewPlayer(Team team) {
		PlayerInputForm inputForm = new PlayerInputForm(frame);
		if(inputForm.getNewPlayer() != null) {
			team.addPlayer(inputForm.getNewPlayer());
		}
		setUpTeamPage(team);
		frame.getContentPane().revalidate();	
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
		CoachingStaffInputForm inputForm = new CoachingStaffInputForm(frame);
		if(inputForm.getNewCoachingStaffMember() != null) {
			team.addCoachingStaffMember(inputForm.getNewCoachingStaffMember());
		}
		setUpTeamPage(team);
		frame.getContentPane().revalidate();
	}
	
	public void updateRefereeTable(Team team) {
		String[] columnNames = {"Name"};
		Object[][] data = new Object[team.getReferees().size()][columnNames.length];
		for(int i = 0; i < team.getReferees().size(); i++) {
			Referee currentRef = team.getReferees().get(i);
			
			data[i][0] = currentRef.getName();
		}
		
		refereeTable = new UneditableTable(data, columnNames);
	}
	
	public void addNewReferee(Team team) {
		RefereeInputForm inputForm = new RefereeInputForm(frame);
		if(inputForm.getNewReferee() != null) {
			team.addReferee(inputForm.getNewReferee());
		}
		setUpTeamPage(team);
		frame.getContentPane().revalidate();
	}
	
	public void updateStatsTableFor(StatisticsCalculator e) {
		String[] columnNames = {"Stat", "Result"};
		Object[] statResults = new Object[4]; // makes stats null initially
		if(e.topGoalScorer() != null) { // only find stats if there are stats to be found, otherwise an error occurs
			statResults[0] = e.topGoalScorer().getName();
			statResults[1] = e.topAssister().getName();
			statResults[2] = e.totalGoalsScored();
			statResults[3] = e.totalCardsGiven();
		}
		Object[][] data = {
				{"Top Goal Scorer", statResults[0]},
				{"Top Assister", statResults[1]},
				{"Total Goals Scored", statResults[2]},
				{"Total Cards Given", statResults[3]}
				};
		statsTable = new UneditableTable(data, columnNames);
	}

	public void updateResultsTableFor(HasResults e) {
		String[] columnNames = {"Home Team", "Score", "Away Team"};
		Object[][] data = new Object[e.getResults().size()][3];
		Result[] results = new Result[e.getResults().size()];
		e.getResults().toArray(results);
		
		for(int i = 0; i < e.getResults().size(); i++) {
			Result currentResult = e.getResults().get(i);
			data[e.getResults().size() - (1 + i)][0] = currentResult.getHomeTeam().getTeamName();
			data[e.getResults().size() - (1 + i)][1] = currentResult.scoreString();
			data[e.getResults().size() - (1 + i)][2] = currentResult.getAwayTeam().getTeamName();
		}
		
		resultsTable = new UneditableTableWithRowObjectReturn(data, columnNames, results);
	}
	
	public void addNewResult(League league) {
		ResultInputForm inputForm = new ResultInputForm(frame, league);
		if(inputForm.getNewResult() != null) {
			league.addResult(inputForm.getNewResult());
		}
		setUpLeaguePage(league);
		frame.getContentPane().revalidate();
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
		}
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
