#Coffee Time 

##OVERVIEW:
The game is based on "Coffee Bar" which is a time management game that requires
the player to make drinks that customers order within a certain time so that
they can earn money. The drinks they can make and that customers can order is 
determined by what is in the recipe book. 

##FEATURES:
1) Conveyor belt animation
- Submitted recipes are turned into drinks (if valid) that move from one 
end of the conveyor belt to the other
- The drink on the conveyor is continually checked for its x-location to
see if it matches the location of a customer, and if the object itself 
matches the one that the customer ordered
- If a match is detected, both the customer and object get removed 

2) Recipe book implementation
- Used a JTabbedPanel to display a popup of recipe tabs 
- Loops through the data structures containing the recipes and displays them
each in a new tab, with the product on one side and the ingredients needed
to make it on the other
