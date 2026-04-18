package src.main.java;

public class Hold{
    private int row;
    private int col;
    private HoldType type;
    private boolean onRoute;

    public Hold(int row, int col, HoldType type) {
        this.row = row;
        this.col = col;
        this.type = type;
        this.onRoute = false;
    }

    public Hold(int row, int col) {
        this.row = row;
        this.col = col;
        this.type = HoldType.JUG;
        this.onRoute = false;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public HoldType getType() {
        return type;
    }

    public void setType(HoldType type) {
        this.type = type;
    }

    public boolean isOnRoute() {
        return onRoute;
    }

    public void setOnRoute(boolean onRoute) {
        this.onRoute = onRoute;
    }
}