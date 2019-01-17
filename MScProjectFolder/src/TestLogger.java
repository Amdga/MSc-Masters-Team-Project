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
			if (this.testMode) { 
				fr = new FileWriter("toptrumps.log");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//-------Logging Methods------------------------------------
	public void write(String output) {
		/**
		 * Writes a string to the file
		 * 
		 * @Param String to be written
		 */
		try {
			if (this.testMode) {
				fr.write(output);
				writeSeparator();
				fr.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void logDeckCreation(ArrayList<Card> deck) {
		/**
		 * Writes the contents of the created deck to the file
		 * 
		 * @Param  deck an ArrayList of type Card
		 */

		String logOutput = "Deck has been created\n";
		logOutput += deckToString(deck);
		write(logOutput);
	}

	public void logDeckShuffle(ArrayList<Card> deck) {
		/**
		 * Writes the contents of the shuffled deck to the file
		 * 
		 * @Param  deck an ArrayList of type Card
		 */
		String logOutput = "Deck has been shuffled\n";
		logOutput += deckToString(deck);
		write(logOutput);
	}

	public void logPlayerInitialDeck(int playerNumber, boolean isHuman, ArrayList<Card> deck) {
		/**
		 * Writes a string with player type and all initial cards in the deck.
		 * 
		 * @param playerNumber	the number assigned to the player within the Top Trumps game
		 */
		String logOutput = "Initial deck: ";
		logOutput += logDeckContents(playerNumber, isHuman, deck);
		write(logOutput);
	}



	public void logCommunalPile(ArrayList<Card> deck) {
		/**
		 * Writes the contents of the communal pile to the file
		 * 
		 * @Param  deck an ArrayList of type Card
		 */
		String logOutput = "Communal pile contains:\n";
		logOutput += deckToString(deck);
		write(logOutput);
	}

	public void logActiveCards(ArrayList<Card> deck) {
		/**
		 * Writes the active cards to the file
		 * 
		 * @Param  deck an ArrayList of type Card
		 */
		String logOutput = "Active cards are:\n";
		logOutput += deckToString(deck);
		write(logOutput);
	}

	public void logCategory(int playerNumber, String choice, int[] playerOrder, ArrayList<Card> activeCards) {
		/**
		 * Writes a string detailing the player number and their category selection.
		 * 
		 * @param playerNumber	the number assigned to the player within the game.
		 * @param choice		the category choice of the player
		 */
		write(logCategorySelection(playerNumber, choice));
		write(logCategoryScores(playerOrder, activeCards, choice));
	}

	public void logPlayerDeck(int playerNumber, boolean isHuman, ArrayList<Card> deck) {
		/**
		 * Writes a string with player type and all card names in the deck.
		 * 
		 * @param playerNumber	the number assigned to the player within the Top Trumps game
		 */
		String logOutput = logDeckContents(playerNumber, isHuman, deck);
		write(logOutput);
	}

	public void logWinningPlayer(int playerNumber) {
		/**
		 * Writes a string detailing who won the game.
		 * 
		 * @param playerNumber	the number assigned to the player within the game.
		 */
		String logOutput = logWinner(playerNumber);
		write(logOutput);
	}


	//-------Helper Methods-------------------------------------

	public void writeSeparator() {
		String separator = "\n------------------------------\n";
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
			logOutput += "\n\t Deck is Empty \n";
		} else {
			logOutput += "\tCards: \n";
			for(Card c: deck) {
				logOutput += "\t\t" + c.getCardName() + "\n";
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

		logOutput += "Player  " + playerNumber + " is " + playerType + " and has\n";
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
		String logOutput = "Player " + playerNumber + " chose the category: " + choice + "\n";
		return logOutput;

	}
	
	private String logCategoryScores(int[] playerOrder, ArrayList<Card> activeCards, String choice) {
		/**
		 * Creates a string detailing the scores of the category
		 * 
		 * @param playerOrder	the order in which players have added cards to the active card deck
		 * @param activeCards	Arraylist<Card> with the active cards of each player
		 * @param choice		categoty choice made by the player
		 * @return	string with the scores of each player
		 */
		String logOutput = "\tScores:\n";
		for(int i=0;i<activeCards.size();i++) {
			logOutput += "\t\tPlayer " + playerOrder[i] + ":\t" + activeCards.get(i).getValue(choice) + "\n";
		}
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
