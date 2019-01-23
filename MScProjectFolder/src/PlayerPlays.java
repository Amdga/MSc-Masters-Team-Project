
public class PlayerPlays implements Comparable<PlayerPlays> {
	
	private PlayerAbstract player;
	private Card card;
	private String category;
	
	public PlayerPlays(PlayerAbstract player, Card card, String category) {
		
		this.player = player;
		this.card = card;
		this.category = category;
		
	}

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
	
	public String toString() {
		
		String to_string = "Player "+player.playerNumber + " has card value "+card.getValue(category);
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
