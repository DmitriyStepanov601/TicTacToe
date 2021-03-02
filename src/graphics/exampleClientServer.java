package graphics;
import enumerations.ColorSymbols;
import enumerations.GameMode;
import enumerations.GuiStyle;
import utilities.Constants;
import utilities.GameParameters;

/**
 * Class describing the example of the first client server
 * @author Dmitriy Stepanov
 */
public class exampleClientServer {
	static int clientServerSymbol = Constants.X;
	static int serverPort = 4000;
	static String clientIP = "127.0.0.1";
	static int clientPort = 4001;

	/**
	 * Constructor - create a new the first client server
	 * @see exampleClientServer#exampleClientServer()
	 */
	public exampleClientServer() { }

	public static void main(String[] args) {
		GameParameters.guiStyle = GuiStyle.SYSTEM_STYLE;
		GameParameters.gameMode = GameMode.CLIENT_SERVER;
		GameParameters.maxDepth1 = Constants.BEST_RESPONSE;
		GameParameters.maxDepth2 = Constants.BEST_RESPONSE;
		GameParameters.player1ColorSymbols = ColorSymbols.BLUE;
		GameParameters.player2ColorSymbols = ColorSymbols.RED;
		GameParameters.clientServerSymbol = clientServerSymbol;
		GameParameters.serverPort = serverPort;
		GameParameters.clientIP = clientIP;
		GameParameters.clientPort = clientPort;

		Game game = new Game();
		Game.createClientServerNewGame();
	}
}