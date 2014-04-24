import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.imageio.ImageIO;


public class Output {
	private static TreeMap<Image, Set<String>> output = new TreeMap<Image, Set<String>>();
	
	//COMPARE FOR CONVEYOR
	static Set<String> coffee = new TreeSet<String>();
	
	
	public static TreeMap<Image, Set<String>> getOutput() {
		return output;
	}
	
	public static void main (String[] args) throws IOException {
		coffee.add("mug");
		coffee.add("bean");
		Image c = ImageIO.read(new File ("coffeecup.png"));
		c = c.getScaledInstance(40, 50, 0);
		output.put(c, coffee);
	}
	
	
	
	
	
}