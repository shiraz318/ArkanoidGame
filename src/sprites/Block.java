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
    //public static final int HIT_POINTS = 1;
    public static final int FONT = 14;
    private Rectangle block;
    private int hitPoints;
    private List<HitListener> hitListeners;
    private int k;
    private Color fillK;
    private int hitByNow;
    private int originalHitPoints;
    private List<Integer> kListColor;
    private List<Integer> kListImage;
    private Map<Integer, Color> mapKListColor;
    private Map<Integer, Image> imageMap;
    private Image image;
    private Image originalFillImage;
    private Image previousImage;

    /**
     * Create a new Block.
     *
     * @param upperLeft the upper left point
     * @param width     the width
     * @param height    the height
     */
    public Block(Point upperLeft, double width, double height) {
        this.k = 0;
        this.hitByNow = 0;
        this.kListColor = new ArrayList<Integer>();
        this.kListImage = new ArrayList<Integer>();
        this.mapKListColor = new HashMap<Integer, Color>();
        this.imageMap = new HashMap<Integer, Image>();
        this.image = null;
        this.originalFillImage = null;
        this.previousImage = null;

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
        return hitByNow;
    }

    /**
     * Sets hit by now.
     *
     * @param i the
     */
    public void setHitByNow(int i) {
        hitByNow += i;
    }

    /**
     * Reset hit by now.
     */
    public void resetHitByNow() {
        hitByNow = 0;
    }


    /**
     * Sets images.
     *
     * @param imag the imag
     */
    public void setImages(Image imag) {
        image = imag;
        originalFillImage = image;
        previousImage = image;
    }

    /**
     * Gets original image.
     *
     * @return the original image
     */
    public Image getOriginalImage() {
        return originalFillImage;
    }

    /**
     * Sets image.
     *
     * @param im the im
     */
    public void setImage(Image im) {
        image = im;
    }

    /**
     * Gets image.
     *
     * @return the image
     */
    public Image getImage() {
        return image;
    }

    /**
     * Sets hit points.
     *
     * @param hitPointsToSet the hit points to set
     */
    public void setHitPoints(int hitPointsToSet) {
        hitPoints = hitPointsToSet;
    }

    /**
     * Sets original hit points.
     *
     * @param h the h
     */
    public void setOriginalHitPoints(int h) {
        originalHitPoints = h;
    }

    /**
     * Gets original hit points.
     *
     * @return the original hit points
     */
    public int getOriginalHitPoints() {
        return originalHitPoints;
    }

    /**
     * Get the number of hit points.
     *
     * @return the number of hit points
     */
    public int getHitPoints() {
        return hitPoints;
    }

    /**
     * Gets the "collision shape" of the object.
     *
     * @return the "collision shape" of the object
     */
    public Rectangle getCollisionRectangle() {
        return block;
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
        // Set the hit points to one point less.
        if (hitPoints > 0) {
            setHitPoints(hitPoints - 1);
        }
        double dy = currentVelocity.getDy();
        double dx = currentVelocity.getDx();
        Line up = block.getUpperLine();
        Line down = block.getLowerLine();
        Line right = block.getRightLine();
        Line left = block.getLeftLine();

        // The collision is from the upper or lower side of the rectangle.
        if ((up.checkRange(collisionPoint)) || (down.checkRange(collisionPoint))) {
            dy = (-1 * dy);
        }
        // The collision is from the right or left side of the rectangle.
        if ((right.checkRange(collisionPoint)) || (left.checkRange(collisionPoint))) {
            dx = (-1 * dx);
        }
        notifyHit(hitter);
        return new Velocity(dx, dy);
    }

    // Set the color of the block.
    private boolean setColorOfBlock() {
        boolean flag = true;
        for (int i: kListColor) {
            if (i == hitPoints) {
                getCollisionRectangle().setFillColor(getMapFillKColor().get(i));
                flag = false;
            }
        }
        if (flag) block.setFillColor(getCollisionRectangle().getOriginalFillColor());
        return flag;
    }

    // Set the image of the block.
    private void setImageOfBlock(boolean flag) {
        for (int i: kListImage) {
            if (i == hitPoints) {
                setImage(getImageMap().get(i));
                flag = false;
            }
        }
        if (flag) setImage(getOriginalImage());
    }

    @Override
    public void drawOn(DrawSurface d) {
        boolean flag = setColorOfBlock();
        setImageOfBlock(flag);

        block.drawOn(d);

        int x = (int) block.getUpperLeft().getX();
        int y = (int) block.getUpperLeft().getY();
        int wide = (int) block.getWidth();
        int high = (int) block.getHeight();

        if (image != null) d.drawImage(x, y, image);

        Color frame = block.getFrameColor();
        if (frame != null) {
            d.setColor(frame);
            d.drawRectangle(x, y, wide, high);
        }
        d.setColor(block.getTextColor());
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
        hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        hitListeners.remove(hl);
    }

    /**
     * Notify the listeners that a hit accured.
     *
     * @param hitter the ball doing the hit
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(hitListeners);
        // Notify all listeners about a hit event.
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
        return hitListeners;
    }

    /**
     * Sets k list color.
     *
     * @param list the list
     */
    public void setKListColor(List<Integer> list) {
        kListColor = list;
    }

    /**
     * Gets list color.
     *
     * @return the list color
     */
    public List<Integer> getkListColor() {
        return kListColor;
    }

    /**
     * Gets list image.
     *
     * @return the list image
     */
    public List<Integer> getkListImage() {
        return kListImage;
    }

    /**
     * Sets map k fill color.
     *
     * @param map the map
     */
    public void setMapKFillColor(Map<Integer, Color> map) {
        mapKListColor = map;
    }

    /**
     * Gets map fill k color.
     *
     * @return the map fill k color
     */
    public Map<Integer, Color> getMapFillKColor() {
        return mapKListColor;
    }

    /**
     * Sets image map.
     *
     * @param map the map
     */
    public void setImageMap(Map<Integer, Image> map) {
        imageMap = map;
    }

    /**
     * Gets image map.
     *
     * @return the image map
     */
    public Map<Integer, Image> getImageMap() {
        return imageMap;
    }

    /**
     * Sets list image.
     *
     * @param i the
     */
    public void setkListImage(List<Integer> i) {
        kListImage = i;
    }
}
