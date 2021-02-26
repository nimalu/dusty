package mainPackage;

import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public abstract class ImageLoader {
	private static HashMap<String, Image> images = new HashMap<>();
	public static void loadImages() {
		images.put("icon", loadImage("icon.png"));
		images.put("right", loadImage("right.png"));
	}
	private static Image loadImage(String name) {
		try {
			return ImageIO.read(ImageLoader.class.getResourceAsStream("/"+name));
		} catch (IOException e) {e.printStackTrace();
		}
		return null;
	}
	public static Image getImage(String name) {
		return images.get(name);
	}

}
