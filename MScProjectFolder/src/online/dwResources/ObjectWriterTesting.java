package online.dwResources;

import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import common.Card;
import common.GetDeckModel;

public class ObjectWriterTesting {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ObjectWriter oWriter = new ObjectMapper().writerWithDefaultPrettyPrinter();
		
		//creating objects to test
		String[] headers = {"1", "2", "3", "4", "5", "6"};
		int[] values = {1, 2, 3, 4, 5};
		
		Card card = new Card("testcard", headers, values);
		
		ArrayList<Card> deck = new ArrayList<Card>();
		GetDeckModel deckmodel = new GetDeckModel();
		
		for(int i=0; i<6; i++) {
			card = new Card("testcard " + (i+1), headers, values);
			deck.add(card);
		}
		
		deck = null;
		
		//testing of the writer
		try {
			String cardOutput = oWriter.writeValueAsString(deck);
			System.out.println(cardOutput);
			System.out.println((String) null);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String[] testArr = "1 2 33 44 55 66".split(" ");
		try {
			System.out.println(oWriter.writeValueAsString(testArr));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
}
