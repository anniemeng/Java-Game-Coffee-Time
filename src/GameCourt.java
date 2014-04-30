/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;
import java.util.ArrayList;


/**
 * GameCourt controls customer and conveyor belt item appearance. It also 
 * controls scoring by checking if customer orders are satisfied
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {

	private Conveyor conveyor;
	private Background background;
	private ArrayList<ConveyorItem> onConveyor = new ArrayList<ConveyorItem>();
	private ArrayList<Customers> customerList = new ArrayList<Customers>();
	private int counter = 0;					//number of customers
	private JLabel scoreCnt = new JLabel();
	private int score = 0;
	private ConveyorItem current;
	private boolean[] atLoc = new boolean[4];	 //if customer at certain loc
	
	/** Adds user creation onto the conveyor belt if it is valid
	 * 
	 * @param: file: name of image file 
	 * 		   nameDrink: name of the creation
	 */
	public void addToConveyor(String file, String nameDrink) {
		current = new ConveyorItem(COURT_WIDTH, COURT_HEIGHT, file, nameDrink);
		onConveyor.add(current);
		repaint();
	}

	// the state of the game logic
	public boolean playing = false; 

	// Game constants
	public static final int COURT_WIDTH = 1000;
	public static final int COURT_HEIGHT = 400;
	
	// Update interval for timer, in milliseconds
	public static final int INTERVAL = 35;
	public static final int customerInterval = 5000;
	public static final int timeOut = 25000;
	private Timer people;
	private Timer removePeople;
	
	/** Creates a new game court
	 * 
	 * @param: scoreCnt: scoring label
	 */
	public GameCourt(JLabel scoreCnt) {
		this.scoreCnt = scoreCnt;
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		//overall timer
		Timer timer = new Timer(INTERVAL, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tick();
			}
		});
		timer.start(); 
		
		//customer appearance timer
		people = new Timer(customerInterval, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//only 3 customers allowed 
				if (customerList.size() < 3 && customerList.size() >= 0) {
					if (counter > 3) {
						counter = 0;
					}
				
				//possible locations
				int[] loc = {250, 750, 0, 500};
				int xLoc = loc[counter];
				
				//only if not an existing customer at same location
				if (!atLoc[counter]) {
					atLoc[counter] = true;
				
					//change possible recipes
					int recipeNum = (int) (Math.random() * 5);
					String[] names = Game.output.keySet().toArray(new String[0]);
					String currentName = names[recipeNum];
					String currentFile = Game.output.get(currentName);
				
					//create new customer
					Customers current = new Customers(COURT_WIDTH, COURT_HEIGHT, 
												   		xLoc, currentName,
												   		currentFile);
					customerList.add(current);
					repaint();
					counter += 1;
					}
				}
			}
		});
		people.setInitialDelay(10000);
			
		//customer removal timer
		removePeople = new Timer(timeOut, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!customerList.isEmpty()) {
					int xLocCurrent = customerList.get(0).pos_x;
					if (xLocCurrent == 250) {
						atLoc[0] = false;
					} else if (xLocCurrent == 750) {
						atLoc[1] = false;
					} else if (xLocCurrent == 0) {
						atLoc[2] = false;
					} else if (xLocCurrent == 500) {
						atLoc[3] = false;
					}
					customerList.remove(0);
					repaint();
				}
			}
		});
		removePeople.setInitialDelay(25000);

		setFocusable(true);
	}

	/**
	 *  (Re-)set the game to its initial state.
	 * @throws IOException 
	 */
	public void reset() throws IOException {
		conveyor = new Conveyor(COURT_WIDTH, COURT_HEIGHT);
		background = new Background (COURT_WIDTH, COURT_HEIGHT);
		repaint();
		
		//initial pop-up displaying instructions
		JOptionPane.showMessageDialog(Game.court, 
				"Today, you'll be a barista for the day!"
				+ "\n \n GOAL:"
		 		+ "\n College students low on sleep will be dropping in "
		 		+ "randomly with orders to the left of their head."
		 		+ "\n You have to FULFILL these orders!" 
		 		+ " \n \n HOW TO WIN: Your Boss wants $100!" 
		 		+ "\n \n HOW TO CREATE:"
		 		+ "\n The RECIPE BOOK contains all the ingredients for "
		 		+ "each drink. Use this to make the drink correctly!"
		 		+ "\n Create their orders by clicking on the ingredient button."
		 		+ " The ingredients you select will appear in the "
		 		+ "creation area. \n When finished "
		 		+ "click 'CREATE'." + "\n They will be put on the "
		 		+ "conveyor belt (if they match the recipe book)."
		 		+ "\n If they match the customer's order, "
		 		+ "they'll disappear and you earn $10!" 
		 		+ "\n \n WATCH OUT!" 
		 		+ "\n Ingredients will run out! The number beside the"
		 		+ " ingredient button shows how much is left." 
		 		+ "\n If it reaches 0, right click the button and follow the "
		 		+ "instructions on the dialog. "
		 		+ "\n ONLY if you follow it CORRECTLY will your supplies refill!"
		 		+ "\n \n Drinks will go to the FIRST customer it encounters on"
		 		+ " the conveyor belt with that order, so be wise with your "
		 		+ "decisions!"
		 		+ "\n \n ALSO BEWARE CUSTOMER WAITING TIME! After a certain "
		 		+ "amount of time your customers will be unhappy and leave!"
		 		+ "\n \n FEATURES:" 
	            + "\n - Conveyor animation and item-customer location check"
		 		+ "\n - Tabbed recipe book: easy to add new recipes", 
								"Think you have what it takes?", 
								JOptionPane.INFORMATION_MESSAGE);
		//game starts only after pop-up close
		playing = true;
		
		//start timers controlling customers
		people.start(); 
		removePeople.start(); 
		requestFocusInWindow();
	}
	
	/**
	 * Restarts the game. Sets to initial state.
	 * @throws IOException 
	 */
	public void restart() throws IOException {
		people.stop();
		removePeople.stop();
		customerList.clear();
		onConveyor.clear();
		counter = 0;
		score = 0;
		scoreCnt.setText("$" + score);
		reset();
	}
	
	/**
	 * Ends the game
	 */
	public void quit() {
		System.exit(0);
	}
	

	/**
	 * Activated by the overall timer
	 */
	void tick() {
		if (playing) {
			
			// checks for order satisfaction and sets order bounds
			if (!onConveyor.isEmpty()) {
				for (int i = 0; i < onConveyor.size(); i++) {
					onConveyor.get(i).move();
					
					//conveyor object bounds
					if (onConveyor.get(i).pos_x > 1000) {
						onConveyor.remove(i); 
					}
					
					//order completion
					for (int j = 0; j < customerList.size(); j++) {
					  if (!onConveyor.isEmpty()) {
						int drinkLoc = onConveyor.get(i).pos_x;
						int upperBnd = customerList.get(j).pos_x + 
								customerList.get(j).sizeX;
						int lowerBnd = customerList.get(j).pos_x + 50;
						String conveyorImg = onConveyor.get(i).img_file;
						String orderImg = customerList.get(j).orderImg;
						int xLocCurrent = customerList.get(j).pos_x;
						
						if (conveyorImg == orderImg && 
								(drinkLoc > lowerBnd && drinkLoc < upperBnd)) {
							if (xLocCurrent == 250) {
								atLoc[0] = false;
							} else if (xLocCurrent == 750) {
								atLoc[1] = false;
							} else if (xLocCurrent == 0) {
								atLoc[2] = false;
							} else if (xLocCurrent == 500) {
								atLoc[3] = false;
							}
	
							onConveyor.remove(i);
							customerList.remove(j);
							repaint();
							score += 10;
							
							if (score >= 100) {
								scoreCnt.setText("$" + score);
								people.stop();
								customerList.clear();
								onConveyor.clear();
								repaint();
								JOptionPane.showMessageDialog(Game.court, 
										"YOU WIN! You are now a barista pro!");
								playing = false;
								break;
							}
							scoreCnt.setText("$" + score);
							break;
						}
					  }
					}
				}
			}
			repaint();
		}
	}

	
	@Override
	public void paintComponent(Graphics g) {		
		super.paintComponent(g);
		background.draw(g);
		conveyor.draw(g);

		//draw customers
		if (!customerList.isEmpty()) {
			for (int i = 0; i < customerList.size(); i++) {
				customerList.get(i).draw(g);
			}
		}
		
		//draw drinks
		if (!onConveyor.isEmpty()) {
			for (int j = 0; j < onConveyor.size(); j++) {
				onConveyor.get(j).draw(g);
			}
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(COURT_WIDTH, COURT_HEIGHT);
	}
}
