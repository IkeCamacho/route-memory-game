package src.main.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    private Hold[][] grid;
    private final int row;
    private final int col;
    private List<Hold> holds;

    public Board(int row, int col) {
        this.row = row;
        this.col = col;
    }
    public Board(int row, int col,  List<Hold> holds) {
        this.row = row;
        this.col = col;
        this.holds = holds;
        setupGrid();
    }

    public static Board createPopulated(int rows, int cols, Random rand) {
        HoldType[] types = HoldType.values();
        List<Hold> holds = new ArrayList<>();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                HoldType type = types[rand.nextInt(types.length)];
                holds.add(new Hold(r, c, type));
            }
        }
        return new Board(rows, cols, holds);
    }

    public boolean inBounds(int r, int c) {
        return r >= 0 && r < row && c >= 0 && c < col;
    }

    private void setupGrid() {
        grid = new Hold[row][col];
        for(Hold hold : holds) {
            grid[hold.getRow()][hold.getCol()] = hold;
        }
    }

    public int getRowLength() {
        return row;
    }
    public int getColLength() {
        return col;
    }

    public Hold getHold(int row, int col) {
        return grid[row][col];
    }
}