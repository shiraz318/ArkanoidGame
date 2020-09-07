package sprites;

import biuoop.DrawSurface;
import components.GameLevel;
import graphics.Point;
import graphics.Rectangle;
import interfaces.Sprite;

import java.awt.Color;

import static sprites.LivesIndicator.WIDE_OF_LIVES;

/**
 * The type Name indicator - show the level's name.
 */
public class NameIndicator implements Sprite {
    private String name;
    private Rectangle nameIndicator;

    /**
     * Instantiates a new Name indicator.
     *
     * @param name the name of the level
     */
    public NameIndicator(String name) {
        this.name = name;
        int high = ScoreIndicator.INDICATOR_HEIGHT;
        Point upperLeft = ScoreIndicator.UPPER_LEFT_INDICATOR;
        this.nameIndicator = new Rectangle(upperLeft, WIDE_OF_LIVES, high);

    }
    @Override
    public void drawOn(DrawSurface d) {
        int x = (int) nameIndicator.getUpperLeft().getX();
        int y = (int) nameIndicator.getUpperLeft().getY();
        d.setColor(Color.BLUE.darker().darker());
        String nameLevel = name;
        int high = ScoreIndicator.INDICATOR_HEIGHT;
        int xLocation = (int) (x + (GameLevel.WIDTH_GAME / 2) + 100);
        int yLocation = (int) (y + (high) / 2) + 6;
        int font = ScoreIndicator.SCORE_FONT;
        d.drawText(xLocation, yLocation, "Level Name: " + nameLevel, font);
    }
    @Override
    public void timePassed() {

    }
}
