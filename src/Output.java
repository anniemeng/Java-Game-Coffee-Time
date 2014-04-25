import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.imageio.ImageIO;


public class Output {
	
	private String name;
	private Image img;
	private TreeMap<String, Image> output;

	public Output() {
		output = new TreeMap<String, Image>();
	}
	
	public TreeMap<String,Image> getOutput() {
		return output;
	}
	
	public void setOutput(String name, String file) throws IOException {
		this.name = name;

		Image temp = ImageIO.read(new File (file));
		img = temp.getScaledInstance(40, 50, 0);
		output.put(name,img);
		
	}
	

}