package graphics;
/**
 * @author Shiraz Berger.
 * Point has x and y value
 */
public class Point {
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
        double xDistance = (x - other.x) * (x - other.x);
        double yDistance = (y - other.y) * (y - other.y);
        return Math.sqrt(xDistance + yDistance);
    }

    /**
     * Check if two points are equal.
     *
     * @param other - other point
     * @return true is the points are equal, false otherwise
     */
    public boolean equals(Point other) {
        return (x == other.x) && y == other.y;
    }

    /**
     * Return the x value of the point.
     *
     * @return the x value of the point
     */
    public double getX() {
        return x;
    }

    /**
     * Return the y value of the point.
     *
     * @return the y value of the point
     */
    public double getY() {
        return y;
    }
}
