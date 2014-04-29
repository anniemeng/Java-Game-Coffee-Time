
// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
	private ArrayList<JLabel> ingredientArea = new ArrayList<JLabel>();		//displays ingredients selected
	private ArrayList<JButton> areaButtons = new ArrayList<JButton>();		//tracks ingredient buttons 
	private TreeSet<String> submitted = new TreeSet<String>();				//stores creation
	
	private static HashMap<TreeSet<String>, String> recipes;				//recipe book
	public static TreeMap<String, String> output;							//matches output to image
	public static String[] images = new String[5];							//output images
	
	public static JLabel scoreCnt = new JLabel();							//score counts
	final static GameCourt court = new GameCourt(scoreCnt);
	private JPanel topPanelChange;
	private final JFrame frame = new JFrame("Study Break");;
	
	//keep track of ingredients selected and displays
	public void addIngredient(Image img, JButton button, String name) {
		submitted.add(name);
		areaButtons.add(button);
		
		// create new image icon 
		JLabel added = new JLabel(new ImageIcon(img));
		added.setPreferredSize(new Dimension(50, 50));
		ingredientArea.add(added);
		
		// add jlabels 
		for (int i = 0; i < ingredientArea.size(); i++) {
			topPanelChange.add(ingredientArea.get(i));
		}
		 
		topPanelChange.repaint();
		frame.setVisible(true);
	}
	
	//clear button clicked
	public void canvasClear() {
		submitted.clear();
		ingredientArea.clear();
		areaButtons.clear();
		topPanelChange.removeAll();
		topPanelChange.repaint();
		frame.setVisible(true);
	}
	
	//create button clicked
	public void canvasSubmit() {		
		if (recipes.containsKey(submitted)) {
			String nameDrink = recipes.get(submitted);
			String file = output.get(nameDrink);
			court.addToConveyor(file, nameDrink);
			frame.setVisible(true);
			canvasClear();
		}
		else {
			canvasClear();
		}
		
	}

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
		
		//score panel
		scoreCnt.setText("$ 0");
		scoreCnt.setBorder(BorderFactory.createLineBorder(Color.black));
		control_panel.add(scoreCnt, "Center");

		//contains quit and instructions
		final JPanel topButtons = new JPanel(new FlowLayout());
		control_panel.add(topButtons, "East");
		
		//start button
	    final JButton start = new JButton("Start");
	    start.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		court.playing = true;
	         }
	    });
	    topButtons.add(start);
		
		//instructions button
	    final JButton instructions = new JButton("Instructions");
	    instructions.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		JOptionPane.showMessageDialog(court, "instructions go here", "Instructions", JOptionPane.INFORMATION_MESSAGE);
	            court.requestFocusInWindow();
	         }
	    });
	    topButtons.add(instructions);
		
		// quit button
		final JButton quit = new JButton("Quit");
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				court.quit();
			}
		});
		topButtons.add(quit);
		
		
		// BOTTOM PANEL
		final JPanel bottom = new JPanel(new BorderLayout());
		bottom.setPreferredSize(new Dimension(1000, 300));
		frame.add(bottom, BorderLayout.SOUTH);
		
		
		// INGREDIENT BUTTONS
		final JPanel ingredients = new JPanel(new GridLayout(2, 4, 10, 10));
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
					if (label < 10) {
						areaButtons.get(i).setText("" + (label + 1));
					}
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
					addIngredient(resizeMug, normCupButton, "mug");
				}
			}
		});
		
		normCupButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent m) {
				if (SwingUtilities.isRightMouseButton(m)) {
					
					//restock ingredients
					int label = Integer.parseInt(normCupButton.getText());
					if (label == 0) {
						//choose store to buy from
						int option = (int) (Math.random() * 3);
						Object[] possibilities = {"FroGo", "Gourmet Grocer", "CVS"};
						String store = possibilities[option].toString();
						
						//dialog with options
						try {
							String selection = (String)JOptionPane.showInputDialog(
												frame,
												"Please buy ingredients from "
												+ store,
												"Grocery Shopping!",
												JOptionPane.PLAIN_MESSAGE,
												new ImageIcon(Customers.getImage()),
												possibilities,
												"FroGo");
							
							//chose right item
							if ((selection != null) && (selection == store)) {
								normCupButton.setText("10");
							}
						
							//failed to choose right item
							else {
								JOptionPane.showMessageDialog(frame,
								    "WRONG SELECTION: please try again");
							}
						
					} catch (HeadlessException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					}
				}
			}
		});
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
					addIngredient(resizeToGo, toGoCupButton, "togo");
				}
			}
		});
		ingredients.add(toGoCupButton);
		
		//SHORT MUG
		Image espCup = null;
		try {
			espCup = ImageIO.read(new File ("espressocup.png"));
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
					addIngredient(espResize, shortCupButton, "espresso");
				}
			}
		});
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
					addIngredient(coffeeBeanResize, coffeeBeanButton, "bean");
				}
			}
		});
		ingredients.add(coffeeBeanButton);
		
		
		//CHOCOLATE 
		Image chocolate = null;
		try {
			chocolate = ImageIO.read(new File ("chocolate.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		final Image chocolateResize = chocolate.getScaledInstance(40, 60, 0);
		
		ImageIcon chocImg = new ImageIcon(chocolateResize);
		final JButton chocButton = new JButton("10", chocImg);
		chocButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int label = Integer.parseInt(chocButton.getText());
				if (label != 0) {
					chocButton.setText("" + (label - 1));
					addIngredient(chocolateResize, chocButton, "chocolate");
				}
			}
		});
		
		ingredients.add(chocButton);
		
		//MILK
		Image milk = null;
		try {
			milk = ImageIO.read(new File ("milk.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		final Image milkResize = milk.getScaledInstance(40, 60, 0);
		
		
		ImageIcon milkImg = new ImageIcon(milkResize);
		final JButton milkButton = new JButton("10", milkImg);
		milkButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int label = Integer.parseInt(milkButton.getText());
				if (label != 0) {
					milkButton.setText("" + (label - 1));
					addIngredient(milkResize, milkButton, "milk");
				}
			}
		});
		ingredients.add(milkButton);
		
		
		//TEA
		Image tea = null;
		try {
			tea = ImageIO.read(new File ("teabag.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		final Image teaResize = tea.getScaledInstance(50, 60, 0);
		
		ImageIcon teaImg = new ImageIcon(teaResize);
		final JButton teaButton = new JButton("10", teaImg);
		teaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int label = Integer.parseInt(teaButton.getText());
				if (label != 0) {
					teaButton.setText("" + (label - 1));
					addIngredient(teaResize, teaButton, "tea");
				}
			}
		});
		ingredients.add(teaButton);
		
		//WHIPPED CREAM
		Image cream = null;
		try {
			cream = ImageIO.read(new File ("cream.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		final Image creamResize = cream.getScaledInstance(50, 40, 0);
		
		ImageIcon creamImg = new ImageIcon(creamResize);
		final JButton creamButton = new JButton("10", creamImg);
		creamButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int label = Integer.parseInt(creamButton.getText());
				if (label != 0) {
					creamButton.setText("" + (label - 1));
					addIngredient(creamResize, creamButton, "cream");
				}
			}
		});
		ingredients.add(creamButton);
		
		/*

		//ICE
		ImageIcon iceImg = new ImageIcon("coffeecup.jpg");
		final JButton iceButton = new JButton("10", iceImg);
		iceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addIngredient(creationTop, frame, "coffeecup.jpg");
			}
		});
		ingredients.add(iceButton);
		

		
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
		
	*/

		
		//RECIPE AND PHONE
		final JPanel interactionArea = new JPanel(new GridLayout(2, 1, 0, 30));
		interactionArea.setPreferredSize(new Dimension(200, 300));
		interactionArea.setBorder(BorderFactory.createLineBorder(Color.black));
		bottom.add(interactionArea, BorderLayout.EAST);
		
		final JButton recipes = new JButton("Recipes");
		recipes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane recipePopUp = new JOptionPane();
				recipePopUp.showMessageDialog(interactionArea, "RECIPEBOOK", "Recipes", JOptionPane.INFORMATION_MESSAGE);
	            court.requestFocusInWindow();
	            
			}
		});
		interactionArea.add(recipes, "NORTH");
		
	 
		
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
		
		String[] teaList = {"togo", "tea"};
		recipeBook.setRecipes(teaList, "tea");
		
		String[] mochaList = {"togo", "bean", "chocolate"};
		recipeBook.setRecipes(mochaList, "mocha");
		
		String[] latteList = {"mug", "bean", "milk", "cream"};
		recipeBook.setRecipes(latteList, "latte");
		
		//change Recipes to HashMap
		recipes = recipeBook.getBook();
		
		
		
		//INITIALIZE IMAGE OUTPUTS
		Output outputImgs = new Output();
		outputImgs.setOutput("coffee", "coffee.png");
		outputImgs.setOutput("espresso", "espresso.png");
		outputImgs.setOutput("mocha", "mocha.png");
		outputImgs.setOutput("latte", "latte.png");
		outputImgs.setOutput("tea", "tea.png");
		
		//change Output to TreeMap
		output = outputImgs.getOutput();
		
		//temp = output.values().toArray();
		
		//GET ARRAY OF IMAGES FOR MATCHING LATER 
		/*
		 for (int i = 0; i <temp.length; i++) {
			images[i] = temp[i].toString();
		}
		 */

		SwingUtilities.invokeLater(new Game());
	}
}
