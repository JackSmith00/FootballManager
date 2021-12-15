package guiInputForms;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import interfaces.DialogWithButtons;
import leagueComponents.Stadium;
import leagueComponents.Team;

import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * An input form for creating a new Team.
 * The input form appears as a JDialog.
 * 
 * @author Jack
 *
 */
public class TeamInputForm extends JDialog implements DialogWithButtons, ActionListener {
	
	protected JPanel form, buttons; // holds all components and buttons in the form
	// labels for the input fields
	private JLabel teamName;
	private JLabel stadiumName;
	private JLabel stadiumCapacity;
	
	/*
	 * Below attributes are protected in case the
	 * class gets extended, so the child classes
	 * can access their input fields
	 */
	protected JTextField teamNameInput; // input for team name
	protected JTextField stadiumNameInput; // input for stadium name
	protected JSpinner stadiumCapacityInput; // input spinner for stadium capacity
	
	private Team newTeam; // holds the newly created Team

	/**
	 * Creates an input form to create a new
	 * Team, that is linked to the given frame.
	 * 
	 * @param owner the parent frame of the Dialog
	 */
	public TeamInputForm(JFrame owner) {
		super(owner, "Add Team", true); // creates a modal JDialog with the given title and owner
		
		setUpComponents(); // sets up all components for the input form
		setUpButtons(); // sets up all buttons for the input form
		
		getContentPane().add(form, BorderLayout.CENTER); // add the form components to the centre of the content pane
		getContentPane().add(buttons, BorderLayout.SOUTH); // add the buttons to the bottom of the content pane
		
		setResizable(false); // stop the input form being resized and ruining the layout
		pack(); // resize to fit the components
		setLocationRelativeTo(null); // position in the centre of the screen
		setVisible(true); // make dialog visible
	}
	
	@Override
	public void setUpComponents() {
		form = new JPanel(new GridBagLayout()); // initialise panel with GridBagLayout
		GridBagConstraints constraints = new GridBagConstraints(); // to position the components in the GridBagLayout
		
		// initialise components
		
		//input labels
		teamName = new JLabel("Team Name:");
		stadiumName = new JLabel("Stadium Name:");
		stadiumCapacity = new JLabel("Stadium Capacity:");
		
		// input fields
		teamNameInput = new JTextField(15);
		stadiumNameInput = new JTextField(15);
		stadiumCapacityInput = new JSpinner(new SpinnerNumberModel(10_000, 0, null, 1000)); // initialise the capacity spinner to always be 0+ and initial capacity is 10,000
		/*
		 * The code below was researched here:
		 * 
		 * Wood, C., 2004. JSpinner Size. [Online] 
		 * Available at: https://www.coderanch.com/t/339225/java/JSpinner-Size
		 * [Accessed 30 November 2021].
		 * 
		 * The resource above was used to find how to resize the input field
		 * size of the JSpinner as there is no option to initialise it with
		 * a set number of columns like a JTextField, the code was not copied
		 * verbatim, but was used as a guide to achieve this resizing
		 */
		Dimension spinnerSize = stadiumCapacityInput.getPreferredSize();  // get the current dimensions of the JSpinner
		spinnerSize.width = 185; // change the width of the retrieved dimension
		stadiumCapacityInput.setPreferredSize(spinnerSize); // change the JSpinner size to the altered dimension
		
		constraints.anchor = GridBagConstraints.LINE_END; // pin components to the right of the cells
		// add labels to form
		form.add(teamName, constraints);
		
		constraints.gridy = 1; // next row
		form.add(stadiumName, constraints);
		
		constraints.gridy = 2; // next row
		form.add(stadiumCapacity, constraints);
		
		constraints.anchor = GridBagConstraints.LINE_START; // pin to the left of cells
		// add input fields
		constraints.gridx = 1; // right column
		constraints.gridy = 0; // first row
		form.add(teamNameInput, constraints);
		
		constraints.gridy = 1; // next row
		form.add(stadiumNameInput, constraints);
		
		constraints.gridy = 2; // next row
		form.add(stadiumCapacityInput, constraints);
		
		form.setBorder(new EmptyBorder(10, 10, 10, 10)); // create padding between the edge of the form and the frame
	}
	
	/**
	 * Sets up the Submit and Cancel buttons
	 */
	@Override
	public void setUpButtons() {
		buttons = new JPanel(); // initialise the panel to hold the buttons
		JButton submitButton = new JButton("Submit"); // create the submit button
		submitButton.addActionListener(this); // make the button be listened to by this class
		JButton cancelButton = new JButton("Cancel"); // create the cancel button
		cancelButton.addActionListener(this); // make the button be listened to by this class
		
		buttons.add(submitButton); // add the submit button to the buttons panel
		buttons.add(cancelButton); // add the cancel button to the buttons panel
		
		buttons.setBorder(new EmptyBorder(10, 10, 10, 10)); // create padding around the button panel
	}

	/**
	 * Invoked when a button is pressed in the dialog
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "Submit") { // when the submit button is pressed
			// initialise the newTeam with all values from the input form
			newTeam = new Team(teamNameInput.getText(), new Stadium(stadiumNameInput.getText(), (int) stadiumCapacityInput.getValue()));
		}
		// for both the submit and cancel buttons
		dispose(); // close the input dialog
	}
	
	/**
	 * @return the new Team created by the input form
	 */
	public Team getNewTeam() {
		return newTeam;
	}
}
