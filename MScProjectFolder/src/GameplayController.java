import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

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
			
			//System.out.println("Player "+player_to_give_card_to+" gets "+c.getCardName());
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
			//System.out.println("Adding human player "+i);
			players.add(new HumanPlayer(player_counter,cli_view));
			player_counter++;
		}
		
		for(int i=0; i<number_of_ai; i++) {
			//System.out.println("Adding AI player "+i);
			players.add(new AIPlayer(player_counter));
			player_counter++;
		}
		
	}
	
	private PlayerAbstract topTrumpsRound(PlayerAbstract current_player) {
		
		current_player.lookAtTopCard();
		//String category = current_player.decideOnCategory();
		
		String category = "Food"; //This will be deleted once I know everything else is working!
		
		//PlayerAbstract winning_player = players.get(0);
		//int winning_value = Integer.MIN_VALUE;
		
		ArrayList<PlayerAbstract> players_to_remove = new ArrayList<PlayerAbstract>();
		ArrayList<PlayerPlays> player_plays_list = new ArrayList<PlayerPlays>();
		
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
				int current_value = players_card.getValue("Food");
				System.out.println("And their card has value "+current_value);
				
				/*if(current_value > winning_value) {
					winning_value = current_value;
					winning_player = p;
				}*/
				
				cardsInPlay.add(players_card);
				
				//System.out.println(players_card.getCardName());
				//System.out.println(players_card.getValue(category));
				
				PlayerPlays player_plays = new PlayerPlays(p,players_card,category);
				player_plays_list.add(player_plays);
			}
		}
		
		Collections.sort(player_plays_list, Collections.reverseOrder());
		
		for(PlayerPlays p : player_plays_list) {
			
			System.out.println(p.toString());
			
		}
		
		ArrayList<PlayerAbstract> winning_players = new ArrayList<PlayerAbstract>();
		ArrayList<Card> winning_cards_pile = new ArrayList<Card>();
		
		int winning_value = player_plays_list.get(0).getCategoryValue();
		//winning_players.add(player_plays_list.get(0).getPlayer());
		
		int current_value = winning_value;
		int i=0;
		
		while(winning_value == current_value && (i<player_plays_list.size())) {

			winning_players.add(player_plays_list.get(i).getPlayer());
			winning_cards_pile.add(player_plays_list.get(i).getCard());
			System.out.println("Player "+player_plays_list.get(i).getPlayer().playerNumber+" has been added to winning players list");
			
			i++;
			if(i<player_plays_list.size()) {
				current_value = player_plays_list.get(i).getCategoryValue();
			}
		}
		
		//then iterate through array removing all players with no cards left
		for(PlayerAbstract p_to_be_removed : players_to_remove) {
			
			System.out.println("Player "+p_to_be_removed.whoAmI()+" being removed from the game");
			players.remove(p_to_be_removed);
			
		}
		
		if(winning_cards_pile.size() == 1) {
			
			System.out.println("Just to check, when there is one winner, winning_player size = "+winning_players.size());
			PlayerAbstract winning_player = winning_players.get(0);
			
			System.out.println("Winning player = "+winning_player.whoAmI());
			Collections.shuffle(cardsInPlay);
			Collections.shuffle(cardsInDrawPile);
			
			for(Card c : cardsInPlay) {
				winning_player.addToDeck(c);
			}
			
			for(Card c : cardsInDrawPile) {
				winning_player.addToDeck(c);
			}
			
			cardsInDrawPile.clear();
			
		}
		else {
			
			for(Card c : winning_cards_pile) {
				cardsInDrawPile.add(c);
			}
			System.out.println("No winning player, round is a draw");
			
			cardsInPlay.clear();
			return current_player;
			
		}
		
		cardsInPlay.clear();
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
		cardsInDrawPile = new ArrayList<Card>();
		
		this.model = model;
		this.cli_view = view;
		
		createPlayers(number_of_human_players,number_of_ai_players);
		
		for(int i=0; i<10; i++) {
			
			Random r = new Random();
			
			String[] headers = {"","Food","Tastiness","Speed of Consumption"};
			int[] input_values = {r.nextInt(10),r.nextInt(10),r.nextInt(10),r.nextInt(10)};
			
			Card card = new Card("hello "+i,headers,input_values);
			cardsInDeck.add(card);
			
		}
		
	}
	
	public static void main(String[] args) {
		
		CLIView placeholder_view = new CLIView();
		
		GameplayController game = new GameplayController(0,placeholder_view,1,2);
		
		game.topTrumpsGame();
		
	}

}
