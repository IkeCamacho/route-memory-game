public class Hold{
    private int row;
    private int col;
    private HoldType type;
    private boolean onRoute;

    public Hold(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}