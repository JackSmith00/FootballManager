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
	 * @param table : The table to add to the pane
	 * @param title : The title of the table
	 */
	public TablePaneWithTitle(JTable table, String title) {
		super();
		new BoxLayout(this, BoxLayout.Y_AXIS);
		JLabel titleLabel = new JLabel(title);
		add(titleLabel);
		titleLabel.setHorizontalAlignment(JLabel.CENTER); // https://www.youtube.com/watch?v=Kmgo00avvEw&t=1698s
		add(new JScrollPane(table));
		
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
		super();
		new BoxLayout(this, BoxLayout.Y_AXIS);
		JLabel titleLabel = new JLabel(title);
		add(titleLabel);
		titleLabel.setHorizontalAlignment(JLabel.CENTER); // https://www.youtube.com/watch?v=Kmgo00avvEw&t=1698s
		add(new JScrollPane(table));
		JButton addButton = new JButton(buttonTitle);
		addButton.addActionListener(buttonActionListener);
		add(addButton);
		
	}
}
