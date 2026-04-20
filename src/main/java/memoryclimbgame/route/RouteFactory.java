package memoryclimbgame.route;

import memoryclimbgame.Board;
import memoryclimbgame.Difficulty;
import memoryclimbgame.Hold;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RouteFactory {

    private static final Random random = new Random();

    public static Route createRoute(Board board, Difficulty difficulty) {
        return createRoute(
                board,
                difficulty.routeLength,
                difficulty.maxColShift,
                difficulty.rowSpacing
        );
    }

    private static Route createRoute(Board board, int routeLength, int maxColShift, int rowSpacing) {
        List<Hold> routeHolds = new ArrayList<>();

        int rows = board.getRowLength();
        int cols = board.getColLength();

        int currentRow = rows - 1;
        int currentCol = random.nextInt(cols);

        for (int i = 0; i < routeLength && currentRow >= 0; i++) {

            // limit sideways movement
            int minCol = Math.max(0, currentCol - maxColShift);
            int maxCol = Math.min(cols - 1, currentCol + maxColShift);

            // pick next column within range
            int nextCol = random.nextInt(maxCol - minCol + 1) + minCol;

            // get hold at (row, col)
            Hold nextHold = board.getHold(currentRow, nextCol);

            // add to route
            routeHolds.add(nextHold);

            // update position for next step
            currentCol = nextCol;
            currentRow -= rowSpacing;
        }

        return new Route(routeHolds);
    }

    public static IRouteStrategy createStrategy(Difficulty difficulty) {
        return switch (difficulty) {
            case EASY -> new EasyRoute();
            case MEDIUM -> new MediumRoute();
            case HARD -> new HardRoute();
        };
    }
}