# Climbing Route Memorization Game
Authors: Isaac Camacho, Cody Mattox
## Project Summary
For our project we will create a memory game geared towards rock climbers. The game will be memory based - users will be prompted with a screen that shows the route they need to complete for a short period of time, the user will then have to complete the route based on memory marking each hold they used to complete the route, if the climber correctly replicates the route then they will have won the game.

## Design Patterns
### Builder Pattern
Builder pattern will enable the creation of a multitude of different routes(RouteBuilder), each landing within preterminded class of difficulty based on conventional climbing grading practices

### Strategy Pattern
Using the strategy pattern we can create routes of different grades so that instead of hardcoding factory methods, 
each difficulty level becomes it's own strategy class that knows how to generate each route

### Observer Pattern
Observer Pattern will be used for a UI element where it will notify the visual display when the game logic updates,
it will also be used as a guessObserver that will update when a new guess is passed in and confirm against the actual route

### Factory Pattern
Factory pattern will be implemented to create boards of different sizes, making creation simple and easy to understand





