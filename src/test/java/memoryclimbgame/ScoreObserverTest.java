package memoryclimbgame;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreObserverTest {

    @Test
    public void scoreObserverStartsWithNoFinalScoreAndGameNotOver() {
        MemoryClimbGame game = new MemoryClimbGame();
        ScoreObserver observer = new ScoreObserver(game);

        assertEquals(0, observer.getFinalScore());
        assertFalse(observer.isGameOver());
    }

    @Test
    public void scoreObserverUpdatesWhenGameEnds() {
        MemoryClimbGame game = new MemoryClimbGame();
        ScoreObserver observer = new ScoreObserver(game);

        game.endGame();

        assertTrue(observer.isGameOver());
        assertEquals(game.getScore(), observer.getFinalScore());
    }

    @Test
    public void resultMessageUsesPluralRoutesWhenScoreIsNotOne() {
        MemoryClimbGame game = new MemoryClimbGame();
        ScoreObserver observer = new ScoreObserver(game);

        game.endGame();

        assertEquals("Game Over! You correctly memorized 0 routes.",
                observer.getResultMessage()
        );
    }
}
