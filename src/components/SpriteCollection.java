package components;

import biuoop.DrawSurface;
import interfaces.Sprite;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Sprite collection.
 * @author Shiraz Berger
 */
public class SpriteCollection {
    // Fields
    private List<Sprite> spriteCollection;

    /**
     * Create a new Sprite collection.
     */
    public SpriteCollection() {
        this.spriteCollection = new ArrayList<Sprite>();
    }

    /**
     * Gets the sprite collection.
     *
     * @return the sprite collection
     */
    public List<Sprite> getSpriteCollection() {
        return this.spriteCollection;
    }

    /**
     * Add a given sprite.
     *
     * @param s the sprite
     */
    public void addSprite(Sprite s) {
        this.spriteCollection.add(s);
    }

    /**
     * Notify all sprites that time passed.
     */
    public void notifyAllTimePassed() {
        List<Sprite> spriters = new ArrayList<Sprite>(this.spriteCollection);
        for (Sprite s : spriters) {
            s.timePassed();
        }
    }

    /**
     * Draw all sprites on a given surface.
     *
     * @param d the surface
     */
    public void drawAllOn(DrawSurface d) {
        List<Sprite> spriters = new ArrayList<Sprite>(this.spriteCollection);
        for (Sprite s : spriters) {
            s.drawOn(d);
        }
    }
}