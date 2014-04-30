
// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;


/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
	
	//DATA STRUCTURE TO STORE INGREDIENTS APPEARING IN CREATION AREA
	private ArrayList<JLabel> ingredientArea = new ArrayList<JLabel>();		//displays ingredients selected
	private ArrayList<JButton> areaButtons = new ArrayList<JButton>();		//tracks ingredient buttons selected 
	private TreeSet<String> submitted = new TreeSet<String>();				//stores creation
	private ArrayList<JButton> allButtons = new ArrayList<JButton>();       //keeps track of all ingredient buttons
																			//created
	
	private HashMap<String, Image> ingredientImgs = new HashMap<String,Image>();  //ingredient images 
	private static HashMap<TreeSet<String>, String> recipes;				//recipe book
	public static TreeMap<String, String> output;							//output images					
	
	public static JLabel scoreCnt = new JLabel();							//score counts
	final static GameCourt court = new GameCourt(scoreCnt);
	private JPanel topPanelChange;
	private JTabbedPane recipeDisplay;
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

	//manipulate ingredient buttons
	public void ingrButtons() {
		for (int i = 0; i < allButtons.size(); i++) {
			allButtons.get(i).setBackground(new Color(221,184,128));
			allButtons.get(i).setOpaque(true);
			allButtons.get(i).setBorder(BorderFactory.createLoweredBevelBorder());
		}
	}
	
	//refill supplies dialog
	public void supplies(JButton button) {
		//restock ingredients
		int label = Integer.parseInt(button.getText());
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
					button.setText("10");
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
	
	
	public void run() {

		// Top-level frame in which game components live
		frame.setLocation(0, 0);
		
		// Main playing area
		//final GameCourt court = new GameCourt();
		court.setBorder(BorderFactory.createLineBorder(Color.black));
		frame.add(court, BorderLayout.CENTER);
		
		// TOP PANEL
		final JPanel control_panel = new JPanel();
		control_panel.setBackground(new Color(34,17,22));
		BorderLayout top = new BorderLayout();
		top.setHgap(50);
		control_panel.setLayout(top);
		frame.add(control_panel, BorderLayout.NORTH);
		
		//title
		final JLabel title = new JLabel("      Penn Cafe");
		title.setForeground(Color.white);
		title.setFont(new Font("Lucida Calligraphy", Font.BOLD, 20));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		control_panel.add(title, "West");
		
		//score panel
		scoreCnt.setFont(new Font("Monotype Corsiva", Font.PLAIN, 18));
		scoreCnt.setHorizontalAlignment(SwingConstants.CENTER);
		scoreCnt.setText("$ 0");
		scoreCnt.setForeground(Color.white);
		control_panel.add(scoreCnt, "Center");

		//contains quit and instructions
		final JPanel topButtons = new JPanel(new FlowLayout());
		topButtons.setBackground(new Color(34,17,22));
		control_panel.add(topButtons, "East");
		
		//restart button
	    final JButton restart = new JButton("Restart");
	    restart.setFont(new Font("Monotype Corsiva", Font.BOLD, 14));
	    restart.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		try {
					canvasClear();	
					//reset button labels
					for (int i = 0; i < allButtons.size(); i++) {
						allButtons.get(i).setText("10");
					}
					court.restart();
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
	         }
	    });
	    topButtons.add(restart);
		
		//instructions button
	    final JButton instructions = new JButton("Instructions");
	    instructions.setFont(new Font("Monotype Corsiva", Font.BOLD, 14));
	    instructions.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		JOptionPane.showMessageDialog(court, 
				"Today, you'll be a barista for the day!"
				+ "\n \n GOAL:"
		 		+ "\n College students low on sleep will be dropping in "
		 		+ "randomly with orders to the left of their head."
		 		+ "\n You have to FULFILL these orders!" 
		 		+ "\n \n HOW TO CREATE:"
		 		+ "\n The RECIPE BOOK contains all the ingredients for "
		 		+ "each drink. Use this to make the drink correctly!"
		 		+ "\n Create their orders by clicking on the ingredient button."
		 		+ "\n The ingredients you select will appear in the "
		 		+ "creation area. \n When finished "
		 		+ "click 'CREATE'." + "\n They will be put on the "
		 		+ "conveyor belt and if they match the customer's order, "
		 		+ "they'll disappear and you earn $10!" 
		 		+ "\n \n WATCH OUT!" 
		 		+ "\n Ingredients will run out! The number beside the"
		 		+ " ingredient button shows how much is left." 
		 		+ "\n If it reaches 0, right click the button and follow the "
		 		+ "instructions on the dialog. "
		 		+ "\n ONLY if you follow it CORRECTLY will your supplies refill!"
		 		+ "\n \n ALSO BEWARE CUSTOMER WAITING TIME! After a certain "
		 		+ "amount of time your customers will be unhappy and leave!"
		 		+ " \n \n HOW TO WIN: Your Boss wants $100!", 
	    				"Instructions", JOptionPane.INFORMATION_MESSAGE);
	            court.requestFocusInWindow();
	         }
	    });
	    topButtons.add(instructions);
		
		// quit button
		final JButton quit = new JButton("Quit");
	    quit.setFont(new Font("Monotype Corsiva", Font.BOLD, 14));
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
		
		//CREATION AREA
		final JPanel creationArea = new JPanel(new BorderLayout());
		creationArea.setPreferredSize(new Dimension(400, 300));
		creationArea.setBorder(BorderFactory.createLoweredBevelBorder());
		bottom.add(creationArea, BorderLayout.CENTER);
		
		//BUTTONS IN CREATION
		final JPanel creationButtons = new JPanel(new FlowLayout());
		creationButtons.setBackground(new Color(34,17,22));
		creationButtons.setBorder(BorderFactory.createLineBorder(Color.black, 
																5));
		creationArea.add(creationButtons, BorderLayout.PAGE_END);
		
		//CREATION AREA DISPLAY
		final JPanel creationTop = new JPanel();
		topPanelChange = creationTop;
		creationTop.setBackground(new Color(237, 219, 166));
		creationTop.setBorder(BorderFactory.createLineBorder(Color.black));
		creationTop.setLayout(new FlowLayout()); 
		creationArea.add(creationTop, BorderLayout.CENTER);
		
		//CREATE BUTTON
		final JButton createButton = new JButton("Create");
	    createButton.setFont(new Font("Monotype Corsiva", Font.BOLD, 20));
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvasSubmit();
			}
		});
		creationButtons.add(createButton);
		
		//CLEAR BUTTON
		final JButton clearButton = new JButton("X");
	    clearButton.setFont(new Font("Monotype Corsiva", Font.BOLD, 20));
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
		
		//INGREDIENT BUTTONS!
		final JPanel ingredients = new JPanel(new GridLayout(2, 4, 10, 10));
		ingredients.setPreferredSize(new Dimension(400, 300));
		ingredients.setBackground(new Color(53,26,5));
		bottom.add(ingredients, BorderLayout.WEST);
		
		//NORMAL MUG
		Image mug = null;
		try {
			mug = ImageIO.read(new File ("normalcup.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		final Image resizeMug = mug.getScaledInstance(50, 50, 0);
		ingredientImgs.put("mug", resizeMug);
		
		//create button for mug
		ImageIcon normImg = new ImageIcon(resizeMug);
		final JButton normCupButton = new JButton("10", normImg);
		allButtons.add(normCupButton);
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
					supplies(normCupButton);
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
		ingredientImgs.put("togo", resizeToGo);
		ImageIcon toGoCupImg = new ImageIcon(resizeToGo);
		final JButton toGoCupButton = new JButton("10", toGoCupImg);
		allButtons.add(toGoCupButton);
		toGoCupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int label = Integer.parseInt(toGoCupButton.getText());
				if (label != 0) {
					toGoCupButton.setText("" + (label - 1));
					addIngredient(resizeToGo, toGoCupButton, "togo");
				}
			}
		});
		
		toGoCupButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent m) {
				if (SwingUtilities.isRightMouseButton(m)) {
					supplies(toGoCupButton);
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
		ingredientImgs.put("espresso", espResize);
		ImageIcon espImg = new ImageIcon(espResize);
		
		final JButton shortCupButton = new JButton("10", espImg);
		allButtons.add(shortCupButton);
		shortCupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int label = Integer.parseInt(shortCupButton.getText());
				if (label != 0) {
					shortCupButton.setText("" + (label - 1));
					addIngredient(espResize, shortCupButton, "espresso");
				}
			}
		});
		
		shortCupButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent m) {
				if (SwingUtilities.isRightMouseButton(m)) {
					supplies(shortCupButton);
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
		ingredientImgs.put("bean", coffeeBeanResize);
		ImageIcon coffeeBeanImg = new ImageIcon(coffeeBeanResize);
		
		final JButton coffeeBeanButton = new JButton("10", coffeeBeanImg);
		allButtons.add(coffeeBeanButton);
		coffeeBeanButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int label = Integer.parseInt(coffeeBeanButton.getText());
				if (label != 0) {
					coffeeBeanButton.setText("" + (label - 1));
					addIngredient(coffeeBeanResize, coffeeBeanButton, "bean");
				}
			}
		});
		
		coffeeBeanButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent m) {
				if (SwingUtilities.isRightMouseButton(m)) {
					supplies(coffeeBeanButton);
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
		ingredientImgs.put("chocolate", chocolateResize);
		
		final JButton chocButton = new JButton("10", chocImg);
		allButtons.add(chocButton);
		chocButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int label = Integer.parseInt(chocButton.getText());
				if (label != 0) {
					chocButton.setText("" + (label - 1));
					addIngredient(chocolateResize, chocButton, "chocolate");
				}
			}
		});
		
		chocButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent m) {
				if (SwingUtilities.isRightMouseButton(m)) {
					supplies(chocButton);
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
		ingredientImgs.put("milk", milkResize);
		
		final JButton milkButton = new JButton("10", milkImg);
		allButtons.add(milkButton);
		milkButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int label = Integer.parseInt(milkButton.getText());
				if (label != 0) {
					milkButton.setText("" + (label - 1));
					addIngredient(milkResize, milkButton, "milk");
				}
			}
		});
		
		milkButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent m) {
				if (SwingUtilities.isRightMouseButton(m)) {
					supplies(milkButton);
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
		ingredientImgs.put("tea", teaResize);
		
		final JButton teaButton = new JButton("10", teaImg);
		allButtons.add(teaButton);
		teaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int label = Integer.parseInt(teaButton.getText());
				if (label != 0) {
					teaButton.setText("" + (label - 1));
					addIngredient(teaResize, teaButton, "tea");
				}
			}
		});
		
		teaButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent m) {
				if (SwingUtilities.isRightMouseButton(m)) {
					supplies(teaButton);
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
		ingredientImgs.put("cream", creamResize);
		
		final JButton creamButton = new JButton("10", creamImg);
		allButtons.add(creamButton);
		creamButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int label = Integer.parseInt(creamButton.getText());
				if (label != 0) {
					creamButton.setText("" + (label - 1));
					addIngredient(creamResize, creamButton, "cream");
				}
			}
		});
		
		creamButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent m) {
				if (SwingUtilities.isRightMouseButton(m)) {
					supplies(creamButton);
				}
			}
		});
		
		ingredients.add(creamButton);
		
		// CALL ON BUTTON METHOD
		ingrButtons();
		
		//RECIPE AREA
		final JButton recipesButton = new JButton("Recipes");
		recipesButton.setPreferredSize(new Dimension(200, 300));
		recipesButton.setBackground(new Color(97,25,11));
		recipesButton.setOpaque(true);
		recipesButton.setBorder(BorderFactory.createLoweredBevelBorder());
		recipesButton.setFont(new Font("Monotype Corsiva", Font.BOLD, 30));
		recipesButton.setForeground(Color.white);
		recipesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//NEW JFRAME OPTION
				JFrame newRecipes = new JFrame("Recipe Book");
				newRecipes.setLocation(1000,500);
				newRecipes.setSize(new Dimension(300,300));
				newRecipes.getContentPane().setBackground(new 
															Color(255,250,222));
				
				//TABBED OPTION PANE OPTION
				recipeDisplay = new JTabbedPane(JTabbedPane.TOP);
				JComponent panel1;
				//number of separate recipes
				for (Map.Entry<TreeSet<String>, String> entry :
														recipes.entrySet()) {
					//new tab panel
					panel1 = new JPanel();
					panel1.setLayout(new BorderLayout());
					
					//diplay recipe
					TreeSet<String> ingr = entry.getKey();
					String name = entry.getValue();
					System.out.println(name);
					String imgName = output.get(name);
					System.out.println(imgName);
					
					//display final product
					JPanel recipeBlock = new JPanel();
					recipeBlock.setBorder(BorderFactory.createRaisedBevelBorder());
					recipeBlock.setLocation(0,0);
					
					ImageIcon icon = null;
					try {
						icon = new ImageIcon(ImageIO.read(new File(imgName)));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					JLabel product = new JLabel(name, icon, JLabel.CENTER);
					recipeBlock.add(product);
					
					//display ingredients
					JPanel ingredientDisp = new JPanel();
					ingredientDisp.setLayout(new FlowLayout(FlowLayout.LEADING, 
															20, 0));
					Border raisedBev = BorderFactory.createRaisedBevelBorder();
					ingredientDisp.setBorder(raisedBev);
					TreeSet<String> currentIngredients = null;
			        for(Map.Entry<TreeSet<String>, String> ingredientList : 
			        									recipes.entrySet()) {
			            if (name != null && 
			            			name.equals(ingredientList.getValue())) {
			                currentIngredients = ingredientList.getKey();
			                break;
			            }
			        }
			        
			        Iterator<String> onTreeSet = currentIngredients.iterator();
			        while (onTreeSet.hasNext()) {
			        	String current = onTreeSet.next();
			        	Icon img = new ImageIcon(ingredientImgs.get(current));
			        	JLabel ingredientLabel = new JLabel(null, img, 
			        										JLabel.CENTER);
			        	ingredientDisp.add(ingredientLabel);
			        }
			        
					panel1.add(recipeBlock, BorderLayout.WEST);
					panel1.add(ingredientDisp, BorderLayout.CENTER);
					recipeDisplay.addTab(null, icon, panel1);
				
				}
				recipeDisplay.setVisible(true);
				newRecipes.setContentPane(recipeDisplay);
				newRecipes.setVisible(true);
				court.requestFocusInWindow();
			}
		});
		bottom.add(recipesButton, BorderLayout.EAST);
		
		// Put the frame on the screen
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		// Start game
		try {
			court.reset();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
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
		
		//ADD NEW RECIPES
		/*
		String[] test = {"mug", "milk"};
		recipeBook.setRecipes(test, "test");
		*/
		//change Recipes to HashMap
		recipes = recipeBook.getBook();
		
		
		
		//INITIALIZE IMAGE OUTPUTS
		Output outputImgs = new Output();
		outputImgs.setOutput("coffee", "coffee.png");
		outputImgs.setOutput("espresso", "espresso.png");
		outputImgs.setOutput("mocha", "mocha.png");
		outputImgs.setOutput("latte", "latte.png");
		outputImgs.setOutput("tea", "tea.png");
		
		//ADD NEW RECIPES
		//outputImgs.setOutput("test", "coffeecup.png");
		
		//change Output to TreeMap
		output = outputImgs.getOutput();

		SwingUtilities.invokeLater(new Game());
	}
}
