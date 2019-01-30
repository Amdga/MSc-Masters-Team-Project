package logger;

public class PersistentGameData {
	
		private int number_of_draws;
		private int winning_player;
		private int number_of_rounds;
		private int[] player_wins;
		private boolean gameWasQuit = false;
		
	    private static PersistentGameData game_data; 
	  
	    private PersistentGameData(int number_of_players) {
	    	
	    	//Uses the Singleton design pattern since we only want one of these per game
	    	//(multiple games can't be run at once)
	    	
	    	player_wins = new int[number_of_players];
	    	for(int i=0; i<player_wins.length; i++) {
	    		player_wins[i] = 0;
	    	}
	    	
	    	number_of_draws = 0;
	    	number_of_rounds = 0;
	    	
	    } 
	  
	    public static PersistentGameData getInstance(int number_of_players) 
	    { 
	        if (game_data==null) 
	            game_data = new PersistentGameData(number_of_players); 
	        return game_data; 
	    } 
	    
	    public void increment_rounds() {
	    	number_of_rounds++;
	    }
	    
	    public void increment_number_of_draws() {
	    	number_of_draws++;
	    }
	    
	    public void log_player_who_won(int player_id) {
	    	winning_player = player_id;
	    }
	    
	    public void log_player_won_rounds(int player_id) {
	    	player_wins[player_id]++;
	    }
	    
	    public int get_number_of_rounds() {
	    	return number_of_rounds;
	    }
	    
	    public int get_number_of_draws() {
	    	return number_of_draws;
	    }
	    
	    public int get_winning_player() {
	    	return winning_player;
	    }
	    
	    public int[] get_player_wins() {
	    	return player_wins; 
	    }
	    
	    public void setWhetherGameWasQuit(boolean quit_game) {
	    	this.gameWasQuit = quit_game;
	    }
	    
	    public boolean wasGameCompleted() {
	    	return !this.gameWasQuit;
	    }
}
