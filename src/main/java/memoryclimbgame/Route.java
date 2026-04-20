package memoryclimbgame;

import java.util.ArrayList;
import java.util.List;

public class Route {
    private final List<Hold> sequence;

    public Route() {
        this.sequence = new ArrayList<>();
    }

    public void addHold(Hold hold) {
        sequence.add(hold);
    }

    public int length() {
        return sequence.size();
    }

    public Hold getHoldAt(int i) {
        return sequence.get(i);
    }
}