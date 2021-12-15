package guiInputForms;

import java.awt.GridBagConstraints;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import leagueComponents.Formation;
import leagueComponents.FormationManager;
import leagueComponents.Person;
import leagueComponents.PersonWithFormationPreference;

/**
 * An input form for creating a new CoachingStaffMember.
 * The input form appears as a JDialog.
 * 
 * @author Jack
 *
 */
public abstract class PersonWithFormationPreferenceInputForm extends PersonInputForm {
	
	private JPanel formationInputPanel; // panel for inputting formations
	
	protected JComboBox<Formation> formationInput; // used to select an existing formation
	
	protected JButton newFormationButton; // switches the display from the combobox selecting to spinners to make a new formation
	protected boolean creatingNewFormation = false; // default behaviour is not creating a new formation, but selecting an existing one
	protected JSpinner defenderInput; // for inputting the number of defenders in a formation
	protected JSpinner midfielderInput; // for inputting the number of midfielders in a formation
	protected JSpinner strikerInput;// for inputting the number of strikers in a formation

	/**
	 * Creates a PersonWithFormationPreferenceInputForm by setting up necessary
	 * components for child classes that call this constructor.
	 * 
	 * @param owner the JFrame that displays this dialog
	 * @param title the title of this dialog frame
	 */
	public PersonWithFormationPreferenceInputForm(JFrame owner, String title) {
		super(owner, title); // call PersonInputForm constructor
	}
	
	@Override
	public void setUpComponents() {
		
		super.setUpComponents(); // set up inherited components
		
		GridBagConstraints constraints = new GridBagConstraints(); // to position components in the GridBagLayout
		
		constraints.anchor = GridBagConstraints.LINE_END; // pin components to the right of cell
		constraints.gridy = 2; // the third row in mainAttributes
		mainAttributes.add(new JLabel("Prefered Formation:"), constraints); // add label for the formation preference
		
		Formation[] storedFormations = new Formation[FormationManager.getFormations().size()]; // initialise array big enough to hold all existing formations
		FormationManager.getFormations().toArray(storedFormations); // add saved formations to array
		formationInput = new JComboBox<Formation>(storedFormations); // create combobox of all existing formations
		
		newFormationButton = new JButton("New"); // create button to open new formation input
		newFormationButton.addActionListener(this); // make this class listen for the button being pressed

		formationInputPanel = new JPanel(); // initialise
		
		formationInputPanel.add(formationInput); // add formationInput combo box to the panel
		formationInputPanel.add(newFormationButton); // add the new formation button to the panel
		
		constraints.anchor = GridBagConstraints.LINE_START; // pin components to the left of cell
		constraints.gridx = 1; // move to the right column
		mainAttributes.add(formationInputPanel, constraints); // add formation input to the mainAttributes panel
	}
	
	@Override
	public void setExistingValues(Person person) {
		super.setExistingValues(person); // set existing values for inherited input fields
		// get the index of the persons formation from the formation manager
		int index = Arrays.binarySearch(FormationManager.getFormations().toArray(), ((PersonWithFormationPreference) person).getPreferredFormation());
		// had to use index rather than `setSelectedItem()` as on first use the current formation is not recognised as a component of `formationInput`
		formationInput.setSelectedIndex(index); // set selected formation to the persons existing preferred formation
	}
	
	/**
	 * Sets up the input display for a new formation.
	 * To be called when the new formation button is pressed.
	 */
	public void newFormationInput() {
		creatingNewFormation = true; // toggle this as now creating a new formation
		
		mainAttributes.remove(formationInputPanel); // remove existing formation panel from the mainAttributes
		JPanel newFormationPanel = new JPanel(); // make a new panel to hold formation creation inputs

		/*
		 * Initialise JSpinners for formation creation, with a max
		 * input no bigger than 11 as there can only be 11 players
		 * on the pitch.
		 */
		defenderInput = new JSpinner(new SpinnerNumberModel(4, 0, 11, 1));
		midfielderInput = new JSpinner(new SpinnerNumberModel(3, 0, 11, 1));
		strikerInput = new JSpinner(new SpinnerNumberModel(3, 0, 11, 1));
		
		// add inputs to the new formation panel
		newFormationPanel.add(defenderInput);
		newFormationPanel.add(new JLabel("-")); // add dashes between inputs to match the format of a formation (i.e. 4-3-3)
		newFormationPanel.add(midfielderInput);
		newFormationPanel.add(new JLabel("-"));
		newFormationPanel.add(strikerInput);
		
		GridBagConstraints constraints = new GridBagConstraints(); // allows the component to be positioned in the GridBagLayout
		constraints.anchor = GridBagConstraints.LINE_START; // pin to the left of the cell
		constraints.gridx = 1; // right column
		constraints.gridy = 2; // 3rd row
		
		mainAttributes.add(newFormationPanel, constraints); // add to the mainAttributes in place of the formationInputPanel
		
		revalidate(); // revalidate the display to show the new input fields
	}

}
