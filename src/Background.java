import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Background extends GameObj {
	public static final String img_file = "shop.jpg";
	public static final String chair = "chair.jpg";
	public static final int sizeX = 1000;
	public static final int sizeY = 600;
	public static final int INIT_X = 0;
	public static final int INIT_Y = -200;
	public static final int INIT_VEL_X = 0;
	public static final int INIT_VEL_Y = 0;

	private static BufferedImage img;
	private static BufferedImage chairImg;

	public Background (int courtWidth, int courtHeight) {
		super(INIT_VEL_X, INIT_VEL_Y, INIT_X, INIT_Y, sizeX, sizeY, courtWidth,
				courtHeight);
		try {
			if (img == null) {
				img = ImageIO.read(new File(img_file));
			}

				chairImg = ImageIO.read(new File(chair));

		} catch (IOException e) {
			System.out.println("Internal Error:" + e.getMessage());
		}
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(img, pos_x, pos_y, width, height, null);
		g.drawImage(chairImg, 0, 180, 150, 100, null);
		g.drawImage(chairImg, 250, 180, 150, 100, null);
		g.drawImage(chairImg, 500, 180, 150, 100, null);
		g.drawImage(chairImg, 750, 180, 150, 100, null);
	}

}