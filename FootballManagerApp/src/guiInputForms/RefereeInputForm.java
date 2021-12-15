package guiInputForms;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;

import enums.EmploymentStatus;
import leagueComponents.Referee;

/**
 * An input form for creating a new Referee.
 * The input form appears as a JDialog.
 * 
 * @author Jack
 *
 */
public class RefereeInputForm extends PersonInputForm {
	
	private Referee newReferee; // holds the newly created referee

	/**
	 * Creates an input form to create a new
	 * Referee, that is linked to the given frame.
	 * 
	 * @param owner the parent frame of the Dialog
	 */
	public RefereeInputForm(JFrame owner) {
		super(owner, "Add Referee"); // create a PersonInputForm with the given owner and title
		setUpComponents(); // sets up all components for the input form
		getContentPane().add(form, BorderLayout.CENTER); // add the form components to the centre of the content pane
		setBounds(0, 0, 350, 200); // set the size of the dialog window
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
	 * Referee, pre-populating input fields
	 * with the Referee's existing attributes.
	 * 
	 * The form dialog is owned by a parent frame.
	 * 
	 * @param owner the parent frame of the dialog
	 * @param referee the existing Referee to pre-populate the input form with
	 */
	public RefereeInputForm(JFrame owner, Referee referee) {
		/*
		 * This constructor is similar to the one above, however
		 * it cannot simply call the existing constructor as
		 * it needs to set the input values before it is made
		 * visible to the user
		 */
		super(owner, "Add Referee"); // create a PersonInputForm with the given owner and title
		setUpComponents(); // sets up all components for the input form
		setExistingValues(referee); // changes the values of the input components to match the existing Referee
		getContentPane().add(form, BorderLayout.CENTER); // add the form components to the centre of the content pane
		setBounds(0, 0, 350, 200); // set the size of the dialog window
		setLocationRelativeTo(null); // position the dialog window in the centre of the screen
		setVisible(true); // make the dialog visible
	}
	
	/**
	 * @return the new Referee created by the input form
	 */
	public Referee getNewReferee() {
		return newReferee;
	}

	/**
	 * Invoked when a button is pressed in the dialog
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "Submit") { // when the submit button is pressed
			// initialise the newReferee with all values from the input form
			newReferee = new Referee(nameInput.getText(), (EmploymentStatus) employmentStatusInput.getSelectedItem(), (int) payPerYearInput.getValue());
		}
		// for both the submit and cancel buttons
		dispose(); // close the input dialog
	}

}
