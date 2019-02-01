package online.dwResources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import online.configuration.TopTrumpsJSONConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import common.Card;
import common.Database;
import common.GameplayController;
import common.GetDeckModel;

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
	private RoundRequestHandler requestHandler; // should implement the same interface as CLIView
	private GameplayController gameController;
	private Database db;
	private GetDeckModel deckModel;
	private int number_of_human_players = 1;
	private int number_of_ai_players;

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
		
		requestHandler = new RoundRequestHandler();
		db = new Database();
		deckModel = new GetDeckModel(conf.getDeckFile());
		number_of_ai_players = conf.getNumAIPlayers();
		
	}
	
	// ----------------------------------------------------
	// Add relevant API methods here
	// ----------------------------------------------------
	@GET
	@Path("/initialiseGameplay")
	public String initialiseGameplay() {
		gameController = new GameplayController(deckModel, view, 1, number_of_ai_players, false); // Replace view with interfacable object
		gameController.topTrumpsGame();
		return requestHandler.getStartOfRoundJSON(); // returns round number, cards in deck, active player, card drawn by human
	}
	
	@GET
	@Path("/decideOnCategory")
	public String getHumanDecision(@QueryParam("Choice") String choice) throws IOException{
		// HOW DO WE TELL THE GAMEPLAYCONTROLLER HUMAN CHOICE BEFORE IT ASKS FOR IT???????????????
		return requestHandler.getRestOfHumanChoiceRoundJSON();
		
	}
	
	
	//################## examples and tests
	
	@GET
	@Path("/showCard")
	public String showCard() throws IOException{
		
		Card c = new Card("Name", new String[] {"0", "1", "2", "3"}, new int[] {1, 2, 3});
		
		return oWriter.writeValueAsString(c);	
	}
	
	
	@GET
	@Path("/helloJSONList")
	/**
	 * Here is an example of a simple REST get request that returns a String.
	 * We also illustrate here how we can convert Java objects to JSON strings.
	 * @return - List of words as JSON
	 * @throws IOException
	 */
	public String helloJSONList() throws IOException {
		
		List<String> listOfWords = new ArrayList<String>();
		listOfWords.add("Hello");
		listOfWords.add("World!");
		
		// We can turn arbatory Java objects directly into JSON strings using
		// Jackson seralization, assuming that the Java objects are not too complex.
		String listAsJSONString = oWriter.writeValueAsString(listOfWords);
		
		return listAsJSONString;
	}
	
	@GET
	@Path("/helloWord")
	/**
	 * Here is an example of how to read parameters provided in an HTML Get request.
	 * @param Word - A word
	 * @return - A String
	 * @throws IOException
	 */
	public String helloWord(@QueryParam("Word") String Word) throws IOException {
		return "Hello "+Word;
	}
	
}
