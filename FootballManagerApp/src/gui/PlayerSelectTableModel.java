package gui;

import javax.swing.table.AbstractTableModel;

import leagueComponents.Player;

public class PlayerSelectTableModel extends AbstractTableModel {
	
	private Object[][] data;
	private String[] columnNames;
	private Player[] players;

	public PlayerSelectTableModel(Object[][] data, String[] columnNames, Player[] players) {
		super();
		this.data = data;
		this.columnNames = columnNames;
		this.players = players;
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(getColumnClass(columnIndex) == Boolean.class) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		// TODO Auto-generated method stub
		return data[0][columnIndex].getClass();
	}
	
	public void setValueAt(Object aValue, int row, int column) {
        data[row][column] = aValue;
    }

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return data.length;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return data[rowIndex][columnIndex];
	}
	
	public Player getPlayerAt(int row) {
		return players[row];
	}
	
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return columnNames[column];
	}
	
	public Object[][] getData() {
		return data;
	}
	
	public void setData(Object[][] data) {
		this.data = data;
	}
	
	public Player[] getPlayers() {
		return players;
	}
	
	public void setPlayers(Player[] players) {
		this.players = players;
	}
}
