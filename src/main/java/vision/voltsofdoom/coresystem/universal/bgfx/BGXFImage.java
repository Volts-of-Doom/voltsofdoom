package vision.voltsofdoom.coresystem.universal.bgfx;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.imageio.ImageIO;

import vision.voltsofdoom.coresystem.loading.resource.ResourceLocation;

public class BGXFImage {

	public byte[] RAW_DATA = new byte[] {};
	public final String absolutePath;

	@SuppressWarnings("unused")
	private BGXFImage() {
		throw new IllegalAccessError(
				"Uhhh maybe don't use this constructor. We kinda need a path. (BGFXImage private)");
	}

	public BGXFImage(ResourceLocation location) {
		this(location.absolute());
	}

	public BGXFImage(String absolutePath) {
		this.absolutePath = absolutePath;
	}

	public byte[] getRawData() {
		updateRawData();
		return RAW_DATA;
	}

	public void updateRawData() {
		if (isRawDataInvalid()) {
			RAW_DATA = fromResource();
		}
	}

	private boolean isRawDataInvalid() {
		return RAW_DATA == null ? true : false;
	}

	private byte[] fromResource() {
		try {
			BufferedImage img = ImageIO.read(new File(absolutePath));
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			ImageIO.write(img, "png", stream);
			return stream.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			return RAW_DATA;
		}
	}
}
