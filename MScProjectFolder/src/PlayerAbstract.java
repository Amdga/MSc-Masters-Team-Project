import java.util.ArrayList;

public abstract class PlayerAbstract {

	//Temporary placeholder class so I can use it in GameplayController
	
	private Card topCard;
	public ArrayList<Card> deck = new ArrayList<Card>();
	
	public abstract void speak();
	
	public Card takeTopCard() {
		
		return topCard;
		
	}
	
	public void setDeck(ArrayList<Card> inputDeck) {
		
		deck = inputDeck;
		
	}
	
	public void addToDeck(Card inputCard) {
		
		deck.add(inputCard);
		
	}

}
