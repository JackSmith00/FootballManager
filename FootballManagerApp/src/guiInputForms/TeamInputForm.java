package guiInputForms;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import leagueComponents.Stadium;
import leagueComponents.Team;

import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TeamInputForm extends JDialog implements ActionListener {
	
	protected JPanel form, buttons;
	private JLabel teamName;
	private JLabel stadiumName;
	private JLabel stadiumCapacity;
	
	protected JTextField teamNameInput;
	protected JTextField stadiumNameInput;
	protected JSpinner stadiumCapacityInput;
	
	private Team newTeam;

	public TeamInputForm(JFrame owner) {
		super(owner, "Add Team", true);
		
		setUpComponents();
		setUpButtons();
		
		getContentPane().add(form, BorderLayout.CENTER);
		getContentPane().add(buttons, BorderLayout.SOUTH);
		
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		setVisible(true); // https://stackoverflow.com/questions/49577917/displaying-jdialog-java/49579959
	}
	
	private void setUpComponents() {
		form = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		
		teamName = new JLabel("Team Name:");
		stadiumName = new JLabel("Stadium Name:");
		stadiumCapacity = new JLabel("Stadium Capacity:");
		
		teamNameInput = new JTextField(15);
		stadiumNameInput = new JTextField(15);
		stadiumCapacityInput = new JSpinner(new SpinnerNumberModel(10_000, 0, null, 1000));
		Dimension spinnerSize = stadiumCapacityInput.getPreferredSize(); // https://www.coderanch.com/t/339225/java/JSpinner-Size
		spinnerSize.width = 185;
		stadiumCapacityInput.setPreferredSize(spinnerSize);
		
		constraints.anchor = GridBagConstraints.LINE_END; // https://www.youtube.com/watch?v=YKaea4ezQQE&t=385s
		form.add(teamName, constraints);
		
		constraints.gridy = 1;
		form.add(stadiumName, constraints);
		
		constraints.gridy = 2;
		form.add(stadiumCapacity, constraints);
		
		constraints.anchor = GridBagConstraints.LINE_START;
		constraints.gridx = 1;
		constraints.gridy = 0;
		form.add(teamNameInput, constraints);
		
		constraints.gridy = 1;
		form.add(stadiumNameInput, constraints);
		
		constraints.gridy = 2;
		form.add(stadiumCapacityInput, constraints);
		
		form.setBorder(new EmptyBorder(10, 10, 10, 10));
	}
	
	private void setUpButtons() {
		buttons = new JPanel();
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(this);
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		
		buttons.add(submitButton);
		buttons.add(cancelButton);
		
		buttons.setBorder(new EmptyBorder(10, 10, 10, 10));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "Submit") {
			newTeam = new Team(teamNameInput.getText(), new Stadium(stadiumNameInput.getText(), (int) stadiumCapacityInput.getValue()));
		}
		dispose();
	}
	
	public Team getNewTeam() {
		return newTeam;
	}
}
