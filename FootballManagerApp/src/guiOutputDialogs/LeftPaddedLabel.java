package guiOutputDialogs;

import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

public class LeftPaddedLabel extends JLabel {
	
	public LeftPaddedLabel(String text, int padding) {
		super(text);
		setBorder(new EmptyBorder(0, padding, 0, 0));
	}

}
