/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
	public void run() {

		// Top-level frame in which game components live
		final JFrame frame = new JFrame("Exam Time");
		frame.setLocation(200, 200);


		// Main playing area
		final GameCourt court = new GameCourt();
		frame.add(court, BorderLayout.CENTER);
		
		
		// TOP PANEL
		final JPanel control_panel = new JPanel();
		BorderLayout top = new BorderLayout();
		top.setHgap(50);
		control_panel.setLayout(top);
		frame.add(control_panel, BorderLayout.NORTH);
		
		//title
		final JLabel title = new JLabel("Exam Crunch");
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
		final Graphics g = frame.getGraphics();
		final JPanel bottom = new JPanel(new BorderLayout());
		bottom.setPreferredSize(new Dimension(1000, 300));
		frame.add(bottom, BorderLayout.SOUTH);
		
		
		// INGREDIENT BUTTONS
		final JPanel ingredients = new JPanel(new GridLayout(3, 4, 10, 10));
		ingredients.setPreferredSize(new Dimension(500, 300));
		bottom.add(ingredients, BorderLayout.WEST);
		
		
		//NORMAL MUG
		ImageIcon normImg = new ImageIcon("coffeecup.jpg");
		final JButton normCupButton = new JButton("10", normImg);
		normCupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				court.ingredient(g);
			}
		});
		//normCupButton.setPreferredSize(new Dimension(10,10));
		ingredients.add(normCupButton);
		
		//TALL MUG
		ImageIcon toGoCupImg = new ImageIcon("coffeecup.jpg");
		final JButton toGoCupButton = new JButton("10", toGoCupImg);
		toGoCupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				court.ingredient(g);
			}
		});
		//toGoCupButton.setPreferredSize(new Dimension(10,10));
		ingredients.add(toGoCupButton);
		
		//SHORT MUG
		ImageIcon shortCupImg = new ImageIcon("coffeecup.jpg");
		final JButton shortCupButton = new JButton("10", shortCupImg);
		shortCupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				court.ingredient(g);
			}
		});
		ingredients.add(shortCupButton);
		
		//COFFEE BEANS 
		ImageIcon coffeeBeanImg = new ImageIcon("coffeecup.jpg");
		final JButton coffeeBeanButton = new JButton("10", coffeeBeanImg);
		coffeeBeanButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				court.ingredient(g);
			}
		});
		ingredients.add(coffeeBeanButton);
		
		
		//CHOCOLATE 
		ImageIcon chocImg = new ImageIcon("coffeecup.jpg");
		final JButton chocButton = new JButton("10", chocImg);
		chocButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				court.ingredient(g);
			}
		});
		ingredients.add(chocButton);
		
		
		//ICE
		ImageIcon iceImg = new ImageIcon("coffeecup.jpg");
		final JButton iceButton = new JButton("10", iceImg);
		iceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				court.ingredient(g);
			}
		});
		ingredients.add(iceButton);
		
		
		//MILK
		ImageIcon milkImg = new ImageIcon("coffeecup.jpg");
		final JButton milkButton = new JButton("10", milkImg);
		milkButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				court.ingredient(g);
			}
		});
		ingredients.add(milkButton);
		
		//SYRUP
		ImageIcon syrupImg = new ImageIcon("coffeecup.jpg");
		final JButton syrupButton = new JButton("10", syrupImg);
		syrupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				court.ingredient(g);
			}
		});
		ingredients.add(syrupButton);

		
		//CREATION AREA
		final JPanel creationArea = new JPanel(new GridLayout(2, 3, 10, 10));
		creationArea.setPreferredSize(new Dimension(300, 300));
		creationArea.setBorder(BorderFactory.createLineBorder(Color.black));
		bottom.add(creationArea, BorderLayout.CENTER);
		
		//RECIPE AND PHONE
		final JPanel interactionArea = new JPanel(new GridLayout(2, 1, 0, 30));
		interactionArea.setPreferredSize(new Dimension(200, 300));
		interactionArea.setBorder(BorderFactory.createLineBorder(Color.black));
		bottom.add(creationArea, BorderLayout.EAST);
	    
		
		
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
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Game());
	}
}
