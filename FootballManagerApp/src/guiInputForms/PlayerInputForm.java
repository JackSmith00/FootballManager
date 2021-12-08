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
		super(owner, "Add Player", player);

		setUpComponents();
		
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
