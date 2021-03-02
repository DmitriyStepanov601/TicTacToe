package graphics;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import enumerations.ColorSymbols;
import enumerations.GameMode;
import enumerations.GuiStyle;
import utilities.Constants;
import utilities.GameParameters;

/**
 * A class that describes the game settings window
 * @author Dmitriy Stepanov
 */
public class Settings extends JFrame {
	private final JComboBox<String> gui_style_drop_down;
	private final JComboBox<String> game_mode_drop_down;
	private final JComboBox<String> max_depth1_drop_down;
	private final JComboBox<String> max_depth2_drop_down;
	private final JComboBox<String> player1_color_drop_down;
	private final JComboBox<String> player2_color_drop_down;
	private final JComboBox<String> client_server_symbol_drop_down;

	private final JTextField server_port_text_field;
	private final JTextField client_ip_text_field;
	private final JTextField client_port_text_field;
	
	private final JButton apply;
	private final JButton cancel;

	public static int width = 460;
	public static int height = 450;

	/**
	 * Constructor - creating a new settings window
	 * @param windowIcon - app icon
	 * @see Settings#Settings(Image)
	 */
	public Settings(Image windowIcon) {
		super("Settings");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(null);
		setSize(width, height);
		setLocationRelativeTo(null);
		setResizable(false);

		EventHandler handler = new EventHandler();

		GuiStyle selectedGuiStyle = GameParameters.guiStyle;
		GameMode selectedMode = GameParameters.gameMode;
		int maxDepth1 = GameParameters.maxDepth1;
		int maxDepth2 = GameParameters.maxDepth2;
		ColorSymbols selectedPlayer1ColorSymbols = GameParameters.player1ColorSymbols;
		ColorSymbols selectedPlayer2ColorSymbols = GameParameters.player2ColorSymbols;
		int selectedClientServerSymbol = GameParameters.clientServerSymbol;
		int serverPort = GameParameters.serverPort;
		String clientIP = GameParameters.clientIP;
		int clientPort = GameParameters.clientPort;

		JLabel guiStyleLabel = new JLabel("GUI style");
		JLabel gameModeLabel = new JLabel("Game mode");
		JLabel maxDepth1Label = new JLabel("AI 1 Level");
		JLabel maxDepth2Label = new JLabel("AI 2 Level (AI vs AI)");
		JLabel player1ColorLabel = new JLabel("Player 1 \"X\" color");
		JLabel player2ColorLabel = new JLabel("Player 2 \"O\" color");
		JLabel clientServerSymbolLabel = new JLabel("Client-Server symbol");
		JLabel serverPortLabel = new JLabel("Server port");
		JLabel clientIpLabel = new JLabel("Client IP");
		JLabel clientPortLabel = new JLabel("Client port");
		
		add(guiStyleLabel);
		add(gameModeLabel);
		add(maxDepth1Label);
		add(maxDepth2Label);
		add(player1ColorLabel);
		add(player2ColorLabel);
		add(clientServerSymbolLabel);
		add(serverPortLabel);
		add(clientIpLabel);
		add(clientPortLabel);
		
		gui_style_drop_down = new JComboBox<>();
		gui_style_drop_down.addItem("System style");
		gui_style_drop_down.addItem("Cross-Platform style");
		gui_style_drop_down.addItem("Nimbus style");
		
		if (selectedGuiStyle == GuiStyle.SYSTEM_STYLE)
			gui_style_drop_down.setSelectedIndex(0);
		else if (selectedGuiStyle == GuiStyle.CROSS_PLATFORM_STYLE)
			gui_style_drop_down.setSelectedIndex(1);
		else if (selectedGuiStyle == GuiStyle.NIMBUS_STYLE)
			gui_style_drop_down.setSelectedIndex(2);
			
		game_mode_drop_down = new JComboBox<>();
		game_mode_drop_down.addItem("Player vs AI");
		game_mode_drop_down.addItem("Player vs Player");
		game_mode_drop_down.addItem("AI vs AI");
		game_mode_drop_down.addItem("Client-Server");

		if (selectedMode == GameMode.PLAYER_VS_AI)
			game_mode_drop_down.setSelectedIndex(0);
		else if (selectedMode == GameMode.PLAYER_VS_PLAYER)
			game_mode_drop_down.setSelectedIndex(1);
		else if (selectedMode == GameMode.AI_VS_AI)
			game_mode_drop_down.setSelectedIndex(2);
		else if (selectedMode == GameMode.CLIENT_SERVER)
			game_mode_drop_down.setSelectedIndex(3);
		
		max_depth1_drop_down = new JComboBox<>();
		max_depth1_drop_down.addItem("1");
		max_depth1_drop_down.addItem("2");
		max_depth1_drop_down.addItem("3");
		max_depth1_drop_down.addItem("4");
		max_depth1_drop_down.addItem("Best Response");

		maxDepth1 = (maxDepth1 == Constants.BEST_RESPONSE) ? 4 : maxDepth1-1; 
		max_depth1_drop_down.setSelectedIndex(maxDepth1);
		
		max_depth2_drop_down = new JComboBox<>();
		max_depth2_drop_down.addItem("1");
		max_depth2_drop_down.addItem("2");
		max_depth2_drop_down.addItem("3");
		max_depth2_drop_down.addItem("4");
		max_depth2_drop_down.addItem("Best Response");
		
		maxDepth2 = (maxDepth2 == Constants.BEST_RESPONSE) ? 4 : maxDepth2-1; 
		max_depth2_drop_down.setSelectedIndex(maxDepth2);
		
		player1_color_drop_down = new JComboBox<>();
		player1_color_drop_down.addItem(String.valueOf(ColorSymbols.BLUE));
		player1_color_drop_down.addItem(String.valueOf(ColorSymbols.RED));
		player1_color_drop_down.addItem(String.valueOf(ColorSymbols.BLACK));
		player1_color_drop_down.addItem(String.valueOf(ColorSymbols.GREEN));
		player1_color_drop_down.addItem(String.valueOf(ColorSymbols.ORANGE));
		player1_color_drop_down.addItem(String.valueOf(ColorSymbols.PURPLE));
		player1_color_drop_down.addItem(String.valueOf(ColorSymbols.YELLOW));
		
		if (selectedPlayer1ColorSymbols == ColorSymbols.BLUE)
			player1_color_drop_down.setSelectedIndex(0);
		else if (selectedPlayer1ColorSymbols == ColorSymbols.RED)
			player1_color_drop_down.setSelectedIndex(1);
		else if (selectedPlayer1ColorSymbols == ColorSymbols.BLACK)
			player1_color_drop_down.setSelectedIndex(2);
		else if (selectedPlayer1ColorSymbols == ColorSymbols.GREEN)
			player1_color_drop_down.setSelectedIndex(3);
		else if (selectedPlayer1ColorSymbols == ColorSymbols.ORANGE)
			player1_color_drop_down.setSelectedIndex(4);
		else if (selectedPlayer1ColorSymbols == ColorSymbols.ORANGE)
			player1_color_drop_down.setSelectedIndex(5);
		else if (selectedPlayer1ColorSymbols == ColorSymbols.YELLOW)
			player1_color_drop_down.setSelectedIndex(6);
		
		player2_color_drop_down = new JComboBox<>();
		player2_color_drop_down.addItem(String.valueOf(ColorSymbols.BLUE));
		player2_color_drop_down.addItem(String.valueOf(ColorSymbols.RED));
		player2_color_drop_down.addItem(String.valueOf(ColorSymbols.BLACK));
		player2_color_drop_down.addItem(String.valueOf(ColorSymbols.GREEN));
		player2_color_drop_down.addItem(String.valueOf(ColorSymbols.ORANGE));
		player2_color_drop_down.addItem(String.valueOf(ColorSymbols.PURPLE));
		player2_color_drop_down.addItem(String.valueOf(ColorSymbols.YELLOW));
		
		if (selectedPlayer2ColorSymbols == ColorSymbols.BLUE)
			player2_color_drop_down.setSelectedIndex(0);
		else if (selectedPlayer2ColorSymbols == ColorSymbols.RED)
			player2_color_drop_down.setSelectedIndex(1);
		else if (selectedPlayer2ColorSymbols == ColorSymbols.BLACK)
			player2_color_drop_down.setSelectedIndex(2);
		else if (selectedPlayer2ColorSymbols == ColorSymbols.GREEN)
			player2_color_drop_down.setSelectedIndex(3);
		else if (selectedPlayer2ColorSymbols == ColorSymbols.ORANGE)
			player2_color_drop_down.setSelectedIndex(4);
		else if (selectedPlayer2ColorSymbols == ColorSymbols.ORANGE)
			player2_color_drop_down.setSelectedIndex(5);
		else if (selectedPlayer2ColorSymbols == ColorSymbols.YELLOW)
			player2_color_drop_down.setSelectedIndex(6);
		
		client_server_symbol_drop_down = new JComboBox<>();
		client_server_symbol_drop_down.addItem("X");
		client_server_symbol_drop_down.addItem("O");
		
		if (selectedClientServerSymbol == Constants.X)
			client_server_symbol_drop_down.setSelectedIndex(Constants.X - 1);
		else if (selectedClientServerSymbol == Constants.O)
			client_server_symbol_drop_down.setSelectedIndex(Constants.O - 1);
		
		server_port_text_field = new JTextField();
		server_port_text_field.setText(serverPort + "");
		
		client_ip_text_field = new JTextField(GameParameters.clientIP);
		client_ip_text_field.setText(clientIP);
		
		client_port_text_field = new JTextField(GameParameters.clientPort);
		client_port_text_field.setText(clientPort + "");
		
		add(gui_style_drop_down);
		add(game_mode_drop_down);
		add(max_depth1_drop_down);
		add(max_depth2_drop_down);
		add(player1_color_drop_down);
		add(player2_color_drop_down);
		add(client_server_symbol_drop_down);
		add(server_port_text_field);
		add(client_ip_text_field);
		add(client_port_text_field);

		guiStyleLabel.setBounds(20, 25, 250, 20);
		gameModeLabel.setBounds(20, 55, 250, 20);
		maxDepth1Label.setBounds(20, 85, 250, 20);
		maxDepth2Label.setBounds(20, 115, 250, 20);
		player1ColorLabel.setBounds(20, 145, 250, 20);
		player2ColorLabel.setBounds(20, 175, 250, 20);
		clientServerSymbolLabel.setBounds(20, 205, 250, 20);
		serverPortLabel.setBounds(20, 235, 250, 20);
		clientIpLabel.setBounds(20, 265, 250, 20);
		clientPortLabel.setBounds(20, 295, 250, 20);
		
		gui_style_drop_down.setBounds(260, 25, 160, 20);
		game_mode_drop_down.setBounds(260, 55, 160, 20);
		max_depth1_drop_down.setBounds(260, 85, 160, 20);
		max_depth2_drop_down.setBounds(260, 115, 160, 20);
		player1_color_drop_down.setBounds(260, 145, 160, 20);
		player2_color_drop_down.setBounds(260, 175, 160, 20);
		client_server_symbol_drop_down.setBounds(260, 205, 160, 20);
		server_port_text_field.setBounds(260, 235, 160, 20);
		client_ip_text_field.setBounds(260, 265, 160, 20);
		client_port_text_field.setBounds(260, 295, 160, 20);
				
		apply = new JButton("Apply");
		cancel = new JButton("Cancel");
		add(apply);
		add(cancel);
		
		int distance = 10;
		apply.setBounds((width / 2) - 110 - (distance / 2), 350, 100, 30);
		apply.setBackground(Color.lightGray);
		apply.addActionListener(handler);
		cancel.setBounds((width / 2) - 10 + (distance / 2), 350, 100, 30);
		cancel.setBackground(Color.lightGray);
		cancel.addActionListener(handler);
	}

	private class EventHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ev) {
			if (ev.getSource() == cancel) {
				dispose();
			} else if (ev.getSource() == apply) {
				try {
					GuiStyle guiStyle = 
						GuiStyle.valueOf(gui_style_drop_down.getSelectedItem().toString().toUpperCase().replace("-", "_").replace(" ", "_"));
					GameMode gameMode = 
						GameMode.valueOf(game_mode_drop_down.getSelectedItem().toString().toUpperCase().replace("-", "_").replace(" ", "_"));
					String maxDepth1String = (String) max_depth1_drop_down.getSelectedItem();
					String maxDepth2String = (String) max_depth2_drop_down.getSelectedItem();
					ColorSymbols player1ColorSymbols =
						ColorSymbols.valueOf(player1_color_drop_down.getSelectedItem().toString());
					ColorSymbols player2ColorSymbols =
							ColorSymbols.valueOf(player2_color_drop_down.getSelectedItem().toString());
					int clientServerSymbol = client_server_symbol_drop_down.getSelectedIndex() + 1;
					int serverPort = Integer.parseInt(server_port_text_field.getText());
					String clientIP = client_ip_text_field.getText();
					int clientPort = Integer.parseInt(client_port_text_field.getText());
					
					if (player1ColorSymbols == player2ColorSymbols) {
						ImageIcon warn = new ImageIcon(Game.loadImage("/warn.png"));
						JOptionPane.showMessageDialog(null,
								"Player 1 and Player 2 cannot have the same color of symbols!",
								"ERROR", JOptionPane.ERROR_MESSAGE, warn);
						return;
					}
					
					int maxDepth1;
					try {
						maxDepth1 = Integer.parseInt(maxDepth1String);
					} catch (NumberFormatException e) {
						maxDepth1 = Constants.BEST_RESPONSE;
					}
					
					int maxDepth2;
					try {
						maxDepth2 = Integer.parseInt(maxDepth2String);
					} catch (NumberFormatException e) {
						maxDepth2 = Constants.BEST_RESPONSE;
					}
					
					// Change game parameters based on settings.
					GameParameters.guiStyle = guiStyle;
					GameParameters.gameMode = gameMode;
					GameParameters.maxDepth1 = maxDepth1;
					GameParameters.maxDepth2 = maxDepth2;
					GameParameters.player1ColorSymbols = player1ColorSymbols;
					GameParameters.player2ColorSymbols = player2ColorSymbols;
					GameParameters.clientServerSymbol = clientServerSymbol;
					GameParameters.serverPort = serverPort;
					GameParameters.clientIP = clientIP;
					GameParameters.clientPort = clientPort;

					ImageIcon startgame = new ImageIcon(Game.loadImage("/start.png"));
					JOptionPane.showMessageDialog(null,
							"Game settings have been changed.\nThe changes will be applied in the next new game.",
							"", JOptionPane.INFORMATION_MESSAGE, startgame);
					dispose();
				} catch(Exception e) {
					System.err.println("ERROR : " + e.getMessage());
				}
			}
		}
	}
}