STUDY BREAK GAME: 

OVERVIEW:
The game is based on "Coffee Bar" which is a time management game that requires
the player to make drinks that customers order within a certain time so that
they can earn money. The drinks they can make and that customers can order is 
determined by what is in the recipe book. 

CLASSES:

1) Game.java: 
	- Displays main JFrame and JPanels/JButtons. 
	- Controls ingredient selection and displays them. 
	- Controls the recipe book and initializes the available recipes
	- Input orders are sent to GameCourt.java

2) GameCourt.java: 
	- Displays the mid-section of the game by controlling customer appearance, 
	removal and order
	- Takes object submitted from Game.java and displays them on the conveyor
	- Controls scoring by checking if customer orders are satisfied  
	
3) GameObj.java:
	- Borrowed from initial file but only contains constructor and move method
	
4) Recipes.java:
	- Constructs a new recipe book that stores the ingredients and 
	associates them with the name of the recipe
	
5) Output.java:
	- Constructs treemap linking the name of the drink to the image of the 
	drink itself

6) Background.java:
	- Initializes and creates the images placed in the game court background
	
7) Conveyor.java:
	- Initializes and creates the conveyor belt in the game court
	
8) ConveyorItem.java:
	- Controls the appearance and motion of each valid submitted drink
	 by the user

9) Customers.java:
	- Controls the appearance of the customers and associates them with an 
	order
	
EXTRA FEATURES:
1) Conveyor belt animation
	- submitted recipes are turned into drinks (if valid) that move from one 
	end of the conveyor belt to the other
	- the drink on the conveyor is continually checked for its x-location to
	see if it matches the location of a customer, and if the object itself 
	matches the one that the customer ordered
	- if a match is detected, both the customer and object get removed 

2) Recipe book implementation
	- used a JTabbedPanel to display a popup of recipe tabs 
	- loops through the data structures containing the recipes and displays them
	each in a new tab, with the product on one side and the ingredients needed
	to make it on the other
	- can add additional recipes simply by putting them into the data structures
	in the main method of Game.java
 