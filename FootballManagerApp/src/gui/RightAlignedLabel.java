package gui;

import javax.swing.JLabel;

public class RightAlignedLabel extends JLabel {

	public RightAlignedLabel(String text) {
		super(text);
		setHorizontalAlignment(JLabel.RIGHT);
	}
}
