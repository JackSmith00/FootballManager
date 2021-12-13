package guiOutputDialogs;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;

import gui.FootballManagerGUI;
import gui.LeftPaddedLabel;
import gui.RightAlignedLabel;
import guiInputForms.PlayerInputForm;
import leagueComponents.Player;
import leagueComponents.Team;

public class PlayerOutputDialog extends PersonWithFormationPreferenceOutputDialog {
	
	private LeftPaddedLabel position = new LeftPaddedLabel("", getPadding());
	private LeftPaddedLabel goalsScored, assistsMade, cardsGiven;

	public PlayerOutputDialog(JFrame owner, Player person) {
		super(owner, person, 310, 240);
		setUpComponents();
		getContentPane().add(frame);
		setVisible(true);
	}
	
	@Override
	protected void setUpComponents() {
		super.setUpComponents();
		
		setAllLabelText((Player)getPerson());
		
		mainAttributes.add(new RightAlignedLabel("Position:"), 2);
		mainAttributes.add(position, 3);
		
		mainAttributes.add(new RightAlignedLabel("Goals Scored:"), 4);
		goalsScored = new LeftPaddedLabel(String.valueOf(((Player) getPerson()).getGoalsScored()), getPadding());
		mainAttributes.add(goalsScored, 5);
		
		mainAttributes.add(new RightAlignedLabel("Assists Made:"), 6);
		assistsMade = new LeftPaddedLabel(String.valueOf(((Player) getPerson()).getAssistsMade()), getPadding());
		mainAttributes.add(assistsMade, 7);
		
		mainAttributes.add(new RightAlignedLabel("Cards Given:"), 8);
		cardsGiven = new LeftPaddedLabel(String.valueOf(((Player) getPerson()).getCardsGiven()), getPadding());
		mainAttributes.add(cardsGiven, 9);
		
		frame.revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		FootballManagerGUI appGui = (FootballManagerGUI) getOwner();		
		switch(e.getActionCommand()) {
		case "Edit":
			Player originalPlayer = (Player) getPerson();
			PlayerInputForm updatedPlayerForm = new PlayerInputForm(getOwner(), originalPlayer);
			Player updatedPlayer = updatedPlayerForm.getNewPlayer();
			if(updatedPlayer != null) {
				originalPlayer.setName(updatedPlayer.getName());
				originalPlayer.setTeam(updatedPlayer.getTeam());
				originalPlayer.setEmploymentStatus(updatedPlayer.getEmploymentStatus());
				originalPlayer.setPayPerYear(updatedPlayer.getPayPerYear());
				originalPlayer.setPreferredFormation(updatedPlayer.getPreferredFormation());
				originalPlayer.setPosition(updatedPlayer.getPosition());

				// need to update

				setAllLabelText(updatedPlayer);
				appGui.setUpTeamPage(((FootballManagerGUI) getOwner()).getCurrentTeam());

				revalidate();
				getOwner().getContentPane().revalidate();
			}
			break;
		case "Transfer Team":
			Team newTeam = teamToTransferTo("Player");
			if(newTeam != null) {
				appGui.getCurrentTeam().removePlayer((Player)getPerson());
				newTeam.addPlayer((Player)getPerson());
				appGui.setUpTeamPage(appGui.getCurrentTeam());
				appGui.revalidate();
				dispose();
			}
			break;
		case "Delete":
			boolean shouldDelete = shouldDeleteThisPerson("Player");
			if(shouldDelete) {
				appGui.getCurrentTeam().removePlayer((Player)getPerson());
				appGui.setUpTeamPage(appGui.getCurrentTeam());
				appGui.revalidate();
				dispose();
			}
			break;
		}
	}
	
	public void setAllLabelText(Player person) {
		// TODO Auto-generated method stub
		super.setAllLabelText(person);
		position.setText(person.getPosition().getPositionTitle());
		
	}

}
