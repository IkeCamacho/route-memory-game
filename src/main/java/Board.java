import java.util.List;

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