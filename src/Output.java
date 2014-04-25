import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.imageio.ImageIO;


public class Output {
	
	//ONE word associated with String 
	private static HashMap<TreeSet<String>, String> recipes = new HashMap<TreeSet<String>, String>();
	static TreeSet<String> coffeeRecipe = new TreeSet<String>();
	public static HashMap<TreeSet<String>, String> getString() {
		return recipes;
	}
	
	
	//get image with ONE word
	private static TreeMap<String, Image> output = new TreeMap<String, Image>();
	static String coffee = "coffee";
	public static TreeMap<String,Image> getImage() {
		return output;
	}
	
	public static void main (String[] args) throws IOException {
		//RECIPES
		coffeeRecipe.add("mug");
		coffeeRecipe.add("bean");
		recipes.put(coffeeRecipe, coffee);
		
		//LINK RECIPE TO IMAGE
		Image c = ImageIO.read(new File ("coffeecup.png"));
		c = c.getScaledInstance(40, 50, 0);
		output.put(coffee,c);
	}
	
	
	
	
	
}