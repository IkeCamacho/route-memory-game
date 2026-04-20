package memoryclimbgame;

import memoryclimbgame.route.Route;
import memoryclimbgame.route.RouteFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RouteFactoryTest {

    @Test
    void createEasyRouteReturnsSixHolds() {
        Board board = new Board(6, 6);
        Route route = RouteFactory.createRoute(board, Difficulty.EASY);
        assertEquals(6, route.getHolds().size());
    }

    @Test
    void createEasyRouteUsesValidRowsAndColumns() {
        Board board = new Board(6, 6);
        Route route = RouteFactory.createRoute(board, Difficulty.EASY);
        for (Hold hold : route.getHolds()) {
            assertTrue(hold.getRow() >= 0);
            assertTrue(hold.getRow() < board.getRowLength());
            assertTrue(hold.getCol() >= 0);
            assertTrue(hold.getCol() < board.getColLength());
        }
    }

    @Test
    void createEasyRouteMovesUpOneRowAtATime() {
        Board board = new Board(6, 6);
        Route route = RouteFactory.createRoute(board, Difficulty.EASY);
        List<Hold> holds = route.getHolds();
        for (int i = 0; i < holds.size() - 1; i++) {
            int currentRow = holds.get(i).getRow();
            int nextRow = holds.get(i + 1).getRow();

            assertEquals(currentRow - 1, nextRow);
        }
    }

    @Test
    void createEasyRouteColumnShiftIsAtMostOne() {
        Board board = new Board(6, 6);
        Route route = RouteFactory.createRoute(board, Difficulty.EASY);
        List<Hold> holds = route.getHolds();
        for (int i = 0; i < holds.size() - 1; i++) {
            int currentCol = holds.get(i).getCol();
            int nextCol = holds.get(i + 1).getCol();

            assertTrue(Math.abs(currentCol - nextCol) <= 1);
        }
    }
}