import java.util.HashMap;
import java.util.TreeSet;

public class Recipes {
	private TreeSet<String> ingredients;
	private String name;
	
	
	private HashMap<TreeSet<String>, String> recipeBook; 
	
	public Recipes() {
		recipeBook = new HashMap<TreeSet<String>, String>();
	}
	
	public HashMap<TreeSet<String>, String> getBook() {
		return recipeBook;
	}
	
	public TreeSet<String> getRecipes() {
		return ingredients;
	}
	
	public String getName() {
		return name;
	}
	
	public void setRecipes(String[] items, String name) {
		ingredients = new TreeSet<String>();
		for (int i = 0; i < items.length; i++) {
			ingredients.add(items[i]);
		}
		this.name = name;
		recipeBook.put(ingredients, name);
	}

	
}