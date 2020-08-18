package sprites;
import biuoop.DrawSurface;
import components.GameLevel;
import components.Velocity;
import graphics.Line;
import graphics.Point;
import graphics.Rectangle;
import interfaces.Collidable;
import interfaces.HitListener;
import interfaces.HitNotifier;
import interfaces.Sprite;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Block.
 *
 * @author Shiraz Berger.
 */
public class Block implements Collidable, Sprite, HitNotifier {
    /**
     * The constant FONT.
     */
// Static variables
    //public static final int HIT_POINTS = 1;
    public static final int FONT = 14;
    //Fields
    private Rectangle block;
    private int hitPoints;
    private List<HitListener> hitListeners;
    private int k = 0;
    private Color fillK;
    private int hitByNow = 0;
    private int originalHitPoints;
    private List<Integer> kListColor = new ArrayList<Integer>();
    private List<Integer> kListImage = new ArrayList<Integer>();
    private Map<Integer, Color> mapKListColor = new HashMap<Integer, Color>();
    private Map<Integer, Image> imageMap = new HashMap<Integer, Image>();
    private Image image = null;
    private Image originalFillImage = null;
    private Image previousImage = null;

    /**
     * Create a new Block.
     *
     * @param upperLeft the upper left point
     * @param width     the width
     * @param height    the height
     */
    public Block(Point upperLeft, double width, double height) {
        this.block = new Rectangle(upperLeft, width, height);
        this.hitPoints = 1;
        this.originalHitPoints = this.hitPoints;
        this.hitListeners = new ArrayList<HitListener>();
    }

    /**
     * Gets hit by now.
     *
     * @return the hit by now
     */
    public int getHitByNow() {
        return this.hitByNow;
    }

    /**
     * Sets hit by now.
     *
     * @param i the
     */
    public void setHitByNow(int i) {
        this.hitByNow += i;
    }

    /**
     * Reset hit by now.
     */
    public void resetHitByNow() {
        this.hitByNow = 0;
    }


    /**
     * Sets images.
     *
     * @param imag the imag
     */
    public void setImages(Image imag) {
        this.image = imag;
        this.originalFillImage = image;
        this.previousImage = image;
    }

    /**
     * Gets original image.
     *
     * @return the original image
     */
    public Image getOriginalImage() {
        return this.originalFillImage;
    }

    /**
     * Sets image.
     *
     * @param im the im
     */
    public void setImage(Image im) {
        this.image = im;
    }

    /**
     * Gets image.
     *
     * @return the image
     */
    public Image getImage() {
        return this.image;
    }

    /**
     * Sets hit points.
     *
     * @param hitPointsToSet the hit points to set
     */
    public void setHitPoints(int hitPointsToSet) {
        this.hitPoints = hitPointsToSet;
    }

    /**
     * Sets original hit points.
     *
     * @param h the h
     */
    public void setOriginalHitPoints(int h) {
        this.originalHitPoints = h;
    }

    /**
     * Gets original hit points.
     *
     * @return the original hit points
     */
    public int getOriginalHitPoints() {
        return this.originalHitPoints;
    }

    /**
     * Get the number of hit points.
     *
     * @return the number of hit points
     */
    public int getHitPoints() {
        return this.hitPoints;
    }

    /**
     * Gets the "collision shape" of the object.
     *
     * @return the "collision shape" of the object
     */
    public Rectangle getCollisionRectangle() {
        return this.block;
    }
    /**
     * Notify the block that we collided with it at collisionPoint with a given velocity.
     *
     * @param collisionPoint the point of collision
     * @param currentVelocity the velocity we collided with
     * @param hitter the ball doing the hit
     * @return the new velocity expected after the hit
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        // Set the hit points to one point less
        if (this.hitPoints > 0) {
            this.setHitPoints(this.hitPoints - 1);
        }
        double dy = currentVelocity.getDy();
        double dx = currentVelocity.getDx();
        Line up = this.block.getUpperLine();
        Line down = this.block.getLowerLine();
        Line right = this.block.getRightLine();
        Line left = this.block.getLeftLine();

        // The collision is from the upper or lower side of the rectangle
        if ((up.checkRange(collisionPoint)) || (down.checkRange(collisionPoint))) {
            dy = (-1 * dy);
        }
        // The collision is from the right or left side of the rectangle
        if ((right.checkRange(collisionPoint)) || (left.checkRange(collisionPoint))) {
            dx = (-1 * dx);
        }
        this.notifyHit(hitter);
        return new Velocity(dx, dy);
    }

    @Override
    public void drawOn(DrawSurface d) {
        boolean flag = true;

        for (int i: kListColor) {
            if (i == hitPoints) {
                this.getCollisionRectangle().setFillColor(getMapFillKColor().get(i));
                flag = false;
            }
        }
        if (flag) {
            this.block.setFillColor(getCollisionRectangle().getOriginalFillColor());
        }
        for (int i: kListImage) {
            if (i == hitPoints) {
                this.setImage(getImageMap().get(i));
                flag = false;
            }
        }
        if (flag) {
            setImage(getOriginalImage());
        }
        this.block.drawOn(d);
        int x = (int) this.block.getUpperLeft().getX();
        int y = (int) this.block.getUpperLeft().getY();
        int wide = (int) this.block.getWidth();
        int high = (int) this.block.getHeight();
        if (this.image != null) {
            d.drawImage(x, y, this.image);
        }

        Color frame = this.block.getFrameColor();
        if (frame != null) {
            d.setColor(frame);
            d.drawRectangle(x, y, wide, high);
        }
        d.setColor(this.block.getTextColor());
    }

    @Override
    public void timePassed() {
    }

    /**
     * Add the block to the game.
     *
     * @param g the game
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * Remove an object from the gameLevel.
     *
     * @param gameLevel the gameLevel
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeCollidable(this);
        gameLevel.removeSprite(this);
    }

    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * Notify the listeners that a hit accured.
     *
     * @param hitter the ball doing the hit
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * Gets hit listeners.
     *
     * @return the hit listeners
     */
    public List<HitListener> getHitListeners() {
        return this.hitListeners;
    }

    /**
     * Sets k list color.
     *
     * @param list the list
     */
    public void setKListColor(List<Integer> list) {
        this.kListColor = list;
    }

    /**
     * Gets list color.
     *
     * @return the list color
     */
    public List<Integer> getkListColor() {
        return this.kListColor;
    }

    /**
     * Gets list image.
     *
     * @return the list image
     */
    public List<Integer> getkListImage() {
        return this.kListImage;
    }

    /**
     * Sets map k fill color.
     *
     * @param map the map
     */
    public void setMapKFillColor(Map<Integer, Color> map) {
        this.mapKListColor = map;
    }

    /**
     * Gets map fill k color.
     *
     * @return the map fill k color
     */
    public Map<Integer, Color> getMapFillKColor() {
        return this.mapKListColor;
    }

    /**
     * Sets image map.
     *
     * @param map the map
     */
    public void setImageMap(Map<Integer, Image> map) {
        this.imageMap = map;
    }

    /**
     * Gets image map.
     *
     * @return the image map
     */
    public Map<Integer, Image> getImageMap() {
        return this.imageMap;
    }

    /**
     * Sets list image.
     *
     * @param i the
     */
    public void setkListImage(List<Integer> i) {
        this.kListImage = i;
    }
}
