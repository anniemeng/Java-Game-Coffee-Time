import java.util.TreeSet;

public class Recipes {
	private TreeSet<String> ingredients;
	//private String name;
	
	
	//private HashMap<TreeSet<String>, String> recipeBook = new HashMap<TreeSet<String>, String>();
	
	public Recipes(String[] items) {
		for (int i = 0; i < items.length; i++) {
			ingredients.add(items[i]);
		}
		//this.name = name;
	}
	
	public TreeSet<String> getRecipes() {
		return ingredients;
	}
	/*
	public String getName() {
		return name;
	}
	*/
	public void setRecipes(TreeSet<String> ingredients) {
		this.ingredients = ingredients;
		//this. name = name;
	}

	
}