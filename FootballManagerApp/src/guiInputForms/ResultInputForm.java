package guiInputForms;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;

import leagueComponents.League;
import leagueComponents.Team;

public class ResultInputForm extends JDialog {

	protected JPanel form;
	private JLabel homeTeam;
	private JLabel score;
	private JLabel scoreDash;
	private JLabel awayTeam;
	private JLabel datePlayed;
	private JLabel homePlayers;
	private JLabel awayPlayers;
	private JLabel events;
	private JLabel goals;
	private JLabel substitutions;
	private JLabel cards;
	
	protected JComboBox<Team> homeTeamInput;
	protected JComboBox<Team> awayTeamInput;
	protected JSpinner homeScoreInput;
	protected JSpinner awayScoreInput;
	protected JSpinner datePlayedInput;
	protected JTable homePlayersInput;
	protected JTable awayPlayersInput;
	protected JTable goalsInput;
	protected JTable substitutionsInput;
	protected JTable cardsInput;
	
	public ResultInputForm(JFrame owner, League league) {
		super(owner, "Add Result", true);
		GridBagLayout layout = new GridBagLayout();
		form = new JPanel(layout);
		GridBagConstraints constraints = new GridBagConstraints();
		
		homeTeam = new JLabel("Home Team");
		score = new JLabel("Score");
		scoreDash = new JLabel("-");
		awayTeam = new JLabel("Away Team");
		datePlayed = new JLabel("Date");
		homePlayers = new JLabel("Home Team Players");
		awayPlayers = new JLabel("Away Team Players");
		events = new JLabel("Events");
		goals = new JLabel("Goals");
		substitutions = new JLabel("Substitutions");
		cards = new JLabel("Cards");
		
		Team[] teamsInLeague = new Team[league.getTeams().size()];
		league.getTeams().toArray(teamsInLeague);
		homeTeamInput = new JComboBox<Team>(teamsInLeague);
		awayTeamInput = new JComboBox<Team>(teamsInLeague);
		homeScoreInput = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
		awayScoreInput = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
		datePlayedInput = new JSpinner(new SpinnerDateModel());
	}
	
}
