package common;

import players.PlayerAbstract;

public class PlayerPlays implements Comparable<PlayerPlays> {
	
	/**
	 * Class needed to associate players with cards, so if a card wins in GameplayController we know who has played it
	 * 
	 */
	
	private PlayerAbstract player;
	private Card card;
	private String category;
	
	public PlayerPlays(PlayerAbstract player, Card card, String category) {
		
		this.player = player;
		this.card = card;
		this.category = category;
		
	}

	/**
	 * GameplayController sorts the list of cards in play, and therefore a compareTo method is needed
	 * We want to sort the values of the chosen category
	 * 
	 */
	public int compareTo(PlayerPlays compare_to_object) {
		
		int compare_to_value = compare_to_object.getCard().getValue(category);
		
		if(card.getValue(category) > (int)compare_to_value) {
			return 1;
		}
		else if(card.getValue(category) < (int)compare_to_value) {
			return -1;
		}
		else {
			return 0;
		}
		
	}
	
	/**
	 * 
	 * This method is only used for debugging so far
	 */
	public String toString() {
		
		String to_string = "Player "+player.whoAmI() + " has card value "+card.getValue(category) + " with card " + card.getCardName();
		return to_string;
		
	}
	
	public PlayerAbstract getPlayer() {
		return player;
	}
	
	public Card getCard() {
		return card;
	}
	
	public int getCategoryValue() {
		return card.getValue(category);
	}
}
