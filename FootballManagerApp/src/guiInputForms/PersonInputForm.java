package guiInputForms;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;

import enums.EmploymentStatus;
import leagueComponents.Person;

/**
 * An abstract input form for creating a new Person.
 * The input form appears as a JDialog.
 * 
 * @author Jack
 *
 */
public abstract class PersonInputForm extends JDialog implements ActionListener {

	/*
	 * here, variables have been declared as protected instead of
	 * private as these input components are to be used by
	 * child classes as if they are their own
	 */
	protected JPanel form, mainAttributes, secondaryAttributes; // the panels that the form will be added to, main attributes will appear above secondary attributes
	protected JTextField nameInput; // input for a persons name
	protected JComboBox<EmploymentStatus> employmentStatusInput; // to select the employment status of the person
	/*
	 * The attribute below was researched at:
	 * 
	 * Joy, H., 2011. Create a numeric text box in java Swing with increment and decrement buttons. [Online] 
	 * Available at: https://stackoverflow.com/questions/6435668/create-a-numeric-text-box-in-java-swing-with-increment-and-decrement-buttons
	 * [Accessed 28 November 2021].
	 * 
	 * I did not copy the code verbatim, it was used to find which model
	 * to use with the JSpinner to input numbers
	 */
	protected JSpinner payPerYearInput; // input for a persons pay
	
	private JPanel buttons; // panel to hold the submit and cancel buttons
	
	/**
	 * Creates a PersonInputForm by setting up necessary
	 * components for child classes that call this constructor.
	 * 
	 * @param owner the JFrame that displays this dialog
	 * @param title the title of this dialog frame
	 */
	public PersonInputForm(JFrame owner, String title) {		
		/* calls the JDialog constructor to make a modal JDialog,
		 * meaning the user can't interact with the main frame
		 * whilst this dialog is open, preventing inaccurate data
		 */
		super(owner, title, true);
		
		setUpButtons(); // set up the buttons so they can be added to the frame
		
		getContentPane().add(buttons, BorderLayout.SOUTH); // add the buttons to the bottom of the frame
		
		setResizable(false); // prevent person input forms being resized

	}
	
	/**
	 * Sets up all components of the input dialog
	 */
	protected void setUpComponents() {
		form = new JPanel(); // initialise the form JPanel
		new BoxLayout(form, BoxLayout.Y_AXIS); // create a box layout for the form
		mainAttributes = new JPanel(new GridBagLayout()); // initialise the mainAttributes panel with a GridBagLayout
		secondaryAttributes = new JPanel(new GridBagLayout()); // initialise the secondaryAttributes panel with a GridBagLayout
		GridBagConstraints constraints = new GridBagConstraints(); // will be used to position elements in the GridBagLayout panels
		
		// set up and add input labels
		
		/*
		 * The reference below was used for the following code:
		 * 
		 * Cave of Programming, 2012. Advanced Java: Swing (GUI) Programming Part 4 -- GridBagLayout. [Online] 
		 * Available at: https://www.youtube.com/watch?v=YKaea4ezQQE&t=385s
		 * [Accessed 28 November 2021].
		 * 
		 * The code was used to find how to position grid items at certain
		 * positions within their cell, this was adapted to suit the needs
		 * of this particular application and were not direct copies
		 */
		constraints.anchor = GridBagConstraints.LINE_END; // position to the right of the cell
		mainAttributes.add(new JLabel("Name:"), constraints); // add a label for the name input, the first attribute in the mainAttributes
		
		secondaryAttributes.add(new JLabel("Employment Status:"), constraints); // add a label for the employment status input, the first attribute in the secondaryAttributes
		
		constraints.gridy = 1; // next attribute
		secondaryAttributes.add(new JLabel("Pay per year (GBP):"), constraints); // label for the pay input
		
		// set up input fields
		
		nameInput = new JTextField(15); // will take the persons name
		employmentStatusInput = new JComboBox<>(EmploymentStatus.values()); // get all possible employment statuses and have them as options for the JComboBox
		payPerYearInput = new JSpinner(new SpinnerNumberModel(10_000, 0, null, 1000)); // initialise the pay spinner to always be 0+ and initial pay is 10,000
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
		Dimension spinnerSize = payPerYearInput.getPreferredSize(); // get the current dimensions of the JSpinner
		spinnerSize.width = 185; // change the width of the retrieved dimension
		payPerYearInput.setPreferredSize(spinnerSize); // change the JSpinner size to the altered dimension
		
		// add input fields
		
		constraints.anchor = GridBagConstraints.LINE_START; // pin components to the left of the cells
		constraints.gridx = 1; // move to the right column for inputs
		constraints.gridy = 0; // start from the first row
		mainAttributes.add(nameInput, constraints); // add the name input as the first attribute of the mainAttributes
		
		
		secondaryAttributes.add(employmentStatusInput, constraints); // add the employment status input as the first of the secondaryAttributes
		
		constraints.gridy = 1; // next attribute
		secondaryAttributes.add(payPerYearInput, constraints); // add the pay input as the second secondaryAttribute
		
		form.add(mainAttributes); // add the mainAttributes panel to the overall form panel
		form.add(secondaryAttributes); // add the secondaryAttributes panel to the overall form panel
		
		form.setBorder(new EmptyBorder(10, 10, 10, 10)); // create padding between the edge of the form and the frame
	}
	
	/**
	 * Sets up the Submit and Cancel buttons
	 */
	private void setUpButtons() {
		
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
	 * Allows an existing Persons attributes to be pre-selected
	 * in an input form. Useful when the input form will be used
	 * to edit the attributes of an existing Person.
	 * 
	 * @param person the person who's attributes to pre-fill in the form
	 */
	public void setExistingValues(Person person) {
		nameInput.setText(person.getName()); // set the value of the name input to this persons name
		employmentStatusInput.setSelectedItem(person.getEmploymentStatus()); // set the selected employment status to this persons employment status
		payPerYearInput.setValue(person.getPayPerYear()); // set the pay spinner to this persons amount of pay// add the submit button to the buttons panel
	}
}
