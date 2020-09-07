package components;

import graphics.Point;

/**
 * @author Shiraz Berger.
 * Velocity specifies the change in position on the `x` and the `y` axes
 */
public class Velocity {
    private double dx;
    private double dy;

    /**
     * Constructor.
     *
     * @param dx - the change in position on the `x` axis
     * @param dy - the change in position on the `y` axis
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Return the velocity given angle and speed.
     *
     * @param angle - the angle of the velocity
     * @param speed - the speed of the velocity
     * @return the velocity given angle and speed
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        angle -= 90;
        angle *= Math.PI / (180);
        double dx = Math.cos(angle) * speed;
        double dy = Math.sin(angle) * speed;
        return new Velocity(dx, dy);
    }

    /**
     * Returns the change in position on the `x` axis of the ball.
     *
     * @return the change in position on the `x` axis of the ball
     */
    public double getDx() {
        return dx;
    }

    /**
     * Returns the change in position on the `y` axis of the ball.
     *
     * @return the change in position on the `y` axis of the ball
     */
    public double getDy() {
        return dy;
    }

    /**
     * Take a point with position (x,y) and return a new point with position (x+dx, y+dy).
     *
     * @param p - point
     * @return point with position (x+dx, y+dy)
     */
    public Point applyToPoint(Point p) {
        double x = p.getX() + dx;
        double y = p.getY() + dy;
        return new Point(x, y);
    }
}
