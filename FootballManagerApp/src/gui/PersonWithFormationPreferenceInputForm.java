package gui;

import java.awt.event.ActionEvent;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import leagueComponents.Formation;

import java.awt.GridBagConstraints;

public class PersonWithFormationPreferenceInputForm extends PersonInputForm {
	
	private JLabel formation;
	protected JComboBox<Formation> formationInput;

	public PersonWithFormationPreferenceInputForm(JFrame owner, String title) {
		super(owner, title);
		GridBagConstraints constraints = new GridBagConstraints();
		
		formation = new JLabel("Prefered Formation:");
		
		formationInput = new JComboBox<Formation>();
		
		constraints.anchor = GridBagConstraints.LINE_END; // https://www.youtube.com/watch?v=YKaea4ezQQE&t=385s
		constraints.gridy = 3;
		form.add(formation, constraints);
		
		constraints.anchor = GridBagConstraints.LINE_START;
		constraints.gridx = 1;
		form.add(formationInput, constraints);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
