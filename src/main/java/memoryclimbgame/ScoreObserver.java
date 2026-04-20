package memoryclimbgame;

public class ScoreObserver implements IObserver {
    private final MemoryClimbGame game;
    private int finalScore;
    private boolean gameOver;

    public ScoreObserver(MemoryClimbGame game) {
        this.game = game;
        this.finalScore = 0;
        this.gameOver = false;
        game.addObserver(this);
    }

    @Override
    public void update() {
        if (game.getState() == GameState.GAME_OVER) {
            finalScore = game.getScore();
            gameOver = true;
        }
    }

    public int getFinalScore() {
        return finalScore;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public String getResultMessage() {
        return "Game Over! You correctly memorized " + finalScore + " route"
                + (finalScore != 1 ? "s" : "") + ".";
    }
}
