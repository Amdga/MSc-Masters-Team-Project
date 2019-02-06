package commandline;
import common.GameplayControllerAbstract;
import common.GetDeckModel;
import common.PlayerPlays;
import common.ViewInterface;
import players.PlayerAbstract;

import java.util.ArrayList;

public class GameplayControllerCLI extends GameplayControllerAbstract {
	
	/**
	 * Constructor of GameplayController, initialises variables and creates players
	 * 
	 * @param model
	 * @param view
	 * @param number_of_human_players
	 * @param number_of_ai_players
	 * @param log_data - whether the game data is to be logged or not
	 */
	public GameplayControllerCLI(GetDeckModel model, ViewInterface view, int number_of_human_players, int number_of_ai_players, boolean log_data) {

		super(model, view, number_of_human_players, number_of_ai_players, log_data);

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
			super.to_view.beginningOfRound(players.get(0).getCurrentDeck().size(), round_counter);
			current_player = topTrumpsRound(current_player);
			round_counter ++;

			for(PlayerAbstract player : players) {
				test_logger.logPlayerDeck(player.whoAmI(), player.amIHuman(), player.getCurrentDeck());
			}
		}

		if(quit_game == false) {
			//			try {
			int winning_player = players_in_game.get(0).whoAmI();
			super.winning_player = players_in_game.get(0);

			persistent_game_data.log_player_who_won(winning_player);
			to_view.overallWinner(winning_player);
			test_logger.logWinningPlayer(winning_player);
			//			}
			//			catch (IndexOutOfBoundsException e) { // cannot have no players in a game
			//				to_view.noWinner();
			//			}
		} else {
			persistent_game_data.set_logger(false);
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
	public PlayerAbstract topTrumpsRound(PlayerAbstract current_player) {

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
		next_active_player = roundResolution(current_player, player_plays_list);

		return next_active_player;
	}
	
	protected boolean userWantsToQuit() {
		
		if(super.current_category.equals("quit")) {
			return true;
		}
		else {
			return false;
		}
		
	}

}