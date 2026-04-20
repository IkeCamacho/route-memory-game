package memoryclimbgame.route;

import memoryclimbgame.Board;
import memoryclimbgame.Difficulty;

public class EasyRoute implements IRouteStrategy {
    private static final int EASY_BOARD_ROW_SIZE = 6;
    private static final int EASY_BOARD_COL_SIZE = 6;

    private final Board board = Board.createPopulated(EASY_BOARD_ROW_SIZE, EASY_BOARD_COL_SIZE);

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public Route generateRoute() {
        return RouteFactory.createRoute(board, Difficulty.EASY);
    }
}
