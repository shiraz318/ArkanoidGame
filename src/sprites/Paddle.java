package sprites;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import components.GameLevel;
import components.Velocity;
import graphics.Line;
import graphics.Point;
import graphics.Rectangle;
import interfaces.Collidable;
import interfaces.Sprite;

import java.awt.Color;

/**
 * The type Paddle.
 * @author Shiraz Berger
 */
public class Paddle implements Sprite, Collidable {
    public static final int PADDLE = 1;
    //public static final int MOVE_PADDLE = 10;
    public static final int NUMBER_OF_REGIONS = 5;
    private KeyboardSensor keyboard;
    private Rectangle paddle;
    private int speed;

    /**
     * Create a new Paddle.
     *
     * @param upperLeft the upper left point
     * @param width the width
     * @param height the height
     * @param sensor the KeyboardSensor
     */
    public Paddle(Point upperLeft, int width, int height, KeyboardSensor sensor) {
        this.paddle = new Rectangle(upperLeft, width, height);
        this.keyboard = sensor;
//        this.speed = 10;
    }

    /**
     * Move the paddle to the left.
     */
    public void moveLeft() {
        double x = paddle.getUpperLeft().getX();
        double y = paddle.getUpperLeft().getY();
        Point newUpperLeft;
        if ((x - speed) <= (GameLevel.FRAME_WIDTH)) {
            newUpperLeft = new Point(GameLevel.FRAME_WIDTH, y);
        } else {
            newUpperLeft = new Point(x - speed, y);
        }
        createPaddle(newUpperLeft);
    }

    // Create a paddle.
    private void createPaddle(Point newUpperLeft) {
        Color color = paddle.getFillColor();
        paddle = new Rectangle(newUpperLeft, paddle.getWidth(), paddle.getHeight());
        paddle.setFillColor(color);
    }

    /**
     * Move the paddle to the right.
     */
    public void moveRight() {
        double wide = paddle.getWidth();
        double x = paddle.getUpperLeft().getX();
        double y = paddle.getUpperLeft().getY();
        Point newUpperLeft;
        if ((x + speed + wide) >= (GameLevel.WIDTH_GAME - GameLevel.FRAME_WIDTH)) {
            newUpperLeft = new Point(GameLevel.WIDTH_GAME - GameLevel.FRAME_WIDTH - wide, y);
        } else {
            newUpperLeft = new Point(x + speed, y);
        }
        createPaddle(newUpperLeft);
    }
    /**
     * Notify the paddle that time is pass.
     */
    public void timePassed() {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        } else if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
    }

    /**
     * Set the speed of the paddle.
     *
     * @param speedToSet the speed to change
     */
    public void setSpeed(int speedToSet) {
        speed = speedToSet;
    }

    /**
     * Draw the paddle on given surface.
     *
     * @param d the surface
     */
    public void drawOn(DrawSurface d) {
        paddle.drawOn(d);
    }

    private Velocity getVelocityByHitRegion(int hitRegion, Line tempTrajectory, double dx, double dy) {
        switch (hitRegion) {
            // Move 300 degrees.
            case 0:
                return Velocity.fromAngleAndSpeed(300, tempTrajectory.length());
            // Move 330 degrees.
            case 1:
                return Velocity.fromAngleAndSpeed(330, tempTrajectory.length());
            // Move vertically up.
            case 2:
                return new Velocity(dx, -dy);
            // Move 30 degrees.
            case 3:
                return Velocity.fromAngleAndSpeed(30, tempTrajectory.length());
            // Move 60 degrees.
            case 4:
                return Velocity.fromAngleAndSpeed(60, tempTrajectory.length());
            default:
                return new Velocity(dx, dy);
        }
    }


    /**
     * Gets the collision rectangle.
     *
     * @return the collision rectangle
     */
    public Rectangle getCollisionRectangle() {
        return this.paddle;
    }
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        Rectangle[] regions = setRegionArray();
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
        double x = collisionPoint.getX();
        double y = collisionPoint.getY();
        Point currentPoint = new Point(x, y);
        Line tempTrajectory = new Line(currentPoint, new Point(x + dx, y + dy));
        int hitRegion = 0;
        // Check in which region the ball hit.
        for (int i = 0; i < regions.length; i++) {
            if (regions[i].checkIfPointIsOn(collisionPoint)) {
                hitRegion = i;
                break;
            }
        }
        return getVelocityByHitRegion(hitRegion, tempTrajectory, dx, dy);
    }

    /**
     * Set array of rectangle, each is region of the paddle.
     *
     * @return the array of regions
     */
    public Rectangle[] setRegionArray() {
        Rectangle[] regions = new Rectangle[NUMBER_OF_REGIONS];
        // Divide the width of the paddle in he number of regions.
        double widthOfRegion = (paddle.getWidth()) / NUMBER_OF_REGIONS;
        double x = paddle.getUpperLeft().getX();
        double y = paddle.getUpperLeft().getY();
        double height = paddle.getHeight();
        double newX;
        Point newUpperLeft;
        // Each rectangle has the same height and width but a different upper left point.
        for (int i = 0; i < regions.length; i++) {
            newX = x + (i * widthOfRegion);
            newUpperLeft = new Point(newX, y);
            regions[i] = new Rectangle(newUpperLeft, widthOfRegion, height);
        }
        return regions;
    }

    /**
     * Add the paddle to the game.
     *
     * @param g the game
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }
}