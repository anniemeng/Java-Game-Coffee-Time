import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Customers extends GameObj {
	public String img_file = "customer.png";
	public String order = "";
	public String orderImg = "";
	public static final int sizeX = 150;
	public static final int sizeY = 250;
	public static int INIT_X = 0;
	public static int INIT_Y = 45;
	public static final int INIT_VEL_X = 0;
	public static final int INIT_VEL_Y = 0;

	private static BufferedImage img;
	private static BufferedImage drink;

	public Customers (int courtWidth, int courtHeight, int locX, String order, String orderImg) {
		super(INIT_VEL_X, INIT_VEL_Y, locX, INIT_Y, sizeX, sizeY, courtWidth,
				courtHeight);
		try {
			this.order = order;
			INIT_X = locX;
			if (img == null) {
				img = ImageIO.read(new File(img_file));
			}
			
			this.orderImg = orderImg;
			
			if (drink == null) {
				drink = ImageIO.read(new File(orderImg));
			}
			
			
			
		} catch (IOException e) {
			System.out.println("Internal Error:" + e.getMessage());
		}
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(img, pos_x, pos_y, width, height, null);
		g.drawImage(drink, pos_x, 0, width/2, height/4, null);
	}

}