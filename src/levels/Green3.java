package levels;

import components.GameLevel;
import components.Velocity;
import graphics.Point;
import interfaces.LevelInformation;
import interfaces.Sprite;
import sprites.Background3;
import sprites.Block;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


/**
 * The type Green 3 - level 3.
 */
public class Green3 implements LevelInformation {
    @Override
    public int numberOfBalls() {
        return 2;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> velocities = new ArrayList<Velocity>();
        for (int i = 0; i < numberOfBalls(); i++) {
            velocities.add(new Velocity((i * 2) - 1, -10));
        }
        return velocities;
    }

    @Override
    public int paddleSpeed() {
        return 13;
    }

    @Override
    public int paddleWidth() {
        return 100;
    }

    @Override
    public String levelName() {
        return "Green 3";
    }

    @Override
    public Sprite getBackground() {
        return new Background3();
    }

    @Override
    public List<Block> blocks() {
        List<Block> blocksList = new ArrayList<Block>();
        Block[][] blocks = new Block[5][10];
        for (int i = 0; i < blocks.length; i++) {
            // Each raw has one less blocks of the raw before.
            for (int j = 0; j < 10 - i; j++) {
                int x = GameLevel.WIDTH_GAME - GameLevel.FRAME_WIDTH - ((j + 1) * GameLevel.BLOCKS_WIDTH);
                int y = 120 + (GameLevel.BLOCKS_HEIGHT * (i + 1));
                Point p = new Point(x, y);
                blocks[i][j] = new Block(p, GameLevel.BLOCKS_WIDTH, GameLevel.BLOCKS_HEIGHT);
                blocks[i][j].setHitPoints(1);

                setColorOfBlocks(blocks[i][j], i);
                blocksList.add(blocks[i][j]);
            }
        }
        return blocksList;
    }

    /**
     * Sets color of blocks.
     *
     * @param block the block
     * @param raw the raw number
     */
    public void setColorOfBlocks(Block block, int raw) {
        switch (raw) {
            case 0:
                block.getCollisionRectangle().setFillColor(Color.GRAY);
                break;
            case 1:
                block.getCollisionRectangle().setFillColor(Color.RED);
                break;
            case 2:
                block.getCollisionRectangle().setFillColor(Color.YELLOW);
                break;
            case 3:
                block.getCollisionRectangle().setFillColor(Color.BLUE);
                break;
            case 4:
                block.getCollisionRectangle().setFillColor(Color.WHITE);
                break;
            default:
                break;
        }
    }

    @Override
    public int numberOfBlocksToRemove() {
        return 40;
    }
}
