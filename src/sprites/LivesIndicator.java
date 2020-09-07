package sprites;

import biuoop.DrawSurface;
import components.Counter;
import graphics.Point;
import graphics.Rectangle;
import interfaces.Sprite;

import java.awt.Color;

/**
 * The type Lives indicator.
 * show how many lives are left
 */
public class LivesIndicator implements Sprite {
    public static final int WIDE_OF_LIVES = 250;
    private Counter countLives;
    private Rectangle livesIndicator;

    /**
     * Construct a new Lives indicator.
     *
     * @param countLives the lives counter
     */
    public LivesIndicator(Counter countLives) {
        this.countLives = countLives;
        int high = ScoreIndicator.INDICATOR_HEIGHT;
        Point upperLeft = ScoreIndicator.UPPER_LEFT_INDICATOR;
        this.livesIndicator = new Rectangle(upperLeft, WIDE_OF_LIVES, high);
    }

    @Override
    public void drawOn(DrawSurface d) {
        int x = (int) livesIndicator.getUpperLeft().getX();
        int y = (int) livesIndicator.getUpperLeft().getY();
        d.setColor(Color.BLUE.darker().darker());
        String lives = Integer.toString(countLives.getValue());
        int high = ScoreIndicator.INDICATOR_HEIGHT;
        int font = ScoreIndicator.SCORE_FONT;
        d.drawText((int) (x + (WIDE_OF_LIVES / 2) - 25), (int) (y + (high) / 2) + 6, "Lives: " + lives, font);
    }

    @Override
    public void timePassed() {
    }
}
