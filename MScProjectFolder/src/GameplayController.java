import java.util.ArrayList;

public class GameplayController {
	
	private ArrayList<Card> cardsInDeck; //This will be replaced by model.getDeck() when model is implemented
	
	private ArrayList<Card> cardsInDrawPile;
	private ArrayList<Card> cardsInPlay;
	public ArrayList<PlayerAbstract> players;
	
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
	
	/*private void topTrumpsRound() {
		
		//single round of top trumps
		
		for(int i=0; i<players.size(); i++) {
		//for loop done this way to avoid ConcurrentModificationException caused by removing a player from the list	within the loop
			PlayerAbstract p = players.get(i);
			
			Card players_card = p.takeTopCard();
			if(players_card == null) {
				players.remove(p);
			}
			else {
				System.out.println(players_card.getCardName());
				cardsInPlay.add(players_card);
			}
			
		}
	}
	
	private void topTrumpsGame() {
		
		//while each player still has cards left, have a round
		
		int round_counter = 0;
		while(players.size() > 1) {
			System.out.println("Round "+round_counter);
			topTrumpsRound();
			round_counter ++;
		}
		
	}*/
	
	public GameplayController(int model, int controller, int number_of_human_players, int number_of_ai_players) {
		
		players = new ArrayList<PlayerAbstract>();
		cardsInDeck = new ArrayList<Card>();
		cardsInPlay = new ArrayList<Card>();
		
		this.model = model;
		this.controller = controller;
		
		createPlayers(number_of_human_players,number_of_ai_players);
		
		for(int i=0; i<10; i++) {
			
			String[] headers = {"Food","Tastiness","Speed of Consumption"};
			int[] input_values = {1,1,1};
			
			
			Card card = new Card("hello "+i,headers,input_values);
			cardsInDeck.add(card);
			
		}
		
		dealOutDeck();
	}
	
	public static void main(String[] args) {
		
		GameplayController game = new GameplayController(0,0,3,4);
		
		int first_player = game.decideWhoGoesFirst();
		
		System.out.println("Player "+first_player+" goes first");
		System.out.println("Their first card is:");
		System.out.println(game.players.get(first_player).lookAtTopCard().getCardName());
		
	}

}
