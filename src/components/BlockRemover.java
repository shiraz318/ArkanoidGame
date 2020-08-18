package components;

import interfaces.HitListener;
import sprites.Ball;
import sprites.Block;

/**
 * The type Block remover.
 * BlockRemover is in charge of removing blocks from the gameLevel, as well as keeping count
 * of the number of blocks that remain.
 */
public class BlockRemover implements HitListener {
    // Fields
    private GameLevel gameLevel;
    private Counter remainingBlocks;

    /**
     * Construct a new Block remover.
     *
     * @param gameLevel the gameLevel
     * @param removedBlocks the number of removed blocks
     */
    public BlockRemover(GameLevel gameLevel, Counter removedBlocks) {
        this.gameLevel = gameLevel;
        this.remainingBlocks = removedBlocks;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        beingHit.setHitByNow(1);
        boolean flag = true;
        //check if a color or an image needs to replaced into a different color
        for (int k : beingHit.getkListColor()) {
            if (beingHit.getHitPoints() == k) {
                beingHit.getCollisionRectangle().applyKColor(beingHit.getMapFillKColor().get(k));
                flag = false;
                beingHit.setImage(null);
            }
        }
        if (flag) {
            beingHit.getCollisionRectangle().setFillColor(beingHit.getCollisionRectangle().getOriginalFillColor());
        }
        //check if a color or an image needs to replaced into a different image
        for (int k : beingHit.getkListImage()) {
            if (beingHit.getHitPoints() == k) {
                beingHit.setImage(beingHit.getImageMap().get(k));
                flag = false;
            }
        }

        if (flag) {
            beingHit.setImage(beingHit.getOriginalImage());
        }

        if (beingHit.getHitPoints() == 0) {
            this.remainingBlocks.decrease(1);
            beingHit.removeHitListener(this);
            beingHit.removeFromGame(this.gameLevel);
            return;
        }
    }
}