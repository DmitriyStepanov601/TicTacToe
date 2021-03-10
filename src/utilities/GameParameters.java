package utilities;

import enumerations.ColorSymbols;
import enumerations.GameMode;
import enumerations.GuiStyle;

/**
 * A class that describes the default game parameters
 *
 * @author Dmitriy Stepanov
 */
public class GameParameters {
    public static GuiStyle guiStyle = GuiStyle.SYSTEM_STYLE;
    public static GameMode gameMode = GameMode.PLAYER_VS_AI;
    public static int maxDepth1 = Constants.BEST_RESPONSE;
    public static int maxDepth2 = Constants.BEST_RESPONSE;
    public static ColorSymbols player1ColorSymbols = ColorSymbols.BLUE;
    public static ColorSymbols player2ColorSymbols = ColorSymbols.RED;
    public static int clientServerSymbol = Constants.X;
    public static int serverPort = 4000;
    public static String clientIP = "127.0.0.1";
    public static int clientPort = 4001;
}
