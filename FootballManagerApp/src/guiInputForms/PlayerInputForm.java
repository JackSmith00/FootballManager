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
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import enums.EmploymentStatus;
import leagueComponents.CoachingStaffMember;
import leagueComponents.Formation;
import leagueComponents.FormationManager;
import leagueComponents.Person;
import leagueComponents.PersonWithFormationPreference;
import leagueComponents.Player;
import leagueComponents.Position;
import leagueComponents.PositionManager;

public class PlayerInputForm extends PersonWithFormationPreferenceInputForm {
	
	private JPanel positionInputPanel;
	private JLabel positionLabel;
	
	protected JComboBox<Position> positionInput;
	
	protected JButton newPositionButton;
	protected boolean creatingNewPosition = false;
	protected JTextField positionTitleInput;
	protected JTextField positionAbbreviationInput;
	
	private Player newPlayer;

	public PlayerInputForm(JFrame owner) {
		super(owner, "Add Player");
		
		setUpComponents();
		
		getContentPane().add(form, BorderLayout.CENTER);
		setBounds(0, 0, 350, 270);
		setLocationRelativeTo(null);
		setVisible(true); // https://stackoverflow.com/questions/49577917/displaying-jdialog-java/49579959
	}
	
	public PlayerInputForm(JFrame owner, Player player) {
		super(owner, "Add Player");

		setUpComponents();
		setExistingValues(player);
		
		getContentPane().add(form, BorderLayout.CENTER);
		setBounds(0, 0, 350, 270);
		setLocationRelativeTo(null);
		setVisible(true); // https://stackoverflow.com/questions/49577917/displaying-jdialog-java/49579959
		
	}
	
	@Override
	protected void setUpComponents() {
		
		super.setUpComponents();
		
		GridBagConstraints constraints = new GridBagConstraints();
		
		positionLabel = new JLabel("Position:");
		
		constraints.anchor = GridBagConstraints.LINE_END; // https://www.youtube.com/watch?v=YKaea4ezQQE&t=385s
		constraints.gridy = 1;
		mainAttributes.add(positionLabel, constraints);
		
		Position[] storedPositions = new Position[PositionManager.getPositions().size()];
		PositionManager.getPositions().toArray(storedPositions);
		positionInput = new JComboBox<Position>(storedPositions);
		
		newPositionButton = new JButton("New");
		newPositionButton.addActionListener(this);
		
		positionInputPanel = new JPanel();
		
		positionInputPanel.add(positionInput);
		positionInputPanel.add(newPositionButton);
		
		constraints.anchor = GridBagConstraints.LINE_START; // https://www.youtube.com/watch?v=YKaea4ezQQE&t=385s
		constraints.gridx = 1;
		mainAttributes.add(positionInputPanel, constraints);
		
	}
	
	@Override
	public void setExistingValues(Person person) {
		// TODO Auto-generated method stub
		super.setExistingValues(person);
		int index = Arrays.binarySearch(PositionManager.getPositions().toArray(), ((Player) person).getPosition());
		// had to use index rather than `setSelectedItem()` as on first use the current position is not recognised as a component of `positionInput`
		positionInput.setSelectedIndex(index);

	}
	
	public void newPositionInput() {
		creatingNewPosition = true;
		
		mainAttributes.remove(positionInputPanel);
		JPanel newPositionPanel = new JPanel();

		positionTitleInput = new JTextField(10);
		positionAbbreviationInput = new JTextField(3);
		
		positionLabel.setText("Position Title:");
		newPositionPanel.add(positionTitleInput);
		newPositionPanel.add(new JLabel("Abbreviation:"));
		newPositionPanel.add(positionAbbreviationInput);
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.LINE_START;
		constraints.gridx = 1;
		constraints.gridy = 1;
		
		mainAttributes.add(newPositionPanel, constraints);
		
		revalidate();
		setBounds(getX() - 30, getY(), 410, 270);
	}
	
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "New":
			if(e.getSource() == newFormationButton) {
				newFormationInput();
			} else if(e.getSource() == newPositionButton) {
				newPositionInput();
			}
			break;
		case "Submit":
			Position newPosition;
			Formation newFormation;
			if(creatingNewFormation) {
				newFormation = new Formation((int) defenderInput.getValue(),(int) midfielderInput.getValue(), (int) strikerInput.getValue());
				FormationManager.add(newFormation);
			} else {
				newFormation = (Formation) formationInput.getSelectedItem();
			}
			if(creatingNewPosition) {
				newPosition = new Position(positionTitleInput.getText(), positionAbbreviationInput.getText());
				PositionManager.add(newPosition);
			} else {
				newPosition = (Position) positionInput.getSelectedItem();
			}
			newPlayer = new Player(nameInput.getText(), newPosition, newFormation, (EmploymentStatus) employmentStatusInput.getSelectedItem(), (int) payPerYearInput.getValue());
		default:
			dispose();			
		}
	}
	
	public Player getNewPlayer() {
		return newPlayer;
	}
}
