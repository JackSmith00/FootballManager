package guiOutputDialogs;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import enums.EventType;

import java.awt.GridBagLayout;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.awt.GridBagConstraints;

import events.GameEvent;
import events.Result;
import events.TwoPlayerGameEvent;
import gui.UneditableTableWithRowObjectReturn;
import leagueComponents.Player;

public class ResultOutputDialog extends JDialog {
	
	protected JPanel frame;
	
	private Result result;
	
	public ResultOutputDialog(JFrame owner, Result result) {
		super(owner, result.getHomeTeam().getTeamName() + " v " + result.getAwayTeam().getTeamName());
		this.result = result;
		
		setUpComponents();
		getContentPane().add(frame);
		setBounds(0, 0, 300, 300);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	protected void setUpComponents() {
		frame = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		layout.columnWidths = new int[] {300, 50, 300};
		layout.rowHeights = new int[] {25, 25, 25, 150, 25, 200};
		frame.setLayout(layout);
		
		GridBagConstraints constraints = new GridBagConstraints();
		
		JLabel homeTeamLabel = new JLabel("Home Team");
		JLabel scoreLabel = new JLabel("Score");
		JLabel awayTeamLabel = new JLabel("Away Team");
		
		frame.add(homeTeamLabel, constraints);
		constraints.gridx = 1;
		frame.add(scoreLabel, constraints);
		constraints.gridx = 2;
		frame.add(awayTeamLabel, constraints);
		
		JLabel homeTeamName = new JLabel(result.getHomeTeam().getTeamName());
		JLabel score = new JLabel(result.scoreString());
		JLabel awayTeamName = new JLabel(result.getAwayTeam().getTeamName());
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		frame.add(homeTeamName, constraints);
		
		constraints.gridx = 1;
		frame.add(score, constraints);
		
		constraints.gridx = 2;
		frame.add(awayTeamName, constraints);
		
		JLabel homePlayerTableLabel = new JLabel("Home Players");
		JLabel dateLabel = new JLabel("Date");
		JLabel awayPlayerTableLabel = new JLabel("Home Players");
		
		constraints.gridx = 0;
		constraints.gridy = 2;
		frame.add(homePlayerTableLabel, constraints);
		
		constraints.gridx = 1;
		frame.add(dateLabel, constraints);
		
		constraints.gridx = 2;
		frame.add(awayPlayerTableLabel, constraints);
		
		String[] playerTableColumnNames = {"Name", "Position"};
		Player[] homePlayers = result.getHomePlayers();
		UneditableTableWithRowObjectReturn homePlayerTable = new UneditableTableWithRowObjectReturn(generatePlayerTableContents(homePlayers), playerTableColumnNames, homePlayers);
		DateFormat dateFormatter = DateFormat.getDateInstance(); // https://docs.oracle.com/javase/7/docs/api/java/text/DateFormat.html
		JLabel date = new JLabel(dateFormatter.format(result.getDatePlayed()));
		Player[] awayPlayers = result.getAwayPlayers();
		UneditableTableWithRowObjectReturn awayPlayerTable = new UneditableTableWithRowObjectReturn(generatePlayerTableContents(awayPlayers), playerTableColumnNames, awayPlayers);
		
		constraints.gridx = 0;
		constraints.gridy = 3;
		frame.add(homePlayerTable, constraints);
		
		constraints.anchor = GridBagConstraints.NORTH;
		constraints.gridx = 2;
		frame.add(date, constraints);
		
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.gridx = 2;
		frame.add(awayPlayerTable, constraints);
		
		JLabel eventsLabel = new JLabel("Events");
		
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridwidth = 3;
		constraints.gridy = 4;
		frame.add(eventsLabel, constraints);
		
		GameEvent[] events = result.getEvents();
		UneditableTableWithRowObjectReturn eventsTable = new UneditableTableWithRowObjectReturn(generateEventsTableContents(events), new String[] {"Type", "Player(s)", "Time"}, events);
		
		constraints.gridy = 5;
		frame.add(eventsTable, constraints);
	}
	
	private Object[][] generatePlayerTableContents(Player[] players) {
		Object[][] data = new Object[players.length][2];
		for(int i = 0; i < players.length; i++) {
			data[i][0] = players[i].getName();
			data[i][1] = players[i].getPosition();
		}
		return data;
	}
	
	private Object[][] generateEventsTableContents(GameEvent[] events){
		Object[][] data = new Object[events.length][3];
		for(int i = 0; i < events.length; i++) {
			data[i][0] = events[i].getType();
			if (events[i].getType() == EventType.SUBSTITUTION) {
				String subbedPlayers = events[i].getPlayer().getName() + " (on), " + ((TwoPlayerGameEvent)events[i]).getSecondPlayer().getName() + " (off)";
				data[i][1] = subbedPlayers;
			} else {
				data[i][1] = events[i].getPlayer();
			}
			
			data[i][2] = events[i].getGameMinute();
		}
		return data;
	}
}
