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
	private String img;
	private TreeMap<String, String> output;

	public Output() {
		output = new TreeMap<String, String>();
	}
	
	public TreeMap<String,String> getOutput() {
		return output;
	}
	
	public void setOutput(String name, String file) throws IOException {
		this.name = name;

		output.put(name,file);
		
	}
	

}