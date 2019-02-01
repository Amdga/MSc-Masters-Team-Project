package online;
/*********
 * 
 * 
 * Need to remove all cli_view references and replace with whatever will
 * prepare the String/JSON outputs to send to the client
 * 
 * 
 * 
 */
import java.util.ArrayList;

import commandline.CLIView;
import common.Card;
import common.GameplayController;
import common.GetDeckModel;
import common.PlayerAbstract;
import common.PlayerPlays;
import logger.PersistentGameData;
import logger.TestLogger;

public class OnlineGameplayController extends GameplayController{
	private ArrayList<Card> cardsInDeck;

	private ArrayList<Card> cardsInDrawPile;
	private ArrayList<Card> cardsInPlay;
	private ArrayList<PlayerAbstract> players; //a list of every player in the game (including those who have lost)
	private ArrayList<PlayerAbstract> players_in_game; //a list of players who currently have cards left (are still in the game)

	private CLIView cli_view;
	private GetDeckModel model;

	private PersistentGameData persistent_game_data;

	private TestLogger test_logger;

	private boolean quit_game = false;

	// online mode variable initialisation
	private static String [] STATES = {"quit", "pre-initialisation", "initialised", "choosing category", 
									   "showing results", "round ended", "overall winner"};
	private String state = STATES[1]; //Holds the current state of the game
	private PlayerAbstract current_player;
	private int round_counter;
	private String category;

	public OnlineGameplayController(GetDeckModel model, CLIView view, int number_of_human_players, int number_of_ai_players) {
		super(model, view, number_of_human_players, number_of_ai_players, false);
	}

	public String initialiseGame() {
		if (state.equals(STATES[1])) {
			dealOutDeck();
			current_player = decideWhoGoesFirst();
			round_counter = 1;
			persistent_game_data.increment_rounds();

			state = STATES[2];

			String round_init_output = startARound();


			return info_for_game_initialisation();
		}
		else {
			return null;
		}
	}

	public String startARound() {
		if(state.equals(STATES[2]) || state.equals(STATES[5])) {
			cli_view.beginningOfRound(players.get(0).getCurrentDeck().size(), round_counter); // saves deck size and round num
			cli_view.currentPlayer(current_player.whoAmI()); // saves active player
			roundStartForHuman(); // saves human card info if human is still in game

			if (current_player.amIHuman()) { // if active player is human
				state = STATES[3];
				return info_at_choosing_state;
			}
			// else:
			category = current_player.decideOnCategory();
			cli_view.showCategory(category);
			state = STATES[4];
			String rest_of_round_output = showRoundResults();

			return info_from_the_first_half_of_round + rest_of_rouns_output;
		}
		else {
			return null;
		}
	}

	public String chosenCategory(String category) {
		if(state.equals(STATES[3])) {
			this.category = category;
			cli_view.showCategory(category);
			state = STATES[4];
			String rest_of_round_output = showRoundResults();
			return info_from_category_selection + rest_of_round_info;
		}
		else {
			return null;
		}

	}

	public String showRoundResults() {
		if(state.equals(STATES[4])) {
			// saves category values for each player
			ArrayList<PlayerPlays> player_plays_list = playersPlayCards(category, current_player.whoAmI());
			
			//saves if it was a draw or there was a winner
			//saves removal of losing players
			current_player = roundResolution(current_player, player_plays_list);
			
			//save size of communal pile
			cli_view.showCommunalPileSize(cardsInDrawPile.size());
			
			state = STATES[5];
			
			//if we have an overall winner
			if(players_in_game.size() == 1) {
				state = STATES[6];
				persistent_game_data.log_player_who_won(players_in_game.get(0).whoAmI());
				cli_view.overallWinner(players_in_game.get(0).whoAmI()); //save overall winner
			}
			
			return info_saved_in_this_method;
		}
		else {
			return null;
		}
	}
	
	public String quit() {
		state = STATES[0];
		cli_view.quitGame();
		persistent_game_data.set_logger(false);
		return quit_game_info;
	}
}
