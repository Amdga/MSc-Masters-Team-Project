package common;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Human Player class reads user's category decision
 *
 * @author Ifigenia Temesio
 * @version 1
 */

public class HumanPlayer extends PlayerAbstract {
	private String choice = "";
	private ArrayList<String> availableChoices = new ArrayList<String>();
	private CLIView view;

	public HumanPlayer(int playerNumber, CLIView inputView) {
		super(playerNumber);
		amIHuman = true;
		view = inputView;
	}

	public String decideOnCategory() {
//		setAvailableChoices();
//		Scanner input = new Scanner(System.in);
//		// Prompt user
//		String output = getUserPrompt();
//		System.out.println(output);
//		// Get Prompt
//		choice = input.nextLine();
//		// If choice is not suitable, ask again
//		if (!availableChoices.contains(choice)) {
//			System.out.println("Category not recognised!");
//			choice = decideOnCategory();
//		}
//		
//		input.close();
		choice = view.getCategory(this.lookAtTopCard());
		return choice;
	}
	
//	private String getUserPrompt() {
//		// loop through available choices show user what choices are available
//		String output = "Choose a category (" + availableChoices.get(0);
//		for(int i=1;i<availableChoices.size();i++) {
//			if (i != availableChoices.size()-1) {
//				output += ", " + availableChoices.get(i);
//			} else {
//				output += " OR " + availableChoices.get(i) + "):";
//			}
//		}
//		return output;
//	}
//
//	private void setAvailableChoices() {
//		// set the suitable available choices which the user can choose
//		if (availableChoices.isEmpty()) {
//			for(String category: lookAtTopCard().getHeaders()) {
//				availableChoices.add(category);
//			}
//		}
//	}
}
