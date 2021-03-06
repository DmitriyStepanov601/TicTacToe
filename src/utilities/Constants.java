package utilities;

/**
 * A class that describes game constants
 *
 * @author Dmitriy Stepanov
 */
public class Constants {

    // Variables for the board values
    public static final int X = 1;
    public static final int O = -1;
    public static final int EMPTY = 0;

    // AI BestResponse mode
    public static final int BEST_RESPONSE = -1;

    public static String getIconPath(int player, String color) {
        if (player == X) {
            return "img/X/" + color + ".png";
        } else {
            return "img/O/" + color + ".png";
        }
    }
}