package memoryclimbgame;

import memoryclimbgame.route.Route;

import javax.swing.*;
import java.awt.*;

/**
 * Main game window. Contains:
 * - Difficulty selection buttons
 * - The climbing wall grid (BoardPanel)
 * - Score display
 * - Submit guess button
 *
 * Implements IObserver to react to game state changes.
 */
public class GameWindow extends JFrame implements IObserver {
    private MemoryClimbGame game;
    private ScoreObserver scoreObserver;

    // UI components
    private BoardPanel boardPanel;
    private JLabel statusLabel;
    private JLabel scoreLabel;
    private JButton submitButton;
    private JPanel difficultyPanel;
    private JPanel controlPanel;
    private Timer showTimer;

    // How long the route is displayed (milliseconds)
    private static final int SHOW_DURATION_MS = 3000;

    public GameWindow() {
        setTitle("Climbing Route Memory Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        game = new MemoryClimbGame();
        game.addObserver(this);
        scoreObserver = new ScoreObserver(game);

        buildUI();
        pack();
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(500, 600));
    }

    private void buildUI() {
        // --- Top: Status and Score ---
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        statusLabel = new JLabel("Select a difficulty to begin", SwingConstants.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        topPanel.add(statusLabel, BorderLayout.CENTER);

        scoreLabel = new JLabel("Score: 0", SwingConstants.RIGHT);
        scoreLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        topPanel.add(scoreLabel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // --- Center: Board (empty until game starts) ---
        boardPanel = null; // created when game starts

        // --- Bottom: Controls ---
        controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Difficulty buttons
        difficultyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton easyBtn = createDifficultyButton("Easy", new Color(76, 175, 80));
        JButton medBtn = createDifficultyButton("Medium", new Color(255, 193, 7));
        JButton hardBtn = createDifficultyButton("Hard", new Color(244, 67, 54));
        difficultyPanel.add(easyBtn);
        difficultyPanel.add(medBtn);
        difficultyPanel.add(hardBtn);
        controlPanel.add(difficultyPanel);

        // Submit button (hidden until guessing phase)
        submitButton = new JButton("Submit Guess");
        submitButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        submitButton.setEnabled(false);
        submitButton.setVisible(false);
        submitButton.addActionListener(e -> onSubmitGuess());
        controlPanel.add(submitButton);

        add(controlPanel, BorderLayout.SOUTH);
    }

    private JButton createDifficultyButton(String label, Color color) {
        JButton btn = new JButton(label);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.addActionListener(e -> onSelectDifficulty(label.toLowerCase()));
        return btn;
    }

    /**
     * Called when the player selects a difficulty.
     * Creates a temporary mock strategy until partner's code is ready.
     */
    private void onSelectDifficulty(String difficulty) {
        // Hide difficulty buttons during gameplay
        difficultyPanel.setVisible(false);

        // Use partner's strategy when ready, mock for now
        RouteGenerationStrategy strategy = createStrategy(difficulty);
        game.startGame(strategy);
    }

    /**
     * Placeholder: creates a simple strategy for testing the UI.
     * Replace with RouteFactory.createStrategy() when partner's code is ready.
     */
    private RouteGenerationStrategy createStrategy(String difficulty) {
        // TODO: Replace with partner's strategy implementations
        // return RouteFactory.createStrategy(difficulty);
        int rows, cols, routeLen;
        switch (difficulty) {
            case "medium":
                rows = 8; cols = 6; routeLen = 5;
                break;
            case "hard":
                rows = 10; cols = 8; routeLen = 7;
                break;
            default: // easy
                rows = 6; cols = 4; routeLen = 3;
                break;
        }
        return new PlaceholderStrategy(rows, cols, routeLen);
    }

    /**
     * Called when the player clicks Submit Guess.
     */
    private void onSubmitGuess() {
        if (boardPanel == null) return;

        Route guess = boardPanel.getPlayerGuess();
        if (guess.length() == 0) {
            statusLabel.setText("Select at least one hold!");
            return;
        }
        submitButton.setEnabled(false);
        game.submitGuess(guess);
    }

    /**
     * Observer callback - updates the UI based on game state.
     */
    @Override
    public void update() {
        SwingUtilities.invokeLater(() -> {
            GameState state = game.getState();
            scoreLabel.setText("Score: " + game.getScore());

            switch (state) {
                case SHOWING_ROUTE -> showRoute();
                case GUESSING -> startGuessing();
                case CORRECT -> showCorrect();
                case GAME_OVER -> showGameOver();
            }
        });
    }

    private void showRoute() {
        // Build/rebuild the board panel
        if (boardPanel != null) {
            remove(boardPanel);
        }
        Board board = game.getBoard();
        boardPanel = new BoardPanel(board, game.getCurrentRoute());
        add(boardPanel, BorderLayout.CENTER);

        boardPanel.showRoute(true);
        boardPanel.setClickable(false);
        submitButton.setVisible(false);

        statusLabel.setText("Memorize the route! (" + (SHOW_DURATION_MS / 1000) + "s)");

        revalidate();
        repaint();

        // Timer to hide the route after the display period
        if (showTimer != null && showTimer.isRunning()) {
            showTimer.stop();
        }
        showTimer = new Timer(SHOW_DURATION_MS, e -> {
            game.hideRoute();
            showTimer.stop();
        });
        showTimer.setRepeats(false);
        showTimer.start();
    }

    private void startGuessing() {
        boardPanel.showRoute(false);
        boardPanel.setClickable(true);
        submitButton.setVisible(true);
        submitButton.setEnabled(true);
        statusLabel.setText("Recreate the route! Click holds in order.");
        revalidate();
        repaint();
    }

    private void showCorrect() {
        statusLabel.setText("Correct! Get ready for the next route...");
    }

    private void showGameOver() {
        boardPanel.showRoute(true);
        boardPanel.setClickable(false);
        submitButton.setVisible(false);

        String message = scoreObserver.getResultMessage();
        statusLabel.setText(message);

        // Show play again buttons
        difficultyPanel.setVisible(true);

        // Reset game for next round
        game = new MemoryClimbGame();
        game.addObserver(this);
        scoreObserver = new ScoreObserver(game);

        revalidate();
        repaint();
    }
}
