package gui;

import javax.swing.JTable;

import leagueComponents.Player;

/**
 * Allows a table to be created that cannot be edited,
 * but checkboxes can be toggled to select players.
 * 
 * @author Jack
 *
 */
public class PlayerSelectTable extends JTable {

	/**
	 * Creates a table that cannot be edited by user interaction,
	 * can return players associated with each row of data, and
	 * can make selections with checkbox columns.
	 * 
	 * @param data contents of the table rows
	 * @param columnNames the headings for each column
	 * @param players the players associated with the rows
	 */
	public PlayerSelectTable(Object[][] data, String[] columnNames, Player[] players) {
		super(new PlayerSelectTableModel(data, columnNames, players)); // create a JTable with the PlayerSelectTableModel model
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
		this.getTableHeader().setReorderingAllowed(false); // prevent the user from reordering columns
		this.getTableHeader().setResizingAllowed(false); // prevent the columns from being resized
	}
	
	/**
	 * Allows both the data and the rowObject associated
	 * with the data to be updated in one method.
	 * 
	 * @param newData the data to now display in the table
	 * @param newPlayers the player rowObjects associated with the `newData`
	 */
	public void setData(Object[][] newData, Player[] newPlayers) {
		((PlayerSelectTableModel) getModel()).setData(newData);
		((PlayerSelectTableModel) getModel()).setRowObjects(newPlayers);
	}
}
