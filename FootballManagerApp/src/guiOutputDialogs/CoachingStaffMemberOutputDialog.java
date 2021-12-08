package guiOutputDialogs;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;

import leagueComponents.CoachingStaffMember;

public class CoachingStaffMemberOutputDialog extends PersonWithFormationPreferenceOutputDialog {
	
	private CoachingStaffMember person;

	public CoachingStaffMemberOutputDialog(JFrame owner, CoachingStaffMember person) {
		super(owner, person, 300, 190);
		this.person = person;
		getContentPane().add(frame);
		setVisible(true);
	}
	
	@Override
	protected void setUpComponents() {
		super.setUpComponents();
		
		mainAttributes.add(new RightAlignedLabel("Role:"), 2);
		mainAttributes.add(new LeftPaddedLabel(((CoachingStaffMember) getPerson()).getRole().toString(), getPadding()), 3);
		frame.revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
