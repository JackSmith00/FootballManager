package guiInputForms;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;

import enums.EmploymentStatus;
import leagueComponents.Referee;

public class RefereeInputForm extends PersonInputForm {
	
	private Referee newReferee;

	public RefereeInputForm(JFrame owner) {
		super(owner, "Add Referee");
		getContentPane().add(form, BorderLayout.CENTER);
		setBounds(0, 0, 350, 200);
		setLocationRelativeTo(null);
		setVisible(true); // https://stackoverflow.com/questions/49577917/displaying-jdialog-java/49579959
	}
	
	public RefereeInputForm(JFrame owner, Referee referee) {
		super(owner, "Add Referee", referee);
		getContentPane().add(form, BorderLayout.CENTER);
		setBounds(0, 0, 350, 200);
		setLocationRelativeTo(null);
		setVisible(true); // https://stackoverflow.com/questions/49577917/displaying-jdialog-java/49579959
	}
	
	public Referee getNewReferee() {
		return newReferee;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "Submit") {
			newReferee = new Referee(nameInput.getText(), (EmploymentStatus) employmentStatusInput.getSelectedItem(), (int) payPerYearInput.getValue());
		}
		dispose();
	}

}
