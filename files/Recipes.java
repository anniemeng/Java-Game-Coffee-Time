import java.util.HashMap;
import java.util.TreeSet;

/**
 * Recipes constructs a new recipe book that stores the ingredients and 
 * associates them with the name of the recipe
 *
 */
public class Recipes {
	private TreeSet<String> ingredients;
	private HashMap<TreeSet<String>, String> recipeBook; 
	
	public Recipes() {
		recipeBook = new HashMap<TreeSet<String>, String>();
	}
	
	public HashMap<TreeSet<String>, String> getBook() {
		return recipeBook;
	}
	
	public void setRecipes(String[] items, String name) {
		ingredients = new TreeSet<String>();
		for (int i = 0; i < items.length; i++) {
			ingredients.add(items[i]);
		}
		recipeBook.put(ingredients, name);
	}
}