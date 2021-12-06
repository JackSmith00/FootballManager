package guiOutputDialogs;

import javax.swing.JFrame;

import leagueComponents.Person;

public class RefereeOutputDialog extends PersonOutputDialog {

	public RefereeOutputDialog(JFrame owner, Person person) {
		super(owner, person, 300, 110);
		
		setUpComponents();
		getContentPane().add(frame);
		setVisible(true);
	}

	
}
