package memoryclimbgame.route;

import memoryclimbgame.Board;

public interface IRouteStrategy {
    Route generateRoute();
    public Board getBoard();
}
