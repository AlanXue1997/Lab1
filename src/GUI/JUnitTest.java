package GUI;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class JUnitTest {	
	String message1 = "The bridge words from “that” to “men” is:all they ";
	String message2 = "No bridge words from “that” to “rights”!";
	String message3 = "“none” doesn't exist!";
	String message4 = "“none” doesn't exist!";
	String message5 = "“none” and “null” doesn't exist!";
	String message6 = "“exciting” and “synergies” doesn't exist!";
	GUI gui = new GUI();
	
	@Before
	public void setUp() {
		gui.G = new Picture("C:\\Users\\Alan\\Documents\\input.txt");
		gui.g = gui.G.getGraphDraft();
	}
	
	@Test
	public void testBlack() {
		assertEquals(message1, gui.queryBridgeWords("that", "men"));
		assertEquals(message2, gui.queryBridgeWords("that", "rights"));
		assertEquals(message3, gui.queryBridgeWords("that", "none"));
		assertEquals(message4, gui.queryBridgeWords("none", "rights"));
		assertEquals(message5, gui.queryBridgeWords("none", "null"));
		assertEquals(message6, gui.queryBridgeWords("exciting", "synergies"));
	}
}
