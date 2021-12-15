package gui;

import leagueComponents.Player;

/**
 * Model for a table that cannot be edited by the user,
 * however they are able to toggle checkboxes to select
 * players in the row.
 * 
 * @author Jack
 *
 */
public class PlayerSelectTableModel extends UneditableRowObjectReturnTableModel {
	
	/**
	 * Constructs a PlayerSelectTableModel which is a table that
	 * cannot be edited by the user, but they can toggle selection
	 * boxes and retrieve players associated with the selected row
	 * 
	 * @param data row data for the table
	 * @param columnNames column headings for the table
	 * @param players players associated with each row in the table
	 */
	public PlayerSelectTableModel(Object[][] data, String[] columnNames, Player[] players) {
		super(data, columnNames, players); // call super constructor
	}
	
	/**
	 * Will return true if the cell contains a boolean value.
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(getColumnClass(columnIndex) == Boolean.class) { // check if the column contains boolean values
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Returns the class of objects within this column.
	 * 
	 * Does so by finding only the class of the first
	 * cell in this column.
	 * 
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return getData()[0][columnIndex].getClass();
	}
}
