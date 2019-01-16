import java.util.ArrayList;

import javax.smartcardio.Card;

/**
 * The PlayerAbstract class holds all methods for the functioning of a
 * player's actions in a Top Trumps game.
 * 
 * @author Adrian Borg
 * @version 1
 *
 */

public abstract class PlayerAbstract {
	/**
	 * Performs the player actions for a game of Top Trumps.
	 */
	
	private ArrayList<Card> currentDeck = new ArrayList<Card>();
	private boolean amIHuman = false;
	
	//------Abstract methods--------------------------
	
	public abstract String decideOnCategory();
	
	//------Misc methods------------------------------
	
	public void addToDeck(Card inputCard) {
		/**
		 * Adds a card to the bottom of the deck of this player
		 * 
		 * @param inputCard card to place at the bottom of the deck
		 */
		this.currentDeck.add(inputCard);
	}
	
	public Card takeTopCard() {
		/**
		 * Removes top cards from the player's deck deck and returns it as an output
		 * 
		 * @return <code>Card topCard</code> : the top card in the deck;
		 * 		   <code>null</code> : if deck is empty;
		 */
		Card topCard = null;
		if (currentDeck.size() != 0) { // if there are cards in the deck
			topCard = this.currentDeck.get(0);
			this.currentDeck.remove(0);
		} 
		return topCard;
	}
	
	//------Getters-----------------------------------
	public boolean amIHuman() {
		/**
		 * States whether player function requires human input
		 * 
		 * @return <code>true</code> if player requires human input
		 * 		   <code>false</code> if player requires no human input
		 */
		return this.amIHuman;
	}
	
	public Card lookAtTopCard() {
		/**
		 * Gives the top card of the deck
		 * 
		 * @return <code>Card topCard</code> : the top card in the deck;
		 * 		   <code>null</code> : if deck is empty;
		 */
		Card topCard = null;
		if (currentDeck.size() != 0) { // if there are cards in the deck
			topCard = this.currentDeck.get(0);
		} 
		return topCard;
	}
	
	public int getNumberofCardsLeft() {
		/**
		 * Gives the number of cards left in the players deck
		 * 
		 * @return number of cards in the players deck as <code>int</code> 
		 */
		return currentDeck.size();
	}
	
	
	
	//------Setters---------------------------------
	public void setDeck(ArrayList<Card> inputDeck) {
		/**
		 * Sets this player's deck to the input deck.
		 * 
		 * @param inputDeck	an ArrayList of type Card
		 */
		this.currentDeck = inputDeck;
	}
	
	//------Logger Methods--------------------------
	private String logDeckContents() {
		/**
		 * Creates a string with all card names in the deck.
		 * 
		 * @return string with all cards in the deck or;
		 * 		   string saying deck is empty if no cards in the deck
		 */
		String logOutput = "";
		if (this.currentDeck.size()==0) {
			logOutput += "/n/t Deck is Empty /n";
		} else {
			logOutput += "/tDeck: /n";
			for(Card c: this.currentDeck) {
				logOutput += "/t/t" + c.getCardName() + "/n";
			}
		}
	}
	
	public String logDeckContents(int playerNumber) {
		/**
		 * Creates a string with player type and all card names in the deck.
		 * 
		 * @param playerNumber	the number assigned to the player within the Top Trumps game
		 * @return string with all cards in the deck or;
		 * 		   string saying deck is empty if no cards in the deck
		 */
		String playerType = "AI";
		String logOutput = "";
		if (amIHuman) {
			playerType = "Human";
		}
		
		logOutput += playerType + " " + playerNumber + "/n";
		logOutput += logDeckContents();
		return logOutput;
	}
}
