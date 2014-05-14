package minesweeper.server;

import java.net.*;
import java.io.*;

import minesweeper.Board;



/** This server is threadsafe because all the methods that
 * access data members that might be shared such as 
 * numPlayers and board are synchronized */
public class MinesweeperServer {

	private final static int PORT = 4444;
	private ServerSocket serverSocket;
	private static Board board;
	private static boolean debug;
	private static String bombFlag = "";
	private int numPlayers = 0;

	/**
	 * Make a MinesweeperServer that listens for connections on port.
	 * 
	 * @param port
	 *            port number, requires 0 <= port <= 65535.
	 */
	public MinesweeperServer(int port) throws IOException {
		serverSocket = new ServerSocket(port);
	}

	/**
	 * increments the number of players
	 * 
	 */
	public synchronized void incrementPlayers() {
		numPlayers++;
	}

	/**
	 * decrements the number of players
	 * 
	 */
	public synchronized void decrementPlayers() {
		numPlayers--;
	}

	/**
	 * starts a thread for a single client
	 * 
	 * @param the socket object returned from serverSocket.accept()
	 * 
	 */
	public void spawnThread(final Socket socket) {
		Thread t = new Thread(new Runnable() {
			public void run() {

				try {
					PrintWriter out = new PrintWriter(socket.getOutputStream(),
							true);
					BufferedReader in = new BufferedReader(
							new InputStreamReader(socket.getInputStream()));

					out.println("Welcome to Minesweeper.  "
							+ numPlayers
							+ " people are playing including you.  Type 'help' for help.");

					for (String line = in.readLine(); line != null; line = in
							.readLine()) {
						String output = MinesweeperServer.handleRequest(line);
						// System.out.println(output == null);
						if (bombFlag.equals("F")) {
							out.println("BOOM!");
							socket.close();
							decrementPlayers();
							bombFlag = "";
						} else if (bombFlag.equals("T")) {
							out.println("BOOM!");
							bombFlag = "";
							
						}

						else if (output.equals("bye")) {
							socket.close();
							decrementPlayers();
						}
						
						if (output != null) {
							out.println(output);
						}
					}
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		});

		t.start();
	}

	/**
	 * Run the server, listening for multiple client connections and handling
	 * them. Never returns unless an exception is thrown.
	 * 
	 * @throws IOException
	 *             if the main server socket is broken (IOExceptions from
	 *             individual clients do *not* terminate serve()).
	 */
	public void serve() throws IOException {

		boolean listening = true;

		while (listening) {
			// new MinesweeperMultiServerThread(serverSocket.accept()).start();
			spawnThread(serverSocket.accept());
			incrementPlayers();
		}
		// Socket socket = serverSocket.accept();
		// handle the client
		/*
		 * try { handleConnection(socket); } catch (IOException e) {
		 * e.printStackTrace(); // but don't terminate serve() } finally {
		 * socket.close(); }
		 */

		serverSocket.close();
		numPlayers = 0;

	}

	/*    *//**
	 * Handle a single client connection. Returns when client disconnects.
	 * 
	 * @param socket
	 *            socket where client is connected
	 * @throws IOException
	 *             if connection has an error or terminates unexpectedly
	 */
	/*
	 * private void handleConnection(Socket socket) throws IOException {
	 * 
	 * BufferedReader in = new BufferedReader(new
	 * InputStreamReader(socket.getInputStream())); PrintWriter out = new
	 * PrintWriter(socket.getOutputStream(), true);
	 * 
	 * try { for (String line = in.readLine(); line != null; line =
	 * in.readLine()) { String output = handleRequest(line); if(output != null)
	 * { out.println(output); } } } finally { out.close(); in.close(); } }
	 */

	/**
	 * handler for client input
	 * 
	 * make requested mutations on game state if applicable, then return
	 * appropriate message to the user
	 * 
	 * @param input
	 * @return
	 */
	public static String handleRequest(String input) {
		//System.out.println("handling");
		String regex = "(look)|(dig \\d+ \\d+)|(flag \\d+ \\d+)|(deflag \\d+ \\d+)|(help)|(bye)";

		if (!input.matches(regex)) {
			String output = "invalid command";
			return output;
		}

		String[] tokens = input.split(" ");
		if (tokens[0].equals("look")) {
			String output = board.toString();
			return output;
		} else if (tokens[0].equals("help")) {
			// 'help' request
			String output = "Do you need help? Please go to http://en.wikipedia.org/wiki/Minesweeper_(video_game)";
			return output;
		} else if (tokens[0].equals("bye")) {
			// 'bye' request
			String output = "bye";
			return output;
		}

		else {
			int x = Integer.parseInt(tokens[1]);
			int y = Integer.parseInt(tokens[2]);
			if (tokens[0].equals("dig")) {
				// 'dig x y' request
				if ((x < 0) || (y < 0) || (x > board.getSize())
						|| (y > board.getSize())) {
					return board.toString();
				}

				else if (board.hasBomb(x, y)) {
					if (debug) {
						bombFlag = "T";
						board.dig(x, y);
						return board.toString();
					} else if (debug == false) {
						bombFlag = "F";
						board.dig(x, y);
						return board.toString();
					}

				}

				else if (board.getBoard(x, y).equals("-")) {
					board.dig(x, y);
					return board.toString();
				}

			} else if (tokens[0].equals("flag")) {
				// 'flag x y' request
				if ((x >= 0) && (y >= 0) && (x < board.getSize())
						&& (y < board.getSize())) {
					if (board.getBoard(x, y) == "-") {
						board.flag(x, y);
					}

				}
				return board.toString();

			} else if (tokens[0].equals("deflag")) {
				// 'deflag x y' request
				if ((x >= 0) && (y >= 0) && (x < board.getSize())
						&& (y < board.getSize())) {
					if (board.getBoard(x, y) == "F") {
						board.deflag(x, y);
					}

				}
				return board.toString();
			}
		}
		// should never get here
		return "";
	}

	/**
	 * Start a MinesweeperServer running on the default port.
	 * 
	 */
	public static void main(String[] args) {

		try {
			if (args[0].equals("true")) {
				debug = true;
			} else if (args[0].equals("false")) {
				debug = false;
			}

			if (args.length > 1) {
				if (args[1].equals("-s")) {
					int size = Integer.parseInt(args[2]);
					if (size > 0) {
						board = new Board(size);
						//System.out.println(board.toString());
					}
				} else if (args[1].equals("-f")) {
					String filename = args[2];
					board = Board.fromFile(filename);
				}
			}

			int[][] state = board.getBoardState();

			for (int i = 0; i < state.length; i++) {
				for (int j = 0; j < state.length; j++) {
					System.out.print(state[i][j]);
				}
				System.out.println("");
			}
			MinesweeperServer server = new MinesweeperServer(PORT);
			server.serve();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}