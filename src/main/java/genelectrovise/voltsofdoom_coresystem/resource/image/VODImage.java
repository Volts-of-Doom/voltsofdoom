package genelectrovise.voltsofdoom_coresystem.resource.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;
import java.net.URL;

import javax.imageio.ImageIO;

import genelectrovise.voltsofdoom_coresystem.log.VODLog4J;

public class VODImage {
	private URL url;
	private BufferedImage image;

	public VODImage(String path) {
		try {
			File f = new File(path);

			URI formattedUri = new URI(f.getAbsolutePath().replace("\\", "/"));
			url = new URL("file://" + formattedUri.getPath());
			VODLog4J.LOGGER.debug("URL : " + url.getPath());

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
