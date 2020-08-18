package sprites;

import biuoop.DrawSurface;
import components.Counter;
import interfaces.Animation;

import java.awt.Color;

/**
 * The type You win screen - a screen that appears when the player wins.
 */
public class YouWinScreen implements Animation {
    // fields
    private boolean stop;
    private Counter scoreCounter;

    /**
     * Instantiates a new You win screen.
     *
     * @param scoreCounter the score counter
     */
    public YouWinScreen(Counter scoreCounter) {
        this.stop = false;
        this.scoreCounter = scoreCounter;
    }
    @Override
    public void doOneFrame(DrawSurface d) {
        d.setColor(Color.WHITE);
        d.fillRectangle(0, 0, 800, 600);
        d.setColor(Color.BLUE);
        d.fillCircle(400, 400, 400);
        d.setColor(Color.cyan);
        d.fillCircle(400, 400, 370);
        d.setColor(Color.GREEN);
        d.fillCircle(400, 400, 340);
        d.setColor(Color.YELLOW);
        d.fillCircle(400, 400, 310);
        d.setColor(Color.ORANGE);
        d.fillCircle(400, 400, 280);
        d.setColor(Color.RED);
        d.fillCircle(400, 400, 250);
        d.setColor(Color.WHITE);
        d.fillCircle(400, 400, 220);
        d.fillRectangle(0, 350, 800, 600);
        d.setColor(Color.LIGHT_GRAY);
        d.fillCircle(30, 350, 40);
        d.fillCircle(80, 330, 40);
        d.fillCircle(120, 340, 40);
        d.fillCircle(170, 357, 40);
        d.fillRectangle(0, 367, 180, 30);


        d.fillCircle(760, 350, 40);
        d.fillCircle(710, 330, 40);
        d.fillCircle(660, 340, 40);
        d.fillCircle(610, 357, 40);
        d.fillRectangle(615, 359, 200, 38);


        d.setColor(Color.BLACK);
        int yLocation = d.getHeight() / 2;
        d.drawText(220, yLocation - 30, "You Win!", 80);
        d.drawText(250, yLocation + 50, "Your score is " + this.scoreCounter.getValue(), 40);

    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
