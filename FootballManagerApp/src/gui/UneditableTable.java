package gui;

import javax.swing.JTable;

/**
 * A table that cannot be edited by user interaction.
 * 
 * @author Jack
 *
 */
public class UneditableTable extends JTable {
	
	/**
	 * Creates a table that cannot be edited by user interaction.
	 * 
	 * @param data the data to populate the rows of the table
	 * @param columnNames the heading titles for ech column in the table
	 */
	public UneditableTable(Object[][] data, String[] columnNames) {
		super(new UneditableTableModel(data, columnNames)); // construct a JTable with an UneditableTableModel as the model
		/*
		 * The code below was found at the following resource:
		 * 
		 * Raja, 2019. How can we prevent the re-ordering columns of a JTable in Java?. [Online] 
		 * Available at: https://www.tutorialspoint.com/how-can-we-prevent-the-re-ordering-columns-of-a-jtable-in-java
		 * [Accessed 24 November 2021].
		 * 
		 * I did not copy the code verbatim, I looked to find the relevant code to make
		 * the reordering of columns not possible for the user and implemented that
		 * into my own code.
		 */
		this.getTableHeader().setReorderingAllowed(false); // prevent the user from reordering the columns
		this.getTableHeader().setResizingAllowed(false); // prevent the columns from being resized
	}

}
