package sprites;

import biuoop.DrawSurface;
import components.Counter;
import components.GameLevel;
import graphics.Point;
import graphics.Rectangle;
import interfaces.Sprite;

import java.awt.Color;

/**
 * The type Score indicator.
 * show how many points the player scored
 */
public class ScoreIndicator implements Sprite {
    public static final int INDICATOR_HEIGHT = 20;
    public static final Point UPPER_LEFT_INDICATOR = new Point(0, 0);
    public static final int SCORE_FONT = 18;
    private Counter scoreCount;
    private Rectangle scoreIndicator;

    /**
     * Construct a new Score indicator.
     *
     * @param scoreCount the score counter
     */
    public ScoreIndicator(Counter scoreCount) {
        this.scoreCount = scoreCount;
        this.scoreIndicator = new Rectangle(UPPER_LEFT_INDICATOR, GameLevel.WIDTH_GAME, INDICATOR_HEIGHT);
    }

    @Override
    public void drawOn(DrawSurface d) {
        int x = (int) scoreIndicator.getUpperLeft().getX();
        int y = (int) scoreIndicator.getUpperLeft().getY();
        d.setColor(Color.BLUE.darker().darker());
        String score = Integer.toString(scoreCount.getValue());
        int xLocation = x + (GameLevel.WIDTH_GAME / 2) - 90;
        int yLocation = y + ((INDICATOR_HEIGHT) / 2) + 6;
        d.drawText(xLocation, yLocation, "Score: " + score, SCORE_FONT);
    }

    @Override
    public void timePassed() {
    }
}
