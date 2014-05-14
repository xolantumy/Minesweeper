package minesweeper;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


/** Board is a threadsafe datatype with a simple rep. */

public class Board {

	private final int size;
	private String[][] board;
	public int[][] state;
	// Rep invariant: 
    //   board, state != null

    
    // Concurrency argument: this class is threadsafe because
    // all the methods that access data members such as board and state
	// that might be shared are synchronized

	private synchronized void checkRep() {	
		assert size != 0;
		assert board != null;
		assert state != null;
		
	}
	
	/**
	 * Board constructor 
	 * 	constructs a Board object 
	 * 
	 * @param size number of squares for board
	 */
	
	public Board(int size) {
		this.size = size;
		this.board = new String[size][size];
		String untouched = "-";
		this.state = new int[size][size];
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {	
				this.board[i][j] = untouched;
				if (Math.random() < 0.25)
					this.state[i][j] = 1;
				else {
					this.state[i][j] = 0;
				}
			}
		}
		
		checkRep();
	
	}
	

	
	/**
	 * Reads in a file containing a Minesweeper board
	 * 
	 * 
	 * @param filename
	 *            of file containing board. The file should contain one line
	 *            per row, with each square in the row represented by a 0 or a 1.
	 *            
	 * @return int size of board
	 * @throws IOException
	 *             if file reading encounters an error
	 * 
	 */
	public static Board fromFile(String filename) throws IOException {
		int numLines = 0;
		boolean properFormat = false;
		ArrayList<String[]> tokens = new ArrayList<String[]>();
		

		// Open the file that is the first
		// command line parameter
		FileInputStream fstream = new FileInputStream(filename);
		// Get the object of DataInputStream
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;

		// Read File Line By Line

		while ((strLine = br.readLine()) != null) {
			String[] line = strLine.split(" ");
			tokens.add(line);
		}
		
		numLines  = tokens.size();
		
		for (int i=0; i < numLines; i++) {
			if (tokens.get(i).length == numLines) {
				properFormat = true;
			}
		}
		
		Board board = new Board(numLines);
		if (properFormat) {	
		for (int i=0; i < numLines; i++) {
			for (int j=0; j < numLines; j++) {
				int value = Integer.parseInt(tokens.get(i)[j]);
				if (value == 1) {
					board.state[i][j] = 1;
				}
				else {
					board.state[i][j] = 0;
				}
			}
		}
		}
		
		// Close the input stream
		in.close();



		return board;
	}
	
	/**
	 * returns a String representation of the board
	 * 
	 * @return String representation of the board
	 */
	public synchronized String toString() {
		String boardRep = "";
		String space = " ";
	
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				boardRep += this.board[i][j];
				if (j != size-1)
					boardRep += space;
			}
			boardRep += "\n";
		}
		
		return boardRep;
		
	}
	
	/**
	 * Digs up a square by checking if it has a bomb,
	 * checking its neighbors and calling dig recursively on its neighbors if
	 * none of the neighbor sqaures have bombs.
	 * 
	 * @param x x-index of the square
	 * @param y y-index of the square
	 */
	public synchronized void dig(int x, int y) {
		//System.out.println("this is happening");
		String untouched = "-";
		String space = " ";
		int neighbors = 0;

		ArrayList<Integer> bombx = new ArrayList<Integer>();
		ArrayList<Integer> bomby = new ArrayList<Integer>();

	
		//count neighbors
		//up
		if (y-1 >= 0) {
			if (!hasBomb(x,y-1)) {
				bombx.add(x);
				bomby.add(y-1);
			}
			else if (hasBomb(x,y-1))
				neighbors++;
		}
		
		//down
		if (y+1 < size) {
			if (!hasBomb(x,y+1)) {
				bombx.add(x);
				bomby.add(y+1);
			}
			else if (hasBomb(x,y+1))
				neighbors++;
		}
		
		//left
		if (x-1 >= 0) {
			if (!hasBomb(x-1,y)) {
				bombx.add(x-1);
				bomby.add(y);
			}
			else if (hasBomb(x-1, y))
				neighbors++;
		}
		
		//right
		if (x+1 < size) {
			if (!hasBomb(x+1, y)) {
				bombx.add(x+1);
				bomby.add(y);
			}
			else if (hasBomb(x+1, y))
				neighbors++;
		}
		
		//up left
		if ((x-1 >= 0) && (y-1 >= 0)) {
			if (!hasBomb(x-1,y-1)) {
				bombx.add(x-1);
				bomby.add(y-1);
			}
			else if (hasBomb(x-1,y-1))
				neighbors++;
		}
		
		//up right
		if ((x+1 < size) && (y-1 >= 0)) {
			if (!hasBomb(x+1,y-1)) {
				bombx.add(x+1);
				bomby.add(y-1);
			}
			else if (hasBomb(x+1,y-1))
				neighbors++;
		}
		
		//down left
		if ((x-1 >= 0) && (y+1 < size)) {
			if (!hasBomb(x-1,y+1)) {
				bombx.add(x-1);
				bomby.add(y+1);
			}
			else if (hasBomb(x-1,y+1))
				neighbors++;
		}
		
		//down right
		if ((x+1 < size) && (y+1 < size)) {
			if (!hasBomb(x+1,y+1)) {
				bombx.add(x+1);
				bomby.add(y+1);
			}
			else if (hasBomb(x+1,y+1))
				neighbors++;
		}
		
		if (board[x][y] == untouched) {
			// if state is dug, recursively dig all other neighbor squares who have no bomb
			if (hasBomb(x,y)) {
				this.state[x][y] = 0;
			}
			
			if (neighbors == 0) {
				this.board[x][y] = space; 
				//System.out.println("this is happening");
				for (int i= 0; i < bombx.size(); i++) {
					dig(bombx.get(i), bomby.get(i));
					//System.out.println("recursively digging: " + bombx.get(i) + ", " + bomby.get(i));
				}
			}
			else if (neighbors > 0) {
				//System.out.println("this is happening: " +neighbors);
				this.board[x][y] = Integer.toString(neighbors);
			}
		}

	}
	
	
	/**
	 * Flags a square by changing its value in this.board
	 * @param x x-index of square
	 * @param y y-index of square
	 */
	public synchronized void flag(int x, int y) {
		String flag = "F";
		this.board[x][y] = flag;
	}
	
	
	/**
	 * Deflags a square by changing its value in the board's representation
	 * @param x x-index of square
	 * @param y y-index of square
	 */
	public synchronized void deflag(int x, int y) {
		String untouched = "-";
		if (this.board[x][y] == "F")
			this.board[x][y] = untouched;
	}
	
	
	/**
	 * Returns the size (number of rows or columns) of the board
	 * @return size
	 */
	public synchronized int getSize() {
		return this.size;
	}
	
	/**
	 * Returns the value of the square in the board's representation
	 * @param x x-index of square
	 * @param y y-index of square
	 */
	public synchronized String getBoard(int x, int y) {
		return this.board[x][y];
	}
	
	/**
	 * Returns the state of the board
	 * @return state
	 */
	public synchronized int[][] getBoardState() {
		return this.state;
	}
	
	/**
	 * Checks if the square has a bomb by checking the state of the board
	 * @param x x-index of square
	 * @param y y-index of square
	 */
	public synchronized boolean hasBomb(int x, int y) {
		return state[x][y] == 1;
	}
}
