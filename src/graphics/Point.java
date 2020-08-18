package graphics;
/**
 * @author Shiraz Berger.
 * Point has x and y value
 */
public class Point {
    // Fields
    private double x;
    private double y;

    /**
     * Constructor.
     *
     * @param x - x value of the point
     * @param y - y value of the point
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Calculate the distance between this point to the other point.
     *
     * @param other - other point
     * @return the distance of this point to the other point
     */
    public double distance(Point other) {
        double xDistance = (this.x - other.x) * (this.x - other.x);
        double yDistance = (this.y - other.y) * (this.y - other.y);
        double totalDistance = Math.sqrt(xDistance + yDistance);
        return totalDistance;
    }

    /**
     * Check if two points are equal.
     *
     * @param other - other point
     * @return true is the points are equal, false otherwise
     */
    public boolean equals(Point other) {
        if (this.x == other.x) {
            return this.y == other.y;
        }
        return false;
    }

    /**
     * Return the x value of the point.
     *
     * @return the x value of the point
     */
    public double getX() {
        return this.x;
    }

    /**
     * Return the y value of the point.
     *
     * @return the y value of the point
     */
    public double getY() {
        return this.y;
    }
}
