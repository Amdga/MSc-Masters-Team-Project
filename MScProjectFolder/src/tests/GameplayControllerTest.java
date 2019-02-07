package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import commandline.CLIView;
import common.Card;
import common.GameplayControllerCLI;
import common.GetDeckModel;
import players.PlayerAbstract;

public class GameplayControllerTest {
	
//	@Test
//	void test_winning_player_all_cards() {
//		
//		GetDeckModel deck_model = new GetDeckModel();
//		CLIView cli_view = new CLIView();
//		
//		GameplayControllerCLI game = new GameplayControllerCLI(deck_model, cli_view, 0, 4, false);
//		
//		game.topTrumpsGame();
//		
//		PlayerAbstract winning_player = game.getWinningPlayer();
//		ArrayList<Card> expected_deck = deck_model.getDeck();
//		ArrayList<Card> actual_deck = winning_player.getCurrentDeck();
//		
//		for(Card c : expected_deck) {
//			assertTrue(actual_deck.contains(c),"Verifying player deck contains "+c.getCardName());
//		}
//		
//		assertEquals(expected_deck.size(), actual_deck.size(),"Verifying size of card");
//		
//		ArrayList<PlayerAbstract> players_in_game = game.getPlayerList();
//		for(PlayerAbstract p : players_in_game) {
//			
//			if(!p.equals(winning_player)) {
//				assertTrue(p.getCurrentDeck().size() == 0,"Testing all losing player "+p.whoAmI()+" has no cards left");
//			}
//			
//		}
//	
//	}
//	
//	@Test
//	void test_winner_gets_all_cards() {
//		
//		GetDeckModel deck_model = new GetDeckModel();
//		CLIView cli_view = new CLIView();
//		
//		GameplayControllerCLI game = new GameplayControllerCLI(deck_model, cli_view, 0, 3, false);
//		
//		ArrayList<Card> cards = create_mock_deck(game,false);
//		
//		ArrayList<PlayerAbstract> player_list = game.getPlayerList();
//		for(int i=0; i<3; i++) {
//			player_list.get(i).addToDeck(cards.get(i));
//		}
//		
//		PlayerAbstract winning_player = game.topTrumpsRound(player_list.get(0), cards);
//		
//		//Make sure the winning player has received all the cards in play, in this case all of the cards in the game
//		assertEquals(cards.size(),winning_player.getNumberofCardsLeft(),"Ensure winning player has received all of the cards in play");
//		
//	}
//	
//	@Test
//	void test_communal_pile_when_draw() {
//		
//		GetDeckModel deck_model = new GetDeckModel();
//		CLIView cli_view = new CLIView();
//		
//		GameplayControllerCLI game = new GameplayControllerCLI(deck_model, cli_view, 0, 3, false);
//		
//		ArrayList<Card> cards = create_mock_deck(game,true);
//		
//		ArrayList<PlayerAbstract> player_list = game.getPlayerList();
//		for(int i=0; i<3; i++) {
//			player_list.get(i).addToDeck(cards.get(i));
//		}
//		
//		game.topTrumpsRound(player_list.get(0), cards);
//		
//		//Make sure the winning player has received all the cards in play, in this case all of the cards in the game
//		assertEquals(cards.size(),game.getCardsInDrawPile().size(),"Ensure draw pile has all of the cards in play");
//		
//	}
//	
//	@Test
//	void test_players_created() {
//		
//		GetDeckModel deck = new GetDeckModel();
//		
//		GameplayControllerCLI game = new GameplayControllerCLI(deck,null,0,0,false);
//		ArrayList<PlayerAbstract> players = game.createPlayers(0, 0);
//		
//		assertEquals(0,players.size(),"No players");
//		
//		players = game.createPlayers(1, 4);
//		assertEquals(5,players.size(),"Four players");
//		
//		assertEquals(true,players.get(0).amIHuman(),"Human check");
//		for(int i=0; i<4; i++) {
//			
//			assertEquals(false,players.get(i+1).amIHuman(),"AI check");
//			
//		}
//		
//	}
//	
//	@Test
//	void test_players_being_removed() {
//		
//		GetDeckModel deck = new GetDeckModel();
//		CLIView cli_view = new CLIView();
//		
//		GameplayControllerCLI game = new GameplayControllerCLI(deck,cli_view,0,4,false);
//		
//		ArrayList<Card> cards = create_mock_deck(game,false);
//		
//		ArrayList<PlayerAbstract> player_list = game.getPlayerList();
//		for(int i=0; i<3; i++) {
//			player_list.get(i).addToDeck(cards.get(i));
//		}
//		
//		game.topTrumpsRound(player_list.get(0), cards);
//		
//		assertEquals(1,game.getPlayersInGame().size(),"Test players being removed");
//		
//	}
//	
//	/**
//	 * Test to make sure the deck in the game is the shuffled version of the original deck
//	 * 
//	 */
//	
//	@Test
//	void test_deck_shuffling() {
//		
//		GetDeckModel model = new GetDeckModel();
//		CLIView cli_view = new CLIView();
//		
//		GameplayControllerCLI game = new GameplayControllerCLI(model, cli_view, 0, 0, false);
//		
//		ArrayList<Card> non_shuffled_deck = model.getDeck();
//		ArrayList<Card> shuffled_deck = game.getGameDeck();
//		
//		boolean deck_is_shuffled = false;
//		
//		for(Card c : non_shuffled_deck) {
//			
//			//Check to make sure the shuffled deck contains the cards of the original deck
//			if(!shuffled_deck.contains(c)) {
//				fail("Shuffled deck does not contain the cards of the original deck");
//			}
//			else {
//				//Check to make sure at least one card is in a different order in the shuffled deck to the original deck
//				if(non_shuffled_deck.indexOf(c) != shuffled_deck.indexOf(c)) {
//					deck_is_shuffled = true;
//					break;
//				}
//			}
//			
//		}
//		
//		assertTrue(deck_is_shuffled, "Shuffled deck is in a different order from original deck");
//		
//	}
	
	@Test
	void test_deck_divided_evenly_between_players() {
		
		GetDeckModel model = new GetDeckModel();
		CLIView cli_view = new CLIView();
		
		GameplayControllerCLI game = new GameplayControllerCLI(model, cli_view, 0, 6, false);
		game.dealOutDeck();
		ArrayList<PlayerAbstract> players = game.getPlayerList();
		
		ArrayList<Integer> size_of_decks = new ArrayList<Integer>();
		for(PlayerAbstract p : players) {
			size_of_decks.add(p.getCurrentDeck().size());
		}

		Collections.sort(size_of_decks, Collections.reverseOrder());

		int max_value = size_of_decks.get(0);
		int sum_size_of_decks = 0;
		
		//The way the deck is split, no player should have > 1 more card than any other player
		for(Integer deck_size : size_of_decks) {
			if(deck_size < (max_value - 1)) {
				fail("Cards not divided equally");
			}
			sum_size_of_decks = sum_size_of_decks + deck_size;
		}
		
		assertTrue(true, "Cards divided equally");
		
		//Make sure all the cards in the deck have been handed out
		assertEquals(model.getDeck().size(),sum_size_of_decks,"All cards have been handed out");
		
	}
	
	private ArrayList<Card> create_mock_deck(GameplayControllerCLI game, boolean draw_desired) {
		
		String[] headers = {"description","taste","colour","ripeness"};
		String[] card_names = {"apple","blueberry","papaya"};
		ArrayList<int[]> card_values = new ArrayList<int[]>();
		for(int i=0; i<3; i++) {
			if(draw_desired == false ) {
				int[] card = {i,i,i,i};
				card_values.add(card);
			}
			else {
				int[] card = {0,0,0,0};
				card_values.add(card);
			}
		}

		ArrayList<Card> cards = new ArrayList<Card>();
		for(int i=0; i<3; i++) {
			Card c = new Card(card_names[i],headers,card_values.get(i));
			cards.add(c);
		}
		
		return cards;
		
	}
	
}
