package gui;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;

import enums.EmploymentStatus;
import leagueComponents.Referee;

public class RefereeInputForm extends PersonInputForm {
	
	private Referee newReferee;

	public RefereeInputForm(JFrame owner) {
		super(owner, "Add Referee");
	}
	
	public Referee getNewReferee() {
		return newReferee;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "Submit":
			newReferee = new Referee(nameInput.getText(), (EmploymentStatus) employmentStatusInput.getSelectedItem(), (int) payPerYearInput.getValue());
		default :
			dispose();
		}
	}

}
