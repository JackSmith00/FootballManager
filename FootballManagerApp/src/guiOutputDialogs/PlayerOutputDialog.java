package guiOutputDialogs;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;

import leagueComponents.Player;

public class PlayerOutputDialog extends PersonWithFormationPreferenceOutputDialog {

	public PlayerOutputDialog(JFrame owner, Player person) {
		super(owner, person, 300, 240);
		getContentPane().add(frame);
		setVisible(true);
	}
	
	@Override
	protected void setUpComponents() {
		super.setUpComponents();
		
		mainAttributes.add(new RightAlignedLabel("Role:"), 2);
		mainAttributes.add(new LeftPaddedLabel(((Player) getPerson()).getPosition(), getPadding()), 3);
		mainAttributes.add(new RightAlignedLabel("Goals Scored:"), 4);
		mainAttributes.add(new LeftPaddedLabel(String.valueOf(((Player) getPerson()).getGoalsScored()), getPadding()), 5);
		mainAttributes.add(new RightAlignedLabel("Assists Made:"), 6);
		mainAttributes.add(new LeftPaddedLabel(String.valueOf(((Player) getPerson()).getAssistsMade()), getPadding()), 7);
		mainAttributes.add(new RightAlignedLabel("Cards Given:"), 8);
		mainAttributes.add(new LeftPaddedLabel(String.valueOf(((Player) getPerson()).getCardsGiven()), getPadding()), 9);
		
		frame.revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
