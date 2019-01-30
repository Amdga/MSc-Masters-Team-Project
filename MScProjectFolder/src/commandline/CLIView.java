package commandline;
import java.util.Scanner;

import ch.qos.logback.core.net.SyslogOutputStream;
import common.Card;
import common.ReturnsUserInput;

public class CLIView implements ReturnsUserInput{
	
	Scanner input_scanner;
	boolean quit_game = false;
	
	public CLIView() {
		input_scanner = new Scanner(System.in);
	}

	public void welcomeMessage() {
		printStars();
		System.out.println("Welcome to Top Trumps");
		printStars();
	}
	
	public void goodbyeMessage() {
		printStars();
		System.out.println("GoodBye");
		quit_game = true;
		printStars();
	}
	
	public void quitGame() {
		printStars();
		System.out.println("You have quit the game");
		printStars();
	}
	
	public boolean hasGameQuit() {
		return quit_game;
	}

	public void numberOfPlayersPlaying() {
		System.out.println("You are playing against 4 other players");
	}

	public void beginningOfRound(int cardsInDeck, int round) { // ADD WHO IS THE ACTIVE PLAYER?
		System.out.println();
		System.out.println("~ " + "Round " + round + " ~" + "\n\nYou have " + cardsInDeck + " cards in your deck.");
		System.out.println();
	}

	// Method that pass a Card object to the CLIView, calls showTopCard and decideCategory and returns the chosen category as a String
	public String getCategory(Card card) {
		
		//System.out.println("Card name = "+card.getCardName());
		
//		this.card = card;
		//showTopCard();
		return decideCategory(card);
	}

	// Method that shows the top card. Called by the getCategory() method in the
	// same class.
	public void showTopCard(Card card) {

//		this.card = card;
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
		String player = "Player " + number;
		if (number == 0) {
			player = "You";
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
				int number = Integer.parseInt(input_scanner.next());
				checkNumberInput(number);
				return number;
			} catch (NumberFormatException e) {
				System.out.println("Entry invalid. Please try again: ");
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
	public String decideCategory(Card card) {
		String[] headers = card.getHeaders();
		String selectedCategory;
		System.out.print("Please enter a category (");
		for(int i=0;i<headers.length;i++) {
			if (i<headers.length-1) {System.out.print(headers[i] + ", ");}
			else {System.out.print(headers[i] + "): \n");}
		}
		while (true) {
			selectedCategory = input_scanner.next();
			if(selectedCategory.equals("quit")) {
				quitGame();
				return "quit";
			}
			for (int i = 0; i < headers.length; i++) {
				if (selectedCategory.toLowerCase().equals((headers[i]).toLowerCase())) {
					return headers[i];
				}
			}
			System.out.println("That's not an existing category. Please try again: ");
		}

	}
	
	/*public static void main(String[] args) {
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
		
	}*/

}
