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
		frame.add(control_panel, "North");
		
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

		
		// INGREDIENT BUTTONS
		final Graphics g = frame.getGraphics();
		final JPanel ingredients = new JPanel(new GridLayout(3,4));
		frame.add(ingredients, BorderLayout.SOUTH);
		
		//NORMAL MUG
		ImageIcon normImg = new ImageIcon("normalcup.png");
		final JButton normCupButton = new JButton("10", normImg);
		normCupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				court.cupStd(g);
			}
		});
		ingredients.add(normCupButton);
		
		//TALL MUG
		ImageIcon toGoCupImg = new ImageIcon("normalcup.png");
		final JButton toGoCupButton = new JButton("10", toGoCupImg);
		toGoCupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				court.cupStd(g);
			}
		});
		ingredients.add(toGoCupButton);
		
		//SHORT MUG
		ImageIcon shortCupImg = new ImageIcon("normalcup.png");
		final JButton shortCupButton = new JButton("10", shortCupImg);
		shortCupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				court.cupStd(g);
			}
		});
		ingredients.add(shortCupButton);
		
		//COFFEE BEANS 
		ImageIcon coffeeBeanImg = new ImageIcon("coffeebean.jpg");
		final JButton coffeeBeanButton = new JButton("10", coffeeBeanImg);
		coffeeBeanButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				court.cupStd(g);
			}
		});
		ingredients.add(coffeeBeanButton);

		
		
		
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
