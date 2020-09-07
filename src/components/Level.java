package components;

import interfaces.LevelInformation;
import interfaces.Sprite;
import sprites.Block;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Level.
 */
public class Level implements LevelInformation {
    private int numberOfBalls;
    private int numberOfBlocksToRemove;
    private int paddleSpeed;
    private int paddleWidth;
    private List<Velocity> initialBallVelocities;
    private String levelName;
    private Sprite getBackground;
    private List<Block> blocks = new ArrayList<Block>();


    /**
     * Returns number of balls.
     *
     * @return number of balls
     */
    @Override
    public int numberOfBalls() {
        return numberOfBalls;
    }

    /**
     * Sets number of balls.
     *
     * @param num the num
     */
    public void setNumberOfBalls(int num) {
        numberOfBalls = num;
    }
    /**
     * Initial ball velocities.
     *
     * @return the list with the initialized velocities
     */
    @Override
    public List<Velocity> initialBallVelocities() {
        return initialBallVelocities;
    }

    /**
     * Sets initial ball velocities.
     *
     * @param v the v
     */
    public void setInitialBallVelocities(List<Velocity> v) {
        initialBallVelocities = v;
    }
    /**
     * Returns the addle speed.
     *
     * @return the addle speed
     */
    @Override
    public int paddleSpeed() {
        return paddleSpeed;
    }

    /**
     * Sets paddle speed.
     *
     * @param s the s
     */
    public void setPaddleSpeed(int s) {
        paddleSpeed = s;
    }
    /**
     * Returns paddle width.
     *
     * @return the paddle width
     */
    @Override
    public int paddleWidth() {
        return paddleWidth;
    }

    /**
     * Sets paddle width.
     *
     * @param w the w
     */
    public void setPaddleWidth(int w) {
        paddleWidth = w;
    }
    /**
     * Returns level name.
     *
     * @return the level name
     */
    @Override
    public String levelName() {
        return levelName;
    }

    /**
     * Sets level name.
     *
     * @param s the s
     */
    public void setLevelName(String s) {
        levelName = s;
    }
    /**
     * Gets background.
     *
     * @return the background
     */
    @Override
    public Sprite getBackground() {
        return getBackground;
    }

    /**
     * Sets get background.
     *
     * @param s the s
     */
    public void setGetBackground(Sprite s) {
        getBackground = s;
    }
    /**
     * Returns blocks list.
     *
     * @return the list of blocks
     */
    @Override
    public List<Block> blocks() {
        return blocks;
    }

    /**
     * Sets blocks.
     *
     * @param b the b
     */
    public void setBlocks(List<Block> b) {
        blocks = b;
    }
    /**
     * Returns number of blocks to remove.
     *
     * @return the number of blocks to remove
     */
    @Override
    public int numberOfBlocksToRemove() {
        return numberOfBlocksToRemove;
    }

    /**
     * Sets number of blocks to remove.
     *
     * @param num the num
     */
    public void setNumberOfBlocksToRemove(int num) {
        numberOfBlocksToRemove = num;
    }

}