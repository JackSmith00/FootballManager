package gui;

import javax.swing.JTable;

import leagueComponents.Player;

public class PlayerSelectTable extends JTable {

	public PlayerSelectTable(Object[][] data, String[] columnNames, Player[] players) {
		super(new PlayerSelectTableModel(data, columnNames, players));
		this.getTableHeader().setReorderingAllowed(false); // reference https://www.tutorialspoint.com/how-can-we-prevent-the-re-ordering-columns-of-a-jtable-in-java
		this.getTableHeader().setResizingAllowed(false);
	}
	
	public void setData(Object[][] newData, Player[] newPlayers) {
		((PlayerSelectTableModel) getModel()).setData(newData);
		((PlayerSelectTableModel) getModel()).setPlayers(newPlayers);
	}
}
