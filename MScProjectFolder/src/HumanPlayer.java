import java.util.Scanner;
/*
Human Player class reads user's category decision
 *
@author Ifigenia Temesio
@ version 1
 */

public class HumanPlayer extends PlayerAbstract {
	private String choice = "";

	public HumanPlayer() {
		amIHuman = true;
	}
	
	public String decideOnCategory() {
		Scanner input = new Scanner(System.in);

		System.out.println("Choose a category");
		choice = input.nextLine();
		input.close();
		
		return choice;
	}
}


