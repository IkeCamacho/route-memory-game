package memoryclimbgame;

import java.util.List;

public class MemoryClimbGame implements IObservable{
    private Board board;
    private Route route;
    private int score;
    private RouteGenerationStrategy strategy;
    private List<IObserver> observers;
    private GameState state;

    public void submitGuess(Route guess) {
        if (route.equals(guess)) {
            score++;
            state = GameState.CORRECT;
            notifyObservers();
            generateNextRoute();
        } else {
            state = GameState.GAME_OVER;
            notifyObservers();
        }
    }

    public void startGame(){
        //get route type and board
    }

    public void generateNextRoute() {
        //get new route for user's next round of game
    }

    public void hideRoute() {
        state = GameState.GUESSING;
        notifyObservers();
    }

    @Override
    public void addObserver(IObserver o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(IObserver o) {
        observers.remove(o);
    }

    public void notifyObservers(){
        for (IObserver o : observers){
            o.update();
        }
    }

    public GameState getState() {
        return state;
    }

    public int getScore() {
        return score;
    }

    public Route getCurrentRoute() {
        return route;
    }

    public Board getBoard() {
        return board;
    }
}
