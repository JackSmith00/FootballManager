package guiInputForms;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import enums.EmploymentStatus;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;

import leagueComponents.CoachingStaffMember;
import leagueComponents.Formation;
import leagueComponents.Referee;

public class CoachingStaffInputForm extends PersonWithFormationPreferenceInputForm {

	private JLabel role;
	protected JTextField roleInput;
	
	private CoachingStaffMember newCoachingStaffMember;
	
	public CoachingStaffInputForm(JFrame owner) {
		super(owner, "Add Coaching Staff");
		
		GridBagConstraints constraints = new GridBagConstraints();
		
		role = new JLabel("Role:");
		
		roleInput = new JTextField(15);
		
		constraints.anchor = GridBagConstraints.LINE_END; // https://www.youtube.com/watch?v=YKaea4ezQQE&t=385s
		constraints.gridy = 4;
		form.add(role, constraints);
		
		constraints.anchor = GridBagConstraints.LINE_START; // https://www.youtube.com/watch?v=YKaea4ezQQE&t=385s
		constraints.gridx = 1;
		form.add(roleInput, constraints);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true); // https://stackoverflow.com/questions/49577917/displaying-jdialog-java/49579959
	}
	
	public CoachingStaffMember getNewCoachingStaffMember() {
		return newCoachingStaffMember;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "Submit") {
			newCoachingStaffMember = new CoachingStaffMember(nameInput.getText(), roleInput.getText(), (Formation) formationInput.getSelectedItem(), (EmploymentStatus) employmentStatusInput.getSelectedItem(), (int) payPerYearInput.getValue());
		}
		dispose();
	}

}
