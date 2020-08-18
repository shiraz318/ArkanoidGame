package graphics;
import java.util.List;

/**
 * The type Line.
 *
 * @author Shiraz Berger. Line has start point and end point
 */
public class Line {
    /**
     * The constant SMALL.
     */
// Static variabls
    public static final int SMALL = 1;
    /**
     * The constant HIGH.
     */
    public static final int HIGH = 2;
    /**
     * The constant Y.
     */
    public static final int Y = 1;
    /**
     * The constant X.
     */
    public static final int X = 2;
    /**
     * The constant VERTICAL.
     */
    public static final double VERTICAL = (Double.MAX_VALUE);

    // Fields
    private Point start;
    private Point end;
    private double smallX;
    private double highX;
    private double smallY;
    private double highY;
    private double derivative;
    private double constant;

    /**
     * Constructor.
     *
     * @param start - start point
     * @param end   - end point
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
        this.derivative = findDerivative(this);
        this.constant = findConstant(this);
        this.smallX = findValueSize(this.start.getX(), this.end.getX(), SMALL);
        this.highX = findValueSize(this.start.getX(), this.end.getX(), HIGH);
        this.smallY = findValueSize(this.start.getY(), this.end.getY(), SMALL);
        this.highY = findValueSize(this.start.getY(), this.end.getY(), HIGH);
    }

    /**
     * Constructor.
     *
     * @param x1 - value of the x of the start point
     * @param y1 - value of the y of the start point
     * @param x2 - value of the x of the end point
     * @param y2 - value of the x of the end point
     */
    public Line(double x1, double y1, double x2, double y2) {
        this(new Point(x1, y1), new Point(x2, y2));
    }

    /**
     * Calculate the length of the line.
     *
     * @return the length of the line
     */
    public double length() {
        return this.start.distance(this.end);
    }

    /**
     * Calculate the middle point of the line.
     *
     * @return the middle point of the line
     */
    public Point middle() {
        double xMiddle = (this.end.getX() + this.start.getX()) / 2;
        double yMiddle = (this.end.getY() + this.start.getY()) / 2;
        return new Point(xMiddle, yMiddle);
    }

    /**
     * Returns the start point of the line.
     *
     * @return the start point of the line
     */
    public Point start() {
        return this.start;
    }

    /**
     * Returns the end point of the line.
     *
     * @return the end point of the line
     */
    public Point end() {
        return this.end;
    }

    /**
     * Returns the smallest x value of the line.
     *
     * @return the smallest x value of the line
     */
    public double smallX() {
        return this.smallX;
    }

    /**
     * Returns the highest x value of the line.
     *
     * @return the highest x value of the line
     */
    public double highX() {
        return this.highX;
    }

    /**
     * Returns the highest y value of the line.
     *
     * @return the highest y value of the line
     */
    public double highY() {
        return this.highY;
    }

    /**
     * Returns the smallest y value of the line.
     *
     * @return the smallest y value of the line
     */
    public double smallY() {
        return this.smallY;
    }

    /**
     * Check which value is the higher and which is smaller.
     *
     * @param a          - a value of number
     * @param b          - a value of number
     * @param whatToFind - const can be SMALL (=1) or HIGH (=2)
     * @return the smaller value if whatToFind = SMALL and the higher value otherwise
     */
    public double findValueSize(double a, double b, int whatToFind) {
        if (a > b) {
            if (whatToFind == SMALL) {
                return b;
                // whatToFind = HIGH
            } else {
                return a;
            }
            // a <= b
        } else if (whatToFind == SMALL) {
            return a;
            // whatToFind = HIGH
        } else {
            return b;
        }
    }

    /**
     * Check if two lines intersect.
     *
     * @param other - other line
     * @return true if the lines intersect, false otherwise
     */
    public boolean isIntersecting(Line other) {
        if (this.intersectionWith(other) == null) {
            return false;
        }
        return true;
    }

    /**
     * Checks if two boolean numbers are equals.
     *
     * @param a a number
     * @param b a number
     * @return true if the numbers equals, false otherwise
     */
    public boolean equalsDouble(double a, double b) {
        double minus = a - b;
        if ((minus < 0.1) && (minus > -0.1)) {
            return true;
        }
        return false;
    }

    /**
     * Calculate the intersection Point between two lines if exists.
     *
     * @param other - other line
     * @return the intersection point if the lines intersect, and null otherwise
     */
    public Point intersectionWith(Line other) {
        // The lines meet in infinite points, so they dont intersect
        if (this.equals(other)) {
            return null;
        }
        // Both lines are verticals to the x axis
        if ((equalsDouble(this.highX, this.smallX)) && (equalsDouble(other.highX, other.smallX))) {
            return this.verticalsToX(other);
        }
        // Both lines parallel to the x axis
        if ((equalsDouble(this.highY, this.smallY)) && (equalsDouble(other.highY, other.smallY))) {
            return this.parallelToX(other);
        }
        // The lines parallel to one another
        if ((equalsDouble(this.derivative, other.derivative)) && (equalsDouble(this.constant, other.constant))) {
            return null;
        }
        // other.constant = this.constant because we allready checked the case of parallel lines
        if (equalsDouble(this.derivative, other.derivative)) {
            return this.sameLines(other);
        }
        // Only this line is vertical to the x axis
        if (equalsDouble(this.highX, this.smallX)) {
            double yValue = (other.derivative * this.highX) + other.constant;
            Point intersectP = new Point(this.highX, yValue);
            if ((this.checkRange(intersectP)) && (other.checkRange(intersectP))) {
                return intersectP;
            }
            return null;
        }
        // Only other line is vertical to the x axis
        if (equalsDouble(other.highX, other.smallX)) {
            double yValue = (this.derivative * other.highX) + this.constant;
            Point intersectP = new Point(other.highX, yValue);
            if ((this.checkRange(intersectP)) && (other.checkRange(intersectP))) {
                return intersectP;
            }
            return null;
        }
        // this.derivative != other.derivative because we allready handeled this case
        double xValue = (other.constant - this.constant) / (this.derivative - other.derivative);
        double yValue = (this.derivative * xValue) + this.constant;
        Point intersectP = new Point(xValue, yValue);
        if ((this.checkRange(intersectP)) && (other.checkRange(intersectP))) {
            return intersectP;
        }
        return null;
    }

    /**
     * Check for intersection Point in case two lines are vertical to the x axis.
     *
     * @param other - other line
     * @return the intersection point if the lines intersect, and null otherwise
     */
    public Point verticalsToX(Line other) {
        // The lines are parallel
        if (!equalsDouble(this.highX, other.highX)) {
            return null;
        }
        // The lines meet at the highest y of this line and lowest y of other line
        if (equalsDouble(this.highY, other.smallY)) {
            return new Point(this.highX, this.highY);
        }
        // The lines meet at the lowest y of this line and highest y of other line
        if (equalsDouble(this.smallY, other.highY)) {
            return new Point(this.highX, this.smallY);
        }
        // The lines partly equal or one above the other
        return null;
    }

    /**
     * Check for intersection Point in case two lines are parallel to the x axis.
     *
     * @param other - other line
     * @return the intersection point if the lines intersect, and null otherwise
     */
    public Point parallelToX(Line other) {
        // The lines parallel to one another
        if (!equalsDouble(this.highY, other.highY)) {
            return null;
        }
        // The lines meet at the highest x of this line and lowest x of other line
        if (equalsDouble(this.highX, other.smallX)) {
            return new Point(this.highX, this.highY);
        }
        // The lines meet at the lowest x of this line and highest x of other line
        if (equalsDouble(this.smallX, other.highX)) {
            return new Point(this.smallX, this.highY);
        }
        // The lines partly equal or one near the other
        return null;
    }

    /**
     * Check for intersection Point in case two lines have the same equation.
     *
     * @param other - other line
     * @return the intersection point if the lines intersect, and null otherwise
     */
    public Point sameLines(Line other) {
        // Check if the lines have one intersect at the edge of each line
        if (equalsDouble(this.highY, other.smallY)) {
            if (this.derivative < 0) {
                return new Point(this.smallX, this.highY);
            }
            return new Point(this.highX, this.highY);
        }
        if (equalsDouble(this.smallY, other.highY)) {
            if (this.derivative < 0) {
                return new Point(this.highX, this.smallY);
            }
            return new Point(this.smallX, this.smallY);
        }
        // The lines either parallel or partly equals
        return null;
    }

    /**
     * Calculate the derivative of the line's equation.
     *
     * @param line - a line
     * @return the value of the derivative of the line's equation
     */
    public double findDerivative(Line line) {
        if (line.end.getX() == line.start.getX()) {
            return VERTICAL;
        }
        return (line.end.getY() - line.start.getY()) / (line.end.getX() - line.start.getX());
    }

    /**
     * Calculate the constant of the line's equation.
     *
     * @param line - a line
     * @return the value of the constant of the line
     */
    public double findConstant(Line line) {
        if (line.derivative == VERTICAL) {
            return line.start.getX();
        }
        return (line.start.getY() - (line.start.getX() * line.derivative));
    }

    /**
     * Check if a value is in the range of a line.
     *
     * @param point - a number
     * @return true if the point in the range of this line, false otherwise
     */
    public boolean checkRange(Point point) {
        if ((this.highX < point.getX()) || (this.smallX > point.getX())) {
            return false;
        }
        if ((this.highY < point.getY()) || (this.smallY > point.getY())) {
            return false;
        }
        return true;
    }

    /**
     * Check if the lines are equal.
     *
     * @param other - other point
     * @return true is the lines are equal, false otherwise
     */
    public boolean equals(Line other) {
        if (other.start.equals(this.start)) {
            return (other.end.equals(this.end));
        } else if (other.start.equals(this.end)) {
            return (other.end.equals(this.start));
        }
        return false;
    }

    /**
     * Check if this line intersect with the rectangle.
     *
     * @param rect rectangle to check intersection with
     * @return If this line does not intersect with the rectangle, return null.
     * Otherwise, return the closest intersection point to the start of the line.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        List<Point> intersectList = rect.intersectionPoints(this);
        if (intersectList.isEmpty()) {
            return null;
        }
        return this.findClosestPoint(intersectList);

    }

    /**
     * Find the closest point to the start of this line.
     *
     * @param intersectList list of intersection points
     * @return the closest point to the start of this line
     */
    public Point findClosestPoint(List<Point> intersectList) {
        Point closestPoint = intersectList.get(0);
        for (Point p : intersectList) {
            if (p.distance(this.start()) < closestPoint.distance(this.start())) {
                closestPoint = p;
            }
        }
        return closestPoint;
    }
}