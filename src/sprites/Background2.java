package sprites;

import biuoop.DrawSurface;
import components.GameLevel;
import graphics.Point;
import interfaces.Sprite;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Background 2.
 */
public class Background2 implements Sprite {
    private List<Sprite> sprites;

    public Background2() {
        this.sprites = new ArrayList<Sprite>();
    }

    /**
     * Draw background on a given surface.
     *
     * @param d the surface
     */
    public void drawBack(DrawSurface d) {
        int wide = GameLevel.WIDTH_GAME;
        int high = GameLevel.HEIGHT_GAME;
        d.setColor(Color.WHITE);
        d.fillRectangle(0, ScoreIndicator.INDICATOR_HEIGHT, wide, high);
    }

    /**
     * Create sun.
     */
    public void createSun() {
        Ball[] balls = new Ball[3];
        int x = 170;
        int y = 150;
        int r = 65;
        balls[0] = new Ball(new Point(x, y), r, new Color(255, 247, 164));
        balls[1] = new Ball(new Point(x, y), r - 12, new Color(255, 242, 120));
        balls[2] = new Ball(new Point(x, y), r - 24, new Color(251, 236, 30));
        for (int i = 0; i < balls.length; i++) {
            balls[i].setDrawColor(balls[i].getFillColor());
            this.sprites.add(balls[i]);
        }
    }

    /**
     * Draw lines on a given surface.
     *
     * @param d the surface
     */
    public void drawLines(DrawSurface d) {
        int x1 = 170;
        int y1 = 150;
        int x2 = 0;
        int y2 = (int) (GameLevel.HEIGHT_GAME / 3) + 60;
        d.setColor(new Color(255, 247, 164));
        for (int i = 0; x2 < 700; i++) {
            x2 += 8;
            d.drawLine(x1, y1, x2, y2);
        }
    }
    @Override
    public void drawOn(DrawSurface d) {
        drawBack(d);
        createSun();
        drawLines(d);
        for (Sprite s: sprites) {
            s.drawOn(d);
        }
    }
    @Override
    public void timePassed() {

    }
}
