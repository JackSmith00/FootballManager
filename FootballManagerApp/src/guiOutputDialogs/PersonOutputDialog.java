package guiOutputDialogs;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import gui.FootballManagerGUI;
import gui.LeftPaddedLabel;
import gui.RightAlignedLabel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import leagueComponents.Person;
import leagueComponents.Team;

/**
 * Abstract dialog for displaying information
 * on any Person.
 * 
 * The output appears as a JDialog.
 * 
 * @author Jack
 *
 */
public abstract class PersonOutputDialog extends JDialog implements ActionListener {
	
	protected JPanel frame, mainAttributes, secondaryAttributes; // panels used to hold components
	private int padding = 10; // base padding for labels
	// labels for each attribute of a person,  with no text as default
	private LeftPaddedLabel name = new LeftPaddedLabel("", padding);
	private LeftPaddedLabel employmentStatus = new LeftPaddedLabel("", padding);
	private LeftPaddedLabel payPerYear = new LeftPaddedLabel("", padding);
	
	private Person person; // the person represented in the output
	private JFrame owner; // the frame that controls the dialog
	
	private JPanel buttons; // holds the buttons

	/**
	 * Creates an output dialog containing information
	 * on the given Person
	 * 
	 * @param owner the parent frame of the Dialog
	 * @param person the Person whose information to display
	 * @param width the width of the frame
	 * @param height the height of the frame
	 */
	public PersonOutputDialog(JFrame owner, Person person, int width, int height) {
		super(owner); // create a JDialog with the given owner
		this.person = person;
		this.owner = owner;
		
		setUpButtons(); // set up the buttons
		getContentPane().add(buttons, BorderLayout.SOUTH); // add the buttons to the bottom of the frame
		setBounds(0, 0, width, height); // set the size of the frame
		setLocationRelativeTo(null); // centre the frame on the screen
		setResizable(false); // prevent the frame from being resized
	}
	
	/**
	 * Sets up all components for the output dialog
	 */
	protected void setUpComponents() {
		frame = new JPanel(); // initialise
		new BoxLayout(frame, BoxLayout.Y_AXIS); // create a box layout for the frame
		
		GridLayout layout = new GridLayout(0, 2); // create a GridLayout for the main/secondaryAttributes
		layout.setVgap(5); // add vertical gap to the grid rows
		
		setAllLabelText(person); // set the text for the output labels
		
		mainAttributes = new JPanel(layout); // initialise with the GridLayout
		mainAttributes.add(new RightAlignedLabel("Name:")); // add a label for the name output
		mainAttributes.add(name); // add the name output
		
		frame.add(mainAttributes); // add the main attributes to the frame content
		

		secondaryAttributes = new JPanel(layout); 
		secondaryAttributes.add(new RightAlignedLabel("Employment Status:"));
		secondaryAttributes.add(employmentStatus);
		secondaryAttributes.add(new RightAlignedLabel("Pay per year:"));
		secondaryAttributes.add(payPerYear);
		
		frame.add(secondaryAttributes);
	}

	/**
	 * Sets up the Edit, Transfer Team and Delete buttons for
	 * the output dialog
	 */
	private void setUpButtons() {
		buttons = new JPanel();
		
		JButton editButton = new JButton("Edit");
		editButton.addActionListener(this);
		JButton transferButton = new JButton("Transfer Team");
		transferButton.addActionListener(this);
		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(this);
		

		buttons.add(editButton);
		buttons.add(transferButton);
		buttons.add(deleteButton);
	}
	
	/**
	 * Changes the text of all output labels
	 * to match the attributes of the given
	 * Person
	 * 
	 * @param person the person whose attributes to display
	 */
	public void setAllLabelText(Person person) {
		setTitle(person.getName());
		name.setText(person.getName());
		employmentStatus.setText(person.getEmploymentStatus().toString());
		
		/*
		 * The code below was researched at the following resource:
		 * 
		 * daiscog, 2015. Java Currency Number format. [Online] 
		 * Available at: https://stackoverflow.com/questions/2379221/java-currency-number-format
		 * [Accessed 7 December 2021].
		 * 
		 * The resource was used to find how to format an int into a currency
		 * format. The code has been adapted to suit this specific application
		 * and is not a direct copy
		 */
		NumberFormat moneyFormatter = NumberFormat.getCurrencyInstance();
		moneyFormatter.setMaximumFractionDigits(0);
		
		payPerYear.setText(moneyFormatter.format(person.getPayPerYear()));
	}
	
	/**
	 * Sets up a JOption pane to get input
	 * from the user as to which team to
	 * transfer this Person to
	 * 
	 * @param personType the specific type of the Person to display in the output question
	 * @return the team the Person should be moved to
	 */
	public Team teamToTransferTo(String personType) {
		FootballManagerGUI appGui = (FootballManagerGUI) getOwner();
		Team [] teams = new Team[appGui.getCurrentLeague().getTeams().size()];
		appGui.getCurrentLeague().getTeams().toArray(teams);
		/*
		 * The code below is influenced by the following:
		 * 
		 * Knowledge to Share, 2019. How to add Jcombobox in JOptionPane Confirm Dialog Box java swing. [Online] 
		 * Available at: https://www.youtube.com/watch?v=iXFplYFuqFE
		 * [Accessed 7 December 2021].
		 * 
		 * The code was not copied verbatim, it was used to find out how a JComboBox can
		 * be displayed in a JOptionPane, saving time creating a custom JDialog for this
		 */
		JComboBox<Team> teamSelector = new JComboBox<Team>(teams);
		
		JPanel messagePanel = new JPanel();
		messagePanel.add(new JLabel("Which team should the referee be moved to?"));
		messagePanel.add(teamSelector);
		
		int input = JOptionPane.showConfirmDialog(getOwner(), messagePanel, "Transfer Team", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);
		if(input == JOptionPane.OK_OPTION) {
			return (Team) teamSelector.getSelectedItem();
		} else {
			return null;
		}
	}
	
	/**
	 * Creates a confirmation option pane to give the
	 * user a chance to cancel the deletion of the Person
	 * @param personType the type of Person to be deleted which will be displayed in the output question 
	 * @return a boolean value of whether the Person should be deleted or not
	 */
	public boolean shouldDeleteThisPerson(String personType) {
		int input = JOptionPane.showConfirmDialog(getOwner(), "Are you sure you want to delete this " + personType + "?\nThis cannot be undone.", "Delete " + personType, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		if(input == JOptionPane.YES_OPTION) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * @return the person represented by the output dialog
	 */
	public Person getPerson() {
		return person;
	}
	
	/**
	 * @return the frame that owns this dialog
	 */
	public JFrame getOwner() {
		return owner;
	}
	
	/**
	 * @return the padding on the LeftPaddedLabels in this dialog
	 */
	public int getPadding() {
		return padding;
	}
	
	/**
	 * Allows the padding of the LeftPaddedLabels to be altered
	 * 
	 * @param padding the new value of the padding for the LeftPaddedLabels
	 */
	public void setPadding(int padding) {
		this.padding = padding;
	}
}
