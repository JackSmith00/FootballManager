package guiOutputDialogs;

import java.awt.event.ActionEvent;

import gui.FootballManagerGUI;
import guiInputForms.RefereeInputForm;
import leagueComponents.Referee;

public class RefereeOutputDialog extends PersonOutputDialog {

	public RefereeOutputDialog(FootballManagerGUI owner, Referee person) {
		super(owner, person, 300, 140);
		setUpComponents();
		getContentPane().add(frame);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "Edit":
			Referee originalReferee = (Referee) getPerson();
			RefereeInputForm updatedRefForm = new RefereeInputForm(getOwner(), originalReferee);
			Referee updatedReferee = updatedRefForm.getNewReferee();
			if(updatedReferee != null) {
				originalReferee.setName(updatedReferee.getName());
				originalReferee.setTeam(updatedReferee.getTeam());
				originalReferee.setEmploymentStatus(updatedReferee.getEmploymentStatus());
				originalReferee.setPayPerYear(updatedReferee.getPayPerYear());

				// need to update

				setAllLabelText(updatedReferee);
				((FootballManagerGUI) getOwner()).setUpTeamPage(((FootballManagerGUI) getOwner()).getCurrentTeam());

				revalidate();
				getOwner().getContentPane().revalidate();
			}
		}
	}

	
}
