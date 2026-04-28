package memoryclimbgame;

import memoryclimbgame.route.IRouteStrategy;
import memoryclimbgame.route.Route;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    static class FakeRouteStrategy implements IRouteStrategy {
        private final Board board;
        private final Route route;

        FakeRouteStrategy(Board board, Route route) {
            this.board = board;
            this.route = route;
        }

        @Override
        public Route generateRoute() {
            return route;
        }

        @Override
        public Board getBoard() {
            return board;
        }
    }

    static class FakeObserver implements IObserver {
        int updateCount = 0;

        @Override
        public void update() {
            updateCount++;
        }
    }

    @Test
    void newGameStartsInWaitingStateWithScoreZero() {
        MemoryClimbGame game = new MemoryClimbGame();

        assertEquals(GameState.WAITING, game.getState());
        assertEquals(0, game.getScore());
        assertNull(game.getRoute());
        assertNull(game.getBoard());
    }

    @Test
    void startGameGeneratesRouteSetsBoardAndShowsRoute() {
        Board board = new Board(5, 5);
        Route route = new Route();

        MemoryClimbGame game = new MemoryClimbGame();
        game.startGame(new FakeRouteStrategy(board, route));

        assertEquals(GameState.SHOWING_ROUTE, game.getState());
        assertEquals(0, game.getScore());
        assertSame(route, game.getRoute());
        assertSame(board, game.getBoard());
    }

    @Test
    void hideRouteChangesStateToGuessing() {
        Board board = new Board(5, 5);
        Route route = new Route();

        MemoryClimbGame game = new MemoryClimbGame();
        game.startGame(new FakeRouteStrategy(board, route));

        game.hideRoute();

        assertEquals(GameState.GUESSING, game.getState());
    }

    @Test
    void correctGuessIncreasesScoreAndGeneratesNextRoute() {
        Board board = new Board(5, 5);
        Route route = new Route();

        MemoryClimbGame game = new MemoryClimbGame();
        game.startGame(new FakeRouteStrategy(board, route));

        game.submitGuess(route);

        assertEquals(1, game.getScore());
        assertEquals(GameState.SHOWING_ROUTE, game.getState());
    }

    @Test
    void observersAreNotifiedWhenGameStarts() {
        Board board = new Board(5, 5);
        Route route = new Route();

        MemoryClimbGame game = new MemoryClimbGame();
        FakeObserver observer = new FakeObserver();

        game.addObserver(observer);
        game.startGame(new FakeRouteStrategy(board, route));

        assertEquals(1, observer.updateCount);
    }

    @Test
    void observersAreNotifiedWhenRouteIsHidden() {
        Board board = new Board(5, 5);
        Route route = new Route();

        MemoryClimbGame game = new MemoryClimbGame();
        FakeObserver observer = new FakeObserver();

        game.addObserver(observer);
        game.startGame(new FakeRouteStrategy(board, route));
        game.hideRoute();

        assertEquals(2, observer.updateCount);
    }

    @Test
    void removedObserverIsNotNotified() {
        Board board = new Board(5, 5);
        Route route = new Route();

        MemoryClimbGame game = new MemoryClimbGame();
        FakeObserver observer = new FakeObserver();

        game.addObserver(observer);
        game.removeObserver(observer);

        game.startGame(new FakeRouteStrategy(board, route));

        assertEquals(0, observer.updateCount);
    }
}