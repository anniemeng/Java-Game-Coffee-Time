import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ConveyorItem extends GameObj {
	public String img_file = "";
	public static String name = "";
	public static final int sizeX = 70;
	public static final int sizeY = 70;
	public static final int INIT_X = 0;
	public static final int INIT_Y = 280;
	public static final int INIT_VEL_X = 5;
	public static final int INIT_VEL_Y = 0;

	public static BufferedImage img;

	public ConveyorItem (int courtWidth, int courtHeight, String pic, 
						String name) {
		super(INIT_VEL_X, INIT_VEL_Y, INIT_X, INIT_Y, sizeX, sizeY, courtWidth,
				courtHeight);
			this.name = name;
			img_file = pic;
	}

	
	
	@Override
	public void draw(Graphics g) {
		try {
			img = ImageIO.read(new File(img_file));
			g.drawImage(img, pos_x, pos_y, width, height, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}