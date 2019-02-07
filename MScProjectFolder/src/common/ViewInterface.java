package common;

public interface ViewInterface {
	
	public void beginningOfRound(int deck_size, int round); 
	
	public void currentPlayer(int player_number);
	
	public void showTopCard(Card card); 
	
	public void showCategory(String category); 
	
	public void playerHasValue(int player_number, int value);
	
	public void theWinnerIs(int player_number); 
	
	public void itsADraw();
	
	public void playerLoses(int player_number);
	
	public void showCommunalPileSize(int pile_size); 
	
	public void overallWinner(int player_number); 
	
	public void quitGame();
	
	public String getCategory(Card card);
}
