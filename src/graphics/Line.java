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
        return start.distance(end);
    }

    /**
     * Calculate the middle point of the line.
     *
     * @return the middle point of the line
     */
    public Point middle() {
        double xMiddle = (end.getX() + start.getX()) / 2;
        double yMiddle = (end.getY() + start.getY()) / 2;
        return new Point(xMiddle, yMiddle);
    }

    /**
     * Returns the start point of the line.
     *
     * @return the start point of the line
     */
    public Point start() {
        return start;
    }

    /**
     * Returns the end point of the line.
     *
     * @return the end point of the line
     */
    public Point end() {
        return end;
    }

    /**
     * Returns the smallest x value of the line.
     *
     * @return the smallest x value of the line
     */
    public double smallX() {
        return smallX;
    }

    /**
     * Returns the highest x value of the line.
     *
     * @return the highest x value of the line
     */
    public double highX() {
        return highX;
    }

    /**
     * Returns the highest y value of the line.
     *
     * @return the highest y value of the line
     */
    public double highY() {
        return highY;
    }

    /**
     * Returns the smallest y value of the line.
     *
     * @return the smallest y value of the line
     */
    public double smallY() {
        return smallY;
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
            return (whatToFind == SMALL) ? b : a;
            // a <= b
        } else return (whatToFind == SMALL) ? a : b;
    }

    /**
     * Check if two lines intersect.
     *
     * @param other - other line
     * @return true if the lines intersect, false otherwise
     */
    public boolean isIntersecting(Line other) {
        return intersectionWith(other) != null;
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
        return ((minus < 0.1) && (minus > -0.1));
    }


    /**
     * Calculate the intersection Point between two lines if exists.
     *
     * @param other - other line
     * @return the intersection point if the lines intersect, and null otherwise
     */
    public Point intersectionWith(Line other) {
        // The lines meet in infinite points, so they do not intersect.
        if (equals(other)) {
            return null;
        }
        // Both lines are verticals to the x axis.
        if ((equalsDouble(highX, smallX)) && (equalsDouble(other.highX, other.smallX))) {
            return verticalsToX(other);
        }
        // Both lines parallel to the x axis.
        if ((equalsDouble(highY, smallY)) && (equalsDouble(other.highY, other.smallY))) {
            return parallelToX(other);
        }
        // The lines parallel to one another.
        if ((equalsDouble(derivative, other.derivative)) && (equalsDouble(constant, other.constant))) {
            return null;
        }
        // Other.constant = this.constant because we already checked the case of parallel lines.
        if (equalsDouble(derivative, other.derivative)) {
            return sameLines(other);
        }
        // Only this line is vertical to the x axis.
        if (equalsDouble(highX, smallX)) {
            return oneLineVerticalToX(other.derivative, highX, other.constant, other);
        }
        // Only other line is vertical to the x axis.
        if (equalsDouble(other.highX, other.smallX)) {
            return oneLineVerticalToX(derivative, other.highX, constant, other);
        }
        // this.derivative != other.derivative because we already handled this case.
        return linesGeneralCase(other);

    }

    // Get intersection point of two lines in the general case.
    private Point linesGeneralCase(Line other) {
        double xValue = (other.constant - constant) / (derivative - other.derivative);
        double yValue = (derivative * xValue) + constant;
        Point intersectP = new Point(xValue, yValue);
        return ((checkRange(intersectP)) && (other.checkRange(intersectP))) ? intersectP : null;
    }

    // Get intersection point of two lines when one line is vertical to the x axis.
    private Point oneLineVerticalToX(double derivative1, double highX1, double constant1, Line other) {
        double yValue = (derivative1 * highX1) + constant1;
        Point intersectP = new Point(highX1, yValue);
        return  ((checkRange(intersectP)) && (other.checkRange(intersectP))) ? intersectP: null;
    }

    /**
     * Check for intersection Point in case two lines are vertical to the x axis.
     *
     * @param other - other line
     * @return the intersection point if the lines intersect, and null otherwise
     */
    public Point verticalsToX(Line other) {
        return verticalOrParallelToX(other, true);
    }

    /**
     * Check for intersection Point in case two lines are parallel to the x axis.
     *
     * @param other - other line
     * @return the intersection point if the lines intersect, and null otherwise
     */
    public Point parallelToX(Line other) {
       return verticalOrParallelToX(other, false);
    }

    // Get intersection point of two lines when one line is vertical or parallel to the x axis.
    private Point verticalOrParallelToX(Line other, boolean isVerticalToX) {
        double thisParallelCheck = isVerticalToX ? highX : highY;
        double otherParallelCheck = isVerticalToX ? other.highX : other.highY;

        double thisMeetAtUpperPointCheck = isVerticalToX? highY : highX;
        double otherMeetAtLowerPointCheck = isVerticalToX? other.smallY : other.smallX;

        double thisMeetAtLowestPointCheck = isVerticalToX ? smallY : smallX;
        double otherMeetAtUpperPointCheck = isVerticalToX ? other.highY : other.highX;

        double xtoReturn = isVerticalToX ? highX : smallX;
        double yToReturn = isVerticalToX ? smallY : highY;

        // The lines parallel to one another.
        if (!equalsDouble(thisParallelCheck, otherParallelCheck)) return null;
        // The meeting point is at the highest of this line and lowest of other line.
        if (equalsDouble(thisMeetAtUpperPointCheck, otherMeetAtLowerPointCheck)) return new Point(highX, highY);
        // The meeting point is at the lowest of this line and highest of other line.
        if (equalsDouble(thisMeetAtLowestPointCheck, otherMeetAtUpperPointCheck)) return new Point(xtoReturn, yToReturn);
        // The lines partly equal or one above the other
        return null;
    }

    /**
     * Check for intersection Point in case two lines have the same equation.
     *
     * @param other - other line
     * @return the intersection point if the lines intersect, and null otherwise
     */
    public Point sameLines(Line other) {
        // Check if the lines have one intersect at the edge of each line.
        if (equalsDouble(highY, other.smallY)) {
            return (derivative < 0) ? new Point(smallX, highY): new Point(highX, highY);
        }
        if (equalsDouble(smallY, other.highY)) {
            return (derivative < 0) ? new Point(this.highX, this.smallY) : new Point(this.smallX, this.smallY);
        }
        // The lines either parallel or partly equals.
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
        if ((highX < point.getX()) || (smallX > point.getX())) {
            return false;
        }
        return (!(highY < point.getY())) && (!(smallY > point.getY()));
    }

    /**
     * Check if the lines are equal.
     *
     * @param other - other point
     * @return true is the lines are equal, false otherwise
     */
    public boolean equals(Line other) {
        if (other.start.equals(start)) {
            return (other.end.equals(end));
        } else if (other.start.equals(end)) {
            return (other.end.equals(start));
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
        return (intersectList.isEmpty()) ? null : findClosestPoint(intersectList);
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
            if (p.distance(start()) < closestPoint.distance(start())) {
                closestPoint = p;
            }
        }
        return closestPoint;
    }
}