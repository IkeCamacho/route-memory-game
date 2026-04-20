package memoryclimbgame.strategy;

import memoryclimbgame.Board;
import memoryclimbgame.Hold;
import memoryclimbgame.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RouteFactory {
    public static Route createEasyRoute(Board board) {
        List<Hold> routeHolds = new ArrayList<>();
        Random random = new Random();

        int rows = board.getRowLength();
        int cols = board.getColLength();

        int currentCol = random.nextInt(cols);

        for (int row = rows - 1; row >= 0; row--) {
            // one step to the left or one step to the right
            int minCol = Math.max(0, currentCol - 1);
            int maxCol = Math.min(cols - 1, currentCol + 1);

            int nextCol = random.nextInt(maxCol - minCol + 1) + minCol;
            Hold nextHold = board.getHold(row, nextCol);

            routeHolds.add(nextHold);
            currentCol = nextCol;
        }

        return new Route(routeHolds);
        }
    }
}
