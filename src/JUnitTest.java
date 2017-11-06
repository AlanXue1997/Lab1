import static org.junit.Assert.*;

import org.junit.Test;

public class JUnitTest {
	
	String message1 = "No bridge words from “are” to “men”!";
	String message2 = "No bridge words from “men” to “be”!";
	String message3 = "The bridge words from “life” to “and” is:liberty ";
	String message4 = "The bridge words from “that” to “men” is:all they ";
	String message5 = "“exciting” doesn't exist!";
	String message6 = "“exciting” and “synergies” doesn't exist!";
	GUI gui = new GUI();
	
	@Test
	public void test() {
		gui.G = new Picture("C:\\Users\\Alan\\Documents\\input.txt");
		gui.g = gui.G.getGraphDraft();
		
		
		assertEquals(message1, gui.queryBridgeWords("are", "men"));
		assertEquals(message2, gui.queryBridgeWords("men", "be"));
		assertEquals(message3, gui.queryBridgeWords("life", "and"));
		assertEquals(message4, gui.queryBridgeWords("that", "men"));
		assertEquals(message5, gui.queryBridgeWords("that", "exciting"));
		assertEquals(message6, gui.queryBridgeWords("exciting", "synergies"));
	}
}
