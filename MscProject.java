
public class MscProject {
	public static void main(String[] args) {
		String [] headers = {"description", "Range", "Speed", "Velocity", "Strength", "intellect"};
		int [] inputValues = {3, 5, 1, 8, 7};
		
		
		CLIView cliView = new CLIView();
		Card card = new Card("T-rex", headers, inputValues);
		cliView.welcomeMessage();
		cliView.numberOfPlayersPlaying();
		cliView.beginningOfRound(14, 1);
		String category = cliView.getCategory(card);
		System.out.println(category);
		cliView.theWinnerIs(3);
		cliView.overallWinner(0);
		int number = cliView.gameOrStatorQuit();
		System.out.println(number);
		
		
		
	}
}
