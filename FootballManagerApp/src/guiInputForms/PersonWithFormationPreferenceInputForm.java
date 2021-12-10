package guiInputForms;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.util.Arrays;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import leagueComponents.Formation;
import leagueComponents.FormationManager;
import leagueComponents.Person;
import leagueComponents.PersonWithFormationPreference;

public abstract class PersonWithFormationPreferenceInputForm extends PersonInputForm {
	
	protected JComboBox<Formation> formationInput;

	public PersonWithFormationPreferenceInputForm(JFrame owner, String title) {
		super(owner, title);
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
	
	@Override
	public void setExistingValues(Person person) {
		// TODO Auto-generated method stub
		super.setExistingValues(person);
		int index = Arrays.binarySearch(FormationManager.getFormations().toArray(), ((PersonWithFormationPreference) person).getPreferredFormation());
		// had to use index rather than `setSelectedItem()` as on first use the current formation is not recognised as a component of `formationInput`
		formationInput.setSelectedIndex(index);
		System.out.println(formationInput.getSelectedItem());
	}

}
