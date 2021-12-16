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
	
	public void setAllLabelText(Person person) {
		setTitle(person.getName());
		name.setText(person.getName());
		employmentStatus.setText(person.getEmploymentStatus().toString());
		
		NumberFormat moneyFormatter = NumberFormat.getCurrencyInstance(); // https://stackoverflow.com/questions/2379221/java-currency-number-format
		moneyFormatter.setMaximumFractionDigits(0);
		
		payPerYear.setText(moneyFormatter.format(person.getPayPerYear()));
	}
	
	public Team teamToTransferTo(String personType) {
		FootballManagerGUI appGui = (FootballManagerGUI) getOwner();
		Team [] teams = new Team[appGui.getCurrentLeague().getTeams().size()];
		appGui.getCurrentLeague().getTeams().toArray(teams);
		JComboBox<Team> teamSelector = new JComboBox<Team>(teams); // https://www.youtube.com/watch?v=iXFplYFuqFE
		
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
	
	public boolean shouldDeleteThisPerson(String personType) {
		int input = JOptionPane.showConfirmDialog(getOwner(), "Are you sure you want to delete this " + personType + "?\nThis cannot be undone.", "Delete " + personType, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		if(input == JOptionPane.YES_OPTION) {
			return true;
		} else {
			return false;
		}
	}
	
	public Person getPerson() {
		return person;
	}
	
	public JFrame getOwner() {
		return owner;
	}
	
	public int getPadding() {
		return padding;
	}
	
	public void setPadding(int padding) {
		this.padding = padding;
	}
}
