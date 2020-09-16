package vision.voltsofdoom.coresystem.universal.resource.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;
import java.net.URL;

import javax.imageio.ImageIO;

import vision.voltsofdoom.zapbyte.log.Loggers;

/**
 * Holds an image for the game, in an expected format, with additional utility
 * methods thrown in for good measure.
 * 
 * @author GenElectrovise
 *
 */
public class VODImage {
	private URL url;
	private BufferedImage image;

	public VODImage(String path) {
		try {
			File f = new File(path);

			URI formattedUri = new URI(f.getAbsolutePath().replace("\\", "/"));
			url = new URL("file://" + formattedUri.getPath());
			Loggers.ZAPBYTE_LOADING_RESOURCE.finest("URL : " + url.getPath());

			image = ImageIO.read(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public BufferedImage getImage() {
		return image;
	}

	public URL getUrl() {
		return url;
	}

}
