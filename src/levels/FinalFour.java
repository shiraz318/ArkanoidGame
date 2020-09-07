package levels;

import components.GameLevel;
import components.Velocity;
import graphics.Point;
import interfaces.LevelInformation;
import interfaces.Sprite;
import sprites.Background4;
import sprites.Block;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Final four - level 4.
 */
public class FinalFour implements LevelInformation {
    @Override
    public int numberOfBalls() {
        return 3;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> velocities = new ArrayList<Velocity>();
        for (int i = 0; i < numberOfBalls(); i++) {
            velocities.add(new Velocity(-i + 1, 10));
        }
        return velocities;
    }

    @Override
    public int paddleSpeed() {
        return 10;
    }

    @Override
    public int paddleWidth() {
        return 200;
    }

    @Override
    public String levelName() {
        return "Final Four";
    }

    @Override
    public Sprite getBackground() {
        return new Background4();
    }

    @Override
    public List<Block> blocks() {
        List<Block> blocksList = new ArrayList<Block>();
        Block[][] blocks = new Block[7][15];
        int wide = (int) (GameLevel.WIDTH_GAME - (2 * GameLevel.FRAME_WIDTH)) / 15;
        for (int i = 0; i < blocks.length; i++) {
            // Each raw has one less blocks of the raw before.
            for (int j = 0; j < 15; j++) {
                int x = GameLevel.WIDTH_GAME - GameLevel.FRAME_WIDTH - ((j + 1) * wide);
                int y = 100 + (GameLevel.BLOCKS_HEIGHT * (i + 1));
                Point p = new Point(x, y);
                blocks[i][j] = new Block(p, wide, GameLevel.BLOCKS_HEIGHT);
                blocks[i][j].setHitPoints(1);
                blocks[i][j].getCollisionRectangle().setFillColor(setColor(i));
                blocksList.add(blocks[i][j]);
            }
        }
        return blocksList;
    }

    /**
     * Set color.
     *
     * @param index the index
     * @return the color
     */
    public Color setColor(int index) {
        switch (index) {
            case 0:
                return Color.GRAY;
            case 1:
                return Color.RED;
            case 2:
                return Color.YELLOW;
            case 3:
                return Color.GREEN;
            case 4:
                return Color.WHITE;
            case 5:
                return Color.PINK;
            case 6:
                return Color.CYAN;
            case 7:
                return Color.WHITE;
            case 8:
                return Color.CYAN.darker();
            case 9:
                return Color.green;
            case 10:
                return Color.PINK;
            case 11:
                return Color.pink.darker();
            case 12:
                return Color.YELLOW.darker();
            case 13:
                return Color.YELLOW;
            case 14:
                return Color.BLACK;
            default:
                break;
        }
        return Color.BLACK;
    }


    @Override
    public int numberOfBlocksToRemove() {
        return (15 * 7);
    }
}
