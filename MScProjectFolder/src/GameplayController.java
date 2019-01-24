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
	
	/**
	 * Method that randomly decides which player is to go first
	 * @return is a player who has been selected to go first
	 */
	private PlayerAbstract decideWhoGoesFirst() {
		
		int number_of_players = players.size();
		int player_who_goes_first = (int) (Math.random() * number_of_players);
		
		PlayerAbstract player = players.get(player_who_goes_first);
		
		return player;
	}
	
	/**
	 * Takes the deck and deals it out one by one to each player in the game
	 */
	private void dealOutDeck() {
		
		int card_counter = 0;
		for(Card c : cardsInDeck) {
			
			int player_to_give_card_to = card_counter % players.size();
			
			System.out.println("Player "+player_to_give_card_to+" gets "+c.getValue("Food"));
			players.get(player_to_give_card_to).addToDeck(c);
			card_counter ++;
			
		}
		
	}
	
	/**
	 * Method which initialises the players and adds them to the player list
	 * 
	 * @param number of human players
	 * @param number of AI players
	 */
	private void createPlayers(int number_of_humans, int number_of_ai) {
		
		int player_counter = 0;
		
		for(int i=0; i<number_of_humans; i++) {
			players.add(new HumanPlayer(player_counter,cli_view));
			player_counter++;
		}
		
		for(int i=0; i<number_of_ai; i++) {
			players.add(new AIPlayer(player_counter));
			player_counter++;
		}
		
	}
	
	/**
	 * A single round of top trumps, from getting a selected category, determining who wins,
	 * passes cards to players as required and removes players who are out of cards
	 * 
	 * @param current_player: the player whos turn it is
	 * @return next_player: the player whos turn it is next
	 * 						this is the next player in the list if there is a winner,
	 * 						or the player who has just been if there is a draw
	 */
	
	private PlayerAbstract topTrumpsRound(PlayerAbstract current_player) {
		
		current_player.lookAtTopCard();
		//String category = current_player.decideOnCategory();
		
		String category = "Food"; //This will be deleted once I know everything else is working!
		
		ArrayList<PlayerAbstract> players_to_remove = new ArrayList<PlayerAbstract>(); //An arraylist of players with no cards left
		ArrayList<PlayerPlays> player_plays_list = new ArrayList<PlayerPlays>(); //An arraylist of objects storing players and their played cards
		
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
				int current_value = players_card.getValue(category);
				System.out.println("And their card has value "+current_value);
				
				cardsInPlay.add(players_card);
				
				PlayerPlays player_plays = new PlayerPlays(p,players_card,category);
				player_plays_list.add(player_plays);
			}
		}
		
		ArrayList<PlayerAbstract> winning_players = new ArrayList<PlayerAbstract>(); //List of players who have the maximum value of a card
		ArrayList<Card> winning_cards_pile = new ArrayList<Card>(); //List of winning cards (to be moved into the draw pile if > 1)
		
		Collections.sort(player_plays_list, Collections.reverseOrder()); //Sort the cards in play by players from highest to lowest
		int winning_value = Collections.max(player_plays_list).getCategoryValue(); 
		System.out.println("winning value = "+winning_value);
		
		int current_value = winning_value;
		int i=0;
		
		while(winning_value == current_value && (i<player_plays_list.size())) {
			//Loop which determines if there is a draw
			
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
			//If there isn't a draw
			
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
	
	/**
	 * Method used to determine whos turn it is next to play
	 * 
	 * @param current_player: the player who went last
	 * @return next_player: the player whos turn it is next
	 */
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
	
	/**
	 * This is the actual top trumps game, and repeats rounds while there is still players left
	 * 
	 */
	private void topTrumpsGame() {
		
		dealOutDeck();
		PlayerAbstract current_player = decideWhoGoesFirst();
		
		//While there is still players left, have a round
		int round_counter = 0;
		while(players.size() > 1) {
			System.out.println("Round "+round_counter);
			current_player = topTrumpsRound(current_player);
			round_counter ++;
		}		
	}
	
	/**
	 * Constructor of GameplayController, initialises variables and creates players
	 * 
	 * @param model
	 * @param view
	 * @param number_of_human_players
	 * @param number_of_ai_players
	 */
	public GameplayController(int model, CLIView view, int number_of_human_players, int number_of_ai_players) {
		
		players = new ArrayList<PlayerAbstract>();
		cardsInDeck = new ArrayList<Card>();
		cardsInPlay = new ArrayList<Card>();
		cardsInDrawPile = new ArrayList<Card>();
		
		this.model = model;
		this.cli_view = view;
		
		createPlayers(number_of_human_players,number_of_ai_players);
		
		//THIS WHOLE BLOCK WILL BE REMOVED WHEN THE MODEL IS INTEGRATED
		for(int i=0; i<10; i++) {
			
			Random r = new Random();
			
			String[] headers = {"","Food","Tastiness","Speed of Consumption"};
			int[] input_values = {r.nextInt(10),r.nextInt(10),r.nextInt(10),r.nextInt(10)};
			
			Card card = new Card("hello "+i,headers,input_values);
			cardsInDeck.add(card);
			
		}
		
	}
	
	/**
	 * Temporary main to run the test program
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		CLIView placeholder_view = new CLIView();
		
		GameplayController game = new GameplayController(0,placeholder_view,1,2);
		
		game.topTrumpsGame();
		
	}

}
