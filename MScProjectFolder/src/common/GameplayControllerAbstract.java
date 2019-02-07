package common;
import commandline.*;
import logger.PersistentGameData;
import logger.TestLogger;
import players.AIPlayer;
import players.HumanPlayer;
import players.PlayerAbstract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public abstract class GameplayControllerAbstract {

	protected ArrayList<Card> cardsInDeck;
	protected ArrayList<Card> cardsInDrawPile;
	protected ArrayList<Card> cardsInPlay;

	//players = a list of every player in the game (including those who have lost)
	//players_in_game = a list of players who currently have cards left (are still in the game)
	protected ArrayList<PlayerAbstract> players;
	protected ArrayList<PlayerAbstract> players_in_game;
	protected PlayerAbstract winning_player;

	protected ViewInterface to_view;
	protected GetDeckModel model;

	protected PersistentGameData persistent_game_data;

	protected String current_category;
	
	protected TestLogger test_logger;

	protected boolean quit_game = false;
	
	public GameplayControllerAbstract(GetDeckModel model, ViewInterface view, int number_of_human_players, int number_of_ai_players, boolean log_data) {
		
		players = new ArrayList<PlayerAbstract>();
		players_in_game = new ArrayList<PlayerAbstract>();
		cardsInDeck = new ArrayList<Card>();
		cardsInPlay = new ArrayList<Card>();
		cardsInDrawPile = new ArrayList<Card>();

		this.model = model;
		this.to_view = view;

		test_logger = new TestLogger(log_data);

		players = createPlayers(number_of_human_players,number_of_ai_players);

		this.persistent_game_data = new PersistentGameData(number_of_ai_players+number_of_human_players);
		getDeck();
		
	}

	/**
	 * This is the actual top trumps game, and repeats rounds while there is still players left
	 * 
	 */
	protected abstract void topTrumpsGame();

	protected abstract boolean userWantsToQuit();
	
	/**
	 * A single round of top trumps, from getting a selected category, determining who wins,
	 * passes cards to players as required and removes players who are out of cards
	 * 
	 * @param current_player: the player whos turn it is
	 * @return next_player: the player whos turn it is next
	 * 						this is the next player in the list if there is a winner,
	 * 						or the player who has just been if there is a draw
	 */

	public PlayerAbstract topTrumpsRound(PlayerAbstract current_player, ArrayList<Card> cards_in_play) {

		//variable to store the winner of the game
		PlayerAbstract next_active_player;

		//Log the current player on the CLI
		to_view.currentPlayer(current_player.whoAmI());

		//Show User their card if still in game
		roundStartForHuman();

		//Get the category from the current player and send this to the CLI
		current_category = current_player.decideOnCategory();

		if(userWantsToQuit() == true) {
			to_view.quitGame();
			quit_game = true;
			return null;
		}

		to_view.showCategory(current_category);

		//An arraylist of objects storing players and their played cards
		ArrayList<PlayerPlays> player_plays_list = playersPlayCards(current_category, current_player.whoAmI()); 

		//Do round resolution and get next active player
		next_active_player = roundResolution(current_player, player_plays_list,cards_in_play);

		return next_active_player;
	}

	protected void roundStartForHuman() {
		//Get the index of the human player (human player is always stored at index 0 of the players list
		// - we have decided this as a standard in our code)
		int human_player_index = players_in_game.indexOf(players.get(0));

		//Find the index of the human player in the players_in_game arraylist
		//and show them their card if they are still part of the game
		if(human_player_index != -1 ) {
			Card top_card = players_in_game.get(human_player_index).lookAtTopCard();
			to_view.showTopCard(top_card);
		}
	}

	protected ArrayList<PlayerPlays> playersPlayCards(String category, int active_player_number) {

		ArrayList<PlayerPlays> player_plays_list = new ArrayList<PlayerPlays>(); //An arraylist of objects storing players and their played cards

		for(PlayerAbstract p : players_in_game) {
			//now take the top cards off all the players

			p.lookAtTopCard();
			Card players_card = p.takeTopCard();

			//If their card exists (it should always exist but it is just a check)
			if(players_card != null) {
				//Print the value of the players card to the CLI
				int current_value = players_card.getValue(category);
				to_view.playerHasValue(p.whoAmI(), current_value);

				//add their card to the cards in play
				cardsInPlay.add(players_card);

				//Create a new PlayerPlays object to keep track of who played what card
				PlayerPlays player_plays = new PlayerPlays(p,players_card,category);
				player_plays_list.add(player_plays);
			}
		}

		//Send relevant information to logger
		test_logger.logActiveCards(cardsInPlay);
		test_logger.logCategory(active_player_number, category, player_plays_list);

		return player_plays_list;
	}

	public PlayerAbstract roundResolution(PlayerAbstract current_player, ArrayList<PlayerPlays> player_plays_list, ArrayList<Card> cards_in_play) {

		PlayerAbstract next_active_player;

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
			next_active_player = weHaveAWinner(winning_players.get(0),cards_in_play);
		}
		else {
			//If there is a draw

			weHaveADraw();
			next_active_player = current_player;

		}

		removeLosingPlayers();
		cardsInPlay.clear();

		//Return the next player whos turn it is
		return next_active_player;
	}

	public PlayerAbstract weHaveAWinner(PlayerAbstract winning_player, ArrayList<Card> cards_in_play) {

		persistent_game_data.log_player_won_rounds(winning_player.whoAmI());
		to_view.theWinnerIs(winning_player.whoAmI());

		//Shuffle the cards in play and the cards in the draw pile
		Collections.shuffle(cards_in_play);
		Collections.shuffle(cardsInDrawPile);

		//Add the cards currently in play to the winning players deck
		for(Card c : cards_in_play) {
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

		return winning_player;
	}

	private void weHaveADraw() {

		//Add all cards in play to the draw pile
		for(Card c : cardsInPlay) {
			cardsInDrawPile.add(c);
		}

		//Log that there is a draw in all the appropriate places
		persistent_game_data.increment_number_of_draws();
		test_logger.logCommunalPile(cardsInDrawPile);
		to_view.itsADraw();
		to_view.showCommunalPileSize(cardsInDrawPile.size());

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
				to_view.playerLoses(player.whoAmI());
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
	 * Method that randomly decides which player is to go first
	 * @return is a player who has been selected to go first
	 */
	protected PlayerAbstract decideWhoGoesFirst() {

		int number_of_players = players.size();
		int player_who_goes_first = (int) (Math.random() * number_of_players);

		PlayerAbstract player = players.get(player_who_goes_first);

		return player;
	}

	/**
	 * Takes the deck and deals it out one by one to each player in the game
	 */
	protected void dealOutDeck() {

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
	public	 ArrayList<PlayerAbstract> createPlayers(int number_of_humans, int number_of_ai) {

		ArrayList<PlayerAbstract> players = new ArrayList<PlayerAbstract>();
		int player_counter = 0;

		//Create the human players and add them to the players_in_game list and the players list
		for(int i=0; i<number_of_humans; i++) {
			PlayerAbstract human_player = new HumanPlayer(player_counter, to_view);
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

		return players;
		
	}
	
	public PersistentGameData getGameData() {
		return persistent_game_data;
	}
	
	//This is needed for testing to verify that the winning player has all the cards in the deck
	public PlayerAbstract getWinningPlayer() {
		return winning_player;
	}
	
	//This is also needed for testing, to verify that all other players have no cards
	public ArrayList<PlayerAbstract> getPlayerList() {
		return players;
	}
	
	public ArrayList<Card> getCardsInDrawPile() {
		return cardsInDrawPile;
	}
	
	public ArrayList<PlayerAbstract> getPlayersInGame() {
		return players_in_game;
	}
	
	public ArrayList<Card> getGameDeck() {
		return cardsInDeck;
	}

}
