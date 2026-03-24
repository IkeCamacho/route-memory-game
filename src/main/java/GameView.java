public class GameView implements IObserver {
    private final MemoryClimbGame game;

    public GameView(MemoryClimbGame game) {
        this.game = game;
    }

    @Override
    public void update() {
        // pull data from game
        // redraw UI
    }
}
