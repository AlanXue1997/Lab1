package GUI;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class JUnitTest {
	
	String message1 = "No bridge words from “are” to “men”!";
	String message2 = "No bridge words from “men” to “be”!";
	String message3 = "The bridge words from “life” to “and” is:liberty ";
	String message4 = "The bridge words from “that” to “men” is:all they ";
	String message5 = "“exciting” doesn't exist!";
	String message6 = "“exciting” and “synergies” doesn't exist!";
	String message7 = "are => created => equal => that => all => men 路径长度为： 5";
	GUI gui = new GUI();
	
	@Before
	public void setUp() {
		gui.G = new Picture("C:\\Users\\Alan\\Documents\\input.txt");
		gui.g = gui.G.getGraphDraft();
	}
	
	@Test
	public void testBlack() {
		assertEquals(message1, gui.queryBridgeWords("are", "men"));
		assertEquals(message2, gui.queryBridgeWords("men", "be"));
		assertEquals(message3, gui.queryBridgeWords("life", "and"));
		assertEquals(message4, gui.queryBridgeWords("that", "men"));
		assertEquals(message5, gui.queryBridgeWords("that", "exciting"));
		assertEquals(message6, gui.queryBridgeWords("exciting", "synergies"));
	}
	
	@Test
	public void testWrite() {
		assertEquals(message7, gui.calcShortestPath("are", "men"));
	}
}
