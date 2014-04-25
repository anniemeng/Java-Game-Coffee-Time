import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Customers extends GameObj {
	public static String img_file = "student.jpg";
	public static final int sizeX = 200;
	public static final int sizeY = 250;
	public static final int INIT_X = 0;
	public static final int INIT_Y = 0;
	public static final int INIT_VEL_X = 0;
	public static final int INIT_VEL_Y = 0;

	private static BufferedImage img;

	public Customers (int courtWidth, int courtHeight) {
		super(INIT_VEL_X, INIT_VEL_Y, INIT_X, INIT_Y, sizeX, sizeY, courtWidth,
				courtHeight);
		try {

			if (img == null) {
				img = ImageIO.read(new File(img_file));
			}
		} catch (IOException e) {
			System.out.println("Internal Error:" + e.getMessage());
		}
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(img, pos_x, pos_y, width, height, null);
	}

}