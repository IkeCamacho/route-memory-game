# Climbing Route Memorization Game
Authors: Isaac Camacho, Cody Mattox
## Project Summary
For our project we will create a memory game geared towards rock climbers. The game will be memory based - users will be prompted with a screen that shows the route they need to complete for a short period of time, the user will then have to complete the route based on memory marking each hold they used to complete the route, if the climber correctly replicates the route then they will have won the game.

## Design Patterns
### Builder Pattern
We will use builder pattern to enable the creation of a multitude of different routes(RouteBuilder), each landing within preterminded class of difficulty based on conventional climbing grading practices

### Factory Pattern
To create multiple routes of differing difficulties to offer our users, we will create factory that utilizes the RouteBuilder in order to mass produce different route puzzles

### Observer Pattern
In order to confirm that the user is correct when recollecting the route from the initial screen flash, we will have an observer that looks at the route and is updated each time the climber selects another hold - this will allow the observer to act accordingly when the next step is chosen in the route





