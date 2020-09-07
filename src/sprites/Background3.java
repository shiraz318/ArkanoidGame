package sprites;

import biuoop.DrawSurface;
import components.GameLevel;
import graphics.Point;
import interfaces.Sprite;

import java.awt.Color;

/**
 * The type Background 3.
 */
public class Background3 implements Sprite {
    @Override
    public void drawOn(DrawSurface d) {
        drawBack(d);
        drawBuilding(d);
        drawLight(d);
    }

    /**
     * Draw light on a given surface.
     *
     * @param d the surface
     */
    public void drawLight(DrawSurface d) {
        int r = 20;
        int x = 130;
        int y = 200;
        Ball[] balls = new Ball[3];
        for (int i = 0; i < balls.length; i++) {
            if (i == 2) {
                r -= 2;
            }
            r -= 5;
           balls[i] = new Ball(new Point(x, y), r, new Color(255, 255, 153));
           setColor(balls[i], i);
           balls[i].setDrawColor(balls[i].getFillColor());
           balls[i].drawOn(d);
        }
    }

    /**
     * Sets color of ball.
     *
     * @param ball the ball
     * @param i the index of the ball
     */
    public void setColor(Ball ball, int i) {
        switch (i) {
            case 0:
                ball.setFillColor(new Color(211, 192, 67));
                break;
            case 1:
                ball.setFillColor(new Color(255, 51, 51));
                break;
            case 2:
                ball.setFillColor(new Color(255, 255, 204));
                break;
            default:
                break;
        }
    }

    /**
     * Draw building on a given surface.
     *
     * @param d the surface
     */
    public void drawBuilding(DrawSurface d) {
        Block[] blocks = new Block[12];
        int x = 70;
        int y = 420;
        int wide = 118;
        int high = 200;
        int fat = 8;
        int spacex = 14;
        blocks[0] = new Block(new Point(x, y), wide, high);
        blocks[0].getCollisionRectangle().setFillColor(Color.BLACK);
        x += fat;
        y += fat;
        wide -= (2 * fat);
        blocks[1] = new Block(new Point(x, y), wide, high);
        blocks[1].getCollisionRectangle().setFillColor(Color.WHITE);
        // Vertical lines.
        for (int i = 2; i < 6; i++) {
            x += spacex;
            blocks[i] = new Block(new Point(x, y), fat, high);
            x += fat;
            blocks[i].getCollisionRectangle().setFillColor(Color.BLACK);
        }
        x = 70 + fat;
        y = 420;
        wide = 110;
        int yspace = 38;
        // Horizontal lines.
        for (int i = 6; i < blocks.length; i++) {
            y += yspace;
            blocks[i] = new Block(new Point(x, y), wide, fat);
            blocks[i].getCollisionRectangle().setFillColor(Color.BLACK);
        }
        wide = 40;
        high = 70;
        x = 110;
        y = 349;
        blocks[10] = new Block(new Point(x, y), wide, high);
        blocks[10].getCollisionRectangle().setFillColor(Color.GRAY.darker().darker());
        blocks[10].getCollisionRectangle().setFrameColor(Color.GRAY.darker().darker());
        x = 125;
        y = 200;
        wide = 10;
        high = 150;
        blocks[11] = new Block(new Point(x, y), wide, high);
        blocks[11].getCollisionRectangle().setFillColor(Color.GRAY.darker().darker());
        blocks[11].getCollisionRectangle().setFrameColor(Color.GRAY.darker().darker());
        for (int i = 0; i < blocks.length; i++) {
            blocks[i].drawOn(d);
        }
    }

    /**
     * Draw background on a given surface.
     *
     * @param d the surface
     */
    public void drawBack(DrawSurface d) {
        int wide = GameLevel.WIDTH_GAME;
        int high = GameLevel.HEIGHT_GAME;
        d.setColor(Color.GREEN.darker().darker());
        d.fillRectangle(0, ScoreIndicator.INDICATOR_HEIGHT, wide, high);
    }
    @Override
    public void timePassed() {

    }
}
