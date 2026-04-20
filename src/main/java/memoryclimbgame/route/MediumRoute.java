package memoryclimbgame.route;

import memoryclimbgame.Board;
import memoryclimbgame.Difficulty;

public class MediumRoute implements IRouteStrategy {
    private static final int MEDIUM_BOARD_ROW_SIZE = 9;
    private static final int MEDIUM_BOARD_COL_SIZE = 9;

    private final Board board = Board.createPopulated(MEDIUM_BOARD_ROW_SIZE, MEDIUM_BOARD_COL_SIZE);

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public Route generateRoute() {
        return RouteFactory.createRoute(board, Difficulty.MEDIUM);
    }
}
