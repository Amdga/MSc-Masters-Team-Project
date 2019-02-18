package online.dwResources;

import java.io.IOException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import online.OnlineDataBuffer;
import online.OnlineGameplayController;
import online.configuration.TopTrumpsJSONConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import common.Database;
import common.GetDeckModel;
import logger.PersistentGameData;

@Path("/toptrumps") // Resources specified here should be hosted at http://localhost:7777/toptrumps
@Produces(MediaType.APPLICATION_JSON) // This resource returns JSON content
@Consumes(MediaType.APPLICATION_JSON) // This resource can take JSON content as input
/**
 * This is a Dropwizard Resource that specifies what to provide when a user
 * requests a particular URL. In this case, the URLs are associated to the
 * different REST API methods that you will need to expose the game commands
 * to the Web page.
 * 
 * Below are provided some sample methods that illustrate how to create
 * REST API methods in Dropwizard. You will need to replace these with
 * methods that allow a TopTrumps game to be controled from a Web page.
 */
public class TopTrumpsRESTAPI {

	// variable initialisation
	private ArrayList<OnlineDataBuffer> dataBuffers = new ArrayList<>();
	private ArrayList<OnlineGameplayController> gameControllers = new ArrayList<>();
	private Database db;
	private ArrayList<GetDeckModel> deckModels = new ArrayList<>();
	private int number_of_human_players = 1;
	private int number_of_ai_players;
	private TopTrumpsJSONConfiguration config;

	/** A Jackson Object writer. It allows us to turn Java objects
	 * into JSON strings easily. */
	ObjectWriter oWriter = new ObjectMapper().writerWithDefaultPrettyPrinter();

	/**
	 * Contructor method for the REST API. This is called first. It provides
	 * a TopTrumpsJSONConfiguration from which you can get the location of
	 * the deck file and the number of AI players.
	 * @param conf
	 */
	public TopTrumpsRESTAPI(TopTrumpsJSONConfiguration conf) {
		// ----------------------------------------------------
		// Add relevant initalization here
		// ----------------------------------------------------

		number_of_ai_players = conf.getNumAIPlayers();
		db = new Database();
		config = conf;

//		dataBuffer = new OnlineDataBuffer();
//		gameController = new OnlineGameplayController(deckModel, dataBuffer, number_of_human_players, number_of_ai_players);
//		dataBuffer.setOGC(gameController);

	}

	// ----------------------------------------------------
	// Add relevant API methods here
	// ----------------------------------------------------
	
	// ~~~~Stats Methods~~~~~~~~~~~~~~~~
	@GET
	@Path("stats/initialise")
	public String getStats() throws IOException{
		String dbOutput = db.getStats();
		String[] splitOutput = dbOutput.split("\n");
		String[] results = new String[splitOutput.length-1];
		for (int i=1;i<splitOutput.length;i++) {
			String[] temp = splitOutput[i].split(" "); 
			results[i-1] = temp[temp.length-1];
		}
		return oWriter.writeValueAsString(results);
	}

	// ~~~~Gameplay Methods~~~~~~~~~~~~~
	
	@GET
	@Path("/chosenNumberOfPlayers")
		public void chosenNumberOfPlayers(@QueryParam("number") int number) {
		number_of_ai_players = number;
		dataBuffers.add(new OnlineDataBuffer());
		deckModels.add(new GetDeckModel(config.getDeckFile()));
		gameControllers.add(new OnlineGameplayController(deckModels.get(deckModels.size()-1), dataBuffers.get(dataBuffers.size()-1), number_of_human_players, number_of_ai_players));
		dataBuffers.get(dataBuffers.size()-1).setOGC(gameControllers.get(gameControllers.size()-1));
		}
	
	@GET
	@Path("game/newGame")
	public String newGame() throws IOException{
//		dataBuffer = new OnlineDataBuffer();
//		gameController = new OnlineGameplayController(deckModel, dataBuffer, number_of_human_players, number_of_ai_players);
//		dataBuffer.setOGC(gameController);
		System.err.println("starting game with ID: " + gameControllers.get(gameControllers.size()-1).getID());
		return "" + gameControllers.get(gameControllers.size()-1).getID();
	}

	@GET
	@Path("game/initialiseGameplay")
	public String initialiseGameplay(@QueryParam("gameID") int id) throws IOException{
		String json_output = gameControllers.get(id).initialiseGame();
		checkAndWriteToDatabase(id);
		return json_output;
	}

	@GET
	@Path("game/startARound")
	public String startARound(@QueryParam("gameID") int id) throws IOException{
		String json_output = gameControllers.get(id).startARound();
		checkAndWriteToDatabase(id);
		return json_output;
	}

	@GET
	@Path("game/chosenCategory")
	public String chosenCategory(@QueryParam("category") String category, @QueryParam("gameID") int id) throws IOException{
		String json_output = gameControllers.get(id).chosenCategory(category);
		//checkAndWriteToDatabase(id);
		return json_output;
	}

	@GET
	@Path("game/quit")
	public String quit(@QueryParam("gameID") int id) throws IOException{
		String json_output = gameControllers.get(id).quit();
		checkAndWriteToDatabase(id);
		return json_output;
	}

	private void checkAndWriteToDatabase(int id) {
		// if state of game is overall winner
		if (gameControllers.get(id).getState() == OnlineGameplayController.OVERALL_WINNER) { 
			writeToDatabase(id);
		}
	}

	private void writeToDatabase(int id) {
		PersistentGameData game_data = gameControllers.get(id).getGameData();

		if(game_data.data_to_be_logged() == true) {
			db.addGameStats(game_data.get_number_of_rounds(), game_data.get_winning_player(), game_data.get_number_of_draws());

			int[] player_wins = game_data.get_player_wins();
			for(int i=0; i<player_wins.length; i++) {
				db.addRoundStats(i, player_wins[i]);
			}
			db.updateDatabase();
			game_data.set_logger(false); // set to false so we do not rewrite the same data
		}
	}

	//################## examples and tests

	//	@GET
	//	@Path("/showCard")
	//	public String showCard() throws IOException{
	//		
	//		Card c = new Card("Name", new String[] {"0", "1", "2", "3"}, new int[] {1, 2, 3});
	//		
	//		return oWriter.writeValueAsString(c);	
	//	}
	//	
	//	
	//	@GET
	//	@Path("/helloJSONList")
	//	/**
	//	 * Here is an example of a simple REST get request that returns a String.
	//	 * We also illustrate here how we can convert Java objects to JSON strings.
	//	 * @return - List of words as JSON
	//	 * @throws IOException
	//	 */
	//	public String helloJSONList() throws IOException {
	//		
	//		List<String> listOfWords = new ArrayList<String>();
	//		listOfWords.add("Hello");
	//		listOfWords.add("World!");
	//		
	//		// We can turn arbatory Java objects directly into JSON strings using
	//		// Jackson seralization, assuming that the Java objects are not too complex.
	//		String listAsJSONString = oWriter.writeValueAsString(listOfWords);
	//		
	//		return listAsJSONString;
	//	}
	//	
	//	@GET
	//	@Path("/helloWord")
	//	/**
	//	 * Here is an example of how to read parameters provided in an HTML Get request.
	//	 * @param Word - A word
	//	 * @return - A String
	//	 * @throws IOException
	//	 */
	//	public String helloWord(@QueryParam("Word") String Word) throws IOException {
	//		return "Hello "+Word;
	//	}

}
