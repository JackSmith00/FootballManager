package guiOutputDialogs;

import javax.swing.JDialog;
import javax.swing.JFrame;

import events.Result;

public class ResultOutputDialog extends JDialog {
	
	public ResultOutputDialog(JFrame owner, Result result) {
		super(owner, result.getHomeTeam().getTeamName() + " v " + result.getAwayTeam().getTeamName());
	}
	
}
