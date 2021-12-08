package guiInputForms;

import java.awt.GridBagConstraints;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import leagueComponents.Formation;
import leagueComponents.FormationManager;
import leagueComponents.PersonWithFormationPreference;

public abstract class PersonWithFormationPreferenceInputForm extends PersonInputForm {
	
	protected JComboBox<Formation> formationInput;

	public PersonWithFormationPreferenceInputForm(JFrame owner, String title) {
		super(owner, title);
	}
	
	public PersonWithFormationPreferenceInputForm(JFrame owner, String title, PersonWithFormationPreference person) {
		super(owner, title, person);
		formationInput.setSelectedItem(person.getPreferredFormation());
	}
	
	@Override
	protected void setUpComponents() {
		
		super.setUpComponents();
		
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.anchor = GridBagConstraints.LINE_END;
		constraints.gridy = 2;
		mainAttributes.add(new JLabel("Prefered Formation:"), constraints);
		
		Formation[] storedFormations = new Formation[FormationManager.getFormations().size()];
		FormationManager.getFormations().toArray(storedFormations);
		formationInput = new JComboBox<Formation>(storedFormations);
		
		constraints.anchor = GridBagConstraints.LINE_START;
		constraints.gridx = 1;
		mainAttributes.add(formationInput, constraints);
	}

}
