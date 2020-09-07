package sprites;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import interfaces.Animation;

import java.awt.Color;

/**
 * The type Pause screen.
 */
public class PauseScreen implements Animation {
    private KeyboardSensor keyboard;
    private boolean stop;
    /**
     * Instantiates a new Pause screen.
     *
     * @param k the keyboard sensor
     */
    public PauseScreen(KeyboardSensor k) {
        this.keyboard = k;
        this.stop = false;
    }

    // Draw a chess on the screen.
    private void drawChess(DrawSurface d) {
        d.setColor(Color.WHITE);
        d.fillRectangle(0, 0, 800, 600);
        d.setColor(Color.BLACK);
        for (int j = 0; j < 6; j++) {
            for (int i = 0; i < 8; i++) {
                d.fillRectangle(0 + i * 200, 0 + j * 200, 100, 100);
            }
        }
        for (int j = 0; j < 6; j++) {
            for (int i = 0; i < 8; i++) {
                d.fillRectangle(100 + i * 200, 100 + j * 200, 100, 100);
            }
        }
    }

    // Draw the text on the pause screen.
    private void drawText(DrawSurface d) {
        d.setColor(Color.RED.darker());
        int yLocation = d.getHeight() / 2;
        d.drawText(260, yLocation - 30, "paused", 90);
        d.drawText(105, yLocation + 50, "press space to continue", 55);
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        // Draw chess background.
        drawChess(d);
        // Draw text.
        drawText(d);
    }
    @Override
    public boolean shouldStop() {
        return stop;
    }
}
