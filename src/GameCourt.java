/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.ArrayList;
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

	public GameCourt() {
		// creates border around the court area, JComponent method
		setBorder(BorderFactory.createLineBorder(Color.BLACK));

		// The timer is an object which triggers an action periodically
		// with the given INTERVAL. One registers an ActionListener with
		// this timer, whose actionPerformed() method will be called
		// each time the timer triggers. We define a helper method
		// called tick() that actually does everything that should
		// be done in a single timestep.
		Timer timer = new Timer(INTERVAL, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tick();
			}
		});
		timer.start(); // MAKE SURE TO START THE TIMER!
		
		Timer people = new Timer(4, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//possible locations 
				int[] loc = {0, 200, 400, 600};
				int xLoc = (int) (Math.random() * 4);								
				Customers current = new Customers(COURT_WIDTH, COURT_HEIGHT, xLoc, 0, "student.jpg");
			}
		});
		timer.start(); // MAKE SURE TO START THE TIMER!
		
		

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
		if (playing) {

			// update the display
			if (!onConveyor.isEmpty()) {
				for (int i = 0; i < onConveyor.size(); i++) {
					onConveyor.get(i).move();
					boolean wall = onConveyor.get(i).hitWall();
					if (wall) {
						onConveyor.remove(i); 
					}
				}
			}
			
			
			repaint();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		conveyor.draw(g);
		if (!customerList.isEmpty()) {
			for (int i = 0; i < customerList.size(); i++) {
				customerList.get(i).draw(g);
			}
		}
		
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
