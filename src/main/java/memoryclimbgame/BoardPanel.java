package memoryclimbgame;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import memoryclimbgame.route.*;


/**
 * Swing panel that renders the climbing wall as a grid of hold buttons.
 * - During SHOWING_ROUTE: route holds are highlighted, clicks disabled
 * - During GUESSING: all holds are neutral, player clicks to build guess
 * - Holds are numbered in the order the player clicks them
 */
public class BoardPanel extends JPanel {
    private final Board board;
    private final Route actualRoute;
    private final JButton[][] holdButtons;
    private final List<Hold> playerGuess;
    private boolean clickable;


    // Colors
    private static final Color WALL_COLOR = new Color(45, 45, 48);
    private static final Color HOLD_DEFAULT = new Color(180, 180, 180);
    private static final Color HOLD_ON_ROUTE = new Color(0, 200, 83);
    private static final Color HOLD_SELECTED = new Color(33, 150, 243);
    private static final Color HOLD_HOVER = new Color(220, 220, 220);


    public BoardPanel(Board board, Route route) {
        this.board = board;
        this.actualRoute = route;
        this.playerGuess = new ArrayList<>();
        this.clickable = false;


        int rows = board.getRowLength();
        int cols = board.getColLength();
        holdButtons = new JButton[rows][cols];


        setLayout(new GridLayout(rows, cols, 4, 4));
        setBackground(WALL_COLOR);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        buildGrid();
    }


    private void buildGrid() {
        for (int r = 0; r < board.getRowLength(); r++) {
            for (int c = 0; c < board.getColLength(); c++) {
                JButton btn = createHoldButton(r, c);
                holdButtons[r][c] = btn;
                add(btn);
            }
        }
    }


    private JButton createHoldButton(int row, int col) {
        JButton btn = new JButton();
        btn.setPreferredSize(new Dimension(50, 50));
        btn.setBackground(HOLD_DEFAULT);
        btn.setOpaque(true);
        btn.setBorderPainted(true);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setBorder(BorderFactory.createLineBorder(WALL_COLOR, 2));
        btn.setToolTipText("(" + row + ", " + col + ")");


        btn.addActionListener(e -> onHoldClicked(row, col, btn));


        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (clickable && !isInGuess(row, col)) {
                    btn.setBackground(HOLD_HOVER);
                }
            }


            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                if (clickable && !isInGuess(row, col)) {
                    btn.setBackground(HOLD_DEFAULT);
                }
            }
        });


        return btn;
    }


    private void onHoldClicked(int row, int col, JButton btn) {
        if (!clickable) return;


        Hold hold = board.getHold(row, col);


        // If already selected, allow deselecting the last one
        if (isInGuess(row, col)) {
            if (isLastInGuess(row, col)) {
                playerGuess.remove(playerGuess.size() - 1);
                btn.setBackground(HOLD_DEFAULT);
                btn.setText("");
                updateGuessNumbers();
            }
            return;
        }


        // Add to guess
        playerGuess.add(hold);
        btn.setBackground(HOLD_SELECTED);
        btn.setForeground(Color.WHITE);
        btn.setText(String.valueOf(playerGuess.size()));
    }


    private boolean isInGuess(int row, int col) {
        return playerGuess.stream()
                .anyMatch(h -> h.getRow() == row && h.getCol() == col);
    }


    private boolean isLastInGuess(int row, int col) {
        if (playerGuess.isEmpty()) return false;
        Hold last = playerGuess.get(playerGuess.size() - 1);
        return last.getRow() == row && last.getCol() == col;
    }


    private void updateGuessNumbers() {
        for (int r = 0; r < board.getRowLength(); r++) {
            for (int c = 0; c < board.getColLength(); c++) {
                holdButtons[r][c].setText("");
            }
        }
        for (int i = 0; i < playerGuess.size(); i++) {
            Hold h = playerGuess.get(i);
            holdButtons[h.getRow()][h.getCol()].setText(String.valueOf(i + 1));
        }
    }


    public void showRoute(boolean visible) {
        resetColors();
        if (visible) {
            for (int i = 0; i < actualRoute.length(); i++) {
                Hold h = actualRoute.getHoldAt(i);
                JButton btn = holdButtons[h.getRow()][h.getCol()];
                btn.setBackground(HOLD_ON_ROUTE);
                btn.setForeground(Color.WHITE);
                btn.setText(String.valueOf(i + 1));
            }
        }
    }


    public void setClickable(boolean clickable) {
        this.clickable = clickable;
        if (clickable) {
            playerGuess.clear();
            resetColors();
        }
    }


    public Route getPlayerGuess() {
        Route guess = new Route();
        for (Hold h : playerGuess) {
            guess.addHold(h);
        }
        return guess;
    }


    private void resetColors() {
        for (int r = 0; r < board.getRowLength(); r++) {
            for (int c = 0; c < board.getColLength(); c++) {
                holdButtons[r][c].setBackground(HOLD_DEFAULT);
                holdButtons[r][c].setForeground(Color.BLACK);
                holdButtons[r][c].setText("");
            }
        }
    }
}
