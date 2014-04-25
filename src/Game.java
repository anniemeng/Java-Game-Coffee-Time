
// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.imageio.ImageIO;
import javax.swing.*;


/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
	
	//DATA STRUCTURE TO STORE INGREDIENTS APPEARING IN CREATION AREA
	//ARRAYLIST

	private ArrayList<JLabel> ingredientArea = new ArrayList<JLabel>();
	private ArrayList<JButton> areaButtons = new ArrayList<JButton>();
	private TreeSet<String> submitted = new TreeSet<String>();
	
	private static HashMap<TreeSet<String>, String> recipes;
	private static TreeMap<String, String> output;
	private JPanel topPanelChange;
	private final JFrame frame = new JFrame("Study Break");;
	
	//METHODS TO MANIPULATE ARRAYLIST
	public void addIngredient(JPanel current, JFrame frame, Image img, JButton button, String name) {
		submitted.add(name);
		areaButtons.add(button);
		JLabel added = new JLabel(new ImageIcon(img));
		added.setPreferredSize(new Dimension(50, 50));
		ingredientArea.add(added);
		
		// add jlabels to panel
		for (int i = 0; i < ingredientArea.size(); i++) {
			current.add(ingredientArea.get(i));
		}
		
		// then display 
		current.repaint();
		frame.setVisible(true);
	}
	
	//temp
	public void addIngredient(JPanel current, JFrame frame, String text) {

	}
	
	//if clear button clicked
	public void canvasClear() {
		submitted.clear();
		ingredientArea.clear();
		areaButtons.clear();
		topPanelChange.removeAll();
		topPanelChange.repaint();
		frame.setVisible(true);
	}
	
	//if create button clicked
	public void canvasSubmit() {		
		if (recipes.containsKey(submitted)) {
			String nameDrink = recipes.get(submitted);
			String now = output.get(nameDrink);
			ConveyorItem test = new ConveyorItem(GameCourt.COURT_HEIGHT, GameCourt.COURT_WIDTH, now);
			court.addToConveyor(test);
			court.repaint();
			frame.setVisible(true);
			System.out.println("true");
		}
		else System.out.println("false");
		
		canvasClear();
	}
	final static GameCourt court = new GameCourt();

	public void run() {

		// Top-level frame in which game components live
		frame.setLocation(200, 200);

		// Main playing area
		//final GameCourt court = new GameCourt();
		court.setBorder(BorderFactory.createLineBorder(Color.black));
		frame.add(court, BorderLayout.CENTER);

		
		// TOP PANEL
		final JPanel control_panel = new JPanel();
		BorderLayout top = new BorderLayout();
		top.setHgap(50);
		control_panel.setLayout(top);
		frame.add(control_panel, BorderLayout.NORTH);
		
		//title
		final JLabel title = new JLabel("Penn Cafe");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		control_panel.add(title, "West");
		
		/*score label
		final JLabel score = new JLabel("$");
		control_panel.add(score, "Center");
		*/
		
		//score count
		final JLabel scoreCnt = new JLabel("$ 0");
		scoreCnt.setBorder(BorderFactory.createLineBorder(Color.black));
		control_panel.add(scoreCnt, "Center");
		
		// quit button
		final JButton quit = new JButton("Quit");
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				court.quit();
			}
		});
		control_panel.add(quit, "East");
		
		
		// BOTTOM PANEL
		final JPanel bottom = new JPanel(new BorderLayout());
		bottom.setPreferredSize(new Dimension(1000, 300));
		frame.add(bottom, BorderLayout.SOUTH);
		
		
		// INGREDIENT BUTTONS
		final JPanel ingredients = new JPanel(new GridLayout(3, 4, 10, 10));
		ingredients.setPreferredSize(new Dimension(400, 300));
		bottom.add(ingredients, BorderLayout.WEST);
		
		
		//CREATION AREA
		final JPanel creationArea = new JPanel(new BorderLayout());
		creationArea.setPreferredSize(new Dimension(400, 300));
		creationArea.setBorder(BorderFactory.createLineBorder(Color.black));
		bottom.add(creationArea, BorderLayout.CENTER);
		
		//BUTTONS IN CREATION
		final JPanel creationButtons = new JPanel(new FlowLayout());
		creationButtons.setBorder(BorderFactory.createLineBorder(Color.black));
		creationArea.add(creationButtons, BorderLayout.PAGE_END);
		
		//CREATION AREA DISPLAY
		final JPanel creationTop = new JPanel();
		topPanelChange = creationTop;
		creationTop.setBorder(BorderFactory.createLineBorder(Color.black));
		creationTop.setLayout(new FlowLayout()); 
		creationArea.add(creationTop, BorderLayout.CENTER);
		
		
		//CREATE BUTTON
		final JButton createButton = new JButton("CREATE");
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvasSubmit();
			}
		});
		creationButtons.add(createButton);
		
		//CLEAR BUTTON
		final JButton clearButton = new JButton("X");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//reset counter of buttons
				for (int i = 0; i < areaButtons.size(); i++) {
					int label = Integer.parseInt(areaButtons.get(i).getText());
					areaButtons.get(i).setText("" + (label + 1));
				}
				canvasClear();
			}
		});
		creationButtons.add(clearButton);
		
		
		//NORMAL MUG
		//resize mug image
		Image mug = null;
		try {
			mug = ImageIO.read(new File ("normalcup.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		final Image resizeMug = mug.getScaledInstance(50, 50, 0);
		
		//create button for mug
		ImageIcon normImg = new ImageIcon(resizeMug);
		final JButton normCupButton = new JButton("10", normImg);
		normCupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int label = Integer.parseInt(normCupButton.getText());
				if (label != 0) {
					normCupButton.setText("" + (label - 1));
					addIngredient(creationTop, frame, resizeMug, normCupButton, "mug");
				}

			}
		});
		//add to treemap
		//buttonIds.put("mug", normCupButton);
		
		ingredients.add(normCupButton);
		
		//TALL MUG
		Image toGoCup = null;
		try {
			toGoCup = ImageIO.read(new File ("togocup.jpg"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		final Image resizeToGo = toGoCup.getScaledInstance(40, 50, 0);
		ImageIcon toGoCupImg = new ImageIcon(resizeToGo);
		final JButton toGoCupButton = new JButton("10", toGoCupImg);
		toGoCupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int label = Integer.parseInt(toGoCupButton.getText());
				if (label != 0) {
					toGoCupButton.setText("" + (label - 1));
					addIngredient(creationTop, frame, resizeToGo, toGoCupButton, "togo");
				}
			}
		});
		//add to treemap
		//buttonIds.put("togo", toGoCupButton);
		
		ingredients.add(toGoCupButton);
		
		//SHORT MUG
		Image espCup = null;
		try {
			espCup = ImageIO.read(new File ("espresso.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		final Image espResize = espCup.getScaledInstance(50, 40, 0);
		
		ImageIcon espImg = new ImageIcon(espResize);
		final JButton shortCupButton = new JButton("10", espImg);
		shortCupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int label = Integer.parseInt(shortCupButton.getText());
				if (label != 0) {
					shortCupButton.setText("" + (label - 1));
					addIngredient(creationTop, frame, espResize, shortCupButton, "espresso");
				}
			}
		});
		//add to treemap
		//buttonIds.put("espresso", shortCupButton);
		
		ingredients.add(shortCupButton);
		
		//COFFEE BEANS 
		Image coffeeBean = null;
		try {
			coffeeBean = ImageIO.read(new File ("cbean.jpg"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		final Image coffeeBeanResize = coffeeBean.getScaledInstance(50, 40, 0);
		
		
		ImageIcon coffeeBeanImg = new ImageIcon(coffeeBeanResize);
		final JButton coffeeBeanButton = new JButton("10", coffeeBeanImg);
		coffeeBeanButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int label = Integer.parseInt(coffeeBeanButton.getText());
				if (label != 0) {
					coffeeBeanButton.setText("" + (label - 1));
					addIngredient(creationTop, frame, coffeeBeanResize, coffeeBeanButton, "bean");
				}
			}
		});
		//add to treemap
		//buttonIds.put("bean", coffeeBeanButton);
		
		ingredients.add(coffeeBeanButton);
		
		
		//CHOCOLATE 
		ImageIcon chocImg = new ImageIcon("coffeecup.jpg");
		final JButton chocButton = new JButton("10", chocImg);
		chocButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addIngredient(creationTop, frame, "coffeecup.jpg");
			}
		});
		//add to treemap
		//buttonIds.put("chocolate", chocButton);
		
		ingredients.add(chocButton);
		
		
		//ICE
		ImageIcon iceImg = new ImageIcon("coffeecup.jpg");
		final JButton iceButton = new JButton("10", iceImg);
		iceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addIngredient(creationTop, frame, "coffeecup.jpg");
			}
		});
		//add to treemap
		//buttonIds.put("ice",iceButton);
		
		ingredients.add(iceButton);
		
		
		//MILK
		ImageIcon milkImg = new ImageIcon("coffeecup.jpg");
		final JButton milkButton = new JButton("10", milkImg);
		milkButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addIngredient(creationTop, frame, "coffeecup.jpg");
			}
		});
		//add to treemap
		//buttonIds.put("milk", milkButton);
		
		ingredients.add(milkButton);
		
		//SYRUP
		ImageIcon syrupImg = new ImageIcon("coffeecup.jpg");
		final JButton syrupButton = new JButton("10", syrupImg);
		syrupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addIngredient(creationTop, frame, "coffeecup.jpg");
			}
		});
		//add to treemap
		//buttonIds.put("syrup", syrupButton);
		
		ingredients.add(syrupButton);
		
		//TEA
		ImageIcon teaImg = new ImageIcon("coffeecup.jpg");
		final JButton teaButton = new JButton("10", teaImg);
		teaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addIngredient(creationTop, frame, "coffeecup.jpg");
			}
		});
		//add to treemap
		//buttonIds.put("tea", teaButton);
		
		ingredients.add(teaButton);
		
		//WHIPPED CREAM
		ImageIcon creamImg = new ImageIcon("coffeecup.jpg");
		final JButton creamButton = new JButton("10", creamImg);
		creamButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addIngredient(creationTop, frame, "coffeecup.jpg");
			}
		});
		//add to treemap
		//buttonIds.put("cream", creamButton);
		
		ingredients.add(creamButton);
		
		//CINNAMON 
		ImageIcon cinnaImg = new ImageIcon("coffeecup.jpg");
		final JButton cinnaButton = new JButton("10", cinnaImg);
		cinnaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addIngredient(creationTop, frame, "coffeecup.jpg");
			}
		});
		//add to treemap
		//buttonIds.put("cinnamon", cinnaButton);
		
		ingredients.add(cinnaButton);
		

		//VANILLA
		ImageIcon vanillaImg = new ImageIcon("coffeecup.jpg");
		final JButton vanillaButton = new JButton("10", vanillaImg);
		vanillaButton.setPreferredSize(new Dimension(50, 50));
		vanillaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addIngredient(creationTop, frame, "coffeecup.jpg");
			}
		});
		//add to treemap
		//buttonIds.put( "vanilla", vanillaButton);
		
		ingredients.add(vanillaButton);	
		


		
		//RECIPE AND PHONE
		final JPanel interactionArea = new JPanel(new GridLayout(2, 1, 0, 30));
		interactionArea.setPreferredSize(new Dimension(200, 300));
		interactionArea.setBorder(BorderFactory.createLineBorder(Color.black));
		bottom.add(interactionArea, BorderLayout.EAST);
	 
		
		// Put the frame on the screen
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		// Start game
		court.reset();
	}

	/*
	 * Main method run to start and run the game Initializes the GUI elements
	 * specified in Game and runs it IMPORTANT: Do NOT delete! You MUST include
	 * this in the final submission of your game.
	 */
	public static void main(String[] args) throws IOException {
		
		//INITIALIZE RECIPES
		//create new book
		Recipes recipeBook = new Recipes();
		
		//adding recipes
		String[] coffeeList = {"mug", "bean"};
		recipeBook.setRecipes(coffeeList, "coffee");
		
		String[] espressoList = {"espresso", "bean"};
		recipeBook.setRecipes(espressoList, "espresso");
		

		
		//change Recipes to HashMap
		recipes = recipeBook.getBook();
		
		//INITIALIZE IMAGE OUTPUTS
		Output outputImgs = new Output();
		outputImgs.setOutput("coffee", "coffee.png");
		
		//change Output to TreeMap
		output = outputImgs.getOutput();

		//INITIALIZE CUSTOMERS
		court.addToCustomers();
	
		SwingUtilities.invokeLater(new Game());
	}
}
