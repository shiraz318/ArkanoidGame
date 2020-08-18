package interfaces;

import components.Velocity;
import sprites.Block;

import java.util.List;

/**
 * The interface Level information.
 */
public interface LevelInformation {
    /**
     * Returns number of balls.
     *
     * @return number of balls
     */
    int numberOfBalls();

    /**
     * Initial ball velocities.
     *
     * @return the list with the initialized velocities
     */
    List<Velocity> initialBallVelocities();

    /**
     * Returns the addle speed.
     *
     * @return the addle speed
     */
    int paddleSpeed();

    /**
     * Returns paddle width.
     *
     * @return the paddle width
     */
    int paddleWidth();

    /**
     * Returns level name.
     *
     * @return the level name
     */
    String levelName();

    /**
     * Gets background.
     *
     * @return the background
     */
    Sprite getBackground();

    /**
     * Returns blocks list.
     *
     * @return the list of blocks
     */
    List<Block> blocks();

    /**
     * Returns number of blocks to remove.
     *
     * @return the number of blocks to remove
     */
    int numberOfBlocksToRemove();

}