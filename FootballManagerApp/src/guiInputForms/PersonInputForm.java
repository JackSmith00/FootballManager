package guiInputForms;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;

import enums.EmploymentStatus;
import leagueComponents.Person;

public abstract class PersonInputForm extends JDialog implements ActionListener {

	protected JPanel form, mainAttributes, secondaryAttributes;
	protected JTextField nameInput;
	protected JComboBox<EmploymentStatus> employmentStatusInput;
	protected JSpinner payPerYearInput; // https://stackoverflow.com/questions/6435668/create-a-numeric-text-box-in-java-swing-with-increment-and-decrement-buttons
	
	private JPanel buttons;
	
	public PersonInputForm(JFrame owner, String title) {
		super(owner, title, true);
		
		setUpSubmitButtons();
		
		getContentPane().add(buttons, BorderLayout.SOUTH);
		
		setResizable(false);
		
		//pack();
		// setLocationRelativeTo(null);
		//setVisible(true); // https://stackoverflow.com/questions/49577917/displaying-jdialog-java/49579959
	}
	
	/**
	 * Constructor that allows an existing person to be passed in to have their attributes edited
	 * 
	 * @param owner
	 * @param title
	 * @param person
	 */
	public PersonInputForm(JFrame owner, String title, Person person) {
		this(owner, title);
		nameInput.setText(person.getName());
		employmentStatusInput.setSelectedItem(person.getEmploymentStatus());
		payPerYearInput.setValue(person.getPayPerYear());
	}
	
	protected void setUpComponents() {
		form = new JPanel();
		new BoxLayout(form, BoxLayout.Y_AXIS);
		mainAttributes = new JPanel(new GridBagLayout());
		secondaryAttributes = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		
		
		constraints.anchor = GridBagConstraints.LINE_END; // https://www.youtube.com/watch?v=YKaea4ezQQE&t=385s
		mainAttributes.add(new JLabel("Name:"), constraints);
		
		secondaryAttributes.add(new JLabel("Employment Status:"), constraints);
		
		constraints.gridy = 1;
		secondaryAttributes.add(new JLabel("Pay per year (GBP):"), constraints);
		
		
		nameInput = new JTextField(15);
		employmentStatusInput = new JComboBox<>(EmploymentStatus.values());
		payPerYearInput = new JSpinner(new SpinnerNumberModel(10_000, 0, null, 1000));
		Dimension spinnerSize = payPerYearInput.getPreferredSize(); // https://www.coderanch.com/t/339225/java/JSpinner-Size
		spinnerSize.width = 185;
		payPerYearInput.setPreferredSize(spinnerSize);
		
		
		constraints.anchor = GridBagConstraints.LINE_START;
		constraints.gridx = 1;
		constraints.gridy = 0;
		mainAttributes.add(nameInput, constraints);
		
		constraints.gridy = 0;
		secondaryAttributes.add(employmentStatusInput, constraints);
		
		constraints.gridy = 1;
		secondaryAttributes.add(payPerYearInput, constraints);
		
		form.add(mainAttributes);
		form.add(secondaryAttributes);
		
		form.setBorder(new EmptyBorder(10, 10, 10, 10));
	}
	
	private void setUpSubmitButtons() {
		
		buttons = new JPanel();
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(this);
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		
		buttons.add(submitButton);
		buttons.add(cancelButton);
		
		buttons.setBorder(new EmptyBorder(10, 10, 10, 10));
	}
}
