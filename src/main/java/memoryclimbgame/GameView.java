package memoryclimbgame;

public class GameView implements IObserver {
    private final MemoryClimbGame game;
    private GameState lastState;
    private int lastScore;

    public GameView(MemoryClimbGame game) {
        this.game = game;
        game.addObserver(this);
    }

    @Override
    public void update() {
        lastState = game.getState();
        lastScore = game.getScore();
    }

    public GameState getLastState() {
        return lastState;
    }

    public int getLastScore() {
        return lastScore;
    }
}
