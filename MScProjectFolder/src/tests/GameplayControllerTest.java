package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import commandline.CLIView;
import common.Card;
import common.GameplayControllerCLI;
import common.GetDeckModel;
import players.PlayerAbstract;

public class GameplayControllerTest {
	
	/**
	 * Test to make sure the winning player has all of the cards at the end of the game
	 * (and therefore the game has ended at the correct point)
	 * It does this by running a game with 4 AI players and no human player (so no user input is required)
	 * and checking the following:
	 * 
	 * 	- That the winning players deck contains the same number of cards as the original deck
	 *  - That all of the cards in the original deck are in the winning players deck (and therefore no cards are
	 *    duplicated/missing)
	 *  - That all of the losing players have no cards left
	 * 
	 */
	@Test
	void test_winning_player_all_cards() {
		
		GetDeckModel deck_model = new GetDeckModel();

		//We don't need the CLI in this test so create a mocked version
		CLIView mocked_cli_view = Mockito.mock(CLIView.class);
		
		
		//Start a game with 4 AI players and no human players (so no user input is required)
		GameplayControllerCLI game = new GameplayControllerCLI(deck_model, mocked_cli_view, 0, 4, false);
		
		//Run the game until it ends
		game.topTrumpsGame();
		
		PlayerAbstract winning_player = game.getWinningPlayer();
		ArrayList<Card> expected_deck = deck_model.getDeck();
		ArrayList<Card> actual_deck = winning_player.getCurrentDeck();
		
		//Verify that all of the cards in the original deck are in the winners deck
		for(Card c : expected_deck) {
			assertTrue(actual_deck.contains(c),"Verifying player deck contains "+c.getCardName());
		}
		
		//Verify that the size of the winners deck is the same size as the actual deck
		assertEquals(expected_deck.size(), actual_deck.size(),"Verifying size of deck");
		
		//Get a list of all players
		ArrayList<PlayerAbstract> players_in_game = game.getPlayerList();
		
		for(PlayerAbstract p : players_in_game) {
			//Check all losing players decks have no cards in them
			if(!p.equals(winning_player)) {
				assertTrue(p.getCurrentDeck().size() == 0,"Testing all losing player "+p.whoAmI()+" has no cards left");
			}
			
		}
	
	}
	
	/**
	 * Test to make sure that at the end of a round, the winning player gets all the cards played
	 * in that round
	 */
	@Test
	void test_winner_gets_all_cards() {

		//Neither the GetDeckModel or the CLIView are needed for this test (since we're creating our own test deck)
		//so mock these classes for input into the GameplayController
		GetDeckModel mocked_deck_model = Mockito.mock(GetDeckModel.class);
		CLIView mocked_cli_view = Mockito.mock(CLIView.class);
		
		//Create a game with 3 AI players and no human players (so no user input is needed)
		GameplayControllerCLI game = new GameplayControllerCLI(mocked_deck_model, mocked_cli_view, 0, 3, false);
		
		//Create a test deck of cards with 3 cards in it (each card has a different value so there is a winner)
		ArrayList<Card> cards = create_mock_deck(false);
		
		//Give each of the 3 playes one of the 3 cards
		ArrayList<PlayerAbstract> player_list = game.getPlayerList();
		for(int i=0; i<3; i++) {
			player_list.get(i).addToDeck(cards.get(i));
		}
		
		//Run a round, and get the winning player
		PlayerAbstract winning_player = game.topTrumpsRound(player_list.get(0), cards);
		
		//Make sure the winning player has received all the cards in play, in this case all of the cards in the game
		assertEquals(cards.size(),winning_player.getNumberofCardsLeft(),"Ensure winning player has received all of the cards in play");
		
	}
	
	/**
	 * Test to make sure the communal pile (or draw pile) contains all the cards in play when there is a draw
	 */
	@Test
	void test_communal_pile_when_draw() {
		
		//Neither the GetDeckModel or the CLIView are needed for this test (since we're creating our own test deck)
		//so mock these classes for input into the GameplayController
		GetDeckModel mocked_deck_model = Mockito.mock(GetDeckModel.class);
		CLIView mocked_cli_view = Mockito.mock(CLIView.class);
		
		//Create a game with 3 AI players and no human players (so no user input is needed)
		GameplayControllerCLI game = new GameplayControllerCLI(mocked_deck_model, mocked_cli_view, 0, 3, false);
		
		//Create a test deck of 3 cards where all cards have the same values (so there is a draw)
		ArrayList<Card> cards = create_mock_deck(true);
		
		//Divide the cards between the 3 players
		ArrayList<PlayerAbstract> player_list = game.getPlayerList();
		for(int i=0; i<3; i++) {
			player_list.get(i).addToDeck(cards.get(i));
		}
		
		//Run a round
		game.topTrumpsRound(player_list.get(0), cards);
		
		//Make sure the winning player has received all the cards in play, in this case all of the cards in the game
		assertEquals(cards.size(),game.getCardsInDrawPile().size(),"Ensure draw pile has all of the cards in play");
		
	}
	
	/**
	 * Test to make sure the correct number of players are created and they are
	 * of the correct type
	 */
	@Test
	void test_players_created() {
		
		//We don't need the deck for this test so mock it
		GetDeckModel mocked_deck = Mockito.mock(GetDeckModel.class);
		
		//Create a game, and get the list of players when no players are wanted
		GameplayControllerCLI game = new GameplayControllerCLI(mocked_deck,null,0,0,false);
		ArrayList<PlayerAbstract> players = game.createPlayers(0, 0);
		
		//Verify that the size of the player list in this case is 0
		assertEquals(0,players.size(),"No players");
		
		//Create a new set of players, 1 human and 4 AI (as will be used in the command line)
		players = game.createPlayers(1, 4);
		
		//Verify that 5 players are returned
		assertEquals(5,players.size(),"Five players");
		
		//Verify that the first player is a human
		assertEquals(true,players.get(0).amIHuman(),"Human check");
		//Verify that the last four players are AI
		for(int i=0; i<4; i++) {
			assertEquals(false,players.get(i+1).amIHuman(),"AI check");
		}
		
	}
	
	/**
	 * A test to make sure the players are removed from play when they have no cards left
	 */
	@Test
	void test_players_being_removed() {
		
		//Neither the GetDeckModel or the CLIView are needed for this test (since we're creating our own test deck)
		//so mock these classes for input into the GameplayController
		GetDeckModel mocked_deck_model = Mockito.mock(GetDeckModel.class);
		CLIView mocked_cli_view = Mockito.mock(CLIView.class);
		
		//Start a game with no human players and 3 AI Players
		GameplayControllerCLI game = new GameplayControllerCLI(mocked_deck_model,mocked_cli_view,0,3,false);
		
		//Create a test deck with 3 cards, each of different values (so one player wins)
		ArrayList<Card> cards = create_mock_deck(false);
		
		//Hand out these 3 cards to the 3 players
		ArrayList<PlayerAbstract> player_list = game.getPlayerList();
		for(int i=0; i<3; i++) {
			player_list.get(i).addToDeck(cards.get(i));
		}
		
		//Check to make sure all 3 players are originally in the game
		assertEquals(3, game.getPlayersInGame().size(),"Test all 3 players are in the game");
		
		//After a round has been played, with each player only having one card, all losing players
		//should be left with 0 cards and should be removed from the game
		game.topTrumpsRound(player_list.get(0), cards);
		
		//Verify that that number of players left in the game has been reduced to 1
		assertEquals(1,game.getPlayersInGame().size(),"Test players being removed");
		
	}
	
	/**
	 * Test to make sure the deck in the game is the shuffled version of the original deck
	 * It does this by making sure at least one of the cards are in different places in both decks
	 */
	@Test
	void test_deck_shuffling() {
		
		GetDeckModel model = new GetDeckModel();
		//We don't need the CLI for this test so mock the class
		CLIView mocked_cli_view = Mockito.mock(CLIView.class);
		
		GameplayControllerCLI game = new GameplayControllerCLI(model, mocked_cli_view, 0, 0, false);
		
		//Get the shuffled and non-shuffled decks
		ArrayList<Card> non_shuffled_deck = model.getDeck();
		ArrayList<Card> shuffled_deck = game.getGameDeck();
		
		//For completeness, make sure the decks are the same size
		assertEquals(non_shuffled_deck.size(),shuffled_deck.size(),"The deck sizes should be the same");
		
		boolean deck_is_shuffled = false;
		
		//For each of the cards in the original deck
		for(Card c : non_shuffled_deck) {
			
			//Check to make sure the shuffled deck contains the cards of the original deck
			if(!shuffled_deck.contains(c)) {
				fail("Shuffled deck does not contain the cards of the original deck");
			}
			else {
				//Check to make sure at least one card is in a different order in the shuffled deck to the original deck
				if(non_shuffled_deck.indexOf(c) != shuffled_deck.indexOf(c)) {
					deck_is_shuffled = true;
					break;
				}
			}
			
		}
		
		//Test passes when one of the cards are found to be in a different place in the two decks
		assertTrue(deck_is_shuffled, "Shuffled deck is in a different order from original deck");
		
	}
	
	/**
	 * Test to make sure the deck is divided properly between the players
	 * The deck is split as follows:
	 * 
	 *  - Give one card to each of the players in turn
	 *  - When the last player is reached, go back to the first player and continue
	 *  - Repeat until all cards have been handed out
	 *  
	 * This means that it is likely some players will have more cards than others, but only plus or minus one card
	 */
	@Test
	void test_deck_divided_evenly_between_players() {
		
		GetDeckModel model = new GetDeckModel();
		//The CLIView is not needed for this test so mock it
		CLIView mocked_cli_view = Mockito.mock(CLIView.class);
		
		GameplayControllerCLI game = new GameplayControllerCLI(model, mocked_cli_view, 0, 6, false);
		
		//Deal out the deck and get the list of players
		game.dealOutDeck();
		ArrayList<PlayerAbstract> players = game.getPlayerList();
		
		//Create an arrayList of the deck sizes from the players
		ArrayList<Integer> size_of_decks = new ArrayList<Integer>();
		for(PlayerAbstract p : players) {
			size_of_decks.add(p.getCurrentDeck().size());
		}

		//Sort this list of deck sizes, to get the maximum deck size
		Collections.sort(size_of_decks, Collections.reverseOrder());

		//Get the max deck size, and take a sum of all the deck sizes to ensure all cards have been handed out
		int max_value = size_of_decks.get(0);
		int sum_size_of_decks = 0;
		
		//The way the deck is split, no player should have > 1 more card than any other player
		for(Integer deck_size : size_of_decks) {
			if(deck_size < (max_value - 1)) {
				fail("Cards not divided equally");
			}
			sum_size_of_decks = sum_size_of_decks + deck_size;
		}
		
		//If the test reaches this point, no deck has more than one more card of any other
		assertTrue(true, "Cards divided equally");
		
		//Make sure all the cards in the deck have been handed out for completeness
		assertEquals(model.getDeck().size(),sum_size_of_decks,"All cards have been handed out");
		
	}
	
	/**
	 * Private method used by many tests to create a test deck with 3 cards in it
	 * 
	 * @param draw_desired - whether or not the cards are to all be the same (to generate a draw)
	 * @return the test deck
	 */
	private ArrayList<Card> create_mock_deck(boolean draw_desired) {
		
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
