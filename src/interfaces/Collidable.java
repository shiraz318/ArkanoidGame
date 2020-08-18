package interfaces;

import components.Velocity;
import graphics.Point;
import graphics.Rectangle;
import sprites.Ball;

/**
 * The interface Collidable - an object that you can collide with.
 */
public interface Collidable {
    /**
     * @return the "collision shape" of the object.
     */
    Rectangle getCollisionRectangle();

    /**
     * Notify the object that we collided with it at collisionPoint with
     * a given velocity.
     *
     * @param collisionPoint the point of collision
     * @param currentVelocity the velocity we hit the object with
     * @param hitter the ball doing the hit
     * @return the new velocity expected after the hit (based on
     * the force the object inflicted on us).
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}