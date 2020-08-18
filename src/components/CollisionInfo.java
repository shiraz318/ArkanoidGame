package components;


import graphics.Point;
import interfaces.Collidable;

/**
 * The type Collision info holds information of the collision.
 * @author Shiraz Berger
 */
public class CollisionInfo {
    // Fields
    private Point collisionPoint;
    private Collidable collisionObject;

    /**
     * Create a new Collision info.
     *
     * @param p the point of collision
     * @param collide the coliidable object we collided with
     */
    public CollisionInfo(Point p, Collidable collide) {
        this.collisionPoint = p;
        this.collisionObject = collide;
    }

    /**
     * Gets the point of collision.
     *
     * @return collisionPoint
     */
    public Point collisionPoint() {
        return this.collisionPoint;
    }

    /**
     * Gets the collidable object involved in the collision.
     *
     * @return collisionObject
     */
    public Collidable collisionObject() {
        return this.collisionObject;
    }
}