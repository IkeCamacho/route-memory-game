package memoryclimbgame.route;

import memoryclimbgame.Board;
import memoryclimbgame.Difficulty;

public class HardRoute implements IRouteStrategy {
    private static final int HARD_BOARD_ROW_SIZE = 12;
    private static final int HARD_BOARD_COL_SIZE = 12;

    private final Board board = Board.createPopulated(HARD_BOARD_ROW_SIZE, HARD_BOARD_COL_SIZE);

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public Route generateRoute() {
        return RouteFactory.createRoute(board, Difficulty.HARD);
    }
}