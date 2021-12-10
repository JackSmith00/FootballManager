package guiOutputDialogs;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import gui.FootballManagerGUI;
import gui.LeftPaddedLabel;
import gui.RightAlignedLabel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import leagueComponents.Person;
import leagueComponents.Team;

public abstract class PersonOutputDialog extends JDialog implements ActionListener {
	
	protected JPanel frame, mainAttributes, secondaryAttributes;
	private int padding = 10;
	private LeftPaddedLabel name = new LeftPaddedLabel("", padding);
	private LeftPaddedLabel employmentStatus = new LeftPaddedLabel("", padding);
	private LeftPaddedLabel payPerYear = new LeftPaddedLabel("", padding);
	
	private Person person;
	private JFrame owner;
	
	private JPanel buttons;

	public PersonOutputDialog(JFrame owner, Person person, int width, int height) {
		super(owner);
		this.person = person;
		this.owner = owner;
		
		setUpButtons();
		getContentPane().add(buttons, BorderLayout.SOUTH);
		setBounds(0, 0, width, height);
		setLocationRelativeTo(null);
		setResizable(false);
	}
	
	protected void setUpComponents() {
		frame = new JPanel();
		new BoxLayout(frame, BoxLayout.Y_AXIS);
		
		GridLayout layout = new GridLayout(0, 2);
		layout.setVgap(5);
		
		setAllLabelText(person);
		
		mainAttributes = new JPanel(layout);
		mainAttributes.add(new RightAlignedLabel("Name:"));
		mainAttributes.add(name);
		
		frame.add(mainAttributes);
		

		secondaryAttributes = new JPanel(layout);
		secondaryAttributes.add(new RightAlignedLabel("Employment Status:"));
		secondaryAttributes.add(employmentStatus);
		secondaryAttributes.add(new RightAlignedLabel("Pay per year:"));
		secondaryAttributes.add(payPerYear);
		
		frame.add(secondaryAttributes);
	}

	private void setUpButtons() {
		buttons = new JPanel();
		
		JButton editButton = new JButton("Edit");
		editButton.addActionListener(this);
		JButton transferButton = new JButton("Transfer Team");
		transferButton.addActionListener(this);
		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(this);
		

		buttons.add(editButton);
		buttons.add(transferButton);
		buttons.add(deleteButton);
	}
	
	public void setAllLabelText(Person person) {
		setTitle(person.getName());
		name.setText(person.getName());
		employmentStatus.setText(person.getEmploymentStatus().toString());
		
		NumberFormat moneyFormatter = NumberFormat.getCurrencyInstance(); // https://stackoverflow.com/questions/2379221/java-currency-number-format
		moneyFormatter.setMaximumFractionDigits(0);
		
		payPerYear.setText(moneyFormatter.format(person.getPayPerYear()));
	}
	
	public Team teamToTransferTo(String personType) {
		FootballManagerGUI appGui = (FootballManagerGUI) getOwner();
		Team [] teams = new Team[appGui.getCurrentLeague().getTeams().size()];
		appGui.getCurrentLeague().getTeams().toArray(teams);
		JComboBox<Team> teamSelector = new JComboBox<Team>(teams); // https://www.youtube.com/watch?v=iXFplYFuqFE
		
		JPanel messagePanel = new JPanel();
		messagePanel.add(new JLabel("Which team should the referee be moved to?"));
		messagePanel.add(teamSelector);
		
		int input = JOptionPane.showConfirmDialog(getOwner(), messagePanel, "Transfer Team", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);
		if(input == JOptionPane.OK_OPTION) {
			return (Team) teamSelector.getSelectedItem();
		} else {
			return null;
		}
	}
	
	public boolean shouldDeleteThisPerson(String personType) {
		int input = JOptionPane.showConfirmDialog(getOwner(), "Are you sure you want to delete this " + personType + "?\nThis cannot be undone.", "Delete " + personType, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		if(input == JOptionPane.YES_OPTION) {
			return true;
		} else {
			return false;
		}
	}
	
	public Person getPerson() {
		return person;
	}
	
	public JFrame getOwner() {
		return owner;
	}
	
	public int getPadding() {
		return padding;
	}
	
	public void setPadding(int padding) {
		this.padding = padding;
	}
}
