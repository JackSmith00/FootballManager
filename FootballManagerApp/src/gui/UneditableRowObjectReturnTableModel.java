package gui;

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
	
}
