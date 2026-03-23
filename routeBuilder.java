public class routeBuilder {
    private Board board;
    private String difficulty;
    private int routeLength;
    private Random random;

    public routeBuilder(Board board) {
        this.board = board;
        this.random = new Random();
    }

    public routeBuilder setDifficulty(String difficulty) {
        this.difficulty = difficulty;
        return this;
    }

    public routeBuilder setRouteLength(int length) {
        this.routeLength = length;
        return this;
    }

    public Route build() {
        List<Hold> sequence = generateRoute();
        return new Route(sequence, difficulty);
    }

    private List<Hold> generateRoute() {
        //Generate route from the bottom up
    }
}