package gui;

import leagueComponents.Player;

public class UneditableRowObjectReturnTableModel extends UneditableTableModel {
	
	private Object[] rowObjects;
	
	// Constructor
	public UneditableRowObjectReturnTableModel(Object[][] data, String[] columnNames, Object[] rowObjects) {
		super(data, columnNames);
		this.rowObjects = rowObjects;
	}
	
	public Object getRowObject(int rowIndex) {
		return rowObjects[rowIndex];
	}
	
	
	public Object[] getRowObjects() {
		return rowObjects;
	}
	
	public void setRowObjects(Object[] rowObjects) {
		this.rowObjects = rowObjects;
	}
	
	@Override
	public Object[][] getData() {
		return super.getData();
	}
	
	@Override
	public void setData(Object[][] data) {
		super.setData(data);
	}
	
}
