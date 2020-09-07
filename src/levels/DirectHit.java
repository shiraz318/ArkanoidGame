package levels;

import components.GameLevel;
import components.Velocity;
import graphics.Point;
import interfaces.LevelInformation;
import interfaces.Sprite;
import sprites.Background1;
import sprites.Block;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Direct hit - level 1.
 */
public class DirectHit implements LevelInformation {
    private int blockSize;
    private int yLocation;
    private int xLocation;

    public DirectHit() {
        this.blockSize = 35;
        this.yLocation = 180;
        this.xLocation = (GameLevel.WIDTH_GAME / 2) - 20;
    }

    @Override
    public int numberOfBalls() {
        return 1;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        Velocity v = new Velocity(0,  -9);
        List<Velocity> velocities = new ArrayList<Velocity>();
        velocities.add(v);
        return velocities;
    }

    @Override
    public int paddleSpeed() {
        return 10;
    }

    @Override
    public int paddleWidth() {
        return 150;
    }

    @Override
    public String levelName() {
        return "Direct Hit";
    }

    @Override
    public Sprite getBackground() {
        return new Background1(blockSize, yLocation, xLocation);
    }

    @Override
    public List<Block> blocks() {
        int wide = blockSize;
        int high = blockSize;
        Block block = new Block(new Point(xLocation, yLocation), wide, high);
        block.getCollisionRectangle().setFillColor(Color.RED);
        block.setHitPoints(1);
        List<Block> blocks = new ArrayList<Block>();
        blocks.add(block);
        return blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return 1;
    }
}