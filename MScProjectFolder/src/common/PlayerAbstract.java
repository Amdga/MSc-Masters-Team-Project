package common;
import java.util.ArrayList;

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
	
	protected ArrayList<Card> currentDeck = new ArrayList<Card>();
	protected boolean amIHuman = false;
	protected int playerNumber;
	
	//------Constructor-------------------------------
	
	protected PlayerAbstract(int inputPlayer) {
		this.playerNumber = inputPlayer;
	}
	
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
	
	public ArrayList<Card> getCurrentDeck() {
		/**
		 * Returns the deck of the player
		 * 
		 * @return ArrayList of type Card which is the deck of the player
		 */
		return this.currentDeck;
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
	
//	public static void main(String[] args) {
//		
//		// Test Method
//		
//		HumanPlayer hP = new HumanPlayer();
//		AIPlayer aP = new AIPlayer();
//		GetDeckModel dM = new GetDeckModel();
//		TestLogger tL = new TestLogger(true);
//		
//		int[] pO = {0, 1};
//		
//		ArrayList<Card> deck = dM.getShuffledDeck();
//		hP.setDeck(deck);
//		aP.addToDeck(deck.get(0));
//		aP.addToDeck(deck.get(1));
//		hP.takeTopCard().getCardName();
//		
//		ArrayList<Card> activeCards = new ArrayList<>();
//		activeCards.add(hP.lookAtTopCard());
//		activeCards.add(aP.lookAtTopCard());
//		
//		tL.logPlayerDeck(1, hP.amIHuman(), hP.getCurrentDeck());
//		tL.logPlayerInitialDeck(2, aP.amIHuman(), aP.getCurrentDeck());
//		
//		tL.logCategory(2, aP.decideOnCategory(), pO, activeCards);
//		tL.logCategory(1, hP.decideOnCategory(), pO, activeCards);
//		
//	}
}
