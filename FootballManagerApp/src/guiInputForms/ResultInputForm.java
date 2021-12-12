package guiInputForms;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;

import enums.EventType;
import events.GameEvent;
import events.Result;
import events.TwoPlayerGameEvent;
import gui.PlayerSelectTable;
import gui.PlayerSelectTableModel;
import gui.UneditableTableWithRowObjectReturn;
import leagueComponents.League;
import leagueComponents.Player;
import leagueComponents.Team;

public class ResultInputForm extends JDialog implements ActionListener {

	protected JPanel form;

	private String[] playerInputColumns;
	protected JComboBox<Team> homeTeamInput, awayTeamInput;
	protected JLabel homeScoreLabel, awayScoreLabel;
	protected JSpinner datePlayedInput;
	protected PlayerSelectTable homePlayersInput, awayPlayersInput;
	protected UneditableTableWithRowObjectReturn goalsTable;
	private JPanel goalInput;
	protected JComboBox<Player> goalScorerInput, goalAssisterInput;
	protected JSpinner goalTime;
	protected JButton addGoalButton, updateGoalButton, deleteGoalButton;
	protected UneditableTableWithRowObjectReturn substitutionsTable;
	private JPanel substitutionInput;
	protected JComboBox<Player> subbedOnInput, subbedOffInput;
	protected JSpinner substitutionTime;
	protected JButton addSubstitutionButton, updateSubstitutionButton, deleteSubstitutionButton;
	protected UneditableTableWithRowObjectReturn cardsTable;
	private JPanel cardInput;
	protected JComboBox<Player> playerCardedInput;
	protected JSpinner cardTime;
	protected JButton addCardButton, updateCardButton, deleteCardButton;
	protected JButton submitButton, cancelButton;
	
	private ArrayList<TwoPlayerGameEvent> goals = new ArrayList<TwoPlayerGameEvent>();
	private ArrayList<TwoPlayerGameEvent> substitutions = new ArrayList<TwoPlayerGameEvent>();
	private ArrayList<GameEvent> cards = new ArrayList<GameEvent>();
	private int homeScore = 0;
	private int awayScore = 0;
	
	private Result newResult;
	
	public ResultInputForm(JFrame owner, League league) {
		super(owner, "Add Result", true);
		
		setUpDisplay(league);
		getContentPane().add(form);
	
		setBounds(0, 0, 750, 780);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void setUpDisplay(League league) {
		GridBagLayout layout = new GridBagLayout();
		layout.columnWidths = new int[] {300, 50, 300};
		layout.rowHeights = new int[] {25, 25, 25, 150, 25, 25, 100, 25, 100, 25, 100};
		form = new JPanel(layout);
		GridBagConstraints constraints = new GridBagConstraints();
		
		JLabel homeTeamLabel = new JLabel("Home Team");
		JLabel scoreLabel = new JLabel("Score");
		JLabel scoreDash = new JLabel("-");
		JLabel awayTeamLabel = new JLabel("Away Team");
		JLabel datePlayedLabel = new JLabel("Date");
		JLabel homePlayersLabel = new JLabel("Home Team Players");
		JLabel awayPlayersLabel = new JLabel("Away Team Players");
		JLabel eventsLabel = new JLabel("Events");
		JLabel goalsLabel = new JLabel("Goals");
		JLabel substitutionsLabel = new JLabel("Substitutions");
		JLabel cardsLabel = new JLabel("Cards");
		
		Team[] teamsInLeague = new Team[league.getTeams().size()];
		league.getTeams().toArray(teamsInLeague);
		homeTeamInput = new JComboBox<Team>(teamsInLeague);
		homeTeamInput.addActionListener(this);
		awayTeamInput = new JComboBox<Team>(teamsInLeague);
		awayTeamInput.addActionListener(this);
		
		homeScoreLabel = new JLabel(String.valueOf(homeScore));
		awayScoreLabel = new JLabel(String.valueOf(awayScore));
		JPanel scorePanel = new JPanel();
		scorePanel.add(homeScoreLabel);
		scorePanel.add(scoreDash);
		scorePanel.add(awayScoreLabel);
		
		playerInputColumns =  new String[] {"Player", "Played"};
		homePlayersInput = new PlayerSelectTable(null, playerInputColumns, null);
		updatePlayerTable(homeTeamInput);
		JScrollPane homePlayerScrollPane = new JScrollPane(homePlayersInput);
		
		awayPlayersInput = new PlayerSelectTable(null, playerInputColumns, null);
		updatePlayerTable(awayTeamInput);
		JScrollPane awayPlayerScrollPane = new JScrollPane(awayPlayersInput);
		
		datePlayedInput = new JSpinner(new SpinnerDateModel());
		datePlayedInput.setEditor(new JSpinner.DateEditor(datePlayedInput, "dd/MM/yy")); // https://docs.oracle.com/javase/tutorial/uiswing/components/spinner.html
		
		goalsTable = new UneditableTableWithRowObjectReturn(null, new String[] {"Scored by", "Assisted by", "Game Minute"}, null);
		updateEventTable(goalsTable);
		setUpGoalInput();
		JScrollPane goalScrollPane = new JScrollPane(goalsTable);
		
		substitutionsTable = new UneditableTableWithRowObjectReturn(null, new String[] {"Substituted on", "Substituted off", "Game Minute"}, null);
		updateEventTable(substitutionsTable);
		setUpSubstitutionInput();
		JScrollPane substitutionScrollPane = new JScrollPane(substitutionsTable);
		
		cardsTable = new UneditableTableWithRowObjectReturn(null, new String[] {"Player carded", "Game Minute"}, null);			
		updateEventTable(cardsTable);
		setUpCardInput();
		JScrollPane cardScrollPane = new JScrollPane(cardsTable);
		
		form.add(homeTeamLabel, constraints);
		
		constraints.gridx = 1;
		form.add(scoreLabel, constraints);
		
		constraints.gridx = 2;
		form.add(awayTeamLabel, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		form.add(homeTeamInput, constraints);
		
		constraints.gridx = 1;
		form.add(scorePanel, constraints);
		
		constraints.gridx = 2;
		form.add(awayTeamInput, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.insets = new Insets(5, 0, 0, 0);
		form.add(homePlayersLabel, constraints);
		
		constraints.gridx = 1;
		form.add(datePlayedLabel, constraints);
		
		constraints.gridx = 2;
		form.add(awayPlayersLabel, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.insets = new Insets(0, 0, 0, 0);
		constraints.anchor = GridBagConstraints.NORTH;
		form.add(datePlayedInput, constraints);
		
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.gridy = 4;
		form.add(eventsLabel, constraints);
		
		constraints.gridy = 5;
		constraints.gridwidth = 2;
		constraints.insets = new Insets(10, 0, 0, 0);
		form.add(goalsLabel, constraints);
		
		constraints.gridy = 7;
		form.add(substitutionsLabel, constraints);
		
		constraints.gridy = 9;
		form.add(cardsLabel, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(5, 10, 5, 10);
		form.add(homePlayerScrollPane, constraints);
		
		constraints.gridx = 2;
		form.add(awayPlayerScrollPane, constraints);
		
		constraints.gridy = 6;
		constraints.gridx = 0;
		form.add(goalInput, constraints);
		
		constraints.gridy = 8;
		form.add(substitutionInput, constraints);
		
		constraints.gridy = 10;
		form.add(cardInput, constraints);
		
		constraints.gridy = 6;
		constraints.gridx = 1;		
		constraints.gridwidth = 2;
		form.add(goalScrollPane, constraints);
		
		constraints.gridy = 8;
		form.add(substitutionScrollPane, constraints);
		
		constraints.gridy = 10;
		form.add(cardScrollPane, constraints);
		
		JPanel buttons = new JPanel();
		submitButton = new JButton("Submit");
		submitButton.addActionListener(this);
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		buttons.add(submitButton);
		buttons.add(cancelButton);
		getContentPane().add(buttons, BorderLayout.SOUTH);
		
	}
	
	private void updatePlayerTable(JComboBox<Team> teamSelector) {
		Team selectedTeam = (Team) teamSelector.getSelectedItem();
		Player[] allTeamPlayers = new Player[selectedTeam.getPlayers().size()];
		selectedTeam.getPlayers().toArray(allTeamPlayers);
		Object[][] data = new Object[allTeamPlayers.length][2];
		for(int i = 0; i < allTeamPlayers.length; i++) {
			data[i][0] = allTeamPlayers[i].getName();
			data[i][1] = true;
		}
		if(teamSelector == homeTeamInput) {
			homePlayersInput.setData(data, allTeamPlayers);
		} else {
			awayPlayersInput.setData(data, allTeamPlayers);
		}
	}
	
	private void updateEventTable(UneditableTableWithRowObjectReturn table) {
		ArrayList associatedArray;
		short columns;
		if(table.equals(goalsTable)) {
			columns = 3;
			associatedArray = goals;
		} else if(table == substitutionsTable) {
			columns = 3;
			associatedArray = substitutions;
		} else {
			columns = 2;
			associatedArray = cards;
		}
		Object[][] data = new Object[associatedArray.size()][columns];
		GameEvent[] rowObjects = new GameEvent[associatedArray.size()];
		associatedArray.toArray(rowObjects);
		
		if(table.equals(goalsTable) || table == substitutionsTable) {
			for(int i = 0; i < associatedArray.size(); i++) {
				TwoPlayerGameEvent event = (TwoPlayerGameEvent) associatedArray.get(i);
				
				data[i][0] = event.getPlayer();
				data[i][1] = event.getSecondPlayer();
				data[i][2] = event.getGameMinute();
			}
		} else {
			for(int i = 0; i < associatedArray.size(); i++) {
				GameEvent event = (GameEvent) associatedArray.get(i);
				
				data[i][0] = event.getPlayer();
				data[i][1] = event.getGameMinute();
			}
		}
		table.setData(data, rowObjects);
		table.revalidate();
	}

	public Player[] playersWhoPlayedFor(PlayerSelectTable table) {
		ArrayList<Player> outputBuilder = new ArrayList<Player>();
		for(int i = 0; i < table.getRowCount(); i++) {
			if((boolean) table.getValueAt(i, 1)) { // see if checkbox is ticked, meaning the player played
				outputBuilder.add(((PlayerSelectTableModel) table.getModel()).getPlayerAt(i)); // add the player to the players who played
			}
		}
		
		Player[] output = new Player[outputBuilder.size()];
		outputBuilder.toArray(output);
		
		return output;
	}
	
	private Player[] getAllPlayers() {
		Player[] homePlayers = playersWhoPlayedFor(homePlayersInput);
		Player[] awayPlayers = playersWhoPlayedFor(awayPlayersInput);
		if(homeTeamInput.getSelectedItem().equals(awayTeamInput.getSelectedItem())) {
			return homePlayers; // prevent players appearing twice when the user has not yet set both teams
		}
		Player[] allPlayers = new Player[homePlayers.length + awayPlayers.length];
		for(int i = 0; i < homePlayers.length; i++) {
			allPlayers[i] = homePlayers[i]; // add all home players
		}
		for(int i = 0; i < awayPlayers.length; i++) {
			allPlayers[homePlayers.length + i] = awayPlayers[i]; // add all away players
		}
		return allPlayers;
	}
	
	private void updateGoalScorerInput() {
		Player[] allPlayers = getAllPlayers();
		if(goalScorerInput == null) {			
			goalScorerInput = new JComboBox<Player>(allPlayers);
		} else {
			goalScorerInput.removeActionListener(this); // prevents actions being caused when updates are made to this value with code insted of user input
			goalScorerInput.removeAllItems();
			for(Player player: allPlayers) {
				goalScorerInput.addItem(player);
			}
			goalScorerInput.addActionListener(this); // re-attach action listener to listen for user input
		}
		updateGoalAssisterInput();
	}
	
	private void updateGoalAssisterInput() {
		Team homeTeam = (Team) homeTeamInput.getSelectedItem();
		Team goalScorerTeam = ((Player) goalScorerInput.getSelectedItem()).getTeam();
		if(goalScorerTeam.equals(homeTeam)) { // if goal scorer was a home player, assister is from the home team
			if(goalAssisterInput == null) {
				goalAssisterInput = new JComboBox<Player>(playersWhoPlayedFor(homePlayersInput));
			} else {
				goalAssisterInput.removeAllItems();
				for(Player player: playersWhoPlayedFor(homePlayersInput)) {
					goalAssisterInput.addItem(player);
				}
			}
		} else { // otherwise, the goal scorer and assister are away players
			if(goalAssisterInput == null) {
				goalAssisterInput = new JComboBox<Player>(playersWhoPlayedFor(awayPlayersInput));
			} else {
				goalAssisterInput.removeAllItems();
				for(Player player: playersWhoPlayedFor(awayPlayersInput)) {
					goalAssisterInput.addItem(player);
				}
			}		
		}
	}
	
	private void addGoal(TwoPlayerGameEvent goal) {
		goals.add(goal);
		/*
		 * Below adds the goal to the relevant teams label, however it will not understand own goals
		 */
		if(goal.getPlayer().getTeam() == homeTeamInput.getSelectedItem()) { // if goal was scored by home team player
			homeScoreLabel.setText(String.valueOf(++homeScore));
		} else { // if not home player, then away
			awayScoreLabel.setText(String.valueOf(++awayScore));
		}
		Collections.sort(goals); // https://howtodoinjava.com/java/sort/collections-sort/
	}

	private void deleteGoal(TwoPlayerGameEvent goal) {
		goals.remove(goal);
		if(goal.getPlayer().getTeam() == homeTeamInput.getSelectedItem()) { // if goal was scored by home team player
			homeScoreLabel.setText(String.valueOf(--homeScore));
		} else { // if not home player, then away
			awayScoreLabel.setText(String.valueOf(--awayScore));
		}
	}
	
	private void updateGoal(TwoPlayerGameEvent oldGoalData, TwoPlayerGameEvent newGoalData) {
		int index = goals.indexOf(oldGoalData);
		goals.remove(index);
		goals.add(index, newGoalData);
		if(oldGoalData.getPlayer().getTeam() != newGoalData.getPlayer().getTeam()) { // if goal was not scored by same team, update scores, otherwise, no update needed
			if(oldGoalData.getPlayer().getTeam() == homeTeamInput.getSelectedItem()) { // if old goal was home team, new goal is away team
				// lower home score and increase away score
				homeScoreLabel.setText(String.valueOf(--homeScore));
				awayScoreLabel.setText(String.valueOf(++awayScore));
			} else { // old goal was away team so new goal is home team
				// increase home score and lower away score
				homeScoreLabel.setText(String.valueOf(++homeScore));
				awayScoreLabel.setText(String.valueOf(--awayScore));				
			}
		}
		Collections.sort(goals);
	}
	
	private void updateSubbedOnInput() {
		Player[] allPlayers = getAllPlayers();
		if(subbedOnInput == null) {
			subbedOnInput = new JComboBox<Player>(allPlayers);
		} else {
			subbedOnInput.removeActionListener(this); // prevents actions being caused when updates are made to this value with code insted of user input
			subbedOnInput.removeAllItems();
			for(Player player: allPlayers) {
				subbedOnInput.addItem(player);
			}
			subbedOnInput.addActionListener(this); // re-attach action listener to listen for user input
		}
		updateSubbedOffInput();
	}
	
	private void updateSubbedOffInput() {
		Team homeTeam = (Team) homeTeamInput.getSelectedItem();
		Team subbedOnTeam = ((Player) subbedOnInput.getSelectedItem()).getTeam();
		if(subbedOnTeam.equals(homeTeam)) { // if goal scorer was a home player, assister is from the home team
			if(subbedOffInput == null) {
				subbedOffInput = new JComboBox<Player>(playersWhoPlayedFor(homePlayersInput));
			} else {
				subbedOffInput.removeAllItems();
				for(Player player: playersWhoPlayedFor(homePlayersInput)) {
					subbedOffInput.addItem(player);
				}
			}
		} else { // otherwise, the goal scorer and assister are away players
			if(subbedOffInput == null) {
				subbedOffInput = new JComboBox<Player>(playersWhoPlayedFor(awayPlayersInput));
			} else {
				subbedOffInput.removeAllItems();
				for(Player player: playersWhoPlayedFor(awayPlayersInput)) {
					subbedOffInput.addItem(player);
				}
			}	
		}
	}

	private void addSubstitution(TwoPlayerGameEvent substitution) {
		substitutions.add(substitution);
		Collections.sort(substitutions);
	}

	private void deleteSubstitution(TwoPlayerGameEvent substitution) {
		substitutions.remove(substitution);
	}

	private void updateSubstitution(TwoPlayerGameEvent oldSubstitutionData, TwoPlayerGameEvent newSubstitutionData) {
		int index = substitutions.indexOf(oldSubstitutionData);
		substitutions.remove(index);
		substitutions.add(index, newSubstitutionData);
		Collections.sort(substitutions);
	}
		
	private void updatePlayerCardedInput() {
		Player[] allPlayers = getAllPlayers();
		if(playerCardedInput == null) {
			playerCardedInput = new JComboBox<Player>(allPlayers);
		} else {
			playerCardedInput.removeAllItems();
			for(Player player: allPlayers) {
				playerCardedInput.addItem(player);
			}
		}
	}
	
	private void addCard(GameEvent card) {
		cards.add(card);
		Collections.sort(cards);
	}

	
	private void deleteCard(GameEvent card) {
		cards.remove(card);
	}
	
	
	private void updateCard(GameEvent oldCardData, GameEvent newCardData) {
		int index = cards.indexOf(oldCardData);
		cards.remove(index);
		cards.add(index, newCardData);
		Collections.sort(cards);
	}

	private void setUpGoalInput() {
		goalInput = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		
		updateGoalScorerInput();
		goalScorerInput.addActionListener(this);
		goalTime = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
		addGoalButton = new JButton("Add");
		addGoalButton.addActionListener(this);
		updateGoalButton = new JButton("Update");
		updateGoalButton.addActionListener(this);
		deleteGoalButton = new JButton("Delete");
		deleteGoalButton.addActionListener(this);
		JPanel buttons = new JPanel();
		buttons.add(addGoalButton);
		buttons.add(updateGoalButton);
		buttons.add(deleteGoalButton);
		
		constraints.anchor = GridBagConstraints.LINE_END;
		goalInput.add(new JLabel("Goal Scorer:"), constraints);
		
		constraints.gridy = 1;
		goalInput.add(new JLabel("Goal Assister:"), constraints);
		
		constraints.gridy = 2;
		goalInput.add(new JLabel("Goal Time:"), constraints);
		
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.gridy = 3;
		constraints.gridwidth = 3;
		goalInput.add(buttons, constraints);
		
		constraints.anchor = GridBagConstraints.LINE_START;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 2;
		constraints.gridx = 1;
		constraints.gridy = 0;
		goalInput.add(goalScorerInput, constraints);
		
		constraints.gridy = 1;
		goalInput.add(goalAssisterInput, constraints);
		
		constraints.gridy = 2;
		goalInput.add(goalTime, constraints);
	}
	
	private void setUpSubstitutionInput() {
		substitutionInput = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		
		updateSubbedOnInput();
		subbedOnInput.addActionListener(this);
		substitutionTime = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
		addSubstitutionButton = new JButton("Add");
		addSubstitutionButton.addActionListener(this);
		updateSubstitutionButton = new JButton("Update");
		updateSubstitutionButton.addActionListener(this);
		deleteSubstitutionButton = new JButton("Delete");
		deleteSubstitutionButton.addActionListener(this);
		JPanel buttons = new JPanel();
		buttons.add(addSubstitutionButton);
		buttons.add(updateSubstitutionButton);
		buttons.add(deleteSubstitutionButton);
		
		constraints.anchor = GridBagConstraints.LINE_END;
		substitutionInput.add(new JLabel("Substituted On:"), constraints);
		
		constraints.gridy = 1;
		substitutionInput.add(new JLabel("Substituted Off:"), constraints);
		
		constraints.gridy = 2;
		substitutionInput.add(new JLabel("Substitution Time:"), constraints);
		
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.gridy = 3;
		constraints.gridwidth = 3;
		substitutionInput.add(buttons, constraints);
		
		constraints.anchor = GridBagConstraints.LINE_START;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 2;
		constraints.gridx = 1;
		constraints.gridy = 0;
		substitutionInput.add(subbedOnInput, constraints);
		
		constraints.gridy = 1;
		substitutionInput.add(subbedOffInput, constraints);
		
		constraints.gridy = 2;
		substitutionInput.add(substitutionTime, constraints);
	}
	
	private void setUpCardInput() {
		cardInput = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		
		updatePlayerCardedInput();
		cardTime = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
		addCardButton = new JButton("Add");
		addCardButton.addActionListener(this);
		updateCardButton = new JButton("Update");
		updateCardButton.addActionListener(this);
		deleteCardButton = new JButton("Delete");
		deleteCardButton.addActionListener(this);
		JPanel buttons = new JPanel();
		buttons.add(addCardButton);
		buttons.add(updateCardButton);
		buttons.add(deleteCardButton);
		
		constraints.anchor = GridBagConstraints.LINE_END;
		cardInput.add(new JLabel("Player Carded:"), constraints);
		
		constraints.gridy = 1;
		cardInput.add(new JLabel("Card Time:"), constraints);
		
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.gridy = 2;
		constraints.gridwidth = 3;
		cardInput.add(buttons, constraints);
		
		constraints.anchor = GridBagConstraints.LINE_START;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 2;
		constraints.gridx = 1;
		constraints.gridy = 0;
		cardInput.add(playerCardedInput, constraints);
		
		constraints.gridy = 1;
		cardInput.add(cardTime, constraints);
	}
	
	public Result getNewResult() {
		return newResult;
	}
	
	private GameEvent[] packedEvents() {
		ArrayList<GameEvent> outputBuilder = new ArrayList<GameEvent>(goals.size() + substitutions.size() + cards.size());
		outputBuilder.addAll(goals);
		outputBuilder.addAll(substitutions);
		outputBuilder.addAll(cards);
		Collections.sort(outputBuilder);
		GameEvent[] output = new GameEvent[outputBuilder.size()];
		outputBuilder.toArray(output);
		return output;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == homeTeamInput) {
			updatePlayerTable(homeTeamInput);
		}
		if(e.getSource() == awayTeamInput) {
			updatePlayerTable(awayTeamInput);
		}
		if(e.getSource() == goalScorerInput) {
			updateGoalAssisterInput();
		}
		if(e.getSource() == subbedOnInput) {
			updateSubbedOffInput();
		}
		if(e.getSource() == addGoalButton) {
			Player scorer = (Player) goalScorerInput.getSelectedItem();
			Player assister = (Player) goalAssisterInput.getSelectedItem();
			int time = (int) goalTime.getValue();
			
			addGoal(new TwoPlayerGameEvent(EventType.GOAL, scorer, assister, time));
			updateEventTable(goalsTable);
		}
		if(e.getSource() == deleteGoalButton) {
			int[] selectedRows = goalsTable.getSelectedRows();
			for(int row: selectedRows) {
				TwoPlayerGameEvent goal = (TwoPlayerGameEvent) goalsTable.getRowObject(row);
				deleteGoal(goal);
			}
			updateEventTable(goalsTable);
		}
		if(e.getSource() == updateGoalButton) {
			Player scorer = (Player) goalScorerInput.getSelectedItem();
			Player assister = (Player) goalAssisterInput.getSelectedItem();
			int time = (int) goalTime.getValue();
			
			int selectedRow = goalsTable.getSelectedRow();
			if(selectedRow != -1) { // makes sure a row is selected to update
				TwoPlayerGameEvent oldGoal = (TwoPlayerGameEvent) goalsTable.getRowObject(selectedRow);
				updateGoal(oldGoal, new TwoPlayerGameEvent(EventType.GOAL, scorer, assister, time));
			}
			
			updateEventTable(goalsTable);
		}
		if(e.getSource() == addSubstitutionButton) {
			Player subbedOn = (Player) subbedOnInput.getSelectedItem();
			Player subbedOff = (Player) subbedOffInput.getSelectedItem();
			int time = (int) substitutionTime.getValue();
			
			addSubstitution(new TwoPlayerGameEvent(EventType.SUBSTITUTION, subbedOn, subbedOff, time));
			updateEventTable(substitutionsTable);
		}
		if(e.getSource() == deleteSubstitutionButton) {
			int[] selectedRows = substitutionsTable.getSelectedRows();
			for(int row: selectedRows) {
				TwoPlayerGameEvent substitution = (TwoPlayerGameEvent) substitutionsTable.getRowObject(row);
				deleteSubstitution(substitution);
			}
			updateEventTable(substitutionsTable);
		}
		if(e.getSource() == updateSubstitutionButton) {
			Player subbedOn = (Player) subbedOnInput.getSelectedItem();
			Player subbedOff = (Player) subbedOffInput.getSelectedItem();
			int time = (int) substitutionTime.getValue();
			
			int selectedRow = substitutionsTable.getSelectedRow();
			if(selectedRow != -1) { // makes sure a row is selected to update
				TwoPlayerGameEvent oldSubstitution = (TwoPlayerGameEvent) substitutionsTable.getRowObject(selectedRow);
				updateSubstitution(oldSubstitution, new TwoPlayerGameEvent(EventType.SUBSTITUTION, subbedOn, subbedOff, time));
			}
			
			updateEventTable(substitutionsTable);
		}
		if(e.getSource() == addCardButton) {
			Player carded = (Player) playerCardedInput.getSelectedItem();
			int time = (int) cardTime.getValue();
			
			addCard(new GameEvent(EventType.CARD, carded, time));
			updateEventTable(cardsTable);
		}
		if(e.getSource() == deleteCardButton) {
			int[] selectedRows = cardsTable.getSelectedRows();
			for(int row: selectedRows) {
				GameEvent card = (GameEvent) cardsTable.getRowObject(row);
				deleteCard(card);
			}
			updateEventTable(cardsTable);
		}
		if(e.getSource() == updateCardButton) {
			Player carded = (Player) playerCardedInput.getSelectedItem();
			int time = (int) cardTime.getValue();
			
			int selectedRow = cardsTable.getSelectedRow();
			if(selectedRow != -1) { // makes sure a row is selected to update
				GameEvent oldCard = (GameEvent) cardsTable.getRowObject(selectedRow);
				updateCard(oldCard, new GameEvent(EventType.CARD, carded, time));
			}
			
			updateEventTable(cardsTable);
		}
		if(e.getSource() == homeTeamInput || e.getSource() == awayTeamInput) {			
			updateGoalScorerInput();
			updateSubbedOnInput();
			updatePlayerCardedInput();
		}
		if(e.getSource() == submitButton) {
			Team homeTeam = (Team) homeTeamInput.getSelectedItem();
			Team awayTeam = (Team) awayTeamInput.getSelectedItem();
			Date datePlayed = (Date) datePlayedInput.getValue();
			GameEvent[] events = packedEvents();
			Player[] homePlayers = playersWhoPlayedFor(homePlayersInput);
			Player[] awayPlayers = playersWhoPlayedFor(awayPlayersInput);
			
			newResult = new Result(homeTeam, homeScore, awayTeam, awayScore, datePlayed, events, homePlayers, awayPlayers);
		}
		if(e.getSource() == submitButton || e.getSource() == cancelButton) {
			dispose();
		}
		getContentPane().repaint();
	}
	
}
