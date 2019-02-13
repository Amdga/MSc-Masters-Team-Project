package online;

import java.util.ArrayList;

import commandline.GameplayControllerCLI;
import common.GameplayControllerAbstract;
import common.GetDeckModel;
import common.PlayerPlays;
import players.PlayerAbstract;

/**
 * Online version of the gameplay controller, which has the game progress in states rather than in a game loop.
 * States can be manipulated using the methods of this object. Therefore requires external action to progress
 * the game.
 * 
 * @author Adrian Borg
 * @version 1
 */
public class OnlineGameplayController extends GameplayControllerAbstract{
/*	private ArrayList<Card> cardsInDeck;
//
//	private ArrayList<Card> cardsInDrawPile;
//	private ArrayList<Card> cardsInPlay;
//	private ArrayList<PlayerAbstract> players; //a list of every player in the game (including those who have lost)
//	private ArrayList<PlayerAbstract> players_in_game; //a list of players who currently have cards left (are still in the game)
//
//	private ViewInteface to_view;
//	private GetDeckModel model;
//
//	private PersistentGameData persistent_game_data;
//
//	private TestLogger test_logger;
//
//	private boolean quit_game = false; */

	// ~~~~~~~~ Online mode variable initialisation
	private OnlineDataBuffer to_view;
	private static String [] STATES = {"quit", "pre-initialisation", "initialised", "choosing category", 
									   "showing results", "round ended", "overall winner"};
	public static final String OVERALL_WINNER = STATES[6];
	private static final String STATE_ERROR = "state error";
	private String state = STATES[1]; //Holds the current state of the game
	private PlayerAbstract current_player;
	private int round_counter;
	private String category;
	
	/**
	 * Constructor for the online gameplay controller, initialises variables, gets deck and creates players
	 * 
	 * @param model	- deck model
	 * @param view - OnlineDataBuffer, used to hold data waiting till requests come in from client
	 * @param number_of_human_players - number of human players in the game; 0 or 1
	 * @param number_of_ai_players - number of ai players in the game; 1 - 4
	 */
	public OnlineGameplayController(GetDeckModel model, OnlineDataBuffer view, int number_of_human_players, int number_of_ai_players) {
		super(model, view, number_of_human_players, number_of_ai_players, false);
		this.to_view = view;
		to_view.setOGC(this);
	}
	
	/**
	 * Used to initialise a game and bring the game to its first state. Once completed a round
	 * is started and depending on whether a human has the first turn, the resulting state could 
	 * either be 'choosing category' or 'round ended'.
	 * 
	 * Clears the buffer once data has been retrieved.
	 * 
	 * @return JSON format of the data buffer object at the state the game has reached
	 * 		   null if in the wrong state
	 */
	public String initialiseGame() {
		if (state.equals(STATES[1])) {
			state = STATES[2];
			
			dealOutDeck();
			current_player = decideWhoGoesFirst();
			round_counter = 0;
			persistent_game_data.increment_rounds();

			startARound(false);

			return this.to_view.toJSON();
		}
		else {
			return STATE_ERROR;
		}
	}
	
	/**
	 * Used to start a game round from the 'round ended' state. depending on whether it is a human turn, 
	 * the resulting state could either be 'choosing category' or 'round ended'.
	 * 
	 * This method clears the data buffer after retrieving the data
	 * 
	 * @return JSON format of the data buffer object
	 * 		   null if in the wrong state
	 */
	public String startARound() {
		return startARound(true);
	}
	
	/**
	 * Used to start a game round from the 'round ended' state. depending on whether it is a human turn, 
	 * the resulting state could either be 'choosing category' or 'round ended'.
	 * 
	 * @param willReset - true: clears the buffer once data is retrieved
	 * 					- false: does not clear the buffer
	 * @return JSON format of the data buffer object
	 *		   null if in the wrong state
	 */
	public String startARound(boolean willReset) {
		if(state.equals(STATES[2]) || state.equals(STATES[5])) {
			this.round_counter++;
			state = STATES[3];
			to_view.beginningOfRound(players.get(0).getCurrentDeck().size(), round_counter); // saves deck size and round num
			to_view.currentPlayer(current_player.whoAmI()); // saves active player
			roundStartForHuman(); // saves human card info if human is still in game

			if (current_player.amIHuman()) { // if active player is human
				return this.to_view.toJSON(willReset);
			}
			// else:
			state = STATES[4];
			category = current_player.decideOnCategory();
			to_view.showCategory(category);
			
			showRoundResults(false);

			return this.to_view.toJSON(willReset);
		}
		else {
			return STATE_ERROR;
		}
	}

	/**
	 * Used to set a user category choice and get out of the 'choosing category' state. Once complete, the game
	 * will be in the 'round ended' state.
	 * 
	 * Clears the buffer once data is retrieved
	 * 
	 * @param category - user category choice
	 * @return JSON format of the data buffer object
	 * 		   null if in the wrong state
	 */
	public String chosenCategory(String category) {
		if(state.equals(STATES[3])) {
			state = STATES[4];
			this.category = category;
			to_view.showCategory(category);
			showRoundResults(false);
			return this.to_view.toJSON();
		}
		else {
			return STATE_ERROR;
		}

	}
	
//	public String showRoundResults() {
//		return showRoundResults(true);
//	}
	
	/**
	 * Used to move the game from the 'showing results' state to the 'round ended' state. 
	 * 
	 * @param willReset - true: clears the buffer once data is retrieved
	 * 					- false: does not clear the buffer
	 * @return JSON format of the data buffer object
	 * 		   null if in the wrong state
	 */
	public String showRoundResults(boolean willReset) {
		if(state.equals(STATES[4])) {
			state = STATES[5];
			
			// saves category values for each player
			ArrayList<PlayerPlays> player_plays_list = playersPlayCards(category, current_player.whoAmI());
			
			//saves if it was a draw or there was a winner
			//saves removal of losing players
			current_player = roundResolution(current_player, player_plays_list);
			
			//save the 
			to_view.showPlayerDeckSizes(players_in_game);
			to_view.showPlayerCardNames(player_plays_list);
			
			//save size of communal pile
			to_view.showCommunalPileSize(cardsInDrawPile.size());
			
			//if we have an overall winner
			if(players_in_game.size() == 1) {
				state = STATES[6];
				persistent_game_data.log_player_who_won(players_in_game.get(0).whoAmI());
				to_view.overallWinner(players_in_game.get(0).whoAmI()); //save overall winner
			}
			
			return this.to_view.toJSON(willReset);
		}
		else {
			return STATE_ERROR;
		}
	}
	
	/**
	 * Used to set the state of the game to 'quit', and to properly quit the game.
	 * @return JSON format of the data buffer object
	 */
	public String quit() {
		state = STATES[0];
		to_view.quitGame();
		persistent_game_data.set_logger(false);
		return this.to_view.toJSON();
	}
//	public boolean userWantsToQuit() {return false;}
//	public void topTrumpsGame() {}
	
	
	/**
	 * @return the current state of the game
	 */
	public String getState() {
		return state;
	}
	
}
