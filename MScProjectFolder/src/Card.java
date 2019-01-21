import java.util.HashMap;

public class Card {

	private String cardname;
	private HashMap<String, Integer> values = new HashMap<String, Integer>();

	public Card(String cardname, String[] headers, int[] inputValues) {
		this.cardname = cardname;
		for(int i=0;i<(headers.length-1);i++) {
			values.put(headers[i+1],inputValues[i]);
		}
	}
	
	public String getCardName() {
		return cardname;
	}
	
	public int getValue(String category) {
		return values.get(category);
	}

	public int getValue(int category) {
		return values.get(getHeaders()[category]);
	}
	
	public String[] getHeaders() {
		Object[] arr = values.keySet().toArray();
		String[] output = new String[arr.length];
		for(int i=0;i<arr.length;i++) {
			output[i] = (String) arr[i];
		}
		return output;
	}
}

