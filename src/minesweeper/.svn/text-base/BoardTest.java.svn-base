package minesweeper;
import static org.junit.Assert.*;

import java.util.Arrays;
import org.junit.Test;


public class BoardTest {

	
    // make sure assertions are turned on!  
    // we don't want to run test cases without assertions too.
    // see the handout to find out how to turn them on.
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
    
    
    // test dig without a bomb
    @Test
    public void test1(){
    	int size = 1;
    	Board b = new Board(size);
    
    	try {
    		b = Board.fromFile("src/minesweeper/server/minesweeper.txt");
		}
    	
		catch (Exception e) {
			System.out.println(e);
		}
		
		String expectedOutput = "- - - - - - - - -" + "\n" +
				"- - - - - - - - -" + "\n" +
				"- - - - - - - - -" + "\n" +
				"- - - - - - - - -" + "\n" +
				"- - - - - - - - -" + "\n" +
				"- - - - - - - - -" + "\n" +
				"- - - - - - - - -" + "\n" +
				"- - - - - - - - -" + "\n" +
				"- - - - - - - - -" + "\n";
		
		//System.out.println(b.toString());
		assertEquals(expectedOutput,b.toString());
    }
    
    @Test
    public void test2(){
    	int size = 1;
    	Board b = new Board(size);
    
    	try {
    		b = Board.fromFile("src/minesweeper/server/minesweeper.txt");
		}
    	
		catch (Exception e) {
			System.out.println(e);
		}
		
		b.dig(3,4);
		
		String expectedOutput = "- - - - - - - - -" + "\n" +
		"- - - - - - - - -" + "\n" +
		"- - - - - - - - -" + "\n" +
		"- - - - 2 - - - -" + "\n" +
		"- - - - - - - - -" + "\n" +
		"- - - - - - - - -" + "\n" +
		"- - - - - - - - -" + "\n" +
		"- - - - - - - - -" + "\n" +
		"- - - - - - - - -" + "\n";
		
		//System.out.println(b.toString());
	/*	int[][] state = b.getBoardState();
	 * 
		for (int i = 0; i < state.length; i++) {
			for (int j=0; j < state.length; j++) {
				System.out.print(state[i][j]);
			}
			System.out.println("\n");
		}*/
		
		assertEquals(expectedOutput,b.toString());
    }
    
    //dig a square with a bomb, whose neighbors have no bombs
    @Test
    public void test3(){
    	int size = 1;
    	Board b = new Board(size);
    
    	try {
    		b = Board.fromFile("src/minesweeper/server/minesweeper.txt");
		}
    	
		catch (Exception e) {
			System.out.println(e);
		}
		System.out.println(b.toString());
		b.dig(0,3);
		String expectedOutput = 
		"                 " + "\n" +
		"    1 1 1        " + "\n" +
		"    1 - 1        " + "\n" +
		"  1 2 - 2 1      " + "\n" +
		"  1 - - - 1      " + "\n" +
		"  2 - - - 2      " + "\n" +
		"  1 - - - 1      " + "\n" +
		"  1 2 - 2 1      " + "\n" +
		"    1 - 1        " + "\n";

		
		System.out.println(b.toString());

		
		assertEquals(expectedOutput,b.toString());
    }

    //dig a square with a bomb
    @Test
    public void test4(){
    	int size = 1;
    	Board b = new Board(size);
    
    	try {
    		b = Board.fromFile("src/minesweeper/server/minesweeper.txt");
		}
    	
		catch (Exception e) {
			System.out.println(e);
		}
		
		b.dig(4,3);
		String expectedOutput = "- - - - - - - - -" + "\n" +
		"- - - - - - - - -" + "\n" +
		"- - - - - - - - -" + "\n" +
		"- - - - - - - - -" + "\n" +
		"- - - 3 - - - - -" + "\n" +
		"- - - - - - - - -" + "\n" +
		"- - - - - - - - -" + "\n" +
		"- - - - - - - - -" + "\n" +
		"- - - - - - - - -" + "\n";

		
		//System.out.println(b.toString());

		
		assertEquals(expectedOutput,b.toString());
    }
    
    //flag a square 
    @Test
    public void test5(){
    	int size = 1;
    	Board b = new Board(size);
    
    	try {
    		b = Board.fromFile("src/minesweeper/server/minesweeper.txt");
		}
    	
		catch (Exception e) {
			System.out.println(e);
		}
		
		b.flag(4,3);
		String expectedOutput = "- - - - - - - - -" + "\n" +
		"- - - - - - - - -" + "\n" +
		"- - - - - - - - -" + "\n" +
		"- - - - - - - - -" + "\n" +
		"- - - F - - - - -" + "\n" +
		"- - - - - - - - -" + "\n" +
		"- - - - - - - - -" + "\n" +
		"- - - - - - - - -" + "\n" +
		"- - - - - - - - -" + "\n";

		
		//System.out.println(b.toString());

		
		assertEquals(expectedOutput,b.toString());
    }
    
    @Test
    public void test6(){
    	int size = 1;
    	Board b = new Board(size);
    
    	try {
    		b = Board.fromFile("src/minesweeper/server/minesweeper.txt");
		}
    	
		catch (Exception e) {
			System.out.println(e);
		}
		
		b.deflag(4,3);
		String expectedOutput = "- - - - - - - - -" + "\n" +
		"- - - - - - - - -" + "\n" +
		"- - - - - - - - -" + "\n" +
		"- - - - - - - - -" + "\n" +
		"- - - - - - - - -" + "\n" +
		"- - - - - - - - -" + "\n" +
		"- - - - - - - - -" + "\n" +
		"- - - - - - - - -" + "\n" +
		"- - - - - - - - -" + "\n";

		
		//System.out.println(b.toString());

		
		assertEquals(expectedOutput,b.toString());
    }
    
}
