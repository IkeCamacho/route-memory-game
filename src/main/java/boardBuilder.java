package src.main.java;


import java.util.Random;

public class boardBuilder {
    private int rows;
    private int cols;
    private Hold[][] grid;
    private Random random;

    public boardBuilder() {
        this.random = new Random();
    }

    public boardBuilder setRows(int rows) {
        this.rows = rows;
        return this;
    }

    public boardBuilder setCols(int cols) {
        this.cols = cols;
        return this;
    }

    public boardBuilder populateHolds() {
        this.grid = new Hold[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                HoldType type = randomHoldType();
                grid[r][c] = new Hold(r, c, type);
            }
        }
        return this;
    }

    private HoldType randomHoldType() {
        HoldType[] types = HoldType.values();
        return types[random.nextInt(types.length)];
    }

    public Board build() {
        return new Board(grid, rows, cols);
    }
}