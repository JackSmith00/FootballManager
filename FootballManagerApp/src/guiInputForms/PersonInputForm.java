package guiInputForms;

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

public abstract class PersonInputForm extends JDialog implements ActionListener {

	protected JPanel form;
	private JLabel name;
	private JLabel employmentStatus;
	private JLabel payPerYear;
	protected JTextField nameInput;
	protected JComboBox<EmploymentStatus> employmentStatusInput;
	protected JSpinner payPerYearInput; // https://stackoverflow.com/questions/6435668/create-a-numeric-text-box-in-java-swing-with-increment-and-decrement-buttons
	
	public PersonInputForm(JFrame owner, String title) {
		super(owner, title, true);
		form = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		
		name = new JLabel("Name:");
		employmentStatus = new JLabel("Employment Status:");
		payPerYear = new JLabel("Pay per year (GBP):");
		
		nameInput = new JTextField(15);
		employmentStatusInput = new JComboBox<>(EmploymentStatus.values());
		payPerYearInput = new JSpinner(new SpinnerNumberModel(10_000, 0, null, 1000));
		Dimension spinnerSize = payPerYearInput.getPreferredSize(); // https://www.coderanch.com/t/339225/java/JSpinner-Size
		spinnerSize.width = 185;
		payPerYearInput.setPreferredSize(spinnerSize);
		
		constraints.anchor = GridBagConstraints.LINE_END; // https://www.youtube.com/watch?v=YKaea4ezQQE&t=385s
		form.add(name, constraints);
		
		constraints.gridy = 1;
		form.add(employmentStatus, constraints);
		
		constraints.gridy = 2;
		form.add(payPerYear, constraints);
		
		constraints.anchor = GridBagConstraints.LINE_START;
		constraints.gridx = 1;
		constraints.gridy = 0;
		form.add(nameInput, constraints);
		
		constraints.gridy = 1;
		form.add(employmentStatusInput, constraints);
		
		constraints.gridy = 2;
		form.add(payPerYearInput, constraints);
		
		JPanel buttons = new JPanel();
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(this);
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		
		buttons.add(submitButton);
		buttons.add(cancelButton);
		
		
		form.setBorder(new EmptyBorder(10, 10, 10, 10));
		buttons.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(form, BorderLayout.CENTER);
		getContentPane().add(buttons, BorderLayout.SOUTH);
		
		setResizable(false);
		
		//pack();
		// setLocationRelativeTo(null);
		//setVisible(true); // https://stackoverflow.com/questions/49577917/displaying-jdialog-java/49579959
	}
}
