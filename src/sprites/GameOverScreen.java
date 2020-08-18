package sprites;

import biuoop.DrawSurface;
import components.Counter;
import components.GameLevel;
import interfaces.Animation;

import java.awt.Color;

/**
 * The type Game over screen - appears when the player loses.
 */
public class GameOverScreen implements Animation {
    // fields
    private boolean stop;
    private Counter scoreCounter;

    /**
     * Instantiates a new Game over screen.
     *
     * @param scoreCounter the score counter
     */
    public GameOverScreen(Counter scoreCounter) {
        this.stop = false;
        this.scoreCounter = scoreCounter;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        d.setColor(Color.BLACK);
        d.fillRectangle(0, 0, 800, 600);
        d.setColor(Color.WHITE);
        int xLocation = (int) (GameLevel.WIDTH_GAME / 5);
        int yLocation = d.getHeight() / 2;
        d.drawText(190, yLocation - 30, "Game Over", 80);
        d.drawText(260, yLocation + 50, "Your score is " + this.scoreCounter.getValue(), 40);
        int j = 9;
        for (int i = 0; i < 600; i += j) {
            d.drawLine(765, i, 800, i);
            d.drawLine(0, i, 35, i);
            d.drawLine(740, i + j, 800, i + j);
            d.drawLine(0, i + j, 60, i + j);
            d.drawLine(780, i + 2 * j, 800, i + 2 * j);
            d.drawLine(0, i + 2 * j, 20, i + 2 * j);
            d.drawLine(750, i + 3 * j, 800, i + 3 * j);
            d.drawLine(0, i + 3 * j, 50, i + 3 * j);
            i += 3 * j;
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
