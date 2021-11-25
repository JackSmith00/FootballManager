package gui;

import javax.swing.JTable;

public class UneditableTable extends JTable {
	
	public UneditableTable(Object[][] data, String[] columnNames) {
		super(new UneditableTableModel(data, columnNames));
		this.getTableHeader().setReorderingAllowed(false); // reference https://www.tutorialspoint.com/how-can-we-prevent-the-re-ordering-columns-of-a-jtable-in-java
		this.getTableHeader().setResizingAllowed(false);
	}

}
