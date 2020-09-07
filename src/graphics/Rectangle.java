package graphics;

import biuoop.DrawSurface;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Rectangle.
 *
 * @author Shiraz Berger.
 */
public class Rectangle {
    private Point upperLeft;
    private double width;
    private double height;
    private Point upperRight;
    private Point lowerLeft;
    private Point lowerRight;
    private Line upperLine;
    private Line lowerLine;
    private Line rightLine;
    private Line leftLine;
    private Color fillColor;
    private Color frameColor;
    private Color textColor;
    private Color fillK;
    private Color originalFillColor;
    private Image originalFillImage;
    private Color previousColor;


    /**
     * Create a new rectangle with location and width/height.
     *
     * @param upperLeft the upper left point
     * @param width     the width
     * @param height    the height
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.height = height;
        this.width = width;
        this.upperRight = new Point(this.upperLeft.getX() + this.width, this.upperLeft.getY());
        this.lowerLeft = new Point(this.upperLeft.getX(), this.upperLeft.getY() + this.height);
        this.lowerRight = new Point(this.upperLeft.getX() + this.width, this.upperLeft.getY() + this.height);
        this.upperLine = new Line(upperLeft, upperRight);
        this.lowerLine = new Line(lowerLeft, lowerRight);
        this.rightLine = new Line(upperRight, lowerRight);
        this.leftLine = new Line(upperLeft, lowerLeft);
        this.fillColor = null;
        this.frameColor = null;
        this.textColor = Color.WHITE;

    }

    /**
     * Gets frame color.
     *
     * @return the frame color
     */
    public Color getFrameColor() {
        return frameColor;
    }

    /**
     * Sets previous color.
     *
     * @param c the c
     */
    public void setPreviousColor(Color c) {
        previousColor = c;
    }

    /**
     * Gets previous color.
     *
     * @return the previous color
     */
    public java.awt.Color getPreviousColor() {
        return previousColor;
    }

    /**
     * Sets text color.
     *
     * @param color the color
     */
    public void setTextColor(Color color) {
        textColor = color;
    }

    /**
     * Gets text color.
     *
     * @return the text color
     */
    public Color getTextColor() {
        return textColor;
    }

    /**
     * Sets fill k.
     *
     * @param c the c
     */
    public void setFillK(Color c) {
        fillK = c;
    }

    /**
     * Sets color of the rectangle.
     *
     * @param colorToChange the color to change
     */
    public void setColors(Color colorToChange) {
        fillColor = colorToChange;
        originalFillColor = fillColor;
        previousColor = fillColor;
    }

    /**
     * Gets original fill color.
     *
     * @return the original fill color
     */
    public Color getOriginalFillColor() {
        return originalFillColor;
    }

    /**
     * Sets fill color.
     *
     * @param c the c
     */
    public void setFillColor(Color c) {
        fillColor = c;
    }

    /**
     * Gets k color.
     *
     * @return the k color
     */
    public Color getKColor() {
        return fillK;
    }

    /**
     * Apply k color.
     *
     * @param c the c
     */
    public void applyKColor(Color c) {
        fillColor = c;
    }

    /**
     * Sets frame color.
     *
     * @param colorToChange the color to change
     */
    public void setFrameColor(Color colorToChange) {
        frameColor = colorToChange;
    }

    /**
     * Gets color of the rectangle.
     *
     * @return the color
     */
    public Color getFillColor() {
        return fillColor;
    }

    /**
     * Check for intersection points between this rectangle and a line.
     *
     * @param line the line
     * @return a (possibly empty) List of intersection points with the specified line.
     */
    public List<Point> intersectionPoints(Line line) {
        Line[] lines = setLinesArray();
        List<Point> intersectArr =  new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (lines[i].intersectionWith(line) != null) {
                intersectArr.add(lines[i].intersectionWith(line));
            }
        }
        return intersectArr;
    }

    /**
     * Set array of the lines that this rectangle is build from.
     *
     * @return array of the lines that this rectangle is build from
     */
    public Line[] setLinesArray() {
        Line[] linesArr = new Line[4];
        // The upper side of the rectangle.
        linesArr[0] = upperLine;
        // The lower side of the rectangle.
        linesArr[1] = lowerLine;
        // The right side of the rectangle.
        linesArr[2] = rightLine;
        // The left side of thr rectangle.
        linesArr[3] = leftLine;
        return linesArr;
    }

    /**
     * Gets width of the rectangle.
     *
     * @return the width
     */
    public double getWidth() {
        return width;
    }

    /**
     * Gets height of the rectangle.
     *
     * @return the height
     */
    public double getHeight() {
        return height;
    }

    /**
     * Gets upper left point of the rectangle.
     *
     * @return the upper left point
     */
    public Point getUpperLeft() {
        return upperLeft;
    }

    /**
     * Gets upper right point of the rectangle.
     *
     * @return the upper right point
     */
    public Point getUpperRight() {
        return upperRight;
    }

    /**
     * Gets lower right point of the rectangle.
     *
     * @return the lower right point
     */
    public Point getLowerRight() {
        return lowerRight;
    }

    /**
     * Gets lower left point of the rectangle.
     *
     * @return the lower left point
     */
    public Point getLowerLeft() {
        return lowerLeft;
    }

    /**
     * Gets upper line of the rectangle.
     *
     * @return the upper line
     */
    public Line getUpperLine() {
        return upperLine;
    }

    /**
     * Gets lower line of the rectangle.
     *
     * @return the lower line
     */
    public Line getLowerLine() {
        return lowerLine;
    }

    /**
     * Gets left line of the rectangle.
     *
     * @return the left line
     */
    public Line getLeftLine() {
        return leftLine;
    }

    /**
     * Gets right line of the rectangle.
     *
     * @return the right line
     */
    public Line getRightLine() {
        return rightLine;
    }

    /**
     * Check if point is on the rectangle.
     *
     * @param point the point
     * @return true if the point is on the rectangle, and false otherwise
     */
    public boolean checkIfPointIsOn(Point point) {
        Line up = upperLine;
        Line down = lowerLine;
        Line right = rightLine;
        Line left = leftLine;
        return (up.checkRange(point) || down.checkRange(point) || right.checkRange(point) || left.checkRange(point));
    }

    /**
     * Draw on a given surface.
     *
     * @param d the surface
     */
    public void drawOn(DrawSurface d) {

        int x = (int) getUpperLeft().getX();
        int y = (int) getUpperLeft().getY();
        int wide = (int) getWidth();
        int high = (int) getHeight();
        if (fillColor != null) {
            d.setColor(fillColor);
        }
        d.fillRectangle(x, y, wide, high);
    }

    /**
     * Sets upper left.
     *
     * @param p the p
     */
    public void setUpperLeft(Point p) {
        upperLeft = p;
    }
}
