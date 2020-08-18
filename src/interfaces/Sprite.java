package interfaces;
import biuoop.DrawSurface;

/**
 * The interface Sprite - an object you can draw and let know that time passed.
 */
public interface Sprite {
    /**
     * Draw object on a given surface.
     *
     * @param d the surface
     */
    void drawOn(DrawSurface d);

    /**
     * Notify the sprite that time has passed.
     */
    void timePassed();
}