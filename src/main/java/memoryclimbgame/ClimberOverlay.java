package memoryclimbgame;

import memoryclimbgame.route.Route;

import javax.swing.*;
import java.awt.*;

public class ClimberOverlay extends JPanel {
    private final BoardPanel boardPanel;
    private final Route route;

    // Current drawing position of the climber (pixel coordinates)
    private double climberX = -1;
    private double climberY = -1;
    private boolean visible = false;

    // Animation state
    private int currentHoldIndex = 0;
    private double progress = 0.0; // 0.0 to 1.0 between two holds
    private Timer animationTimer;

    // Climber appearance
    private static final int CLIMBER_SIZE = 20;
    private static final Color CLIMBER_COLOR = new Color(255, 87, 34);
    private static final Color CLIMBER_OUTLINE = new Color(200, 60, 20);
    private static final int ANIMATION_FPS = 60;
    private static final double SPEED = 0.04;


    private double limbSwing = 0;

    public ClimberOverlay(BoardPanel boardPanel, Route route) {
        this.boardPanel = boardPanel;
        this.route = route;
        setOpaque(false); // transparent so board shows through
    }

    public void animateRoute() {
        if (route.length() == 0) return;

        visible = true;
        currentHoldIndex = 0;
        progress = 0.0;

        // Start at the first hold
        Point start = getHoldCenter(0);
        climberX = start.x;
        climberY = start.y;

        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }

        animationTimer = new Timer(1000 / ANIMATION_FPS, e -> tick());
        animationTimer.start();
    }

    public void stopAnimation() {
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }
        visible = false;
        repaint();
    }

    private void tick() {
        if (currentHoldIndex >= route.length() - 1) {
            // Reached the last hold, hold position briefly then stop
            animationTimer.stop();
            repaint();
            return;
        }

        progress += SPEED;
        limbSwing += 0.15;

        if (progress >= 1.0) {
            // Arrived at next hold
            progress = 0.0;
            currentHoldIndex++;

            if (currentHoldIndex >= route.length() - 1) {
                // At the last hold, snap to it
                Point end = getHoldCenter(currentHoldIndex);
                climberX = end.x;
                climberY = end.y;
                repaint();
                return;
            }
        }

        // Interpolate between current hold and next hold
        Point from = getHoldCenter(currentHoldIndex);
        Point to = getHoldCenter(currentHoldIndex + 1);

        climberX = from.x + (to.x - from.x) * easeInOut(progress);
        climberY = from.y + (to.y - from.y) * easeInOut(progress);

        repaint();
    }

    private double easeInOut(double t) {
        return t < 0.5
                ? 2 * t * t
                : 1 - Math.pow(-2 * t + 2, 2) / 2;
    }

    private Point getHoldCenter(int routeIndex) {
        Hold hold = route.getHoldAt(routeIndex);
        JButton btn = boardPanel.getHoldButton(hold.getRow(), hold.getCol());

        // Convert button center to this overlay's coordinate space
        Point btnLocation = SwingUtilities.convertPoint(btn, btn.getWidth() / 2, btn.getHeight() / 2, this);
        return btnLocation;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!visible || climberX < 0) return;

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int cx = (int) climberX;
        int cy = (int) climberY;

        // Draw limbs (arms and legs that swing)
        g2.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.setColor(CLIMBER_OUTLINE);

        int limbLength = CLIMBER_SIZE / 2 + 4;
        double swing = Math.sin(limbSwing) * 15;

        // Left arm
        int lax = cx + (int) (Math.cos(Math.toRadians(-135 + swing)) * limbLength);
        int lay = cy + (int) (Math.sin(Math.toRadians(-135 + swing)) * limbLength);
        g2.drawLine(cx, cy - 2, lax, lay);

        // Right arm
        int rax = cx + (int) (Math.cos(Math.toRadians(-45 - swing)) * limbLength);
        int ray = cy + (int) (Math.sin(Math.toRadians(-45 - swing)) * limbLength);
        g2.drawLine(cx, cy - 2, rax, ray);

        // Left leg
        int llx = cx + (int) (Math.cos(Math.toRadians(135 - swing)) * limbLength);
        int lly = cy + (int) (Math.sin(Math.toRadians(135 - swing)) * limbLength);
        g2.drawLine(cx, cy + 4, llx, lly);

        // Right leg
        int rlx = cx + (int) (Math.cos(Math.toRadians(45 + swing)) * limbLength);
        int rly = cy + (int) (Math.sin(Math.toRadians(45 + swing)) * limbLength);
        g2.drawLine(cx, cy + 4, rlx, rly);

        // Draw body (circle)
        g2.setColor(CLIMBER_COLOR);
        g2.fillOval(cx - CLIMBER_SIZE / 2, cy - CLIMBER_SIZE / 2, CLIMBER_SIZE, CLIMBER_SIZE);

        // Outline
        g2.setColor(CLIMBER_OUTLINE);
        g2.setStroke(new BasicStroke(2));
        g2.drawOval(cx - CLIMBER_SIZE / 2, cy - CLIMBER_SIZE / 2, CLIMBER_SIZE, CLIMBER_SIZE);

        g2.dispose();
    }
}
