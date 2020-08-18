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
    // Static variables
    public static final int PADDLE = 1;
    //public static final int MOVE_PADDLE = 10;
    public static final int NUMBER_OF_REGIONS = 5;
    // Fields
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
        double x = this.paddle.getUpperLeft().getX();
        double y = this.paddle.getUpperLeft().getY();
        Point newUpperLeft;
        if ((x - speed) <= (GameLevel.FRAME_WIDTH)) {
            newUpperLeft = new Point(GameLevel.FRAME_WIDTH, y);
        } else {
            newUpperLeft = new Point(x - speed, y);
        }
        Color color = this.paddle.getFillColor();
        this.paddle = new Rectangle(newUpperLeft, this.paddle.getWidth(), this.paddle.getHeight());
        this.paddle.setFillColor(color);
    }

    /**
     * Move the paddle to the right.
     */
    public void moveRight() {
        double wide = this.paddle.getWidth();
        double x = this.paddle.getUpperLeft().getX();
        double y = this.paddle.getUpperLeft().getY();
        Point newUpperLeft;
        if ((x + speed + wide) >= (GameLevel.WIDTH_GAME - GameLevel.FRAME_WIDTH)) {
            newUpperLeft = new Point(GameLevel.WIDTH_GAME - GameLevel.FRAME_WIDTH - wide, y);
        } else {
            newUpperLeft = new Point(x + speed, y);
        }
        Color color = this.paddle.getFillColor();
        this.paddle = new Rectangle(newUpperLeft, this.paddle.getWidth(), this.paddle.getHeight());
        this.paddle.setFillColor(color);
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
        this.speed = speedToSet;
    }

    /**
     * Draw the paddle on given surface.
     *
     * @param d the surface
     */
    public void drawOn(DrawSurface d) {
        this.paddle.drawOn(d);
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
        Velocity newVelocity = currentVelocity;
        Point currentPoint = new Point(x, y);
        Line tempTrajectory = new Line(currentPoint, new Point(x + dx, y + dy));
        int hitRegion = 0;
        // Check in which region the ball hit
        for (int i = 0; i < regions.length; i++) {
            if (regions[i].checkIfPointIsOn(collisionPoint)) {
                hitRegion = i;
                break;
            }
        }
        switch (hitRegion) {
            //move 300 degrees
            case 0:
                newVelocity = Velocity.fromAngleAndSpeed(300, tempTrajectory.length());
                break;
            //move 330 degrees
            case 1:
                newVelocity = Velocity.fromAngleAndSpeed(330, tempTrajectory.length());
                break;
            //move verticly up
            case 2:
                newVelocity = new Velocity(dx, -dy);
                break;
            //move 30 degrees
            case 3:
                newVelocity = Velocity.fromAngleAndSpeed(30, tempTrajectory.length());
                break;
            //move 60 degrees
            case 4:
                newVelocity = Velocity.fromAngleAndSpeed(60, tempTrajectory.length());
                break;
            default:
                break;
        }
        return newVelocity;
    }

    /**
     * Set array of rectangle, each is region of the paddle.
     *
     * @return the array of regions
     */
    public Rectangle[] setRegionArray() {
        Rectangle[] regions = new Rectangle[NUMBER_OF_REGIONS];
        // Divide the width of the paddle in he number of regions
        double widthOfRegion = (this.paddle.getWidth()) / NUMBER_OF_REGIONS;
        double x = this.paddle.getUpperLeft().getX();
        double y = this.paddle.getUpperLeft().getY();
        double height = this.paddle.getHeight();
        double newX;
        Point newUpperLeft;
        // Each rectangle has the same height and width but a different upper left point
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