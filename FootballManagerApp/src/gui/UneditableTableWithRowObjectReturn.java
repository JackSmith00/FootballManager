package gui;

import javax.swing.JTable;

import leagueComponents.Player;

public class UneditableTableWithRowObjectReturn extends JTable {

	public UneditableTableWithRowObjectReturn(Object[][] data, String[] columnNames, Object[] rowObjects) {
		super(new UneditableRowObjectReturnTableModel(data, columnNames, rowObjects));
		this.getTableHeader().setReorderingAllowed(false); // reference https://www.tutorialspoint.com/how-can-we-prevent-the-re-ordering-columns-of-a-jtable-in-java
		this.getTableHeader().setResizingAllowed(false);
	}
	
	public Object getRowObject(int row) {
		return ((UneditableRowObjectReturnTableModel) getModel()).getRowObject(row);
	}
	
	public void setData(Object[][] newData, Object[] newRowObjects) {
		((UneditableRowObjectReturnTableModel) getModel()).setData(newData);
		((UneditableRowObjectReturnTableModel) getModel()).setRowObjects(newRowObjects);
	}
}
