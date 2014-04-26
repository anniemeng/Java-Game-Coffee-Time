/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.ArrayList;
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
	private ArrayList<ConveyorItem> onConveyor = new ArrayList<ConveyorItem>();
	private ArrayList<Customers> customerList = new ArrayList<Customers>();
	private TreeSet<String> customerImg = new TreeSet<String>();
	private int counter = 0;
	private JLabel scoreCnt = new JLabel();
	private int score = 0;
	
	public void addToConveyor(ConveyorItem current) {
		onConveyor.add(current);
	}
	
	public void addCustomers(String name) {
		customerImg.add(name);
	}
	
	// the state of the game logic
	public boolean playing = false; // whether the game is running

	// Game constants
	public static final int COURT_WIDTH = 1000;
	public static final int COURT_HEIGHT = 1000;
	public static final int SQUARE_VELOCITY = 4;
	// Update interval for timer, in milliseconds
	public static final int INTERVAL = 35;
	public static final int customerInterval = 5000;
	public static final int timeOut = 10000;

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
		Timer people = new Timer(customerInterval, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//possible locations 
				if (counter > 3) {
					counter = 0;
				}
				
				int[] loc = {250, 750, 0, 500};
				int xLoc = loc[counter];
				System.out.println(xLoc);
				
				int recipeNum = (int) (Math.random() * 5);
				//possible recipes
				String[] recipeImgs = Game.images;
				String[] names = {"coffee", "espresso", "mocha", "latte", "tea"};
			
				System.out.println(recipeImgs[recipeNum]);
				Customers current = new Customers(COURT_WIDTH, COURT_HEIGHT, xLoc, names[recipeNum], recipeImgs[recipeNum]);
				customerList.add(current);
				repaint();
				counter += 1;
			}
		});
		people.start(); // MAKE SURE TO START THE TIMER!
		
		//time out for customers
		Timer removePeople = new Timer(timeOut, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!customerList.isEmpty()) {
					customerList.remove(0);
					repaint();
				}
			}
		});
		removePeople.setInitialDelay(10000);
		removePeople.start(); // MAKE SURE TO START THE TIMER!
		
		// Enable keyboard focus on the court area.
		// When this component has the keyboard focus, key
		// events will be handled by its key listener.
		setFocusable(true);

	}

	/**
	 * (Re-)set the game to its initial state.
	 */
	public void reset() {
		playing = true;
		conveyor = new Conveyor(COURT_WIDTH, COURT_HEIGHT);
		// Make sure that this component has the keyboard focus
		requestFocusInWindow();
	}
	
	public void quit() {
		System.exit(0);
	}
	

	/**
	 * This method is called every time the timer defined in the constructor
	 * triggers.
	 */
	void tick() {
			// update the display
			if (!onConveyor.isEmpty()) {
				for (int i = 0; i < onConveyor.size(); i++) {
					onConveyor.get(i).move();
					boolean wall = onConveyor.get(i).hitWall();
					if (wall) {
						onConveyor.remove(i); 
					}
					
					//check if satisfy customer
					for (int j = 0; j < customerList.size(); j++) {
						if (!onConveyor.isEmpty()) {
						int drinkLoc = onConveyor.get(i).pos_x;
						int upperBound = customerList.get(j).pos_x + customerList.get(j).sizeX;
						int lowerBound = customerList.get(j).pos_x + 50;
						
						
						if (onConveyor.get(i).name == customerList.get(j).order && (drinkLoc > lowerBound && drinkLoc < upperBound)) {
							onConveyor.remove(i);
							customerList.remove(j);
							repaint();
							score += 10;
							scoreCnt.setText("$" + score);
							break;
						}
					}
					}
				}
			}
			repaint();
		
	}

	
	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		conveyor.draw(g);
		
		//draw customers
		if (!customerList.isEmpty()) {
			for (int i = 0; i < customerList.size(); i++) {
				customerList.get(i).draw(g);
			}
		}
		
		//draw drinks
		if (!onConveyor.isEmpty()) {
			for (int i = 0; i < onConveyor.size(); i++) {
				onConveyor.get(i).draw(g);
			}
		}
		

	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(COURT_WIDTH, COURT_HEIGHT);
	}
}
