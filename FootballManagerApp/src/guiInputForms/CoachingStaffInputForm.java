package guiInputForms;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import enums.EmploymentStatus;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;

import leagueComponents.CoachingStaffMember;
import leagueComponents.Formation;
import leagueComponents.FormationManager;
import leagueComponents.Person;

/**
 * An input form for creating a new CoachingStaffMember.
 * The input form appears as a JDialog.
 * 
 * @author Jack
 *
 */
public class CoachingStaffInputForm extends PersonWithFormationPreferenceInputForm {

	protected JTextField roleInput; // input field for coach role
	
	private CoachingStaffMember newCoachingStaffMember; // holds the newly created CoachingStaffMember
	
	/**
	 * Creates an input form to create a new
	 * CoachingStaffMember, that is linked to
	 * the given frame.
	 * 
	 * @param owner the parent frame of the Dialog
	 */
	public CoachingStaffInputForm(JFrame owner) {
		super(owner, "Add Coaching Staff"); // create a PersonWithFormationPreferenceInputForm with the given owner and title
		
		setUpComponents(); // sets up all components for the input form
		
		getContentPane().add(form, BorderLayout.CENTER); // add the form components to the centre of the content pane
		
		setBounds(0, 0, 350, 250); // set the size of the dialog window
		setLocationRelativeTo(null); // position the dialog window in the centre of the screen
		/*
		 * The code below was influenced by:
		 * 
		 * victor26567, 2018. Displaying JDialog Java. [Online] 
		 * Available at: https://stackoverflow.com/questions/49577917/displaying-jdialog-java/49579959
		 * [Accessed 28 November 2021].
		 * 
		 * The code was used as a reference to see why the dialog was not displaying.
		 * From the above source I found I was missing the code to make the 
		 * dialog visible.
		 */
		setVisible(true); // make the dialog visible
	}
	
	/**
	 * Creates an input form to update an existing
	 * CoachingStaffMember, pre-populating input fields
	 * with the CoachingStaffMember's existing attributes.
	 * 
	 * The form dialog is owned by a parent frame.
	 * 
	 * @param owner the parent frame of the dialog
	 * @param coachingStaffMember the existing CoachingStaffMember to pre-populate the input form with
	 */
	public CoachingStaffInputForm(JFrame owner, CoachingStaffMember coachingStaffMember) {
		/*
		 * This constructor is similar to the one above, however
		 * it cannot simply call the existing constructor as
		 * it needs to set the input values before it is made
		 * visible to the user
		 */
		super(owner, "Add Coaching Staff"); // create a PersonWithFormationPreferenceInputForm with the given owner and title
		
		setUpComponents(); // sets up all components for the input form
		setExistingValues(coachingStaffMember); // changes the values of the input components to match the existing CoachingStaffMember
		
		getContentPane().add(form, BorderLayout.CENTER); // add the form components to the centre of the content pane
		setBounds(0, 0, 350, 250); // set the size of the dialog window
		setLocationRelativeTo(null); // position the dialog window in the centre of the screen
		setVisible(true); // make the dialog visible
	}
	
	@Override
	protected void setUpComponents() {
				
		super.setUpComponents(); // set up all inherited components
		
		// now set up components specific to a CoachingStaffInputForm
		
		GridBagConstraints constraints = new GridBagConstraints(); // used to position the components in the GridBagLayouts
		
		constraints.anchor = GridBagConstraints.LINE_END; // pin to the right of the cell
		constraints.gridy = 1; // on the second row
		mainAttributes.add(new JLabel("Role:"), constraints); // add label to the mainAttributes
		
		roleInput = new JTextField(15); // initialise the input field for the role
		
		constraints.anchor = GridBagConstraints.LINE_START; // pin to the left of the cell
		constraints.gridx = 1; // move to the next column
		mainAttributes.add(roleInput, constraints); // add the input to the mainAttributes
	}

	@Override
	public void setExistingValues(Person person) {
		super.setExistingValues(person); // set the values for all inherited input components first
		roleInput.setText(((CoachingStaffMember) person).getRole()); // set value for the role input to be the same as the given CoachingStaffMember
	}
	
	/**
	 * @return the new CoachingStaffMember created by the input form
	 */
	public CoachingStaffMember getNewCoachingStaffMember() {
		return newCoachingStaffMember;
	}
	
	/**
	 * Invoked when a button is pressed in the dialog
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) { // gets the title of the button pressed
		case "New": // when the new formation button is pressed
			newFormationInput(); // set up the display to take in a new formation
			break; // do nothing else
		case "Submit": // when the submit button is pressed
			Formation newFormation; // will hold the new formation
			if(creatingNewFormation) { // if creating a new formation
				// initialise a new formation with the values from the new formation input 
				newFormation = new Formation((int) defenderInput.getValue(),(int) midfielderInput.getValue(), (int) strikerInput.getValue());
				FormationManager.add(newFormation); // add the new formation to the FormationManager
			} else { // selecting an existing formation
				// the new formation is the value of the formation input combobox
				newFormation = (Formation) formationInput.getSelectedItem();
			}
			// initialise the newCoachingStaffMember with all values from the input form
			newCoachingStaffMember = new CoachingStaffMember(nameInput.getText(), roleInput.getText(), newFormation, (EmploymentStatus) employmentStatusInput.getSelectedItem(), (int) payPerYearInput.getValue());
			// no break as this case also executes the default clause
		default: // used by the submit and cancel button
			dispose(); // close the input dialog
		}
	}

}
