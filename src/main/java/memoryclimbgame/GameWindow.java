package memoryclimbgame;


import memoryclimbgame.route.IRouteStrategy;
import memoryclimbgame.route.Route;
import memoryclimbgame.route.RouteFactory;


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
        boardPanel = null;


        // --- Bottom: Controls ---
        controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));


        // Difficulty buttons
        difficultyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton easyBtn = createDifficultyButton(Difficulty.EASY, new Color(76, 175, 80));
        JButton medBtn = createDifficultyButton(Difficulty.MEDIUM, new Color(255, 193, 7));
        JButton hardBtn = createDifficultyButton(Difficulty.HARD, new Color(244, 67, 54));
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


    private JButton createDifficultyButton(Difficulty difficulty, Color color) {
        String label = difficulty.name().charAt(0) + difficulty.name().substring(1).toLowerCase();


        JButton btn = new JButton(label);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.addActionListener(e -> onSelectDifficulty(difficulty));
        return btn;
    }


    /**
     * Called when the player selects a difficulty.
     */
    private void onSelectDifficulty(Difficulty difficulty) {
        // Start a fresh game each time a difficulty is selected
        game = new MemoryClimbGame();
        game.addObserver(this);
        scoreObserver = new ScoreObserver(game);


        // Hide difficulty buttons during gameplay
        difficultyPanel.setVisible(false);


        IRouteStrategy strategy = createStrategy(difficulty);
        game.startGame(strategy);
    }


    /**
     * Creates the route generation strategy for the selected difficulty.
     * Assumes RouteFactory now supports Difficulty directly.
     */
    private IRouteStrategy createStrategy(Difficulty difficulty) {
        return RouteFactory.createStrategy(difficulty);
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


        if (guess.length() != game.getRoute().length()) {
            statusLabel.setText("Select exactly " + game.getRoute().length() + " holds!");
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
            boardPanel = null;
        }


        Board board = game.getBoard();
        boardPanel = new BoardPanel(board, game.getRoute());
        add(boardPanel, BorderLayout.CENTER);


        boardPanel.showRoute(true);
        boardPanel.setClickable(false);
        submitButton.setVisible(false);
        submitButton.setEnabled(false);


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
        if (boardPanel == null) return;


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
        if (boardPanel != null) {
            boardPanel.showRoute(true);
            boardPanel.setClickable(false);
        }


        if (showTimer != null && showTimer.isRunning()) {
            showTimer.stop();
        }


        submitButton.setVisible(false);
        submitButton.setEnabled(false);


        String message = scoreObserver.getResultMessage();
        statusLabel.setText(message);


        // Show play again buttons
        difficultyPanel.setVisible(true);


        revalidate();
        repaint();
    }
}
