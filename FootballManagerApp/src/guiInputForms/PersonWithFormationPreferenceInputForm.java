package guiInputForms;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import leagueComponents.Formation;
import leagueComponents.FormationManager;
import leagueComponents.Person;
import leagueComponents.PersonWithFormationPreference;

public abstract class PersonWithFormationPreferenceInputForm extends PersonInputForm {
	
	private JPanel formationInputPanel;
	
	protected JComboBox<Formation> formationInput;
	
	protected boolean creatingNewFormation = false;
	protected JSpinner defenderInput;
	protected JSpinner midfielderInput;
	protected JSpinner strikerInput;

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
		
		JButton newFormationButton = new JButton("New");
		newFormationButton.addActionListener(this);

		formationInputPanel = new JPanel();
		
		formationInputPanel.add(formationInput);
		formationInputPanel.add(newFormationButton);
		
		constraints.anchor = GridBagConstraints.LINE_START;
		constraints.gridx = 1;
		mainAttributes.add(formationInputPanel, constraints);
	}
	
	@Override
	public void setExistingValues(Person person) {
		// TODO Auto-generated method stub
		super.setExistingValues(person);
		int index = Arrays.binarySearch(FormationManager.getFormations().toArray(), ((PersonWithFormationPreference) person).getPreferredFormation());
		// had to use index rather than `setSelectedItem()` as on first use the current formation is not recognised as a component of `formationInput`
		formationInput.setSelectedIndex(index);
	}
	
	public void newFormationInput() {
		creatingNewFormation = true;
		
		mainAttributes.remove(formationInputPanel);
		JPanel newFormationPanel = new JPanel();

		defenderInput = new JSpinner(new SpinnerNumberModel(4, 0, 11, 1));
		midfielderInput = new JSpinner(new SpinnerNumberModel(3, 0, 11, 1));
		strikerInput = new JSpinner(new SpinnerNumberModel(3, 0, 11, 1));
		
		newFormationPanel.add(defenderInput);
		newFormationPanel.add(new JLabel("-"));
		newFormationPanel.add(midfielderInput);
		newFormationPanel.add(new JLabel("-"));
		newFormationPanel.add(strikerInput);
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.LINE_START;
		constraints.gridx = 1;
		constraints.gridy = 2;
		
		mainAttributes.add(newFormationPanel, constraints);
		
		revalidate();
	}

}
