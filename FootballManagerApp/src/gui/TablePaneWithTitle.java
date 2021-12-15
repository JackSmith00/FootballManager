package gui;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

/**
 * A class for creating the pane to hold a table with a title.
 * Also has the option to have a button under the table.
 * 
 * @author Jack
 *
 */
public class TablePaneWithTitle extends JPanel {
	
	/**
	 * Constructor for a table pane with a title
	 * 
	 * @param table the table to add to the pane
	 * @param title the title of the table
	 */
	public TablePaneWithTitle(JTable table, String title) {
		super(); // creates a JPanel
		new BoxLayout(this, BoxLayout.Y_AXIS); // gives the panel a box layout
		JLabel titleLabel = new JLabel(title); // creates a JLabel for the title
		add(titleLabel); // add the title to the panel
		titleLabel.setHorizontalAlignment(JLabel.CENTER); // centre the title in the panel
		add(new JScrollPane(table)); // add the table to the panel in a scroll pane
		
	}
	
	/**
	 * Constructor for a table pane with a title and a button underneath
	 * 
	 * @param table : The table to add to the pane
	 * @param title : The title of the table
	 * @param buttonTitle : The label for the button
	 * @param buttonActionListener : The action listener to be used with the button
	 */
	public TablePaneWithTitle(JTable table, String title, String buttonTitle, ActionListener buttonActionListener) {
		this(table, title); // use existing constructor for other components
		JButton addButton = new JButton(buttonTitle); // create a JButton for the table with the given text
		addButton.addActionListener(buttonActionListener); // add an action listener to the button
		add(addButton); // add the button to the panel
		
	}
}
