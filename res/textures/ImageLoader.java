package textures;
import java.awt.Image;
import java.awt.Toolkit;

public class ImageLoader {
	
	public static ImageLoader imageLoader = new ImageLoader();
	
	public static Image getImage(String fileName){
		return Toolkit.getDefaultToolkit().getImage(imageLoader.getClass().getResource(fileName));
	}

}
