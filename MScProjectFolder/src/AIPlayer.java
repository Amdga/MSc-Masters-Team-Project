import java.util.ArrayList;
import java.util.Random;

/*
 * AI player randomly selecting category from 1 to 5
 * @author Ifigenia Temesio
 * @version 1
 * */

public class AIPlayer extends PlayerAbstract{
	private Deck currentDeck = new Deck();
	private Random r = new Random();
	
	public AIPlayer() {
		
	}
// Random selection of 5 deck headers. 
	public String decideOnCategory() {
		int choiceNum = r.nextInt(5);
		String choice = currentDeck.getHeader()[choiceNum];
		return choice;
	}
	

}


