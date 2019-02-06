package players;
import java.util.Random;

/**
 * AI player randomly selecting category from 1 to 5
 * @author Ifigenia Temesio
 * @version 2
 * */

public class AIPlayer extends PlayerAbstract{
	private String choice = "";
	private Random r = new Random();
	
	// Constructor
	public AIPlayer(int playerNumber) {
		super(playerNumber);
	}
	
	// Random selection of 5 deck headers. 
	public String decideOnCategory() {
		
		int number_of_headers = currentDeck.get(0).getHeaders().length;
		
		int choiceNum = r.nextInt(number_of_headers);
		
		choice = currentDeck.get(0).getHeaders()[choiceNum];
		
		return choice;
	}
}
