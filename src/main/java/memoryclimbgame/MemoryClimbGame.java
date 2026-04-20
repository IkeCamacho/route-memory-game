package memoryclimbgame;

import memoryclimbgame.route.IRouteStrategy;
import memoryclimbgame.route.Route;

import java.util.List;

public class MemoryClimbGame implements IObservable{
    private Board board;
    private Route route;
    private IRouteStrategy strategy;
    private List<IObserver> observers;
    private GameState gameState;
    private IRouteStrategy routeStrategy;
    private Route currentRoute;
    private int score;

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

    public Route getCurrentRoute() {
        return currentRoute;
    }

    public GameState getState() {
        return gameState;
    }

    public int getScore() {
        return score;
    }
}
