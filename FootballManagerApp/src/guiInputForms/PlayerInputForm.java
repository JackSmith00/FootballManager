package guiInputForms;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import enums.EmploymentStatus;
import leagueComponents.Formation;
import leagueComponents.FormationManager;
import leagueComponents.Person;
import leagueComponents.Player;
import leagueComponents.Position;
import leagueComponents.PositionManager;

/**
 * An input form for creating a new Player.
 * The input form appears as a JDialog.
 * 
 * @author Jack
 *
 */
public class PlayerInputForm extends PersonWithFormationPreferenceInputForm {
	
	private JPanel positionInputPanel; // holds position input components
	private JLabel positionLabel; // stored as an attribute so can be altered in the class
	
	protected JComboBox<Position> positionInput; // used to select an existing position
	
	protected JButton newPositionButton; // switches the display from the combobox selecting to JTextFields to make a new position
	protected boolean creatingNewPosition = false; // default behaviour is not creating a new position, but selecting an existing one
	protected JTextField positionTitleInput; // for inputting the position title
	protected JTextField positionAbbreviationInput; // for t=inputting the position abbreviation
	
	private Player newPlayer; // holds the player created by the input form

	/**
	 * Creates an input form to create a new
	 * Player, that is linked to the given frame.
	 * 
	 * @param owner the parent frame of the Dialog
	 */
	public PlayerInputForm(JFrame owner) {
		super(owner, "Add Player"); // create a PersonWithFormationPreferenceInputForm with the given owner and title
		
		setUpComponents(); // sets up all components for the input form
		
		getContentPane().add(form, BorderLayout.CENTER);  // add the form components to the centre of the content pane
		setBounds(0, 0, 350, 270); // set the size of the dialog window
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
	 * Player, pre-populating input fields
	 * with the Player's existing attributes.
	 * 
	 * The form dialog is owned by a parent frame.
	 * 
	 * @param owner the parent frame of the dialog
	 * @param player the existing Player to pre-populate the input form with
	 */
	public PlayerInputForm(JFrame owner, Player player) {
		/*
		 * This constructor is similar to the one above, however
		 * it cannot simply call the existing constructor as
		 * it needs to set the input values before it is made
		 * visible to the user
		 */
		super(owner, "Add Player"); // create a PersonWithFormationPreferenceInputForm with the given owner and title

		setUpComponents(); // sets up all components for the input form
		setExistingValues(player); // changes the values of the input components to match the existing Player
		
		getContentPane().add(form, BorderLayout.CENTER);  // add the form components to the centre of the content pane
		setBounds(0, 0, 350, 270); // set the size of the dialog window
		setLocationRelativeTo(null); // position the dialog window in the centre of the screen
		setVisible(true); // make the dialog visible
		
	}
	
	@Override
	protected void setUpComponents() {
		
		super.setUpComponents(); // set up all inherited components
		
		// now set up components specific to a PlayerInputForm
		
		GridBagConstraints constraints = new GridBagConstraints(); // used to position the components in the GridBagLayouts
		
		positionLabel = new JLabel("Position:"); // initialise label for position input
		
		constraints.anchor = GridBagConstraints.LINE_END; // pin to the right of the cell
		constraints.gridy = 1; // second row
		mainAttributes.add(positionLabel, constraints); // add label to mainAttributes
		
		Position[] storedPositions = new Position[PositionManager.getPositions().size()]; // initialise array big enough to hold all existing positions
		PositionManager.getPositions().toArray(storedPositions); // add saved positions to array
		positionInput = new JComboBox<Position>(storedPositions); // create combobox of all existing positions
		
		newPositionButton = new JButton("New"); // create button to open new position input
		newPositionButton.addActionListener(this); // make this class listen for the button being pressed
		
		positionInputPanel = new JPanel(); // initialise
		
		positionInputPanel.add(positionInput); // add position combobox to the panel
		positionInputPanel.add(newPositionButton); // add the new position button to the panel
		
		constraints.anchor = GridBagConstraints.LINE_START; // pin to the left of cell
		constraints.gridx = 1; // right column
		mainAttributes.add(positionInputPanel, constraints); // add position input to mainAttributes
		
	}
	
	@Override
	public void setExistingValues(Person person) {
		super.setExistingValues(person); // set existing values for inherited input fields
		// get the index of the persons position from the position manager
		int index = Arrays.binarySearch(PositionManager.getPositions().toArray(), ((Player) person).getPosition());
		// had to use index rather than `setSelectedItem()` as on first use the current position is not recognised as a component of `positionInput`
		positionInput.setSelectedIndex(index); // set selected position to the persons existing position

	}
	
	/**
	 * Sets up the input display for a new position.
	 * To be called when the new position button is pressed.
	 */
	public void newPositionInput() {
		creatingNewPosition = true; // toggle this as now creating a new position
		
		mainAttributes.remove(positionInputPanel); // remove existing position panel from the mainAttributes
		JPanel newPositionPanel = new JPanel(); // make a new panel to hold position creation inputs

		/*
		 * Initialise JTextField inputs for position
		 * title and abbreviation
		 */
		positionTitleInput = new JTextField(10);
		positionAbbreviationInput = new JTextField(3);
		
		positionLabel.setText("Position Title:"); // update label text
		// add new inputs to the new position input panel
		newPositionPanel.add(positionTitleInput);
		newPositionPanel.add(new JLabel("Abbreviation:")); // extra label needed for new JTextField
		newPositionPanel.add(positionAbbreviationInput);
		
		GridBagConstraints constraints = new GridBagConstraints(); // to position components in the GridBagLayout
		constraints.anchor = GridBagConstraints.LINE_START; // pin to the left of cell
		constraints.gridx = 1; // right column
		constraints.gridy = 1; // 2nd row
		
		mainAttributes.add(newPositionPanel, constraints); // add to the mainAttributes in place of the positionInputPanel
		
		revalidate(); // revalidate the display to show the new input fields
		setBounds(getX() - 30, getY(), 410, 270); // resize and re-centre the frame to fit the new input fields
	}
	
	/**
	 * Invoked when a button is pressed in the dialog
	 */
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) { // gets the title of the button pressed
		case "New": // when the new formation button is pressed
			// determine if a new formation or new position is being created
			if(e.getSource() == newFormationButton) {
				newFormationInput(); // open new formation input
			} else if(e.getSource() == newPositionButton) {
				newPositionInput(); // open new position input
			}
			break; // do nothing else
		case "Submit": // when the submit button is pressed
			Position newPosition; // will hold the new position
			Formation newFormation; // will hold the new formation
			if(creatingNewFormation) { // if creating a new formation
				// initialise a new formation with the values from the new formation input 
				newFormation = new Formation((int) defenderInput.getValue(),(int) midfielderInput.getValue(), (int) strikerInput.getValue());
				FormationManager.add(newFormation); // add the new formation to the FormationManager
			} else { // selecting an existing formation
				// the new formation is the value of the formation input combobox
				newFormation = (Formation) formationInput.getSelectedItem();
			}
			if(creatingNewPosition) { // if creating a new position
				// initialise a new position with the values from the new position input
				newPosition = new Position(positionTitleInput.getText(), positionAbbreviationInput.getText());
				PositionManager.add(newPosition); // add the new position to the PositionManager
			} else { // selecting an existing formation
				// the new position is the value of the position input combobox
				newPosition = (Position) positionInput.getSelectedItem();
			}
			// initialise the newPlayer with all values from the input form
			newPlayer = new Player(nameInput.getText(), newPosition, newFormation, (EmploymentStatus) employmentStatusInput.getSelectedItem(), (int) payPerYearInput.getValue());
			// no break as this case also executes the default clause
		default: // used by the submit and cancel button
			dispose(); // close the input dialog
		}
	}
	
	/**
	 * @return the new Player created by the input form
	 */
	public Player getNewPlayer() {
		return newPlayer;
	}
}
