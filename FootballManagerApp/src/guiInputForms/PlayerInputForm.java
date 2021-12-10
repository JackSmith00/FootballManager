package guiInputForms;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import enums.EmploymentStatus;
import leagueComponents.CoachingStaffMember;
import leagueComponents.Formation;
import leagueComponents.FormationManager;
import leagueComponents.Person;
import leagueComponents.Player;

public class PlayerInputForm extends PersonWithFormationPreferenceInputForm {
	
	protected JTextField positionInput;
	
	private Player newPlayer;

	public PlayerInputForm(JFrame owner) {
		super(owner, "Add Player");
		
		setUpComponents();
		
		getContentPane().add(form, BorderLayout.CENTER);
		setBounds(0, 0, 350, 250);
		setLocationRelativeTo(null);
		setVisible(true); // https://stackoverflow.com/questions/49577917/displaying-jdialog-java/49579959
	}
	
	public PlayerInputForm(JFrame owner, Player player) {
		super(owner, "Add Player");

		setUpComponents();
		setExistingValues(player);
		
		getContentPane().add(form, BorderLayout.CENTER);
		setBounds(0, 0, 350, 250);
		setLocationRelativeTo(null);
		setVisible(true); // https://stackoverflow.com/questions/49577917/displaying-jdialog-java/49579959
		
	}
	
	@Override
	protected void setUpComponents() {
		
		super.setUpComponents();
		
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.anchor = GridBagConstraints.LINE_END; // https://www.youtube.com/watch?v=YKaea4ezQQE&t=385s
		constraints.gridy = 1;
		mainAttributes.add(new JLabel("Position:"), constraints);
		
		positionInput = new JTextField(15);
		
		constraints.anchor = GridBagConstraints.LINE_START; // https://www.youtube.com/watch?v=YKaea4ezQQE&t=385s
		constraints.gridx = 1;
		mainAttributes.add(positionInput, constraints);
		
	}
	
	@Override
	public void setExistingValues(Person person) {
		// TODO Auto-generated method stub
		super.setExistingValues(person);
		//System.out.println(formationInput.getSelectedItem());
		positionInput.setText(((Player) person).getPosition());

	}
	
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "New":
			break;
		case "Submit":
			Formation newFormation;
			if(creatingNewFormation) {
				newFormation = new Formation((int) defenderInput.getValue(),(int) midfielderInput.getValue(), (int) strikerInput.getValue());
				FormationManager.add(newFormation);
			} else {
				newFormation = (Formation) formationInput.getSelectedItem();
			}
			newPlayer = new Player(nameInput.getText(), positionInput.getText(), newFormation, (EmploymentStatus) employmentStatusInput.getSelectedItem(), (int) payPerYearInput.getValue());
		default:
			dispose();			
		}
	}
	
	public Player getNewPlayer() {
		return newPlayer;
	}
}
