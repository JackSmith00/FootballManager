package guiOutputDialogs;

import javax.swing.JFrame;

import leagueComponents.PersonWithFormationPreference;

public abstract class PersonWithFormationPreferenceOutputDialog extends PersonOutputDialog {
	
	private LeftPaddedLabel preferredFormation = new LeftPaddedLabel("", getPadding());

	public PersonWithFormationPreferenceOutputDialog(JFrame owner, PersonWithFormationPreference person, int width, int height) {
		super(owner, person, width, height);
	}
	
	@Override
	protected void setUpComponents() {
		super.setUpComponents();
		
		setAllLabelText((PersonWithFormationPreference) getPerson());
		
		mainAttributes.add(new RightAlignedLabel("Preferred Formation:"));
		mainAttributes.add(preferredFormation); // needs removing when formations are added
		frame.revalidate();
	}
	
	public void setAllLabelText(PersonWithFormationPreference person) {
		super.setAllLabelText(person);
		preferredFormation.setText(person.getPreferredFormation().toString());
	}

}
