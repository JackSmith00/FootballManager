package guiOutputDialogs;

import javax.swing.JFrame;

import leagueComponents.Person;

public class RefereeOutputDialog extends PersonOutputDialog {

	public RefereeOutputDialog(JFrame owner, Person person) {
		super(owner, person);
		
		setUpComponents();
		getContentPane().add(frame);
		setBounds(0, 0, 300, 110);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	
}
