package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Scanner;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import commandline.CLIView;
import common.Card;

public class CLIViewTest {
	
	CLIView cli_view;
	
	/**
	 * This test ensures that the CLI will only accept a valid category
	 * The test requires some user input in order to run, but the getCategory
	 * method will not end until you have entered a valid category
	 * (apple, pear or blueberry in this case)
	 * 
	 */
	@Test
	public void getCategoryTest() {
		
		cli_view = new CLIView();

		//Mock the card class to make sure the CLIView class is the only thing being tested
		Card mock_card = Mockito.mock(Card.class);
		
		String[] header_string = {"apple","pear","blueberry"};
		Mockito.when(mock_card.getHeaders()).thenReturn(header_string);
		
		String actual_string = cli_view.getCategory(mock_card);

		//Create an arrayList of valid options which the output of the getCategory method is compared to
		ArrayList<String> test_array = new ArrayList<String>();
		for(int i=0;i<header_string.length; i++) {
			test_array.add(header_string[i]);
		}
		
		//Test passes if the return of the getCategory method is a valid category
		assertTrue(test_array.contains(actual_string));
	}
	
	/**
	 * As in the previous test, this test requires some user input
	 * and tests to make sure the gameOrStatorQuit method will only terminate
	 * when a valid input has been entered by the user (0, 1 or 2)
	 * 
	 */
	@Test
	public void gameOrStateOrQuitTest() {
		
		cli_view = new CLIView();
		
		//Create an arrayList of valid options to compare the return of the function to (0, 1 or 2)
		ArrayList<Integer> valid_options = new ArrayList<Integer>();
		for(int i=0; i<3; i++) {
			valid_options.add(i);
		}
		
		//Test passes if the return of the method is either 0, 1 or 2
		assertTrue(valid_options.contains(cli_view.gameOrStatorQuit()));
		
	}

}
