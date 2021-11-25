package gui;

import javax.swing.table.AbstractTableModel;

public class UneditableTableModel extends AbstractTableModel {
	
	private Object[][] data;
	private String[] columnNames;
	
	// Constructor
	public UneditableTableModel(Object[][] data, String[] columnNames) {
		super();
		this.data = data;
		this.columnNames = columnNames;
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public int getRowCount() {
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
	
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return columnNames[column];
	}
	
	

}
