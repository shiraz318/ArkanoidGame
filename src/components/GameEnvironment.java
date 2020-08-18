package components;
import graphics.Line;
import graphics.Point;
import interfaces.Collidable;

import java.util.ArrayList;
import java.util.List;

/**
 * The type GameLevel environment.
 * @author Shiraz Berger
 */
public class GameEnvironment {
    // Fields
    private List<Collidable> collidables;
    /**
     * Create a new GameLevel environment.
     */
    public GameEnvironment() {
        this.collidables = new ArrayList<Collidable>();
    }

    /**
     * Add the given collidable to the environment.
     *
     * @param c the collidable to add
     */
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }

    /**
     * Gets collidables list.
     *
     * @return the collidables list
     */
    public List<Collidable> getCollidables() {
        return this.collidables;
    }

    /**
     * Gets closest collision information.
     *
     * @param trajectory the trajectory of the ball
     * @return If this object will not collide with any of the collidables
     * in this collection, return null. Else, return the information
     * about the closest collision that is going to occur.
     * Assume an object moving from line.start() to line.end().
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        boolean isCollide = false;
        List<Point> collidePoints = new ArrayList<Point>();
        List<Collidable> colliders = new ArrayList<Collidable>(this.collidables);
        for (Collidable c : colliders) {
            Point collidePoint = trajectory.closestIntersectionToStartOfLine(c.getCollisionRectangle());
            // There is a collision
            if (collidePoint != null) {
                isCollide = true;
                collidePoints.add(collidePoint);
            }
        }
        // There is no collision
        if (!isCollide) {
            return null;
        }
        Point closestPoint = trajectory.findClosestPoint(collidePoints);
        Collidable collideObject = this.collidables.get(0);
        // Find the object that collide with trajectory
        for (Collidable c : colliders) {
            if (c.getCollisionRectangle().checkIfPointIsOn(closestPoint)) {
                collideObject = c;
                break;
            }
        }
        return new CollisionInfo(closestPoint, collideObject);
    }
}