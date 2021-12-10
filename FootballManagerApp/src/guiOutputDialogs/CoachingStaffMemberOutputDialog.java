package guiOutputDialogs;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;

import gui.FootballManagerGUI;
import gui.LeftPaddedLabel;
import gui.RightAlignedLabel;
import guiInputForms.CoachingStaffInputForm;
import leagueComponents.CoachingStaffMember;
import leagueComponents.Referee;
import leagueComponents.Team;


public class CoachingStaffMemberOutputDialog extends PersonWithFormationPreferenceOutputDialog {
	
	private LeftPaddedLabel role = new LeftPaddedLabel("", getPadding());

	public CoachingStaffMemberOutputDialog(FootballManagerGUI owner, CoachingStaffMember person) {
		super(owner, person, 300, 190);
		setUpComponents();
		getContentPane().add(frame);
		setVisible(true);
	}
	
	@Override
	protected void setUpComponents() {
		super.setUpComponents();
		
		setAllLabelText((CoachingStaffMember)getPerson());
		
		mainAttributes.add(new RightAlignedLabel("Role:"), 2);
		mainAttributes.add(role , 3);
		frame.revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "Edit":
			CoachingStaffMember originalCoach = (CoachingStaffMember) getPerson();
			CoachingStaffInputForm updatedCoachForm = new CoachingStaffInputForm(getOwner(), originalCoach);
			CoachingStaffMember updatedCoach = updatedCoachForm.getNewCoachingStaffMember();
			if(updatedCoach != null) {
				originalCoach.setName(updatedCoach.getName());
				originalCoach.setTeam(updatedCoach.getTeam());
				originalCoach.setEmploymentStatus(updatedCoach.getEmploymentStatus());
				originalCoach.setPayPerYear(updatedCoach.getPayPerYear());
				originalCoach.setPreferredFormation(updatedCoach.getPreferredFormation());
				originalCoach.setRole(updatedCoach.getRole());

				// need to update

				setAllLabelText(updatedCoach);
				((FootballManagerGUI) getOwner()).setUpTeamPage(((FootballManagerGUI) getOwner()).getCurrentTeam());

				revalidate();
				getOwner().getContentPane().revalidate();
			}
			break;
		case "Transfer Team":
			Team newTeam = teamToTransferTo("Coaching Staff");
			if(newTeam != null) {
				FootballManagerGUI appGui = (FootballManagerGUI) getOwner();
				appGui.getCurrentTeam().removeCoachingStaffMember((CoachingStaffMember)getPerson());
				newTeam.addCoachingStaffMember((CoachingStaffMember)getPerson());
				appGui.setUpTeamPage(appGui.getCurrentTeam());
				appGui.revalidate();
				dispose();
			}
			break;
		}
	}
	
	public void setAllLabelText(CoachingStaffMember person) {
		// TODO Auto-generated method stub
		super.setAllLabelText(person);
		role.setText(person.getRole());
	}

}
