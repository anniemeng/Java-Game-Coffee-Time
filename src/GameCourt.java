/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * GameCourt
 * 
 * This class holds the primary game logic for how different objects interact
 * with one another. Take time to understand how the timer interacts with the
 * different methods and how it repaints the GUI on every tick().
 * 
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {

	private Conveyor conveyor;
	private Background background;
	private ArrayList<ConveyorItem> onConveyor = new ArrayList<ConveyorItem>();
	private ArrayList<Customers> customerList = new ArrayList<Customers>();
	private int counter = 0;
	private JLabel scoreCnt = new JLabel();
	private int score = 0;
	private ConveyorItem current;
	
	public void addToConveyor(String file, String nameDrink) {
		current = new ConveyorItem(COURT_WIDTH, COURT_HEIGHT, file, nameDrink);
		onConveyor.add(current);
		repaint();
	}

	
	// the state of the game logic
	public boolean playing = false; // whether the game is running

	// Game constants
	public static final int COURT_WIDTH = 1000;
	public static final int COURT_HEIGHT = 400;
	
	// Update interval for timer, in milliseconds
	public static final int INTERVAL = 35;
	public static final int customerInterval = 5000;
	public static final int timeOut = 25000;
	private Timer people;
	private Timer removePeople;

	public GameCourt(JLabel scoreCnt) {
		this.scoreCnt = scoreCnt;
		
		// creates border around the court area, JComponent method
		setBorder(BorderFactory.createLineBorder(Color.BLACK));

		// The timer is an object which triggers an action periodically
		// with the given INTERVAL. One registers an ActionListener with
		// this timer, whose actionPerformed() method will be called
		// each time the timer triggers. We define a helper method
		// called tick() that actually does everything that should
		// be done in a single timestep.
		
		//overall timer
		Timer timer = new Timer(INTERVAL, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tick();
			}
		});
		timer.start(); // MAKE SURE TO START THE TIMER!
		
		//customer appearances
		people = new Timer(customerInterval, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(counter);
				if (customerList.size() < 3 && customerList.size() >= 0) {
					if (counter > 3) {
						counter = 0;
					}
				
				//possible locations
				int[] loc = {250, 750, 0, 500};
				int xLoc = loc[counter];
				
				//change possible recipes
				int recipeNum = (int) (Math.random() * 5);
				String [] names = Game.output.keySet().toArray(new String[0]);
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
		});
		people.setInitialDelay(10000);
		//people.start(); // MAKE SURE TO START THE TIMER!
		
		
		//time out for customers
		removePeople = new Timer(timeOut, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!customerList.isEmpty()) {
					customerList.remove(0);
					repaint();
				}
			}
		});
		removePeople.setInitialDelay(15000);
		//removePeople.start(); // MAKE SURE TO START THE TIMER!
		
		
		// Enable keyboard focus on the court area.
		// When this component has the keyboard focus, key
		// events will be handled by its key listener.
		setFocusable(true);
	}

	/**
	 * (Re-)set the game to its initial state.
	 * @throws IOException 
	 */
	
	public void reset() throws IOException {
		conveyor = new Conveyor(COURT_WIDTH, COURT_HEIGHT);
		background = new Background (COURT_WIDTH, COURT_HEIGHT);
		repaint();
		
		JOptionPane start = new JOptionPane();
		start.showMessageDialog(Game.court, 
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
		 		+ " \n \n HOW TO WIN: Your Boss wants $100!" 
		 		+ "\n \n FEATURES:" 
	            + "\n - Conveyor animation and item-customer location check"
		 		+ "\n - Tabbed recipe book: easy to add new recipes", 
								"Think you have what it takes?", 
								JOptionPane.INFORMATION_MESSAGE);
		
		playing = true;
		people.start(); 
		removePeople.start(); 
		// Make sure that this component has the keyboard focus
		requestFocusInWindow();
	}
	
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
	
	
	public void quit() {
		System.exit(0);
	}
	

	/**
	 * This method is called every time the timer defined in the constructor
	 * triggers.
	 */
	void tick() {
		if (playing) {
			// update the display
			if (!onConveyor.isEmpty()) {
				for (int i = 0; i < onConveyor.size(); i++) {
					onConveyor.get(i).move();
					//boolean wall = onConveyor.get(i).pos_x < 1000 ;
					if (onConveyor.get(i).pos_x > 1000) {
						onConveyor.remove(i); 
					}
					
					//check if satisfy customer
					for (int j = 0; j < customerList.size(); j++) {
						if (!onConveyor.isEmpty()) {
						int drinkLoc = onConveyor.get(i).pos_x;
						int upperBnd = customerList.get(j).pos_x + 
								customerList.get(j).sizeX;
						int lowerBnd = customerList.get(j).pos_x + 50;
						String conveyorImg = onConveyor.get(i).img_file;
						String orderImg = customerList.get(j).orderImg;
						if (conveyorImg == orderImg && 
								(drinkLoc > lowerBnd && drinkLoc < upperBnd)) {
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
				System.out.println(onConveyor.get(j).pos_x);
			}
		}
		

	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(COURT_WIDTH, COURT_HEIGHT);
	}
}
