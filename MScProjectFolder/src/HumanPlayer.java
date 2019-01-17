import java.util.ArrayList;
import java.util.Scanner;
/*
Human Player class reads user's category decision
 *
@author Ifigenia Temesio
@ version 1
 */

public class HumanPlayer extends PlayerAbstract {
	private String choice = "";
	private ArrayList<String> availableChoices = new ArrayList<String>();

	public HumanPlayer() {
		amIHuman = true;
	}

	public String decideOnCategory() {
		setAvailableChoices();
		Scanner input = new Scanner(System.in);

		String output = "Choose a category (" + availableChoices.get(0);
		for(int i=1;i<availableChoices.size();i++) {
			if (i != availableChoices.size()-1) {
				output += ", " + availableChoices.get(i);
			} else {
				output += " OR " + availableChoices.get(i) + "):";
			}
		}
		System.out.println(output);
		choice = input.nextLine();
		
		if (!availableChoices.contains(choice)) {
			System.out.println("Category not recognised!");
			choice = decideOnCategory();
		}
		
		input.close();
		
		return choice;
	}

	private void setAvailableChoices() {
		if (availableChoices.isEmpty()) {
			for(String category: lookAtTopCard().getHeaders()) {
				availableChoices.add(category);
			}
		}
	}


}


