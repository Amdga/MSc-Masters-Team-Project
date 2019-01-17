import java.util.ArrayList;

public class GameplayController {
	
	ArrayList<Card> cardsInDeck; //This will be replaced by model.getDeck() when model is implemented
	
	ArrayList<Card> cardsInDrawPile;
	ArrayList<Card> cardsInPlay;
	ArrayList<PlayerAbstract> players;
	
	int model; //This will be replaced by GetDeck class when it is created
	int controller; //This will be replaced by Controller class when it is created
	
	private int decideWhoGoesFirst() {
		
		int number_of_players = players.size();
		int player_who_goes_first = (int) (Math.random() * number_of_players);
		
		return player_who_goes_first;
	}
	
	private void dealOutDeck() {
		
		int card_counter = 0;
		for(Card c : cardsInDeck) {
			
			int player_to_give_card_to = card_counter % players.size();
			
			players.get(player_to_give_card_to).addToDeck(c);
			card_counter ++;
			
		}
		
	}
	
	private void giveCardsToPlayers(ArrayList<Card> card, PlayerAbstract player) {
		
		int card_counter = 0;
		for(Card c : cardsInDeck) {
			
			int player_to_give_card_to = card_counter % players.size();
			
			players.get(player_to_give_card_to).addToDeck(c);
			card_counter ++;
			
		}
		
	}
	
	private void createPlayers(int number_of_humans, int number_of_ai) {
		
		for(int i=0; i<number_of_humans; i++) {
			players.add(new HumanPlayer());
		}
		
		for(int i=0; i<number_of_ai; i++) {
			players.add(new AIPlayer());
		}
		
	}
	
	private void topTrumpsRound() {
		
		//single round of top trumps
		
	}
	
	private void topTrumpsGame() {
		
		//while each player still has cards left, have a round
		
	}
	
	public void speak_player(int player) { //temporary testing method
		
		players.get(player).speak();
		
	}
	
	public GameplayController(int model, int controller, int number_of_human_players, int number_of_ai_players) {
		
		players = new ArrayList<PlayerAbstract>();
		cardsInDeck = new ArrayList<Card>();
		
		this.model = model;
		this.controller = controller;
		
		createPlayers(number_of_human_players,number_of_ai_players);
		
		for(int i=0; i<10; i++) {
			
			Card card = new Card(i);
			cardsInDeck.add(card);
			
		}
		
		dealOutDeck();
		for(PlayerAbstract p : players) {
			
			System.out.println("Player "+players.indexOf(p));
			
			for(Card card : p.deck) {

				System.out.println(card.get_card_number());
				
			}
			
		}
		
		/*for(int i=0; i<100; i++) {
			int player_who_goes_first = decideWhoGoesFirst();
			System.out.println(player_who_goes_first);
			speak_player(player_who_goes_first);
		}*/
	
	}
	
	public static void main(String[] args) {
		
		GameplayController game = new GameplayController(0,0,3,4);
		
	}

}
