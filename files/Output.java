import java.io.IOException;
import java.util.TreeMap;

/**
 * Output stores the output images needed for the conveyor in a treemap. 
 * It links the name of the drink to the image of the drink itself
 *
 */

public class Output {
	private TreeMap<String, String> output;

	public Output() {
		output = new TreeMap<String, String>();
	}
	
	public TreeMap<String,String> getOutput() {
		return output;
	}
	
	public void setOutput(String name, String file) throws IOException {
		output.put(name,file);
	}
}