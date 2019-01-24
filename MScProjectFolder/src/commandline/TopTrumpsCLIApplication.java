package commandline;

import common.GameplayController;
import common.Database;
import common.GetDeckModel;

/**
 * Top Trumps command line application. Contains the main method which is run,
 * and the TopTrumpsCLIApplication class.
 * 
 * @author Adrian Borg
 * @version 1
 */
public class TopTrumpsCLIApplication {

	/**
	 * This main method is called by TopTrumps.java when the user specifies that they want to run in
	 * command line mode. The contents of args[0] is whether we should write game logs to a file.
 	 * @param args
	 */
	
	private GameplayController gameController;
	private Database db;
	private CLIView view;
	private GetDeckModel deckModel;
	private boolean logData = false;
	
	public static void main(String[] args) {

		boolean writeGameLogsToFile = false; // Should we write game logs to file?
		if (args[0].equalsIgnoreCase("true")) writeGameLogsToFile=true; // Command line selection

		boolean userWantsToQuit = false; // flag to check whether the user wants to quit the application
		
		TopTrumpsCLIApplication app = new TopTrumpsCLIApplication(writeGameLogsToFile);
		
		// Loop until the user wants to exit the game
		while (!userWantsToQuit) {
			
			int menuSelection = app.menu(); // Show main menu and get selection
			
			if (menuSelection == 0) { // if user wants to quit, exit
				userWantsToQuit = true;
				app.view.goodbyeMessage();
			} else if (menuSelection == 1) { // if user wants to start a game, start one
				app.playGame();
			} else if (menuSelection == 2) { // if user wants to see stats, show them
				app.showStats();
			}
		}
	}
	
	public TopTrumpsCLIApplication(boolean writeGameLogsToFile) {
		/**
		 * The command line app, controls the main menu, stats page and quitting of the app.
		 * 
		 * @param writeGameLogsToFile	<code>true</code> to run in debug mode and produce a .log file,
		 * 								<code>false</code> to run without producing a log file
		 */
		logData = writeGameLogsToFile;
		db = new Database();
		deckModel = new GetDeckModel();
		view = new CLIView();//deckModel, db);
	}
	
	private int menu() {
		/**
		 * Method to display the main menu and get user selection from the menu
		 * 
		 * @return selection	0 - quit, 1 - play game, 2 - view stats
		 */
		view.welcomeMessage();
		int selection = view.gameOrStatorQuit();
		return selection;
	}
	
	private void playGame() {
		/**
		 * Method to create a gameplayController which runs the game till completion
		 */
		//int aiPlayers = view.getNumOfAIPlayers();
		int aiPlayers = 4;
		gameController = new GameplayController(deckModel, view, 1, aiPlayers, logData);
		//data = gameController.getGameData(); 
		// Might not need the last line
	}
	
	private void showStats() {
		/**
		 * Method to display the aggregate stats for past games
		 */
		view.printStatistics(db.getStats());
	}
	
}
