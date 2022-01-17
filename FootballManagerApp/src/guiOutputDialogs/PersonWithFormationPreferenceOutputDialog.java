package guiOutputDialogs;

import javax.swing.JFrame;

import gui.LeftPaddedLabel;
import gui.RightAlignedLabel;
import leagueComponents.PersonWithFormationPreference;

/**
 * Abstract dialog for displaying information
 * on a PersonWithAFormationPreference.
 * 
 * The output appears as a JDialog.
 * 
 * @author Jack
 *
 */
public abstract class PersonWithFormationPreferenceOutputDialog extends PersonOutputDialog {
	
	private LeftPaddedLabel preferredFormation = new LeftPaddedLabel("", getPadding()); // label for the formation output

	/**
	 * Creates an output dialog containing information
	 * on the given PersonWithFormationPreference
	 * 
	 * @param owner the parent frame of the Dialog
	 * @param person the PersonWithFormationPreference whose information to display
	 * @param width the width of the frame
	 * @param height the height of the frame
	 */
	public PersonWithFormationPreferenceOutputDialog(JFrame owner, PersonWithFormationPreference person, int width, int height) {
		super(owner, person, width, height);
	}
	
	@Override
	protected void setUpComponents() {
		super.setUpComponents(); // set up inherited components
		
		setAllLabelText((PersonWithFormationPreference) getPerson());
		
		mainAttributes.add(new RightAlignedLabel("Preferred Formation:"));
		mainAttributes.add(preferredFormation);
		frame.revalidate();
	}
	
	/**
	 * Sets text of all outputs related to a PersonWithFormationPreference
	 * 
	 * @param person the PersonWithFormationPreference to show the attributes of
	 */
	public void setAllLabelText(PersonWithFormationPreference person) {
		super.setAllLabelText(person);
		preferredFormation.setText(person.getPreferredFormation().toString());
	}

}
