import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The TestLogger Class contains the functionality to log actions performed during the
 * running of the program for test purposes. If in testing mode, this will produce a 
 * log file with the results.
 * 
 * @author Adrian Borg
 * @version 1
 *
 */

public class TestLogger {
	
	/**
	 * Posts debug test information to a log file if in test mode.
	 */
	
	boolean testMode;
	FileWriter fr;
	
	public TestLogger(boolean testModeInput) {
		/**
		 * creates a TestLogger object
		 * 
		 * @param testModeInput  <code>true</code> if in test mode, <code>false</code> if not
		 */
		this.testMode = testModeInput;
		try {
			fr = new FileWriter("toptrumps.log");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//-------Logging Methods------------------------------------
	
	
	
	//-------Helper Methods-------------------------------------
	
	private void writeSeparator() {
		String separator = "/n------------------------------/n";
		try {
			fr.write(separator);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String deckToString(ArrayList<Card> deck) {
		/**
		 * Creates a string with all card names in the deck.
		 * 
		 * @Param  deck an ArrayList of type Card
		 * @return string with all cards in the deck or;
		 * 		   string saying deck is empty if no cards in the deck
		 */
		String logOutput = "";
		if (deck.size()==0) {
			logOutput += "/n/t Deck is Empty /n";
		} else {
			logOutput += "/tDeck: /n";
			for(Card c: deck) {
				logOutput += "/t/t" + c.getCardName() + "/n";
			}
		}
		return logOutput;
	}
	
	private String logDeckContents(int playerNumber, boolean isHuman, ArrayList<Card> deck) {
		/**
		 * Creates a string with player type and all card names in the deck.
		 * 
		 * @param playerNumber	the number assigned to the player within the Top Trumps game
		 * @return string with all cards in the deck or;
		 * 		   string saying deck is empty if no cards in the deck
		 */
		String playerType = "AI";
		String logOutput = "";
		if (isHuman) {
			playerType = "Human";
		}
		
		logOutput += playerType + " is Player  " + playerNumber + " and has/n";
		logOutput += deckToString(deck);
		return logOutput;
	}
	
	private String logCategorySelection(int playerNumber, String choice) {
		/**
		 * Creates a string detailing the player number and their category selection.
		 * 
		 * @param playerNumber	the number assigned to the player within the game.
		 * @param choice		the category choice of the player
		 * @return string with the player number and their category choice
		 */
		String logOutput = "Player " + playerNumber + " chose the category: " + choice;
		return logOutput;
		
	}
	
	private String logWinner(int playerNumber) {
		/**
		 * Creates a string detailing who won the game.
		 * 
		 * @param playerNumber	the number assigned to the player within the game.
		 * @return string with the winning player
		 */
		
		String logOutput =  "Winner is Player " + playerNumber;
		return logOutput;
	}

}
