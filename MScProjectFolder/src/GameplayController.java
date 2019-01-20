import java.util.ArrayList;

public class GameplayController {
	
	private ArrayList<Card> cardsInDeck; //This will be replaced by model.getDeck() when model is implemented
	
	private ArrayList<Card> cardsInDrawPile;
	private ArrayList<Card> cardsInPlay;
	private ArrayList<PlayerAbstract> players;
	
	int model; //This will be replaced by GetDeck class when it is created
	int controller; //This will be replaced by Controller class when it is created
	
	private PlayerAbstract decideWhoGoesFirst() {
		
		//First active player is chosen randomly
		
		int number_of_players = players.size();
		int player_who_goes_first = (int) (Math.random() * number_of_players);
		
		PlayerAbstract player = players.get(player_who_goes_first);
		
		return player;
	}
	
	private void dealOutDeck() {
		
		//Cards are handed out to players
		
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
			System.out.println("Adding human player "+i);
			players.add(new HumanPlayer());
		}
		
		for(int i=0; i<number_of_ai; i++) {
			System.out.println("Adding AI player "+i);
			players.add(new AIPlayer());
		}
		
	}
	
	private PlayerAbstract topTrumpsRound(PlayerAbstract current_player) {
		
		//single round of top trumps
		//Players who are not out draw a card
		//Respective card shown to player
		//Active player makes category selection
		//All drawn cards added to "in play" pile
		//All cards in play shown corresponding with owners
		//Category value selected are compared in all cards
		//Player with the highest value card wins
		//Winner gets all in play cards
		
		/*for(int i=0; i<players.size(); i++) {
		//for loop done this way to avoid ConcurrentModificationException caused by removing a player from the list	within the loop
			PlayerAbstract p = players.get(i);
			
			Card players_card = p.takeTopCard();
			if(players_card == null) {
				players.remove(p);
			}
			else {
				//System.out.println(players_card.getCardName());
				cardsInPlay.add(players_card);
			}
			
		}*/
		
		return nextPlayer(current_player);
		
	}
	
	private PlayerAbstract nextPlayer(PlayerAbstract current_player) {
		
		int current_player_index = players.indexOf(current_player);
		int next_player_index = current_player_index;
		
		next_player_index++;
		if(next_player_index >= players.size()) {
			next_player_index = 0;
		}
		
		PlayerAbstract next_player = players.get(next_player_index);
		return next_player;
	}
	
	private void topTrumpsGame() {
		
		//while each player still has cards left, have a round
		
		PlayerAbstract current_player = decideWhoGoesFirst();
		
		int round_counter = 0;
		//while(players.size() > 1) {
		for(int i=0; i<10; i++) {
			System.out.println("Round "+round_counter);
			current_player = topTrumpsRound(current_player);
			round_counter ++;
		}
		
		//While there are still people left in the game
			//Play rounds
			//Winner gets passed to round and becomes new active player
			//Passed back into round
		
	}
	
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
		
		game.topTrumpsGame();
		
	}

}
