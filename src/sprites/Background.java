package sprites;

import biuoop.DrawSurface;
import components.GameLevel;
import interfaces.Sprite;
import java.awt.Color;
import java.awt.Image;

/**
 * The type Background.
 */
public class Background implements Sprite {
    private Color color;
    private Image image;

    /**
     * Instantiates a new Background.
     *
     * @param c     the c
     * @param image the image
     */
    public Background(Color c, Image image) {
        this.color = c;
        this.image = image;
    }


    /**
     * Draw object on a given surface.
     *
     * @param d the surface
     */
    @Override
    public void drawOn(DrawSurface d) {
        // There is no image to draw.
        if (image == null) {
            d.setColor(color);
            d.fillRectangle(0, 2 * (GameLevel.FRAME_WIDTH) - 5, GameLevel.WIDTH_GAME, GameLevel.HEIGHT_GAME);
        } else {
            d.drawImage(0, 0, image);
        }
    }

    /**
     * Notify the sprite that time has passed.
     */
    @Override
    public void timePassed() {

    }
}
