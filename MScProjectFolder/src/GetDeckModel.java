import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GetDeckModel {
	private ArrayList <Card> deck = new ArrayList();
	private String [] headers;
//	private int [] inputValues;
	private String filename;
	private String [] header;
	private ArrayList <Card> shuffleTheDeck = new ArrayList ();
	
public void getDeckModel(String filename) {
	this.filename = filename;
	Scanner s;
	FileReader cardFile;
	
	try {
		cardFile = new FileReader(filename + ".");
		s = new Scanner(filename);
		String data = s.next();
		 // Separates the headers from card values
		header = data.split(",");
		
		for(int i = 0; i < 40; i ++) {
		String cardTitle = s.next();
		String [] x = data.split(",");
		int [] values = {Integer.parseInt(x[0]), Integer.parseInt(x[1]), Integer.parseInt(x[2]),
				Integer.parseInt(x[3]), Integer.parseInt(x[4])};
		Card newCard = new Card(cardTitle, header, values);
		deck.add(newCard);
		
		}
	} catch (FileNotFoundException e) {
		
		e.printStackTrace();
	}
	shuffleCards();
}

public ArrayList<Card> getShuffledDeck() {
	return shuffleTheDeck;
	
}
public ArrayList<Card> getDeck() {
	return deck;
	
}


private ArrayList<Card> shuffleCards(){
	int max = 39;
	Random randomIndex = new Random();
//	ArrayList <Card> shuffleTheDeck = new ArrayList ();
	for(int i = 0; i < deck.size(); i ++) {
		int x = randomIndex.nextInt(max);
		shuffleTheDeck.add(deck.get(x));
	}
	return shuffleTheDeck;
}
public String [] getHeader() {
	
	return header;
	
}
}

