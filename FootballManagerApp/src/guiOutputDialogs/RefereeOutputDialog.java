package guiOutputDialogs;

import java.awt.event.ActionEvent;

import gui.FootballManagerGUI;
import guiInputForms.RefereeInputForm;
import leagueComponents.Referee;
import leagueComponents.Team;

public class RefereeOutputDialog extends PersonOutputDialog {

	public RefereeOutputDialog(FootballManagerGUI owner, Referee person) {
		super(owner, person, 310, 140);
		setUpComponents();
		getContentPane().add(frame);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		FootballManagerGUI appGui = (FootballManagerGUI) getOwner();
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
				appGui.setUpTeamPage(appGui.getCurrentTeam());

				revalidate();
				appGui.revalidate();
			}
			break;
		case "Transfer Team":
			Team newTeam = teamToTransferTo("Referee");
			if(newTeam != null) {
				appGui.getCurrentTeam().removeReferee((Referee)getPerson());
				newTeam.addReferee((Referee)getPerson());
				appGui.setUpTeamPage(appGui.getCurrentTeam());
				appGui.revalidate();
				dispose();
			}
			break;
		case "Delete":
			boolean shouldDelete = shouldDeleteThisPerson("Referee");
			if(shouldDelete) {
				appGui.getCurrentTeam().removeReferee((Referee)getPerson());
				appGui.setUpTeamPage(appGui.getCurrentTeam());
				appGui.revalidate();
				dispose();
			}
			break;
		}
	}

	
}
