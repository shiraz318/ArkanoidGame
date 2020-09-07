package sprites;

import biuoop.DrawSurface;
import components.CollisionInfo;
import components.GameEnvironment;
import components.GameLevel;
import components.Velocity;
import graphics.Line;
import graphics.Point;
import interfaces.Sprite;

import java.awt.Color;

/**
 * The type Ball.
 *
 * @author Shiraz Berger
 */
public class Ball implements Sprite {
    private Point center;
    private int radius;
    private Color color;
    private Velocity v;
    private int width;
    private int height;
    private Point startRange;
    private Line trajectory;
    private GameEnvironment gameEnvironment;
    private Color fillColor;
    private Color drawColor;

    /**
     * Constructor.
     *
     * @param center - center point
     * @param r      - radius
     * @param color  - color
     */
    public Ball(Point center, int r, Color color) {
        this.center = center;
        this.radius = r;
        this.color = color;
        this.v = new Velocity(0, 0);
        this.width = 0;
        this.height = 0;
        this.startRange = new Point(0, 0);
        this.trajectory = new Line(startRange, startRange);
        this.gameEnvironment = new GameEnvironment();
        this.drawColor = Color.BLACK;
        this.fillColor = this.color;
    }

    /**
     * Constructor.
     *
     * @param x     - x value of the center point
     * @param y     - y value of the center point
     * @param r     - radius
     * @param color - color
     */
    public Ball(int x, int y, int r, Color color) {
        this(new Point(x, y), r, color);
    }

    /**
     * Set the start, width and height fields.
     *
     * @param startPoint  - start point of the range
     * @param widthRange  - width of the range
     * @param heightRange - height of the range
     */
    public void setRange(Point startPoint, int widthRange, int heightRange) {
        width = widthRange;
        height = heightRange;
        startRange = startPoint;
    }

    /**
     * Sets trajectory.
     */
    public void setTrajectory() {
        Point start = new Point(getX(), getY());
        int dx = (int) getVelocity().getDx();
        int dy = (int) getVelocity().getDy();
        Point end = new Point(getX() + dx, getY() + dy);
        trajectory = new Line(start, end);
    }

    /**
     * Change the color of the ball.
     *
     * @param colorToChange the wanted color
     */
    public void changeColor(Color colorToChange) {
        color = colorToChange;
    }

    /**
     * Gets the x value of the center point.
     *
     * @return the x value of the center point
     */
    public int getX() {
        return (int) center.getX();
    }

    /**
     * Gets the y value of the center point.
     *
     * @return the y value of the center point
     */
    public int getY() {
        return (int) center.getY();
    }

    /**
     * Gets the radius of the ball.
     *
     * @return the radius of the ball
     */
    public int getSize() {
        return radius;
    }

    /**
     * Gets the color of the ball.
     *
     * @return the color of the ball
     */
    public Color getFillColor() {
        return fillColor;
    }

    /**
     * Gets the start point of the ball range.
     *
     * @return the start point of the ball range
     */
    public Point getStartRange() {
        return startRange;
    }

    /**
     * Gets the width of the ball range.
     *
     * @return the width of the ball range
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height of the ball range.
     *
     * @return the height of the ball range
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets game environment.
     *
     * @param gameE the game environment
     */
    public void setGame(GameEnvironment gameE) {
        gameEnvironment = gameE;
    }

    /**
     * Set the fill color of the ball.
     *
     * @param c the color
     */
    public void setFillColor(Color c) {
        fillColor = c;
    }

    /**
     * Set the frame color of the ball.
     *
     * @param c the color
     */
    public void setDrawColor(Color c) {
        drawColor = c;
    }

    /**
     * Get the frame color of the ball.
     *
     * @return the frame color of the ball
     */
    public Color getDrawColor() {
        return drawColor;
    }
    /**
     * Draw the ball on the given DrawSurface.
     *
     * @param surface - DrawSurface
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(getFillColor());
        surface.fillCircle(getX(), getY(), getSize());
        surface.setColor(getDrawColor());
        surface.drawCircle(getX(), getY(), getSize());

    }

    /**
     * Notify the ball that time is pass.
     */
    public void timePassed() {
        moveOneStep();
    }

    /**
     * Set the velocity field.
     *
     * @param velocity - velocity
     */
    public void setVelocity(Velocity velocity) {
        v = velocity;
    }

    /**
     * Set the velocity field.
     *
     * @param dx - the change in position on the `x` axis
     * @param dy - the change in position on the `y` axis
     */
    public void setVelocity(double dx, double dy) {
        v = new Velocity(dx, dy);
    }

    /**
     * Gets the velocity of the ball.
     *
     * @return the velocity of the ball
     */
    public Velocity getVelocity() {
        return v;
    }

    private void setCenter(double x, double y, CollisionInfo collide, Point collidePoint) {
        // The collision is on the upper line of the collidable.
        if (collide.collisionObject().getCollisionRectangle().getUpperLine().checkRange(collidePoint)) {
            y = collidePoint.getY() - getSize() - 1;
        }
        // The collision is on the lower line of the collidable.
        if (collide.collisionObject().getCollisionRectangle().getLowerLine().checkRange(collidePoint)) {
            y = collidePoint.getY() + getSize() + 1;
        }
        // The collision is on the right line of the collidable.
        if (collide.collisionObject().getCollisionRectangle().getRightLine().checkRange(collidePoint)) {
            x = collidePoint.getX() + getSize() + 1;
        }
        // The collision is on the left line of the collidable.
        if (collide.collisionObject().getCollisionRectangle().getLeftLine().checkRange(collidePoint)) {
            x = collidePoint.getX() - getSize() - 1;
        }
        // Move the ball to almost the hit point.
        center = new Point(x, y);
    }

    /**
     * Move the ball one step.
     */
    public void moveOneStep() {
        double y = getY();
        double x = getX();
        // Get the information of the closest collidable that collide with trajectory.
        CollisionInfo collide = gameEnvironment.getClosestCollision(trajectory);
        // There is no collision.
        if (collide == null) {
            center = trajectory.end();
        } else {
            // Get the closest point.
            Point collidePoint = collide.collisionPoint();
            // Update the center of the ball.
            setCenter(x, y, collide, collidePoint);
            // Update velocity.
            setVelocity(collide.collisionObject().hit(this, collidePoint, getVelocity()));
        }
        // Update trajectory.
        setTrajectory();
    }

    /**
     * Add the ball to game.
     *
     * @param g the game
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    /**
     * Remove the ball from gameLevel.
     *
     * @param gameLevel the gameLevel
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeSprite(this);
    }
}