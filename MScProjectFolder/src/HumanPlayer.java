import java.util.Scanner;
/*
Human Player class reads user's category decision
*
* @author Ifigenia Temesio
* @ version 2
 */

public class HumanPlayer extends PlayerAbstract {
	private boolean amIHuman;
	private String choice = "";
	Scanner input;

	// Sets human player to human, overwrites false state from PlayerAbstract. 
	public HumanPlayer() {
		amIHuman = true;

	}
	// Human selection returns a string choice based on keyboard input. 
	public String decideOnCategory() {
		input = new Scanner(System.in);

		System.out.println("Choose a category");
		choice = input.nextLine();
		input.close();
		return choice;

	}
}


