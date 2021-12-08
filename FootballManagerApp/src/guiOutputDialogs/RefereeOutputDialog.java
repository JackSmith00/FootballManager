package guiOutputDialogs;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;

import guiInputForms.RefereeInputForm;
import leagueComponents.Referee;

public class RefereeOutputDialog extends PersonOutputDialog {

	public RefereeOutputDialog(JFrame owner, Referee person) {
		super(owner, person, 300, 140);
		setUpComponents();
		getContentPane().add(frame);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "Edit":
			RefereeInputForm updatedRefForm = new RefereeInputForm(owner, (Referee) person);
			Referee updatedReferee = updatedRefForm.getNewReferee();
			person.setName(updatedReferee.getName());
			person.setTeam(updatedReferee.getTeam());
			person.setEmploymentStatus(updatedReferee.getEmploymentStatus());
			person.setPayPerYear(updatedReferee.getPayPerYear());
			
			
			// need to update
			revalidate();
			repaint();
			owner.revalidate();
			owner.repaint();
		}
	}

	
}
