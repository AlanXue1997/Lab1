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
	String message7 = "we:271\n" + 
			"hold:0\n" + 
			"these:1\n" + 
			"truths:2\n" + 
			"to:3\n" + 
			"be:4\n" + 
			"self:5\n" + 
			"evident:6\n" + 
			"that:5\n" + 
			"all:6\n" + 
			"men:7\n" + 
			"are:2\n" + 
			"created:3\n" + 
			"equal:4\n" + 
			"they:6\n" + 
			"endowed:3\n" + 
			"by:4\n" + 
			"their:5\n" + 
			"creator:6\n" + 
			"with:7\n" + 
			"certain:8\n" + 
			"unalienable:9\n" + 
			"rights:10\n" + 
			"among:6\n" + 
			"life:3\n" + 
			"liberty:4\n" + 
			"and:5\n" + 
			"the:6\n" + 
			"pursuit:7\n" + 
			"of:8\n" + 
			"happiness:9\n";
	String message8 = "the and and are not reachable!";
	String message9 = "ss doesn't exist!";
	String message10 = "ss doesn't exist!";
	String message11 = "ss and end doesn't exist!";
	String message12 = "truths => to => be => self => evident => that => among => these 路径长度为： 7";
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
	
	@Test
	public void testWrite() {
		assertEquals(message7, gui.calcShortestPath("hold", ""));
		assertEquals(message8, gui.calcShortestPath("the", "and"));
		assertEquals(message9, gui.calcShortestPath("ss", "and"));
		assertEquals(message10, gui.calcShortestPath("the", "ss"));
		assertEquals(message11, gui.calcShortestPath("ss", "end"));
		assertEquals(message12, gui.calcShortestPath("truths", "these"));
	}
	
}
