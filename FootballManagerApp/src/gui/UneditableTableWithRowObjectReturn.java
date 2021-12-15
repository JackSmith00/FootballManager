package gui;

import javax.swing.JTable;

/**
 * A table that cannot be edited by user interaction and can
 * also store and return objects associated with each row
 * in the table.
 * 
 * @author Jack
 *
 */
public class UneditableTableWithRowObjectReturn extends JTable {

	/**
	 * Creates a table that cannot be edited and can store and return
	 * objects associated with each row of data.
	 * 
	 * @param data the data to display in the table
	 * @param columnNames the headings of each column
	 * @param rowObjects the objects associated with each row in the table
	 */
	public UneditableTableWithRowObjectReturn(Object[][] data, String[] columnNames, Object[] rowObjects) {
		super(new UneditableRowObjectReturnTableModel(data, columnNames, rowObjects)); // create a JTable with an UneditableRowObjectReturnTableModel table model
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
		this.getTableHeader().setResizingAllowed(false); // prevent columns from being resized
	}
	
	/**
	 * Gets the object associated with the given row in the table
	 * 
	 * @param row the row to get the Object for
	 * @return the Object associated with the given row
	 */
	public Object getRowObject(int row) {
		return ((UneditableRowObjectReturnTableModel) getModel()).getRowObject(row); // access the table model to get the rowObject
	}
	
	/**
	 * Allows the data to be updated as necessary
	 * 
	 * @param newData the new data to display in the table
	 * @param newRowObjects the new objects associated with the new data for the table
	 */
	public void setData(Object[][] newData, Object[] newRowObjects) {
		((UneditableRowObjectReturnTableModel) getModel()).setData(newData);
		((UneditableRowObjectReturnTableModel) getModel()).setRowObjects(newRowObjects);
	}
}
