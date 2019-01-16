import java.util.ArrayList;

public class GameplayController {
	
	ArrayList<Card> cardsLeftToDraw;
	ArrayList<Card> cardsInDrawPile;
	ArrayList<Card> cardsInPlay;
	ArrayList<PlayerAbstract> players;
	
	int model; //This will be replaced by GetDeck class when it is created
	int controller; //This will be replaced by Controller class when it is created
	
	private void decideWhoGoesFirst() {
		
	}
	
	private void giveCardsToPlayers(ArrayList<Card> card, PlayerAbstract player) {
		
	}
	
	private void createPlayers(int number_of_humans, int number_of_ai) {
		
	}
	
	private void topTrumpsRound() {
		
		//single round of top trumps
		
	}
	
	private void topTrumpsGame() {
		
		//while each player still has cards left, have a round
		
	}
	
	public GameplayController(int model, int controller, int number_of_human_players, int number_of_ai_players) {
		
		this.model = model;
		this.controller = controller;
		
		createPlayers(number_of_human_players,number_of_ai_players);
	
	}

}
