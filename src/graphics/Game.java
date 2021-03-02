package graphics;
import ai.AI;
import ai.BestResponse;
import ai.Board;
import ai.Move;
import modes.AIvsAI;
import modes.ClientServer;
import modes.PlayervsAI;
import modes.PlayervsPlayer;
import client_server.Server;
import enumerations.GameMode;
import enumerations.GuiStyle;
import utilities.Constants;
import utilities.GameParameters;
import utilities.ResourceLoader;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * A class that describes the game's graphical interface
 * @author Dmitriy Stepanov
 */
public class Game {
	public static JFrame game;
	public static JPanel panel;
	public static GridLayout layout;
	
	public static PlayervsPlayer[] playervsPlayers;
	public static PlayervsAI[] playervsAIS;
	public static AIvsAI[] AIvsAIS;
	public static ClientServer[] clientServers;
	
	public static Board board;
	public static JMenuItem undo;
	public static JMenuItem redo;
	
	// These Stack objects are used for the "Undo" and "Redo" functionalities
	static Stack<Board> undoBoards = new Stack<>();
	static Stack<Board> redoBoards = new Stack<>();

	private static final String TITLE_HELP = "Help";
	private static final String TXTHELP = "<html><center><H2>Help</H2></center><center>Players take turns putting signs " +
			"on the free cells of the 3x3 field (one is always tic-tac-toe, the other is always zeros). The first player " +
			"to line up 3 of his pieces vertically, horizontally, or diagonally wins. The first move is made by the " +
			"player who puts the crosses. Usually, at the end of the game, the winning side crosses out its three " +
			"signs (zeros or crosses), which make up a continuous row.</center></html>";

	private static final String TITLE_ABOUT = "About";
	private static final String TXTABOUT = "<html><center><H2>About</H2></center><center>Initially, the game had a " +
			"different name, namely \"heriki-oniki\". It was called so until the spelling reform, carried out in 1918. The " +
			"name is explained by the fact that our ancestors perceived this fun not with the figures of a cross and a " +
			"zero, but with the letters of the alphabet \"x\" and \"o\", which were then called\" her \"and\"it\". " +
			"\"Heriki-oniki\" was the most popular board game of the time.<br>" +
			"There are many theories about the origin of this game. However, certain versions have some shortcomings. " +
			"But there are a few more likely ones. <br>" +
			"Nothing is known for sure about the fate of tic-tac-toe, but there is a hypothesis that it was invented " +
			"by a French mathematician, and quite by accident, while solving a three-level system of equations." +
			"There was a time when this fun got so popular that there were several themed cafes with this game. Moreover, " +
			"such establishments had a fairly high demand. There were cases when playing tic-tac-toe, people missed " +
			"very important events, including executions, after which the church banned this childishness. But this did " +
			"not greatly affect the number of players, just everyone started playing secretly.<br>" +
			"Another theory suggests that the Italian artist's painting depicted the Pope playing tic-tac-toe with " +
			"himself. Over time, Kazimir Malevich made a copy of this painting." +
			"And a few more interesting facts: by the end of the XIX century, 14 songs, 3 waters, 6 stories and about " +
			"two hundred and fifty articles were devoted to tic-tac-toe in Europe.</center></html>";

	public static Image windowIcon;
	private static JFrame moreInform;

	/**
	 * Constructor - creating a new game
	 * @see Game#Game()
	 */
	public Game() {
		configureGuiStyle();
		game = new JFrame("TicTacToe");
		game.setSize(500, 500);
		game.setResizable(false);
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		game.setLocation((int) (screenSize.getWidth() - game.getWidth()) / 2,
				(int) (screenSize.getHeight() - game.getHeight()) / 2);
	}

	public static BufferedImage loadImage(String path){
		try {
			return ImageIO.read(Game.class.getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}

	private static void addMenu() {
		windowIcon = loadImage("/tictactoe.png");
		Game.game.setIconImage(windowIcon);

		JMenuBar gameMenu = new JMenuBar();
		JMenu game = new JMenu("Game");
		gameMenu.add(game);

		JMenuItem newgame = new JMenuItem("New Game");
		KeyStroke ctrlNKeyStroke = KeyStroke.getKeyStroke("control N");
		newgame.setAccelerator(ctrlNKeyStroke);
		game.add(newgame);
		newgame.addActionListener(e -> {
			if (GameParameters.gameMode == GameMode.PLAYER_VS_AI)
				createPlayerVsAINewGame();
			else if (GameParameters.gameMode == GameMode.PLAYER_VS_PLAYER)
				createPlayerVsPlayerNewGame();
			else if (GameParameters.gameMode == GameMode.AI_VS_AI)
				createAIVsAINewGame();
			else if (GameParameters.gameMode == GameMode.CLIENT_SERVER)
				createClientServerNewGame();

			undoBoards.clear();
			redoBoards.clear();

			undo.setEnabled(false);
			redo.setEnabled(false);
			Board.printBoard(board.getGameBoard());
		});

		undo = new JMenuItem("Undo");
		KeyStroke ctrlUKeyStroke = KeyStroke.getKeyStroke("control U");
		undo.setAccelerator(ctrlUKeyStroke);
		game.add(undo);
		undo.addActionListener(e -> undo());

		redo = new JMenuItem("Redo");
		KeyStroke ctrlRKeyStroke = KeyStroke.getKeyStroke("control R");
		redo.setAccelerator(ctrlRKeyStroke);
		game.add(redo);
		redo.addActionListener(e -> redo());

		JMenuItem settings = new JMenuItem("Settings");
		KeyStroke ctrlSKeyStroke = KeyStroke.getKeyStroke("control S");
		settings.setAccelerator(ctrlSKeyStroke);
		game.add(settings);
		settings.addActionListener(e -> {
			Settings gameSettings = new Settings(windowIcon);
			gameSettings.setVisible(true);
		});

		JMenuItem helpGame = new JMenuItem("Help");
		KeyStroke ctrlHKeyStroke = KeyStroke.getKeyStroke("control H");
		helpGame.setAccelerator(ctrlHKeyStroke);
		game.add(helpGame);
		helpGame.addActionListener(e -> {
			informGame(TITLE_HELP, TXTHELP, 410, 370);
			addPicture(moreInform);
		});

		JMenuItem aboutGame = new JMenuItem("About");
		KeyStroke ctrlAKeyStroke = KeyStroke.getKeyStroke("control A");
		aboutGame.setAccelerator(ctrlAKeyStroke);
		game.add(aboutGame);
		game.addSeparator();
		aboutGame.addActionListener(e -> informGame(TITLE_ABOUT, TXTABOUT, 400, 515));

		JMenuItem exitGame = new JMenuItem("Exit");
		KeyStroke ctrlQKeyStroke = KeyStroke.getKeyStroke("control Q");
		exitGame.setAccelerator(ctrlQKeyStroke);
		game.add(exitGame);
		exitGame.addActionListener(e -> System.exit(0));

		Game.game.setJMenuBar(gameMenu);
		Game.game.setVisible(true);
	}

	// Creating a form template for help and about the game
	private static void informGame(String name, String text, int width, int height){
		moreInform = new JFrame(name);
		JLabel txtMessage = new JLabel(text);
		txtMessage.setHorizontalAlignment(SwingConstants.CENTER);
		txtMessage.setVerticalAlignment(SwingConstants.TOP);
		txtMessage.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		moreInform.setIconImage(windowIcon);
		moreInform.setSize(width, height);
		moreInform.setLocationRelativeTo(null);
		moreInform.setVisible(true);
		moreInform.add(txtMessage);
	}

	private static void addPicture(JFrame MoreInform){
		JLabel jlImage = new JLabel(new ImageIcon(loadImage("/XO.jpg")));
		MoreInform.add(jlImage);
		MoreInform.setLayout(new GridLayout(2, 1));
	}

	public static void undo() {
		if (!undoBoards.isEmpty()) {
			if (GameParameters.gameMode == GameMode.PLAYER_VS_PLAYER) {
				try {
					redoBoards.push(new Board(board));
					board = undoBoards.pop(); 
					
					for (PlayervsPlayer button: playervsPlayers) {
						List<Integer> cell = Game.getBoardCellById(button.id);
						if (board.getGameBoard()[cell.get(0)][cell.get(1)] == Constants.X) {
							String player1Color = String.valueOf(GameParameters.player1ColorSymbols).charAt(0)
									+ String.valueOf(GameParameters.player1ColorSymbols).toLowerCase().substring(1);
							button.setIcon(new ImageIcon(ResourceLoader.load(Constants.getIconPath(Constants.X, player1Color))));
							button.doClick();
						} else if (board.getGameBoard()[cell.get(0)][cell.get(1)] == Constants.O) {
							String player2Color = String.valueOf(GameParameters.player2ColorSymbols).charAt(0)
									+ String.valueOf(GameParameters.player2ColorSymbols).toLowerCase().substring(1);
							button.setIcon(new ImageIcon(ResourceLoader.load(Constants.getIconPath(Constants.O, player2Color))));
							button.doClick();
						} else {
							button.setIcon(null);
							if (button.getActionListeners().length == 0)
								button.addActionListener(button);
						}
					}
					
					if (undoBoards.isEmpty())
						undo.setEnabled(false);
					
					redo.setEnabled(true);
					Board.printBoard(board.getGameBoard());
				} catch (ArrayIndexOutOfBoundsException ex) {
					System.err.println("No move has been made yet!");
					System.err.flush();
				}
			} else if (GameParameters.gameMode == GameMode.PLAYER_VS_AI) {
				try {
					redoBoards.push(new Board(board));
					board = undoBoards.pop();

					for (PlayervsAI button: playervsAIS) {
						List<Integer> cell = Game.getBoardCellById(button.id);
						if (board.getGameBoard()[cell.get(0)][cell.get(1)] == Constants.X) {
							String player1Color = String.valueOf(GameParameters.player1ColorSymbols).charAt(0)
									+ String.valueOf(GameParameters.player1ColorSymbols).toLowerCase().substring(1);
							button.setIcon(new ImageIcon(ResourceLoader.load(Constants.getIconPath(Constants.X, player1Color))));
							button.doClick();
						} else if (board.getGameBoard()[cell.get(0)][cell.get(1)] == Constants.O) {
							String player2Color = String.valueOf(GameParameters.player2ColorSymbols).charAt(0)
									+ String.valueOf(GameParameters.player2ColorSymbols).toLowerCase().substring(1);
							button.setIcon(new ImageIcon(ResourceLoader.load(Constants.getIconPath(Constants.O, player2Color))));
							button.doClick();
						} else {
							button.setIcon(null);
							if (button.getActionListeners().length == 0)
								button.addActionListener(button);
						}
					}
					
					if (undoBoards.isEmpty())
						undo.setEnabled(false);
					
					redo.setEnabled(true);
					Board.printBoard(board.getGameBoard());
				} catch (ArrayIndexOutOfBoundsException ex) {
					System.err.println("No move has been made yet!");
					System.err.flush();
				}
			}
		}
	}

	public static void redo() {
		if (!redoBoards.isEmpty()) {
			if (GameParameters.gameMode == GameMode.PLAYER_VS_PLAYER) {
				try {		
					undoBoards.push(new Board(board));
					board = new Board(redoBoards.pop());
					
					for (PlayervsPlayer button: playervsPlayers) {
						List<Integer> cell = Game.getBoardCellById(button.id);
						if (board.getGameBoard()[cell.get(0)][cell.get(1)] == Constants.X) {
							String player1Color = String.valueOf(GameParameters.player1ColorSymbols).charAt(0)
									+ String.valueOf(GameParameters.player1ColorSymbols).toLowerCase().substring(1);
							button.setIcon(new ImageIcon(ResourceLoader.load(Constants.getIconPath(Constants.X, player1Color))));
							for (ActionListener actionListener: button.getActionListeners()) {
								button.removeActionListener(actionListener);
							}
						} else if (board.getGameBoard()[cell.get(0)][cell.get(1)] == Constants.O) {
							String player2Color = String.valueOf(GameParameters.player2ColorSymbols).charAt(0)
									+ String.valueOf(GameParameters.player2ColorSymbols).toLowerCase().substring(1);
							button.setIcon(new ImageIcon(ResourceLoader.load(Constants.getIconPath(Constants.O, player2Color))));
							for (ActionListener actionListener: button.getActionListeners()) {
								button.removeActionListener(actionListener);
							}
						} else {
							button.setIcon(null);
							if (button.getActionListeners().length == 0)
								button.addActionListener(button);
						}
					}
					
					if (redoBoards.isEmpty())
						redo.setEnabled(false);
					
					undo.setEnabled(true);
					Board.printBoard(board.getGameBoard());

					boolean isGameOver = board.isTerminal(); 
					if (isGameOver) {
						gameOver();
					}
				} catch (ArrayIndexOutOfBoundsException ex) {
					System.err.println("No move has been made yet!");
					System.err.flush();
				}
			} else if (GameParameters.gameMode == GameMode.PLAYER_VS_AI) {
				try {
					undoBoards.push(new Board(board));
					board = new Board(redoBoards.pop());

					for (PlayervsAI button: playervsAIS) {
						List<Integer> cell = Game.getBoardCellById(button.id);
						if (board.getGameBoard()[cell.get(0)][cell.get(1)] == Constants.X) {
							String player1Color = String.valueOf(GameParameters.player1ColorSymbols).charAt(0)
									+ String.valueOf(GameParameters.player1ColorSymbols).toLowerCase().substring(1);
							button.setIcon(new ImageIcon(ResourceLoader.load(Constants.getIconPath(Constants.X, player1Color))));
							for (ActionListener actionListener: button.getActionListeners()) {
								button.removeActionListener(actionListener);
							}
						} else if (board.getGameBoard()[cell.get(0)][cell.get(1)] == Constants.O) {
							String player2Color = String.valueOf(GameParameters.player2ColorSymbols).charAt(0)
									+ String.valueOf(GameParameters.player2ColorSymbols).toLowerCase().substring(1);
							button.setIcon(new ImageIcon(ResourceLoader.load(Constants.getIconPath(Constants.O, player2Color))));
							for (ActionListener actionListener: button.getActionListeners()) {
								button.removeActionListener(actionListener);
							}
						} else {
							button.setIcon(null);
							if (button.getActionListeners().length == 0)
								button.addActionListener(button);
						}
					}
					
					if (redoBoards.isEmpty())
						redo.setEnabled(false);
					
					undo.setEnabled(true);
					Board.printBoard(board.getGameBoard());

					boolean isGameOver = board.isTerminal(); 
					if (isGameOver) {
						gameOver();
					}
				} catch (ArrayIndexOutOfBoundsException ex) {
					System.err.println("No move has been made yet!");
					System.err.flush();
				}
			}
		}
	}
	
	public static List<Integer> getBoardCellById(int id) {
		List<Integer> cell = new ArrayList<>();
		int i = 0, j = 0;
		if (id / 3 == 0) {  
			i = 0;
			j = id % 3;
		} else if (id / 3 == 1) {  
			i = 1;
			j = id % 3;
		} else if (id / 3 == 2) {  
			i = 2;
			j = id % 3; 
		}
		cell.add(i);
		cell.add(j);
		return cell;
	}
	
	public static int getIdByBoardCell(int row, int col) {
		return row * 3 + col;
	}

	public static void createPlayerVsAINewGame() {
		configureGuiStyle();
		addMenu();
		AI aiPlayer = new AI(GameParameters.maxDepth1, Constants.O);
		
		if (panel != null) {
			game.remove(panel);
			game.revalidate();
			game.repaint();
		}
		
		panel = new JPanel();
		game.add(panel);
	
		layout = new GridLayout(3, 3);
		panel.setLayout(layout);
		
		board = new Board();
		playervsAIS = new PlayervsAI[9];
		
		panel.removeAll();
		panel.revalidate();
		panel.repaint();

		for (int i = 0; i < 9; i++) {
			playervsAIS[i] = new PlayervsAI(i, aiPlayer);
			panel.add(playervsAIS[i]);
		}
	
		if (game.getKeyListeners().length == 0)
			game.addKeyListener(gameKeyListener);
		game.setFocusable(true);
		game.setVisible(true);
	}

	public static void createPlayerVsPlayerNewGame() {
		configureGuiStyle();
		addMenu();
		
		if (panel != null) {
			game.remove(panel);
			game.revalidate();
			game.repaint();
		}
		
		panel = new JPanel();
		game.add(panel);
	
		layout = new GridLayout(3, 3);
		panel.setLayout(layout);
		
		board = new Board();
		playervsPlayers = new PlayervsPlayer[9];
		
		panel.removeAll();
		panel.revalidate();
		panel.repaint();

		for (int id = 0; id < 9; id++) {
			playervsPlayers[id] = new PlayervsPlayer(id);
			panel.add(playervsPlayers[id]);
		}
	
		if (game.getKeyListeners().length == 0)
			game.addKeyListener(gameKeyListener);
		game.setFocusable(true);
		game.setVisible(true);
	}

	public static void createAIVsAINewGame() {
		configureGuiStyle();
		addMenu();
		AI ai1Player = new AI(GameParameters.maxDepth1, Constants.X);
		AI ai2Player = new AI(GameParameters.maxDepth2, Constants.O);
	
		if (panel != null) {
			game.remove(panel);
			game.revalidate();
			game.repaint();
		}
		
		panel = new JPanel();
		game.add(panel);
	
		layout = new GridLayout(3, 3);
		panel.setLayout(layout);
		
		board = new Board();
		AIvsAIS = new AIvsAI[9];
	
		panel.removeAll();
		panel.revalidate();
		panel.repaint();

		for (int i = 0; i < 9; i++) {
			AIvsAIS[i] = new AIvsAI(i);
			panel.add(AIvsAIS[i]);
		}
		
		game.setVisible(true);
		playAiVsAi(ai1Player, ai2Player);
	}

	public static void createClientServerNewGame() {
		configureGuiStyle();
		addMenu();
		Server server = new Server(GameParameters.serverPort);
		server.start();

		if (panel != null) {
			game.remove(panel);
			game.revalidate();
			game.repaint();
		}
		
		panel = new JPanel();
		game.add(panel);
		
		layout = new GridLayout(3, 3);
		panel.setLayout(layout);
		
		board = new Board();
		clientServers = new ClientServer[9];
		
		panel.removeAll();
		panel.revalidate();
		panel.repaint();

		for (int i = 0; i < 9; i++) {
			clientServers[i] = new ClientServer(i, GameParameters.clientIP,
					GameParameters.clientPort, GameParameters.clientServerSymbol);
			panel.add(clientServers[i]);
		}
		
		if (game.getKeyListeners().length == 0)
			game.addKeyListener(gameKeyListener);
		game.setFocusable(true);
		game.setVisible(true);
	}
	
	private static void aiMove(AI aiPlayer) {
		Move aiMove;
		if (aiPlayer.getMaxDepth() == Constants.BEST_RESPONSE) {
			BestResponse bestResponse = new BestResponse(board.getGameBoard());   // Best Response Move
			aiMove = bestResponse.findBestResponse();
		} else {
			aiMove = aiPlayer.miniMax(board);      // MiniMax AI Move
		}
		
		makeMove(aiMove.getRow(), aiMove.getColumn(), aiPlayer.getPlayerSymbol());
		int ai_button_id = getIdByBoardCell(aiMove.getRow(), aiMove.getColumn());
		for (AIvsAI button: AIvsAIS) {
			if (button.id == ai_button_id) {
				button.player = aiPlayer.getPlayerSymbol();
				button.doClick();
			}
		}
	}
	
	
	private static void playAiVsAi(AI ai1Player, AI ai2Player) {
		while (!Game.board.isTerminal()) {
			aiMove(ai1Player);
			try {
				Thread.sleep(200);
				game.paint(game.getGraphics());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if (!Game.board.isTerminal()) {
				aiMove(ai2Player);
			}

			try {
				Thread.sleep(200);
				game.paint(game.getGraphics());
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		gameOver();
	}

	private static void configureGuiStyle() {
		try {
			if (GameParameters.guiStyle == GuiStyle.SYSTEM_STYLE) {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} else if (GameParameters.guiStyle == GuiStyle.CROSS_PLATFORM_STYLE) {
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			} else if (GameParameters.guiStyle == GuiStyle.NIMBUS_STYLE) {
			    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			        if ("Nimbus".equals(info.getName())) {
			            UIManager.setLookAndFeel(info.getClassName());
			            break;
			        }
			    }
			}
		} catch (Exception e1) {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (Exception e2) {
				e2.printStackTrace();	
			}
		}
	}
	
	public static void gameOver() {
		ImageIcon win = new ImageIcon(loadImage("/win.png"));
		if (board.getWinner() == Constants.X) {
			int input = JOptionPane.showConfirmDialog(null,
					"Player 1 \"X\" wins!\nPlay again?",
					"Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, win);
			if (input == JOptionPane.OK_OPTION) {
				if (GameParameters.gameMode == GameMode.PLAYER_VS_AI) {
					createPlayerVsAINewGame();
				} else if (GameParameters.gameMode == GameMode.PLAYER_VS_PLAYER) {
					createPlayerVsPlayerNewGame();
				} else if (GameParameters.gameMode == GameMode.AI_VS_AI) {
					createAIVsAINewGame();
				} else if (GameParameters.gameMode == GameMode.CLIENT_SERVER) {
					createClientServerNewGame();
				}
			} else if (input == JOptionPane.NO_OPTION 
					|| input == JOptionPane.CLOSED_OPTION) {
				if (GameParameters.gameMode == GameMode.PLAYER_VS_AI) {
					for (PlayervsAI button: playervsAIS) {
						button.removeActionListener(button);
					}
				} else if (GameParameters.gameMode == GameMode.PLAYER_VS_PLAYER) {
					for (PlayervsPlayer button: playervsPlayers) {
						button.removeActionListener(button);
					}
				} else if (GameParameters.gameMode == GameMode.CLIENT_SERVER) {
					for (ClientServer button: clientServers) {
						button.removeActionListener(button);
					}
				}
			}
		} else if (board.getWinner() == Constants.O) {
			int input = JOptionPane.showConfirmDialog(null,
					"Player 2 \"O\" wins!\nPlay again?",
					"Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, win);
			if (input == JOptionPane.OK_OPTION) {
				if (GameParameters.gameMode == GameMode.PLAYER_VS_AI) {
					createPlayerVsAINewGame();
				} else if (GameParameters.gameMode == GameMode.PLAYER_VS_PLAYER) {
					createPlayerVsPlayerNewGame();
				} else if (GameParameters.gameMode == GameMode.AI_VS_AI) {
					createAIVsAINewGame();
				} else if (GameParameters.gameMode == GameMode.CLIENT_SERVER) {
					createClientServerNewGame();
				}
			} else if (input == JOptionPane.NO_OPTION 
					|| input == JOptionPane.CLOSED_OPTION) {
				if (GameParameters.gameMode == GameMode.PLAYER_VS_AI) {
					for (PlayervsAI button: playervsAIS) {
						button.removeActionListener(button);
					}
				} else if (GameParameters.gameMode == GameMode.PLAYER_VS_PLAYER) {
					for (PlayervsPlayer button: playervsPlayers) {
						button.removeActionListener(button);
					}
				} else if (GameParameters.gameMode == GameMode.CLIENT_SERVER) {
					for (ClientServer button: clientServers) {
						button.removeActionListener(button);
					}
				}
			}
		} else if (Board.isGameBoardFull(board.getGameBoard()) && board.getWinner() == Constants.EMPTY) {
			ImageIcon drawn = new ImageIcon(loadImage("/drawn.png"));
			int input = JOptionPane.showConfirmDialog(null,
					"It is a draw!\nPlay again?",
					"Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, drawn);
			if (input == JOptionPane.OK_OPTION) {
				if (GameParameters.gameMode == GameMode.PLAYER_VS_AI) {
					createPlayerVsAINewGame();
				} else if (GameParameters.gameMode == GameMode.PLAYER_VS_PLAYER) {
					createPlayerVsPlayerNewGame();
				} else if (GameParameters.gameMode == GameMode.AI_VS_AI) {
					createAIVsAINewGame();
				} else if (GameParameters.gameMode == GameMode.CLIENT_SERVER) {
					createClientServerNewGame();
				}
			} else if (input == JOptionPane.NO_OPTION 
					|| input == JOptionPane.CLOSED_OPTION) {
				if (GameParameters.gameMode == GameMode.PLAYER_VS_AI) {
					for (PlayervsAI button: playervsAIS) {
						button.removeActionListener(button);
					}
				} else if (GameParameters.gameMode == GameMode.PLAYER_VS_PLAYER) {
					for (PlayervsPlayer button: playervsPlayers) {
						button.removeActionListener(button);
					}
				} else if (GameParameters.gameMode == GameMode.CLIENT_SERVER) {
					for (ClientServer button: clientServers) {
						button.removeActionListener(button);
					}
				}
			}
		}
	}
	
	public static KeyListener gameKeyListener = new KeyAdapter() {
		@Override
		public void keyPressed(KeyEvent e) {
			String button = KeyEvent.getKeyText(e.getKeyCode());
			for (int i = 0; i < 9; i++) {
				if (button.equals(i+1+"")) {
					if (GameParameters.gameMode == GameMode.PLAYER_VS_AI)
						playervsAIS[i].doClick();
					if (GameParameters.gameMode == GameMode.PLAYER_VS_PLAYER)
						playervsPlayers[i].doClick();
					if (GameParameters.gameMode == GameMode.CLIENT_SERVER)
						clientServers[i].doClick();
					break;
				} 
			}
			
			if (((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0) &&
					(e.getKeyCode() == KeyEvent.VK_Z)) {
                undo();
            } else if (((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0) &&
					(e.getKeyCode() == KeyEvent.VK_Y)) {
                redo();
            }
		}
	};
	
	public static void makeMove(int row, int col, int player) {
		if ((player == Constants.X && GameParameters.gameMode == GameMode.PLAYER_VS_AI)
			|| (GameParameters.gameMode == GameMode.PLAYER_VS_PLAYER)) {
			undoBoards.push(new Board(board));
		}
		
		board.getGameBoard()[row][col] = player;
		board.setLastMove(new Move(row, col));
		board.setLastPlayer(player);
		redoBoards.clear();
		redo.setEnabled(false);
	}

	public static void main(String[] args) {
		Game game = new Game();
		if (GameParameters.gameMode == GameMode.PLAYER_VS_AI)
			Game.createPlayerVsAINewGame();
		else if (GameParameters.gameMode == GameMode.PLAYER_VS_PLAYER)
			Game.createPlayerVsPlayerNewGame();
		else if (GameParameters.gameMode == GameMode.AI_VS_AI)
			Game.createAIVsAINewGame();
		else if (GameParameters.gameMode == GameMode.CLIENT_SERVER)
			Game.createClientServerNewGame();
	}
}