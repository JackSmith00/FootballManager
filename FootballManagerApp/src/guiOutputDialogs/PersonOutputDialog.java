package guiOutputDialogs;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;
import java.text.NumberFormat;

import leagueComponents.Person;

public abstract class PersonOutputDialog extends JDialog {
	
	protected JPanel frame;
	protected JPanel mainAttributes;
	protected JPanel secondaryAttributes;
	private int padding = 10;
	
	private Person person;

	public PersonOutputDialog(JFrame owner, Person person) {
		super(owner, person.getName());
		this.person = person;
		
		setResizable(false);
	}
	
	protected void setUpComponents() {
		frame = new JPanel();
		new BoxLayout(frame, BoxLayout.Y_AXIS);
		
		GridLayout layout = new GridLayout(0, 2);
		layout.setVgap(5);
		
		mainAttributes = new JPanel(layout);
		mainAttributes.add(new RightAlignedLabel("Name:"));
		mainAttributes.add(new LeftPaddedLabel(person.getName(), padding));
		
		frame.add(mainAttributes);
		
		NumberFormat moneyFormatter = NumberFormat.getCurrencyInstance(); // https://stackoverflow.com/questions/2379221/java-currency-number-format
		moneyFormatter.setMaximumFractionDigits(0);

		secondaryAttributes = new JPanel(layout);
		secondaryAttributes.add(new RightAlignedLabel("Employment Status:"));
		secondaryAttributes.add(new LeftPaddedLabel(person.getEmploymentStatus().toString(), padding));
		secondaryAttributes.add(new RightAlignedLabel("Pay per year:"));
		secondaryAttributes.add(new LeftPaddedLabel(moneyFormatter.format(person.getPayPerYear()), padding));
		
		frame.add(secondaryAttributes);
	}
	
	public int getPadding() {
		return padding;
	}
	
	public void setPadding(int padding) {
		this.padding = padding;
	}
}
