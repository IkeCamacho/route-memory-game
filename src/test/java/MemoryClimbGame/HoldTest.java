package MemoryClimbGame;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HoldTest {
    @Test
    void createHold() {
        Hold hold = new Hold(2, 3);

        assertEquals(2, hold.getRow());
        assertEquals(3, hold.getCol());
    }
}
