package guiInputForms;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import enums.EmploymentStatus;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;

import leagueComponents.CoachingStaffMember;
import leagueComponents.Formation;

public class CoachingStaffInputForm extends PersonWithFormationPreferenceInputForm {

	protected JTextField roleInput;
	
	private CoachingStaffMember newCoachingStaffMember;
	
	public CoachingStaffInputForm(JFrame owner) {
		super(owner, "Add Coaching Staff");
		
		setUpCoachStaffComponents();
		
		getContentPane().add(form, BorderLayout.CENTER);
		setBounds(0, 0, 350, 250);
		setLocationRelativeTo(null);
		setVisible(true); // https://stackoverflow.com/questions/49577917/displaying-jdialog-java/49579959
	}
	
	public CoachingStaffInputForm(JFrame owner, CoachingStaffMember coachingStaffMember) {
		super(owner, "Add Coaching Staff", coachingStaffMember);
		
		setUpCoachStaffComponents();
		
		getContentPane().add(form, BorderLayout.CENTER);
		setBounds(0, 0, 350, 250);
		setLocationRelativeTo(null);
		setVisible(true); // https://stackoverflow.com/questions/49577917/displaying-jdialog-java/49579959
	}
	
	protected void setUpCoachStaffComponents() {
		// TODO Auto-generated method stub
		
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.anchor = GridBagConstraints.LINE_END;
		constraints.gridy = 1;
		mainAttributes.add(new JLabel("Role:"), constraints);
		
		roleInput = new JTextField(15);
		
		constraints.anchor = GridBagConstraints.LINE_START;
		constraints.gridx = 1;
		mainAttributes.add(roleInput, constraints);
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
