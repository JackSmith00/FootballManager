package gui;

import javax.swing.JLabel;

/**
 * A JLabel that is automatically aligned to the right.
 * 
 * @author Jack
 *
 */
public class RightAlignedLabel extends JLabel {

	/**
	 * Allows a RightAlignedLabel to be created with a
	 * given String
	 * @param text the String to display in the JLabel
	 */
	public RightAlignedLabel(String text) {
		super(text); // create a JLabel with the given text
		setHorizontalAlignment(JLabel.RIGHT); // align the JLabel to the right
	}
}
