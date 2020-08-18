package interfaces;

import biuoop.DrawSurface;

/**
 * The interface Animation.
 */
public interface Animation {
    /**
     * Draw one frame on a given surface.
     *
     * @param d the surface
     */
    void doOneFrame(DrawSurface d);

    /**
     * Returns if the animation should stop.
     *
     * @return the true if the animation should stop, and false otherwize
     */
    boolean shouldStop();
}