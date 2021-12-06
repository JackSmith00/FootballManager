package guiOutputDialogs;

import javax.swing.JFrame;

import leagueComponents.CoachingStaffMember;

public class CoachingStaffMemberOutputDialog extends PersonWithFormationPreferenceOutputDialog {

	public CoachingStaffMemberOutputDialog(JFrame owner, CoachingStaffMember person) {
		super(owner, person, 300, 180);
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

}
