package memoryclimbgame;


import memoryclimbgame.route.IRouteStrategy;
import memoryclimbgame.route.Route;


import java.util.ArrayList;
import java.util.List;


public class MemoryClimbGame implements IObservable{
    private Board board;
    private Route route;
    private IRouteStrategy strategy;
    private List<IObserver> observers;
    private GameState gameState;
    private int score;


    public MemoryClimbGame() {
        this.gameState = GameState.WAITING;
        this.score = 0;
        this.observers = new ArrayList<>();
    }


    public void startGame(IRouteStrategy strategy) {
        this.strategy = strategy;
        this.score = 0;
        generateNextRoute();
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


    public void submitGuess(Route guess) {
        if (route.equals(guess)) {
            score++;
            gameState = GameState.CORRECT;
            notifyObservers();
            generateNextRoute();
        } else {
            gameState = GameState.GAME_OVER;
            notifyObservers();
        }
    }


    private void generateNextRoute() {
        route = strategy.generateRoute();
        board = strategy.getBoard();
        gameState = GameState.SHOWING_ROUTE;
        notifyObservers();
    }


    public void hideRoute() {
        gameState = GameState.GUESSING;
        notifyObservers();
    }


    public Route getRoute() {
        return route;
    }


    public Board getBoard() {return board;}


    public GameState getState() {
        return gameState;
    }


    public int getScore() {
        return score;
    }
}
