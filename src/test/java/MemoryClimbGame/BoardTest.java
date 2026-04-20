package MemoryClimbGame;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardTest {
    @Test
    void create4x4Board(){
        int row = 4;
        int col = 4;
        Board board = new Board(row, col);
        assertEquals(row, board.getRowLength());
        assertEquals(col, board.getColLength());
    }
    @Test
    void getHoldByCoordinateReturnsCorrectHold() {
        int rows = 2;
        int cols = 2;

        Hold hold1 = new Hold(0, 0);
        Hold hold2 = new Hold(0, 1);
        Hold hold3 = new Hold(1, 0);
        Hold hold4 = new Hold(1, 1);

        List<Hold> holds = new ArrayList<>();
        holds.add(hold1);
        holds.add(hold2);
        holds.add(hold3);
        holds.add(hold4);

        Board board = new Board(rows, cols, holds);

        assertEquals(hold1, board.getHold(0, 0));
        assertEquals(hold2, board.getHold(0, 1));
        assertEquals(hold3, board.getHold(1, 0));
        assertEquals(hold4, board.getHold(1, 1));
    }
}
