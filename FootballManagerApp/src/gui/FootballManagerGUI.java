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

public class FootballManagerGUI extends JFrame implements MouseListener, ActionListener, WindowListener {
	
	// Attributes
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
		setTitle("League Manager");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Makes the app quit when frame is closed
		addWindowListener(this); // https://docs.oracle.com/javase/tutorial/uiswing/events/windowlistener.html
		
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
			String leagueName = JOptionPane.showInputDialog(this, "League Name:"); // https://docs.oracle.com/javase/7/docs/api/javax/swing/JOptionPane.html
			currentLeague = new League(leagueName);
		}
		  
		setUpLeaguePage(currentLeague); // Separate method to set up all components for the GUI
		add(leaguePage);
		setBounds(100, 100, 900, 650);
		/*
		 * The line below makes the frame appear in the centre of the
		 * screen rather than in the top left, for a nicer user experience
		 * 
		 * I found how to do this at the following site:
		 * http://www.fredosaurus.com/notes-java/GUI/containers/10windows/15framesize.html
		 */
		setLocationRelativeTo(null);
		setResizable(false); // Stops the user from resizing the app, ruining the appearance
		setVisible(true); // Allows the user to see the frame and interact with it
	}
	
	public void setUpLeaguePage(League league) {
		if(leaguePage == null) {
			GridBagLayout layout = new GridBagLayout();
			layout.rowHeights = new int[] {30, 25, 120, 45, 225, 25};
			layout.columnWidths = new int[]{473, 312};
			leaguePage = new JPanel(layout); // create a panel with a gridbag layout
		} else {
			leaguePage.removeAll();
		}
		GridBagConstraints constraints = new GridBagConstraints(); // Constraints will be used for all items but with updated values
		
		JLabel leagueName = new JLabel(league.getName());
		leagueName.setHorizontalAlignment(JLabel.CENTER);

		updateLeagueTable(league);
		leagueTable.addMouseListener(this);
		JLabel leagueTableTitle = new JLabel("League Table");
		leagueTableTitle.setHorizontalAlignment(JLabel.CENTER);
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
		
		constraints.gridwidth = 2;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		leaguePage.add(leagueName, constraints);
		
		constraints.insets = new Insets(0, 0, 0, 5); // code found at https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html
		/*
		 * Spaces are numbered starting at 0 for the top, leftmost
		 * cell in the grid and each column to the right and row below
		 * has a higher x/y value
		 */
		//constraints.gridheight = 1; // Sets the item to span 4 grid spaces
		constraints.gridwidth = 1;
		constraints.gridx = 0;
		constraints.gridy = 1;		
		constraints.fill = GridBagConstraints.BOTH; // Allows the component to resize to fill the required space for its cells
		leaguePage.add(leagueTableTitle, constraints);
		
		constraints.gridy = 2;
		constraints.gridheight = 3;
		leaguePage.add(leagueScrollPane, constraints); // reference https://stackoverflow.com/questions/2320812/jtable-wont-show-column-headers
		
		constraints.gridheight = 2;
		constraints.gridy = 5;
		leaguePage.add(addTeamButton, constraints);
		
		constraints.gridheight = 1;
		constraints.gridy = 1;
		constraints.gridx = 1;
		constraints.insets = new Insets(0, 5, 0, 0);
		leaguePage.add(statsTableTitle, constraints);
		
		constraints.gridy = 2;
		leaguePage.add(statsScrollPane, constraints);
		
		constraints.gridy = 3;
		leaguePage.add(resultsTableTitle, constraints);		
		
		constraints.gridy = 4;
		leaguePage.add(resultsScrollPane, constraints);
		
		constraints.gridy = 5;
		leaguePage.add(addResultsButton, constraints);
		
	}
	
	public void updateLeagueTable(League league) {
		String[] columnNames = {"Team Name", "Wins", "Draws", "Losses", "Points", "Goal Difference"};
		Object[][] data = new Object[league.getTeams().size()][columnNames.length];
		Team[] teams = new Team[league.getTeams().size()];
		league.getTeams().toArray(teams);
		
		for(int i = 0; i < teams.length; i++) {
			Team currentTeam = teams[i];
			
			currentTeam.setLeaguePosition(i + 1);
			
			data[i][0] = currentTeam.getTeamName();
			data[i][1] = currentTeam.getGamesWon();
			data[i][2] = currentTeam.getGamesDrew();
			data[i][3] = currentTeam.getGamesLost();
			data[i][4] = currentTeam.getPoints();
			data[i][5] = currentTeam.getGoalDifference();
		}
		
		leagueTable = new UneditableTableWithRowObjectReturn(data, columnNames, teams);
		leagueTable.getColumn("Team Name").setMinWidth(150);
		leagueTable.getColumn("Goal Difference").setMinWidth(100);
		
	}
	
	public void addNewTeam(League league) {
		TeamInputForm inputForm = new TeamInputForm(this);
		if(inputForm.getNewTeam() != null) {
			league.addTeam(inputForm.getNewTeam());
		}
		setUpLeaguePage(league);
		getContentPane().revalidate();
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
