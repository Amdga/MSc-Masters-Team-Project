package commandline;
import java.util.InputMismatchException;
import java.util.Scanner;

import ch.qos.logback.core.net.SyslogOutputStream;
import common.Card;
import common.ReturnsUserInput;

public class CLIView implements ReturnsUserInput{
	Scanner s;

	public CLIView() {
		s = new Scanner(System.in);
	}

	private Card card;

	public void welcomeMessage() {
		printStars();
		System.out.println("Welcome to Top Trumps");
		printStars();
	}
	
	public void goodbyeMessage() {
		printStars();
		System.out.println("GoodBye");
		printStars();
	}

	public void numberOfPlayersPlaying() {
		System.out.println("You are playing against 4 other players");
	}

	public void beginningOfRound(int cards, int round) {
		System.out.println();
		System.out.println("~ " + "Round " + round + " ~" + "\n\nYou have " + cards + " cards in your deck.");
		System.out.println();
	}

	// Method that pass a Card object to the CLIView, calls showTopCard and decideCategory and returns the chosen category as a String
	public String getCategory(Card card) {
		
		//System.out.println("Card name = "+card.getCardName());
		
		this.card = card;
		//showTopCard();
		return decideCategory();
	}

	// Method that shows the top card. Called by the getCategory() method in the
	// same class.
	public void showTopCard(Card card) {

		this.card = card;
		String[] headers = card.getHeaders();
		System.out.println("Players have drawn their cards." + "\n\nYou drew: " + card.getCardName());
		printStars();
		for (int i = 0; i < headers.length; i++) {
			System.out.println("  " + headers[i] + ": " + card.getValue(i));
		}
		printStars();

	}

	public void printStars() {
		System.out.println("***************");
	}

	// Method that print a message if its a draw
	public void itsADraw() {
		System.out.println("Its a draw!");
	}

	// Method displays the winner of the round. Takes an int number as input.
	public void theWinnerIs(int number) {
		System.out.println("The winner of this round: ");
		translatePlayer(number);
	}

	// Method that displays the winner of the game. Takes an int number as input.
	public void overallWinner(int number) {
		System.out.println("The winner of the game: ");
		translatePlayer(number);
	}

	// Method that translates what player won from the passed number. Player 0 is
	// the user and is displayed differently.
	public void translatePlayer(int number) {
		String player = "Player" + number;
		if (number == 0) {
			player = "You ";

		}
		System.out.println(player);

	}
	
	public void playerLoses(int player) {
		System.out.println("Player" + player + " has no more cards left to draw and is being removed from play");
	}
	
	public void currentPlayer(int player) {
		System.out.println("The current player is: ");
		translatePlayer(player);
	}
	
	public void showCategory(String category) {
		System.out.println("The selected category is "+category);
	}
	
	public void playerHasValue(int player, int value) {
		System.out.println("Player "+player+" has card value "+value);
	}

	// Method that lets the user choose between quitting, play a game, or view statiscs.
	//0: quit, 1: game, 2: statistics.
	public int gameOrStatorQuit() {
		System.out.println(
				"To quit, select 0. To play a game, select 1. To view statistics from previous games, select 2. Please enter a digit: ");
		while (true) {
			try {
				int number = Integer.parseInt(s.next());
				checkNumberInput(number);
				return number;
			} catch (NumberFormatException e) {
				System.out.println("That was an invalid digit. Please try again: ");

			}
		}
	}
	//Method that checks if the input is within the valid range.
	public void checkNumberInput(int number) throws NumberFormatException {
		if (number < 0 || 2 < number) {
			throw new NumberFormatException();
		}

	}
	//Method that prints statistics
	public void printStatistics(String statistics) {
		System.out.println(statistics);
	}
	//Method that take user input, checks if the input is an existing category, and returns the chosen category as a String.
	public String decideCategory() {
		String[] headers = card.getHeaders();
		String selectedCategory;
		System.out.println("Please enter a category: ");
		while (true) {
			selectedCategory = s.next();
			for (int i = 0; i < headers.length; i++) {
				if (selectedCategory.toLowerCase().equals((headers[i]).toLowerCase())) {
					return headers[i];
				}
			}
			System.out.println("That's not an existing category. Please try again: ");
		}

	}
	
	public static void main(String[] args) {
		String [] headers = {"description", "Range", "Speed", "Velocity", "Strength", "intellect"};
		int [] inputValues = {3, 5, 1, 8, 7};
		
		
		CLIView cliView = new CLIView();
		Card card = new Card("T-rex", headers, inputValues);
		cliView.welcomeMessage();
		cliView.numberOfPlayersPlaying();
		cliView.beginningOfRound(14, 1);
		String category = cliView.getCategory(card);
		System.out.println(category);
		cliView.theWinnerIs(3);
		cliView.overallWinner(0);
		int number = cliView.gameOrStatorQuit();
		System.out.println(number);
		
		
		
	}

}
