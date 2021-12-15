package gui;

import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

/**
 * A JLabel that comes with padding on the left side.
 *
 * @author Jack
 *
 */
public class LeftPaddedLabel extends JLabel {
	
	/**
	 * Creates a JLabel with the specified padding on
	 * the left hand side.
	 * 
	 * @param text the String to display in the JLabel
	 * @param padding how much padding to add to the left hand side
	 */
	public LeftPaddedLabel(String text, int padding) {
		super(text);
		setBorder(new EmptyBorder(0, padding, 0, 0));
	}
	
}
