package memoryclimbgame;

import memoryclimbgame.route.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RouteStrategyTest {
    @Test
    public void easyRouteHasCorrectBoardAndGeneratesRoute() {
        IRouteStrategy easyStrategy = RouteFactory.createStrategy(Difficulty.EASY);

        Board board = easyStrategy.getBoard();
        Route route = easyStrategy.generateRoute();

        assertNotNull(board);
        assertEquals(6, board.getRowLength());
        assertEquals(6, board.getColLength());

        assertNotNull(route);
        assertEquals(Difficulty.EASY.routeLength, route.getHolds().size());
    }

    @Test
    public void mediumRouteHasCorrectBoardAndGeneratesRoute() {
        IRouteStrategy mediumStrategy = RouteFactory.createStrategy(Difficulty.MEDIUM);

        Board board = mediumStrategy.getBoard();
        Route route = mediumStrategy.generateRoute();

        assertNotNull(board);
        assertEquals(9, board.getRowLength());
        assertEquals(9, board.getColLength());

        assertNotNull(route);
        assertEquals(Difficulty.MEDIUM.routeLength, route.getHolds().size());
    }

    @Test
    public void hardRouteHasCorrectBoardAndGeneratesRoute() {
        IRouteStrategy hardStrategy = RouteFactory.createStrategy(Difficulty.HARD);

        Board board = hardStrategy.getBoard();
        Route route = hardStrategy.generateRoute();

        assertNotNull(board);
        assertEquals(12, board.getRowLength());
        assertEquals(12, board.getColLength());

        assertNotNull(route);
        assertEquals(Difficulty.HARD.routeLength, route.getHolds().size());
    }
}
