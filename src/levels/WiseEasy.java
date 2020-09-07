package levels;

import components.GameLevel;
import components.Velocity;
import graphics.Point;
import interfaces.LevelInformation;
import interfaces.Sprite;
import sprites.Background2;
import sprites.Block;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Wise easy - 2.
 */
public class WiseEasy implements LevelInformation {
    @Override
    public int numberOfBalls() {
        return 10;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> velocities = new ArrayList<Velocity>();
         double speed = 8;
         for (int i = 0; i < numberOfBalls() / 2; i++) {
             velocities.add(Velocity.fromAngleAndSpeed(130 + (i * 100), speed));
             velocities.add(Velocity.fromAngleAndSpeed(-(130 + (i * 100)), speed));
         }
        return velocities;
    }

    @Override
    public int paddleSpeed() {
        return 5;
    }

    @Override
    public int paddleWidth() {
        return 400;
    }

    @Override
    public String levelName() {
        return "Wise Easy";
    }

    @Override
    public Sprite getBackground() {
        return new Background2();
    }

    @Override
    public List<Block> blocks() {
        List<Block> blocks = new ArrayList<Block>();
        int wide = (int) (GameLevel.WIDTH_GAME - 2 * GameLevel.FRAME_WIDTH) / numberOfBlocksToRemove();
        int high = 30;
        int x = GameLevel.FRAME_WIDTH;
        int y = (int) (GameLevel.HEIGHT_GAME / 3) + 60;
        for (int i = 0; i < numberOfBlocksToRemove(); i++) {
            int x1 = x + (i) * wide;
            Block b = new Block(new Point(x1, y), wide, high);
            setColor(b, i);
            b.getCollisionRectangle().setFrameColor(Color.BLACK);
            blocks.add(b);
        }
        return blocks;
    }
    /**
     * Sets color.
     *
     * @param block the block
     * @param i the index
     */
    public void setColor(Block block, int i) {
        if (i == 0 || i == 1) {
            block.getCollisionRectangle().setFillColor(Color.RED);
        }
        if (i == 2 || i == 3) {
            block.getCollisionRectangle().setFillColor(Color.ORANGE);
        }
        if (i == 4 || i == 5) {
            block.getCollisionRectangle().setFillColor(Color.YELLOW);
        }
        if (i == 6 || i == 7 || i == 8) {
            block.getCollisionRectangle().setFillColor(Color.GREEN);
        }
        if (i == 10 || i == 9) {
            block.getCollisionRectangle().setFillColor(Color.BLUE);
        }
        if (i == 12 || i == 11) {
            block.getCollisionRectangle().setFillColor(Color.PINK);
        }
        if (i == 13 || i == 14) {
          block.getCollisionRectangle().setFillColor(Color.CYAN);
        }
    }

    @Override
    public int numberOfBlocksToRemove() {
        return 15;
    }
}
