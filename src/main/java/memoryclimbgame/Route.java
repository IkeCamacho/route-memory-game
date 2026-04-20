package memoryclimbgame;

import java.util.ArrayList;
import java.util.List;

public class Route {
    private final List<Hold> sequence;

    public Route() {
        this.sequence = new ArrayList<>();
    }

    public Route(List<Hold> sequence) {
        this.sequence = sequence;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Route other)) return false;
        return this.sequence.equals(other.sequence);
    }

    @Override
    public int hashCode() {
        return sequence.hashCode();
    }
}