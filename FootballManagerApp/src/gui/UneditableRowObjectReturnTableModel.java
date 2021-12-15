package gui;

/**
 * Model for a table that cannot be edited by the user
 * and can return the object represented by each
 * row of data.
 * 
 * @author Jack
 *
 */
public class UneditableRowObjectReturnTableModel extends UneditableTableModel {
	
	private Object[] rowObjects; // holds the object associated with each row
	
	/**
	 * Constructs a UneditableRowObjectReturnTableModel which is a
	 * table that cannot be edited by the user that can also
	 * retrieve the objects associated with the rows in the table
	 * 
	 * @param data the data to display in the rows of the table
	 * @param columnNames the heading titles of the columns
	 * @param rowObjects the objects associated with each row
	 */
	public UneditableRowObjectReturnTableModel(Object[][] data, String[] columnNames, Object[] rowObjects) {
		super(data, columnNames); // construct an UneditableTableModel with given data and columnNames
		this.rowObjects = rowObjects; // assign rowObjects to passed in Objects
	}
	
	/**
	 * Gets a single object associated with the
	 * particular given row in the table.
	 * 
	 * @param rowIndex the index of the row to return the object for
	 * @return the Object associates with the given row
	 */
	public Object getRowObject(int rowIndex) {
		return rowObjects[rowIndex]; // use the rowIndex to access the object in the rowObject array
	}
	
	/**
	 * Returns all Objects stored in the table represented
	 * by each row.
	 * 
	 * @return all rowObjects in the table
	 */
	public Object[] getRowObjects() {
		return rowObjects;
	}
	
	/**
	 * Allows rowObjects to be updated if new values are
	 * created elsewhere in the program.
	 * 
	 * @param rowObjects an array of Objects that will be represented by a row in the table
	 */
	public void setRowObjects(Object[] rowObjects) {
		this.rowObjects = rowObjects;
	}
	
}
