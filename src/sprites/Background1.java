package sprites;

import biuoop.DrawSurface;
import components.GameLevel;
import graphics.Point;
import interfaces.Sprite;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Background 1.
 */
public class Background1 implements Sprite {
    // static variable
    public static final int RADIUS = 42;
    // fields
    private List<Sprite> sprites;
    private Block background;
    private int yLocation;
    private int blockSize;
    private int xLocation;

    /**
     * Instantiates a new Background 1.
     *
     * @param blockSize the block size
     * @param yLocation the y location
     * @param xLocation the x location
     */
    public Background1(int blockSize, int yLocation, int xLocation) {
        this.blockSize = blockSize;
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        this.sprites = new ArrayList<Sprite>();
        this.background = new Block(new Point(0, GameLevel.FRAME_WIDTH), GameLevel.WIDTH_GAME, GameLevel.HEIGHT_GAME);
        this.background.getCollisionRectangle().setFillColor(Color.BLACK);
        sprites.add(this.background);
        createCircles();
        createLines();
    }

    /**
     * Create circles.
     *
     **/
    public void createCircles() {
        int xCenter = xLocation + (int) (blockSize / 2);
        int yCenter = yLocation + (int) (blockSize / 2);
        Ball[] balls = new Ball[3];
        for (int i = balls.length - 1; i >= 0; i--) {
            balls[i] = new Ball(new Point(xCenter, yCenter), RADIUS + 35 * (i + 1), Color.BLUE);
            balls[i].setDrawColor(Color.BLUE);
            balls[i].setFillColor(Color.BLACK);
            sprites.add(balls[i]);
        }
    }

    /**
     * Create lines.
     *
     */
    public void createLines() {
        Block[] lines = new Block[4];
        int high = yLocation + (int) (blockSize / 2) - 50;
        int wideLine = 2;
        int x = xLocation + (int) (blockSize / 2);
        int y = GameLevel.FRAME_WIDTH;
        // Up.
        lines[0] = new Block(new Point(x, y), wideLine, high);
        y = yLocation + blockSize + 10;
        // Down.
        lines[1] = new Block(new Point(x, y), wideLine, high);
        x = xLocation + blockSize + 10;
        y = yLocation + (int) (blockSize / 2);
        // Right.
        lines[2] = new Block(new Point(x, y), high, wideLine);
        x = xLocation - high - 10;
        // Left.
        lines[3] = new Block(new Point(x, y), high, wideLine);
        for (int i = 0; i < lines.length; i++) {
            lines[i].getCollisionRectangle().setFillColor(Color.BLUE);
            sprites.add(lines[i]);
        }
    }
    @Override
    public void drawOn(DrawSurface d) {
        for (Sprite s: sprites) {
            s.drawOn(d);
        }
    }
    @Override
    public void timePassed() {

    }
}
