package tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import commandline.CLIView;
import common.Card;

public class CLIViewTest {
	
	CLIView cli_view;
	
	@Test
	public void getCategoryTest() {
		
		cli_view = new CLIView();

		Card mock_card = Mockito.mock(Card.class);
		
		String[] header_string = {"apple","pear","blueberry"};
		Mockito.when(mock_card.getHeaders()).thenReturn(header_string);
		
		String actual_string = cli_view.getCategory(mock_card);
		
		ArrayList<String> test_array = new ArrayList<String>();
		
		for(int i=0;i<header_string.length; i++) {
			test_array.add(header_string[i]);
		}
		
		//This requires some user input but should not end the test until the user has entered a valid string
		assertTrue(test_array.contains(actual_string));
	}
	
	@Test
	public void gameOrStateOrQuitTest() {
		
		cli_view = new CLIView();
		
		ArrayList<Integer> valid_options = new ArrayList<Integer>();
		for(int i=0; i<3; i++) {
			valid_options.add(i);
		}
		
		assertTrue(valid_options.contains(cli_view.gameOrStatorQuit()));
		
	}

}
