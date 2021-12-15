package gui;

import javax.swing.table.AbstractTableModel;

/**
 * Model for a table that cannot be edited by the user.
 * 
 * @author Jack
 *
 */
public class UneditableTableModel extends AbstractTableModel {
	
	private Object[][] data; // holds data for each row in the table
	private String[] columnNames; // holds the headings for each column in the table
	
	/**
	 * Constructs a UneditableTableModel which is a
	 * table that cannot be edited by the user.
	 * 
	 * @param data a 2 dimensional array of data for the rows in the table
	 * @param columnNames the headings for the columns in the table
	 */
	public UneditableTableModel(Object[][] data, String[] columnNames) {
		super(); // call the constructor for the AbstractTableModel
		this.data = data;
		this.columnNames = columnNames;
	}
	
	/**
	 * No cell is editable with an UneditableTableModel.
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override // existing Javadoc is accurate
	public int getRowCount() {
		return data.length;
	}

	@Override // existing Javadoc is accurate
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override // existing Javadoc is accurate
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data[rowIndex][columnIndex];
	}
	
	/**
	 * Returns the name of the column at the given index.
	 */
	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}
	
	/**
	 * @return a String array of all column headings in the table
	 */
	public String[] getColumnNames() {
		return columnNames;
	}

	/**
	 * @return all data from the table in a 2 dimensional array
	 */
	public Object[][] getData() {
		return data;
	}
	
	/**
	 * Used to change the value held in a given cell in the table
	 */
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		data[rowIndex][columnIndex] = aValue;;
	}
	
	/**
	 * @param columnNames a String array containing new headings for the columns in the table
	 */
	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}
	
	/**
	 * Allows data in the table to be updated with new values
	 * when data changes elsewhere in the program.
	 * 
	 * @param data the new data to display in the table
	 */
	public void setData(Object[][] data) {
		this.data = data;
	}

}
