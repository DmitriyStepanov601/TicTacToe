package utilities;
import java.net.URL;

/**
 * The final class describing the loading of images into the game
 * @author Dmitriy Stepanov
 */
final public class ResourceLoader {
	public static URL load(String path) {
		URL input = ResourceLoader.class.getResource(path);
		if (input == null) {
			input = ResourceLoader.class.getResource("/" + path);
		}
		return input;
	}
}