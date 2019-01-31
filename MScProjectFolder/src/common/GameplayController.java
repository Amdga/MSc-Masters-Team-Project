package common;
import commandline.*;
import logger.PersistentGameData;
import logger.TestLogger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class GameplayController {
	
	private ArrayList<Card> cardsInDeck;
	
	private ArrayList<Card> cardsInDrawPile;
	private ArrayList<Card> cardsInPlay;
	
	//players = a list of every player in the game (including those who have lost)
	//players_in_game = a list of players who currently have cards left (are still in the game)
	private ArrayList<PlayerAbstract> players;
	private ArrayList<PlayerAbstract> players_in_game;
	
	private CLIView cli_view;
	private GetDeckModel model;
	
	private PersistentGameData persistent_game_data;
	
	private TestLogger test_logger;
	
	private boolean quit_game = false;
	
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
			//The modulus of the number of players in the game gives an index between 0 and the number of players
			//This number is the index of the player the card is to be given to
			int player_to_give_card_to = card_counter % players.size();
			
			players.get(player_to_give_card_to).addToDeck(c);
			card_counter ++;
		}

		//Log the initial state of each players deck
		for(PlayerAbstract player : players) {
			test_logger.logPlayerInitialDeck(player.whoAmI(), player.amIHuman(), player.getCurrentDeck());
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
		
		//Create the human players and add them to the players_in_game list and the players list
		for(int i=0; i<number_of_humans; i++) {
			PlayerAbstract human_player = new HumanPlayer(player_counter,cli_view);
			players.add(human_player);
			players_in_game.add(human_player);
			player_counter++;
		}
		
		//Create the AI players adn add tehm to the players_in_game list and the players list
		for(int i=0; i<number_of_ai; i++) {
			PlayerAbstract ai_player = new AIPlayer(player_counter);
			players.add(ai_player);
			players_in_game.add(ai_player);
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
		
		//variable to store the winner of the game
		PlayerAbstract winning_player;
		
		//Log the current player on the CLI
		cli_view.currentPlayer(current_player.whoAmI());
		
		//Get the index of the human player (human player is always stored at index 0 of the players list
		// - we have decided this as a standard in our code)
		int human_player_index = players_in_game.indexOf(players.get(0));
		
		//Find the index of the human player in the players_in_game arraylist
		//and show them their card if they are still part of the game
		if(human_player_index != -1 ) {
			Card top_card = players_in_game.get(human_player_index).lookAtTopCard();
			cli_view.showTopCard(top_card);
		}

		//Get the category from the current player and send this to the CLI
		String category = current_player.decideOnCategory();
		
		if(category.equals("quit")) {
			quit_game = true;
			return null;
		}
		
		cli_view.showCategory(category);
		
		ArrayList<PlayerPlays> player_plays_list = new ArrayList<PlayerPlays>(); //An arraylist of objects storing players and their played cards
		
		for(PlayerAbstract p : players_in_game) {
			//now take the top cards off all the players
			
			p.lookAtTopCard();
			Card players_card = p.takeTopCard();
			
			//If their card exists (it should always exist but it is just a check)
			if(players_card != null) {
				//Print the value of the players card to the CLI
				int current_value = players_card.getValue(category);
				cli_view.playerHasValue(p.whoAmI(), current_value);
				
				//add their card to the cards in play
				cardsInPlay.add(players_card);
				
				//Create a new PlayerPlays object to keep track of who played what card
				PlayerPlays player_plays = new PlayerPlays(p,players_card,category);
				player_plays_list.add(player_plays);
			}
		}
		
		//Send relevant information to logger
		test_logger.logActiveCards(cardsInPlay);
		test_logger.logCategory(current_player.whoAmI(), category, player_plays_list);		
		
		ArrayList<PlayerAbstract> winning_players = new ArrayList<PlayerAbstract>(); //List of players who have the maximum value of a card
		ArrayList<Card> winning_cards_pile = new ArrayList<Card>(); //List of winning cards (to be moved into the draw pile if > 1)
		
		Collections.sort(player_plays_list, Collections.reverseOrder()); //Sort the cards in play by players from highest to lowest
		int winning_value = Collections.max(player_plays_list).getCategoryValue(); 
		
		int current_value = winning_value;
		int i=0;
		
		while(winning_value == current_value && (i<player_plays_list.size())) {
			//Loop which determines if there is a draw
			
			//While the next value in the list is equal to the first value (while there is multiple "winning" values
			//add the card to the winning players list and the winning cards list
			winning_players.add(player_plays_list.get(i).getPlayer());
			winning_cards_pile.add(player_plays_list.get(i).getCard());
			
			i++;
			if(i<player_plays_list.size()) {
				current_value = player_plays_list.get(i).getCategoryValue();
			}
		}
			
		if(winning_cards_pile.size() == 1) {
			//If there isn't a draw
			
			//Get and log the winning player
			winning_player = winning_players.get(0);
			persistent_game_data.log_player_won_rounds(winning_player.whoAmI());
			cli_view.theWinnerIs(winning_player.whoAmI());
			
			//Shuffle the cards in play and teh cards in the draw pile
			Collections.shuffle(cardsInPlay);
			Collections.shuffle(cardsInDrawPile);
			
			//Add the cards currently in play to the winning players deck
			for(Card c : cardsInPlay) {
				winning_player.addToDeck(c);
			}
			
			//Add the cards in the draw pile to the winning players deck
			//(this list is only populated if the previous round resulted in a draw)
			for(Card c : cardsInDrawPile) {
				winning_player.addToDeck(c);
			}
			
			//Clear the cards in the draw pile (as they have just been handed out) and log this
			cardsInDrawPile.clear();
			test_logger.logCommunalPile(cardsInDrawPile);
			
		}
		else {
			//If there is a draw
			
			//Add all cards in play to the draw pile
			for(Card c : cardsInPlay) {
				cardsInDrawPile.add(c);
			}
			
			//Log that there is a draw in all the appropriate places
			persistent_game_data.increment_number_of_draws();
			test_logger.logCommunalPile(cardsInDrawPile);
			cli_view.itsADraw();
			
			removeLosingPlayers();
			cardsInPlay.clear();
			
			//The current player gets to play again if it is a draw, so return the current player
			return current_player;
			
		}
			
		removeLosingPlayers();
		cardsInPlay.clear();
		
		//Return the next player whos turn it is
		return winning_player;
		
	}
	
	/**
	 * Method which iterates through the list of players in the game and removes all
	 * of those with 0 cards left
	 */
	
	private void removeLosingPlayers() {
		
		//Iterate through the arrayList of players in the game, removing those with 0 cards left
		Iterator<PlayerAbstract> it = players_in_game.iterator();
		while(it.hasNext()) {
			
			PlayerAbstract player = it.next();
			if(player.getNumberofCardsLeft() == 0) {
				//Tell the CLI that a player has lost
				cli_view.playerLoses(player.whoAmI());
				it.remove();
			}
			
		}
		
		//If there is only one player left, give all cards in the draw pile to the winning player
		//this is for completeness and results in the winning player ending up with the complete deck
		if(players_in_game.size() == 1) {
			for(Card c : cardsInDrawPile) {
				players_in_game.get(0).addToDeck(c);
			}
		}
		
	}
	
	/**
	 * This is the actual top trumps game, and repeats rounds while there is still players left
	 * 
	 */
	public void topTrumpsGame() {
		
		dealOutDeck();
		PlayerAbstract current_player = decideWhoGoesFirst();
		
		//While there is still players left, have a round
		int round_counter = 1;
		while(players_in_game.size() > 1 && quit_game == false) {
			persistent_game_data.increment_rounds();
			test_logger.logNewRound(round_counter);
			cli_view.beginningOfRound(players.get(0).getCurrentDeck().size(), round_counter);
			current_player = topTrumpsRound(current_player);
			round_counter ++;
			
			for(PlayerAbstract player : players) {
				test_logger.logPlayerDeck(player.whoAmI(), player.amIHuman(), player.getCurrentDeck());
			}
			
		}
		
		if(quit_game == false) {
			try {
				int winning_player = players_in_game.get(0).whoAmI();
				
				persistent_game_data.log_player_who_won(winning_player);
				cli_view.overallWinner(winning_player);
				test_logger.logWinningPlayer(winning_player);
			}
			catch (IndexOutOfBoundsException e) {
				cli_view.noWinner();
			}
		} else {
			persistent_game_data.set_logger(false);
		}
			
		//We don't need this in here but it's here until Database is set up
		/*System.out.println("GAME DATA");
		System.out.println("Number of rounds = "+persistent_game_data.get_number_of_rounds());
		System.out.println("Number of draws = "+persistent_game_data.get_number_of_draws());
		System.out.println("Player who won = "+persistent_game_data.get_winning_player());
		
		int[] player_wins = persistent_game_data.get_player_wins();
		for(int i=0; i<player_wins.length; i++) {
			System.out.println("Player "+i+" won "+player_wins[i]+" games");
		}*/

	}
	
	/**
	 * Method which gets the deck from the model and stores it in
	 * the cardsInDeck arrayList
	 * Also logs the deck before and after shuffling
	 */
	
	public void getDeck() {
		cardsInDeck.addAll(model.getShuffledDeck());
		
		test_logger.logDeckCreation(model.getDeck());
		test_logger.logDeckShuffle(cardsInDeck);
	}
	
	/**
	 * PersistentGameData is used to store game data as the game progresses
	 * 
	 * @return the PersistentGameData object for this game
	 */
	
	public PersistentGameData get_game_data() {
		return persistent_game_data;
	}
	
	/**
	 * Constructor of GameplayController, initialises variables and creates players
	 * 
	 * @param model
	 * @param view
	 * @param number_of_human_players
	 * @param number_of_ai_players
	 * @param log_data - whether the game data is to be logged or not
	 */
	public GameplayController(GetDeckModel model, CLIView view, int number_of_human_players, int number_of_ai_players, boolean log_data) {
		
		players = new ArrayList<PlayerAbstract>();
		players_in_game = new ArrayList<PlayerAbstract>();
		cardsInDeck = new ArrayList<Card>();
		cardsInPlay = new ArrayList<Card>();
		cardsInDrawPile = new ArrayList<Card>();
		
		this.model = model;
		this.cli_view = view;
		
		test_logger = new TestLogger(log_data);
		
		createPlayers(number_of_human_players,number_of_ai_players);
		
		this.persistent_game_data = PersistentGameData.getInstance(number_of_human_players+number_of_ai_players);
		
		getDeck();
		
	}
	
	/*
	 * Testing note to self:
	 * 
	 * Test database, main gameplay logic, at least 5 major test cases
	 * 
	 */

	
	/**
	 * Temporary main to run the test program
	 * 
	 * @param args
	 */
	/*public static void main(String[] args) {
		
		CLIView placeholder_view = new CLIView();
		GetDeckModel placeholder_model = new GetDeckModel();
		
		GameplayController game = new GameplayController(placeholder_model,placeholder_view,1,2, false);
		
		//game.topTrumpsGame();
		
		Database db = new Database();
		
	}*/

}
