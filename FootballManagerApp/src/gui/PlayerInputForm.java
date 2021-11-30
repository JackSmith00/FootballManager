package gui;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import enums.EmploymentStatus;
import leagueComponents.CoachingStaffMember;
import leagueComponents.Formation;
import leagueComponents.Player;

public class PlayerInputForm extends PersonWithFormationPreferenceInputForm {
	
	private JLabel position;
	protected JTextField positionInput;
	
	private Player newPlayer;

	public PlayerInputForm(JFrame owner) {
		super(owner, "Add Player");
		GridBagConstraints constraints = new GridBagConstraints();
		
		position = new JLabel("Position:");
		
		positionInput = new JTextField(15);
		
		constraints.anchor = GridBagConstraints.LINE_END; // https://www.youtube.com/watch?v=YKaea4ezQQE&t=385s
		constraints.gridy = 4;
		form.add(position, constraints);
		
		constraints.anchor = GridBagConstraints.LINE_START; // https://www.youtube.com/watch?v=YKaea4ezQQE&t=385s
		constraints.gridx = 1;
		form.add(positionInput, constraints);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true); // https://stackoverflow.com/questions/49577917/displaying-jdialog-java/49579959	}
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "Submit") {
			newPlayer = new Player(nameInput.getText(), positionInput.getText(), (Formation) formationInput.getSelectedItem(), (EmploymentStatus) employmentStatusInput.getSelectedItem(), (int) payPerYearInput.getValue());
		}
		dispose();
	}
	
	public Player getNewPlayer() {
		return newPlayer;
	}
}
