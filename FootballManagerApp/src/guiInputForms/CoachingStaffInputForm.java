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
import leagueComponents.FormationManager;
import leagueComponents.Person;

public class CoachingStaffInputForm extends PersonWithFormationPreferenceInputForm {

	protected JTextField roleInput;
	
	private CoachingStaffMember newCoachingStaffMember;
	
	public CoachingStaffInputForm(JFrame owner) {
		super(owner, "Add Coaching Staff");
		
		setUpComponents();
		
		getContentPane().add(form, BorderLayout.CENTER);
		setBounds(0, 0, 350, 250);
		setLocationRelativeTo(null);
		setVisible(true); // https://stackoverflow.com/questions/49577917/displaying-jdialog-java/49579959
	}
	
	public CoachingStaffInputForm(JFrame owner, CoachingStaffMember coachingStaffMember) {
		super(owner, "Add Coaching Staff");
		
		setUpComponents();
		setExistingValues(coachingStaffMember);
		
		getContentPane().add(form, BorderLayout.CENTER);
		setBounds(0, 0, 350, 250);
		setLocationRelativeTo(null);
		setVisible(true); // https://stackoverflow.com/questions/49577917/displaying-jdialog-java/49579959
	}
	
	@Override
	protected void setUpComponents() {
		// TODO Auto-generated method stub
		
		super.setUpComponents();
		
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.anchor = GridBagConstraints.LINE_END;
		constraints.gridy = 1;
		mainAttributes.add(new JLabel("Role:"), constraints);
		
		roleInput = new JTextField(15);
		
		constraints.anchor = GridBagConstraints.LINE_START;
		constraints.gridx = 1;
		mainAttributes.add(roleInput, constraints);
	}

	@Override
	public void setExistingValues(Person person) {
		// TODO Auto-generated method stub
		super.setExistingValues(person);
		roleInput.setText(((CoachingStaffMember) person).getRole());
	}
	
	public CoachingStaffMember getNewCoachingStaffMember() {
		return newCoachingStaffMember;
	}
	
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "New":
			newFormationInput();
			break;
		case "Submit":
			Formation newFormation;
			if(creatingNewFormation) {
				newFormation = new Formation((int) defenderInput.getValue(),(int) midfielderInput.getValue(), (int) strikerInput.getValue());
				FormationManager.add(newFormation);
			} else {
				newFormation = (Formation) formationInput.getSelectedItem();
			}
			newCoachingStaffMember = new CoachingStaffMember(nameInput.getText(), roleInput.getText(), newFormation, (EmploymentStatus) employmentStatusInput.getSelectedItem(), (int) payPerYearInput.getValue());
		default:
			dispose();			
		}
	}

}
