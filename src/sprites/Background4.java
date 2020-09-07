package sprites;

import biuoop.DrawSurface;
import components.GameLevel;
import graphics.Point;
import interfaces.Sprite;

import java.awt.Color;

/**
 * The type Background 4.
 */
public class Background4 implements Sprite {
    /**
     * Draw background on a given surface.
     *
     * @param d the surface
     */
    @Override
    public void drawOn(DrawSurface d) {
        drawBack(d);
        drawCloudes(d);
    }

    /**
     * Draw cloudes.
     *
     * @param d the d
     */
    public void drawCloudes(DrawSurface d) {
        int x1 = 120;
        int y1 = 380;
        int x2 = 90;
        int y2 = 600;
        d.setColor(Color.WHITE);
        // Draw rain.
        for (int i = 0; i < 10; i++) {
            x1 = x1 + 11;
            x2 = x2 + 11;
            d.drawLine(x1, y1, x2, y2);
        }
        x1 = 620;
        y1 = 470;
        x2 = 600;
        y2 = 600;
        d.setColor(Color.WHITE);
        // Draw rain.
        for (int i = 0; i < 10; i++) {
            x1 = x1 + 11;
            x2 = x2 + 11;
            d.drawLine(x1, y1, x2, y2);
        }
        // Set the clouds.
        Ball[] balls = setClouds();
        for (int i = 0; i < balls.length; i++) {
            balls[i].setDrawColor(balls[i].getFillColor());
            balls[i].drawOn(d);
        }
    }

    // Set the clouds to be drawn.
    private Ball[] setClouds() {
        Ball[] balls = new Ball[10];
        // Left cloud.
        balls[0] = new Ball(new Point(130, 380), 25, new Color(153, 153, 153).brighter());
        balls[2] = new Ball(new Point(150, 407), 30, new Color(153, 153, 153).brighter());
        balls[4] = new Ball(new Point(170, 375), 32, new Color(204, 204, 204));
        balls[6] = new Ball(new Point(210, 380), 34, Color.GRAY.brighter());
        balls[8] = new Ball(new Point(190, 410), 25, Color.GRAY.brighter());
        // Right cloud.
        balls[1] = new Ball(new Point(630, 470), 25, new Color(153, 153, 153).brighter());
        balls[3] = new Ball(new Point(650, 497), 30, new Color(153, 153, 153).brighter());
        balls[5] = new Ball(new Point(670, 465), 32, new Color(204, 204, 204));
        balls[7] = new Ball(new Point(710, 470), 34, Color.GRAY.brighter());
        balls[9] = new Ball(new Point(690, 500), 25, Color.GRAY.brighter());
        return balls;
    }

    /**
     * Draw background on a given Surface.
     *
     * @param d the Surface
     */
    public void drawBack(DrawSurface d) {
        int wide = GameLevel.WIDTH_GAME;
        int high = GameLevel.HEIGHT_GAME;
        d.setColor(new Color(51, 153, 255));
        d.fillRectangle(0, ScoreIndicator.INDICATOR_HEIGHT, wide, high);
    }

    @Override
    public void timePassed() {

    }
}
