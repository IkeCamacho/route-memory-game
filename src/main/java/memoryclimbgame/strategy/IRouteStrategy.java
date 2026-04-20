package memoryclimbgame.strategy;

import memoryclimbgame.Board;
import memoryclimbgame.Route;

public interface IRouteStrategy {
    Route generateRoute();
    public Board getBoard();
}
