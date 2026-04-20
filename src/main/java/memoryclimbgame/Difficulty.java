package memoryclimbgame;

public enum Difficulty {
    EASY(6, 1, 1),
    MEDIUM(6, 2, 1),
    HARD(6, 3, 2);

    public final int routeLength;
    public final int maxColShift;
    public final int rowSpacing;

    Difficulty(int routeLength, int maxColShift, int rowSpacing) {
        this.routeLength = routeLength;
        this.maxColShift = maxColShift;
        this.rowSpacing = rowSpacing;
    }
}
