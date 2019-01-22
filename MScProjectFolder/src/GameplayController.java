import java.util.ArrayList;
import java.util.Collections;

public class GameplayController {
	
	private ArrayList<Card> cardsInDeck; //This will be replaced by model.getDeck() when model is implemented
	
	private ArrayList<Card> cardsInDrawPile;
	private ArrayList<Card> cardsInPlay;
	private ArrayList<PlayerAbstract> players;
	
	private CLIView cli_view;
	
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
			
			System.out.println("Player "+player_to_give_card_to+" gets "+c.getCardName());
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
		
		int player_counter = 0;
		
		for(int i=0; i<number_of_humans; i++) {
			System.out.println("Adding human player "+i);
			players.add(new HumanPlayer(player_counter,cli_view));
			player_counter++;
		}
		
		for(int i=0; i<number_of_ai; i++) {
			System.out.println("Adding AI player "+i);
			players.add(new AIPlayer(player_counter));
			player_counter++;
		}
		
	}
	
	private PlayerAbstract topTrumpsRound(PlayerAbstract current_player) {
		
		current_player.lookAtTopCard();
		String category = current_player.decideOnCategory();
		
			category = "Food"; //This will be deleted once I know everything else is working!
		
		PlayerAbstract winning_player = players.get(0);
		int winning_value = Integer.MIN_VALUE;
		
		ArrayList<PlayerAbstract> players_to_remove = new ArrayList<PlayerAbstract>();
		for(PlayerAbstract p : players) {
			//now take the top cards off all the players
			
			p.lookAtTopCard();
			Card players_card = p.takeTopCard();
			//show card to player - called in View??
			
			if(players_card == null) {
				System.out.println("Player "+p.whoAmI()+" has no cards left");
				players_to_remove.add(p);
			}
			else {
				System.out.println("Player "+p.whoAmI()+" has 1 less card");
				int current_value = players_card.getValue("Tastiness");
				System.out.println("And their card has value "+current_value);
				
				if(current_value > winning_value) {
					winning_value = current_value;
					winning_player = p;
				}
				
				cardsInPlay.add(players_card);
			}
		}
		
		System.out.println("Winning player = "+winning_player.whoAmI());
		Collections.shuffle(cardsInPlay);
		
		for(Card c : cardsInPlay) {
			winning_player.addToDeck(c);
		}
		
		cardsInPlay.clear();
		
		
		//then iterate through array removing all players with no cards left
		for(PlayerAbstract p_to_be_removed : players_to_remove) {
			
			System.out.println("Player "+p_to_be_removed.whoAmI()+" being removed from the game");
			players.remove(p_to_be_removed);
			
		}
		
		return nextPlayer(current_player);
		
	}
	
	private PlayerAbstract nextPlayer(PlayerAbstract current_player) {
		
		int current_player_index = players.indexOf(current_player);
		int next_player_index = current_player_index;
		
		next_player_index++;
		if(next_player_index >= players.size()) {
			next_player_index = 0;
		}
		
		if(!players.isEmpty()) {
			PlayerAbstract next_player = players.get(next_player_index);
			return next_player;
		}
		else {
			return null;
		}

	}
	
	private void topTrumpsGame() {
		
		//while each player still has cards left, have a round
		
		dealOutDeck();
		PlayerAbstract current_player = decideWhoGoesFirst();
		
		int round_counter = 0;
		while(players.size() > 1) {
			System.out.println("Round "+round_counter);
			current_player = topTrumpsRound(current_player);
			round_counter ++;
		}		
	}
	
	public GameplayController(int model, CLIView view, int number_of_human_players, int number_of_ai_players) {
		
		players = new ArrayList<PlayerAbstract>();
		cardsInDeck = new ArrayList<Card>();
		cardsInPlay = new ArrayList<Card>();
		
		this.model = model;
		this.cli_view = view;
		
		createPlayers(number_of_human_players,number_of_ai_players);
		
		/*for(int i=0; i<4; i++) {
			
			String[] headers = {"Food","Tastiness","Speed of Consumption"};
			int[] input_values = {i,i,i};
			
			
			Card card = new Card("hello "+i,headers,input_values);
			cardsInDeck.add(card);
			
		}*/
		
		String[] headers = {"Food","Tastiness","Speed of Consumption"};
		
		int[] input_values = {0,0,0};
		Card card = new Card("hello 0",headers,input_values);
		cardsInDeck.add(card);
		
		int[] input_values_2 = {1,1,1};
		card = new Card("hello 0",headers,input_values_2);
		cardsInDeck.add(card);
		
		
	}
	
	public static void main(String[] args) {
		
		CLIView placeholder_view = new CLIView();
		
		GameplayController game = new GameplayController(0,placeholder_view,1,2);
		
		game.topTrumpsGame();
		
	}

}
