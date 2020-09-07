package sprites;

import biuoop.DrawSurface;
import components.Counter;
import interfaces.Animation;

import java.awt.Color;

/**
 * The type You win screen - a screen that appears when the player wins.
 */
public class YouWinScreen implements Animation {
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

    // Draw clouds.
    private void drawClouds(DrawSurface d) {
        int radius = 40;
        int[] yLocations = {350, 330, 340, 357};
        d.fillRectangle(0, 350, d.getWidth(), d.getHeight());
        d.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < yLocations.length; i++) {
            d.fillCircle((i * 50) + 30, yLocations[i], radius);
            d.fillCircle((i * -50) + 760, yLocations[i], radius);
        }
        d.fillRectangle(0, 367, 180, 30);
        d.fillRectangle(615, 359, 200, 38);
    }

    // Draw a rainbow.
    private void drawRainbow(DrawSurface d) {
        int locationX = 400;
        int locationY = 400;
        int radius = 400;
        Color[] colors = {Color.BLUE, Color.cyan, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.RED, Color.WHITE};
        for (int i = 0; i < colors.length; i++) {
            d.setColor(colors[i]);
            d.fillCircle(locationX, locationY, radius - (i * 30));
        }

    }

    // Draw the text on the screen.
    private void drawText(DrawSurface d){
        d.setColor(Color.BLACK);
        int yLocation = d.getHeight() / 2;
        d.drawText(220, yLocation - 30, "You Win!", 80);
        d.drawText(250, yLocation + 50, "Your score is " + scoreCounter.getValue(), 40);
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        // Draw background.
        d.setColor(Color.WHITE);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        // Draw a rainbow.
        drawRainbow(d);
        // Draw clouds.
        drawClouds(d);
       // Draw text.
       drawText(d);
    }

    @Override
    public boolean shouldStop() {
        return stop;
    }
}
