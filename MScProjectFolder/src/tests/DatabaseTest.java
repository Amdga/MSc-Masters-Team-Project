package tests;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeAll;

import common.Database;

public class DatabaseTest {
	
	static Database db;

	@BeforeAll
	static void init() {
		db = new Database();
	}
	
	/**
	 * This test is needed as it verifies the methods that are used in other tests
	 * Tests the addGameStats, updateDatabase and deleteLastGame methods
	 * 
	 */
	@Test
	void test_query_add_and_remove_stats() {
		
		//adding game stats should not change anything until the database has been updated
		//The output stats before and after the addGameStats function is called should be the same
		String initial_stats = db.getStats();
		db.addGameStats(1, 1, 1);
		String actual_stats = db.getStats();
		
		assertEquals(initial_stats,actual_stats,"Not updated - nothing should have changed yet");
		
		//On update, the changes should be applied and the database should be updated
		//The stats should have changed, and the initial stats and the current stats should no longer be the same
		db.updateDatabase();
		
		actual_stats = db.getStats();
		assertNotEquals(initial_stats,actual_stats,"Updated database - stats should now have changed");
	
		//When the last game is deleted, the stats should be restored back to the stats recorded at the start of this test
		db.deleteLastGame();
		db.updateDatabase();
		
		actual_stats = db.getStats();
		
		assertEquals(initial_stats,actual_stats,"Last game removed - stats should be reset to initial state");
	}
	
	/**
	 * This test tests whether the query that determines the number of times the computer has won works
	 * It does this by getting the number of computer wins, then logging another computer win and checking
	 * to make sure the query returns the original value + 1
	 */
	@Test
	void test_AI_query() {
		
		//Get the initial number of times the computer has won
		String initial_value = db.getAIWinnerQuery();
		int integer_value = get_last_int(initial_value);
		
		//Add a game stat to the database telling it that player 4 (AI) has won
		db.addGameStats(1, 4, 1);
		db.updateDatabase();
		
		//The expected output of the query is now the original value + 1 (since a new win has been logged)
		int expected_int = integer_value + 1;
		String expected_string = "AI total wins: "+expected_int;
		
		assertEquals(expected_string, db.getAIWinnerQuery(), "Failed at AI return query");
		
		//Remove the game just added to test the database
		db.deleteLastGame();
		db.updateDatabase();
	}
	
	/**
	 * This does exactly the same thing as test_AI_query except with a human player rather than an AI
	 * It tests the query function by getting the number of human wins, then logging another human win and checking
	 * to make sure the query returns the original value + 1
	 */
	@Test
	void test_human_query() {
		
		//Get the initial number of times the human has won
		String initial_value = db.getHumanWinnerQuery();
		int integer_value = get_last_int(initial_value);
		
		//Log a human win and update the database
		db.addGameStats(1, 0, 1);
		db.updateDatabase();

		//The expected output of the query is now the original value + 1 (since a new win has been logged)
		int expected_int = integer_value + 1;
		String expected_string = "Human total wins: "+expected_int;
		
		assertEquals(expected_string, db.getHumanWinnerQuery(), "Failed at human return query");
		
		//Remove the game just added to test the database
		db.deleteLastGame();
		db.updateDatabase();
	}
	
	/**
	 * This function validates the getTotalGames query method, in the same way as the test_AI_query and test_human_query methods
	 * By finding the initial value, adding one to this, logging a game and verifying that the new value is 1 + the old value
	 */
	@Test
	void test_overall_games() {
		
		//Get the initial number of games
		String initial_value = db.getTotalGames();
		int integer_value = get_last_int(initial_value);
		
		//Log a new game
		db.addGameStats(1, 0, 1);
		db.updateDatabase();
		
		//The expected value is the original value + 1 (since a new game has been added)
		int expected_int = integer_value + 1;
		String expected_string = "Total number of games played: "+expected_int;
		
		assertEquals(expected_string, db.getTotalGames(), "Failed at total games query");
		
		//Remove the game just added to test the database
		db.deleteLastGame();
		db.updateDatabase();
	}
	
	/**
	 * This test validates the getMaxRounds query method, by getting the maximum number of rounds so far,
	 * adding 1 to this value, and logging a new game with that number of rounds
	 * The new result of getMaxRounds should be 1 + the old value
	 */
	@Test
	void test_largest_number_of_rounds() {
		
		//Get the initial number of rounds, and add 1 to this value
		String initial_value = db.getMaxRounds();
				
		int max_rounds = get_last_int(initial_value);
		int new_max_rounds = max_rounds + 1;

		//Log a game as having this new maximum number of rounds
		db.addGameStats(new_max_rounds, 0, 0);
		db.updateDatabase();
		
		String expected_string = "Most number of rounds played: "+new_max_rounds;
		
		//Verify that the maximum number of rounds has changed to this new max value
		assertEquals(expected_string, db.getMaxRounds(), "Failed at max rounds query");

		//Remove the game just added to test the database
		db.deleteLastGame();
		db.updateDatabase();
	}
	
	/**
	 * This method verifies the result of the getAvgDraws query method
	 * It does this by getting the current average, adding a new game with a set number of draws,
	 * calculating the new average and verifying that the function returns this new value
	 */
	@Test
	void test_average_draws() {
		
		//Get the total number of games played so far (tested previously)
		//This is used to calculate the original sum of all the values
		String number_of_games_string = db.getTotalGames();
		int number_of_games = get_last_int(number_of_games_string);
		
		//Get the initial average number of draws
		String initial_value = db.getAvgDraws();
		int initial_average = get_last_int(initial_value);
		
		//The initial sum of all draws per game is the average draws * the number of games
		int initial_sum = number_of_games * initial_average;
		
		//Calculate the new average number of draws
		int additional_draws = 400;
		int calculated_average = (int)Math.round((initial_sum + additional_draws)/(number_of_games+1));
		
		//Add a game with the number of draws used in the previous calculation
		db.addGameStats(1, 1, additional_draws);
		db.updateDatabase();
		
		//Get the new average number of draws
		String actual_string = db.getAvgDraws();
		int actual_average = get_last_int(actual_string);
		
		//Done this way because the returned value of the database is rounded from a double to an int, and therefore isn't easy to predict exactly how this rounding happens
		//Because only one calculation is taking place, the new value is going to be (plus or minus 1) what I have calculated
		assertTrue((calculated_average-1) <= actual_average && actual_average <= (calculated_average+1),"Failed at averages");
	
		//Remove the game just added to test the database
		db.deleteLastGame();
		db.updateDatabase();
	
	}
	
	/**
	 * A method to return the last value in a given input string,
	 * since the query returns a string such as:
	 * 		Average draws are: [value we're after]
	 * 
	 * @param input_string with a number at the end
	 * @return the last number of that string
	 */
	private int get_last_int(String input_string) {
		
		String last_int_string = "";
		
		//Use regex to find the last set of single numbers in the string, and group them to find the last number
		Pattern p = Pattern.compile("[0-9]+$");
		Matcher m = p.matcher(input_string);
		if(m.find()) {
		    last_int_string = m.group();
		}
		
		//Convert the String version of the last number into an integer and return it
		int last_int = Integer.parseInt(last_int_string);
		return last_int;
		
	}

}
