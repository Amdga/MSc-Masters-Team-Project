package common;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Database {

	private ArrayList <String> statements = new ArrayList<String>();
	private ArrayList <String> query = new ArrayList <String>();
	private final String username = "postgres";
	private final String password = "2409217J";
	private final String serverLocation = "jdbc:postgresql://localhost:5432/Top Trumps/";
//	private final String username = "m_18_1101610r";
//	private final String password = "1101610r";
//	private final String serverLocation = "jdbc:postgresql://yacata.dcs.gla.ac.uk:5432/";

	public Database () {
	}

	public void updateDatabase() {
		//When called, update Database will loop through an array of waiting 
		//data to be inserted into the database
		//it then clears the ArrayList once they have been added so there is no chance of duplication

		try {
			//test that the JBDC file works 
			Class.forName("org.postgresql.Driver");
			Connection connection = DriverManager.getConnection 
					//input server location, username, and password
					(serverLocation,username ,
							password);

			Statement gameDetails = connection.createStatement();
			for (String s: statements) {
				gameDetails.execute(s);

			}
			connection.close();
			statements.clear();	
		}
		catch (SQLException | ClassNotFoundException e) { 
			System.err.println("#############################\n\nDatabase Connection Failed\n\n#############################" ); 
			e.printStackTrace(); 
			return; 
		}
	}

	//the queryDatabase connects to the database and implements the passed SELECT statement
	private  String queryDatabase(String query, String output, String columnName) {
		String returnQueryOutput =null;
		try {
			//test that the JBDC file works 
			Class.forName("org.postgresql.Driver");
			Connection connection = DriverManager.getConnection 
					//input server location, username, and password
					(serverLocation,username ,
							password);

			Statement gameDetails = connection.createStatement();

			ResultSet queryResult =  gameDetails.executeQuery(query);

			while(queryResult.next()) {
				int value = queryResult.getInt(columnName);
				returnQueryOutput = output + value;
			}
			//System.out.println(output + value);

			connection.close();	

		}
		catch (SQLException | ClassNotFoundException e) { 
			System.err.println("#############################\n\nDatabase Connection Failed\n\n#############################" ); 
			e.printStackTrace(); 
		}
		return returnQueryOutput;
	}

	//////////////////////Insert Methods

	public void addGameStats(int rounds, int playerID, int draws) {
		//add gamestats info to an array to be executed by main	
		String statement = String.format("INSERT INTO GAMESTATS (no_of_rounds,winner,draws)VALUES (%d, %d, %d);", rounds, playerID, draws);
		statements.add(0, statement);
	}
	public void addRoundStats(int playerID, int noRoundsWon ) {
		//add round stats to the array executed by main, will be bumped to after gamestats,
		//to so the serial ID pulled is correct
		String statement = String.format("INSERT INTO ROUNDSTATS(gameID, playerID, no_rounds_won) VALUES ((SELECT MAX(gameid) from gamestats), %d, %d);", playerID, noRoundsWon);
		statements.add(statement);

	}
	
	//Used to delete fake games inserted by testing
	public void deleteLastGame() {
		
		String statement = "DELETE FROM GAMESTATS WHERE gameid=(SELECT MAX(gameid) FROM GAMESTATS);";
		statements.add(statement);
		
	}

	////////////////////////////Query methods

	public String getHumanWinnerQuery() {
		//	How many times Human has won:
		String hwinner =
				"SELECT COUNT(g.winner) FROM PLAYER AS p, GAMESTATS as g WHERE p.playerID = g.winner AND p.playerID IN (SELECT p.playerID WHERE ptype = 'human');";
		String output = "Human total wins: ";
		String column = "COUNT";
		return queryDatabase(hwinner, output, column);
	}

	public String getAIWinnerQuery() {
		//query to get how many times AI has won
		String aiwinner = 
				"SELECT COUNT(g.winner) FROM GAMESTATS as g, PLAYER as p WHERE p.playerID = g.winner AND p.playerID IN (SELECT playerID WHERE ptype = 'AI');";
		String output = "AI total wins: ";
		String columnName = "COUNT";
		return queryDatabase(aiwinner, output, columnName);
	}

	public String getAvgDraws() {
		//queries database the average number of draws per game
		String nDraws = 
				"SELECT AVG(g.draws) FROM GAMESTATS as g;";
		String output = "Average draws are: ";
		String columnName = "AVG";
		return queryDatabase(nDraws, output, columnName);
	}

	public String getMaxRounds() {
		//queries database the max number of rounds per game that has happened
		String mRounds = 
				"SELECT MAX(g.no_of_rounds) FROM GAMESTATS as g;";
		String output = "Most number of rounds played: ";
		String column = "MAX";
		return queryDatabase(mRounds, output, column);

	}
	public String getTotalGames() {
		//get's the total amount of games that have been played
		String tgames = "SELECT COUNT(g.gameID) FROM GAMESTATS AS g;";
		String output = "Total number of games played: ";
		String column = "COUNT";
		String x = queryDatabase(tgames, output, column);
		return x;
	}

	public String getStats() {
		String stats = "Statistics: \n";
		stats += getHumanWinnerQuery() + "\n";
		stats += getAIWinnerQuery() + "\n";
		stats += getAvgDraws() + "\n";
		stats += getMaxRounds() + "\n";
		stats += getTotalGames() + "\n";

		return stats;
	}

	/*public static void main(String[] args) { 

		Database db = new Database();
		System.out.println(db.getAvgDraws());
		
		//db.addGameStats(10, 0, 4);
		//String x = db.getTotalGames();
		//System.out.println( x);
		//		db.addGameStats(10, 0, 4);
		//		db.addRoundStats(0, 6);
		//		db.updateDatabase();
		//		
		//		db.addRoundStats(1, 6);
		//		db.addRoundStats(2,2);
		//		db.addGameStats(8, 1, 0);
		//		db.updateDatabase();

	}*/
}
