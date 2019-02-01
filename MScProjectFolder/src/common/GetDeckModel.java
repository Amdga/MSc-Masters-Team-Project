package common;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GetDeckModel {
	
	private ArrayList <Card> deck = new ArrayList<>();
	private String filepath = ".\\StarCitizenDeck.txt";
	private String [] header;
	private ArrayList <Card> shuffleTheDeck = new ArrayList<>();

	// ~~~~~~~ Constructors
	
	public GetDeckModel() {
		constructionHelper();
	}

	public GetDeckModel(String deckFileName) {
		filepath = ".\\" + deckFileName;
		constructionHelper();
	}
	
	private void constructionHelper() {
		
		Scanner s;
		FileReader cardFile;

		try {
			// Read deck file
			cardFile = new FileReader(filepath);
			s = new Scanner(cardFile);

			// Separates the headers from card values
			String data = s.nextLine();
			header = data.split(" ");

			// Saves values of categories
			while(s.hasNextLine()) {
				String [] x = s.nextLine().split(" ");
				String cardTitle = x[0];
				int [] values = {Integer.parseInt(x[1]), Integer.parseInt(x[2]), Integer.parseInt(x[3]),
						Integer.parseInt(x[4]), Integer.parseInt(x[5])};
				// Create card and add to deck
				Card newCard = new Card(cardTitle, header, values);
				deck.add(newCard);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		// Shuffle the cards
		shuffleCards();
	}
	
	// ~~~~~~~ Rest of Methods
	
	private ArrayList<Card> shuffleCards(){
		Random randomIndex = new Random();
		// Create temporary deck to remove cards out of
		ArrayList <Card> tempDeck = (ArrayList<Card>) deck.clone();

		while(tempDeck.size()!=0) { // While the temp deck still has cards
			int x = randomIndex.nextInt(tempDeck.size()); // Choose a random card from the deck
			shuffleTheDeck.add(tempDeck.get(x)); // Add the chosen card to the shuffled deck
			tempDeck.remove(x); // Remove the chosen card from the temp deck
		}
		return shuffleTheDeck; // Return the shuffled deck
	}
	
	// Getters

	public ArrayList<Card> getShuffledDeck() {
		//return shuffled deck
		return shuffleTheDeck;
	}

	public ArrayList<Card> getDeck() {
		//return initial deck
		return deck;
	}

	public String [] getHeader() {
		String[] output = new String[header.length-1];
		for(int i=1;i<header.length;i++) {
			output[i-1] = header[i];
		}
		return output; // Return the headers, excluding the Description header
	}

//	public static void main(String[] args) {
//		// Testing method
//		GetDeckModel deckModel = new GetDeckModel();
//		System.out.println("end");
//		TestLogger tL = new TestLogger(true);
//		tL.logDeckCreation(deckModel.deck);
//		tL.logDeckShuffle(deckModel.shuffleTheDeck);
//	}
}

