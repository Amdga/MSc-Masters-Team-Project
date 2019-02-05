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

public class GameplayController extends GameplayControllerAbstract {
	
	/**
	 * Constructor of GameplayController, initialises variables and creates players
	 * 
	 * @param model
	 * @param view
	 * @param number_of_human_players
	 * @param number_of_ai_players
	 * @param log_data - whether the game data is to be logged or not
	 */
	public GameplayController(GetDeckModel model, ViewInterface view, int number_of_human_players, int number_of_ai_players, boolean log_data) {

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
			current_player = super.topTrumpsRound(current_player);
			round_counter ++;

			for(PlayerAbstract player : players) {
				test_logger.logPlayerDeck(player.whoAmI(), player.amIHuman(), player.getCurrentDeck());
			}
		}

		if(quit_game == false) {
			//			try {
			int winning_player = players_in_game.get(0).whoAmI();

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
	
	protected boolean userWantsToQuit() {
		
		if(super.current_category.equals("quit")) {
			return true;
		}
		else {
			return false;
		}
		
	}

}