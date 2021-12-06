package guiOutputDialogs;

import javax.swing.JFrame;

import leagueComponents.PersonWithFormationPreference;

public abstract class PersonWithFormationPreferenceOutputDialog extends PersonOutputDialog {

	public PersonWithFormationPreferenceOutputDialog(JFrame owner, PersonWithFormationPreference person, int width, int height) {
		super(owner, person, width, height);
		setUpComponents();
		getContentPane().add(frame);
		setVisible(true);
	}
	
	@Override
	protected void setUpComponents() {
		super.setUpComponents();
		mainAttributes.add(new RightAlignedLabel("Preferred Formation:"));
		//mainAttributes.add(new LeftPaddedLabel(((PersonWithFormationPreference) getPerson()).getPreferredFormation().toString(), getPadding()));
		mainAttributes.add(new LeftPaddedLabel("Test formation", getPadding())); // needs removing when formations are added
		frame.revalidate();
	}

}
