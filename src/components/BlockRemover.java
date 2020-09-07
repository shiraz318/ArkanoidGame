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


    // Change the fill of the block if needed.
    private void changeFillIfNeeded(Block beingHit) {
        boolean notNeedToChangeFill = true;
        // Check if a color or an image needs to replaced into a different color.
        for (int k : beingHit.getkListColor()) {
            if (beingHit.getHitPoints() == k) {
                beingHit.getCollisionRectangle().applyKColor(beingHit.getMapFillKColor().get(k));
                notNeedToChangeFill = false;
                beingHit.setImage(null);
            }
        }
        // If the fill does not need to be changed, set the fill to the original fill of the block.
        if (notNeedToChangeFill) {
            beingHit.getCollisionRectangle().setFillColor(beingHit.getCollisionRectangle().getOriginalFillColor());
        }
        // Check if a color or an image needs to replaced into a different image
        for (int k : beingHit.getkListImage()) {
            if (beingHit.getHitPoints() == k) {
                beingHit.setImage(beingHit.getImageMap().get(k));
                notNeedToChangeFill = false;
            }
        }
        // If the image does not need to be changed, set the image to the original image of the block.
        if (notNeedToChangeFill) {
            beingHit.setImage(beingHit.getOriginalImage());
        }
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        beingHit.setHitByNow(1);
        // The block got hit enough times to disappear.
        if (beingHit.getHitPoints() == 0) {
            remainingBlocks.decrease(1);
            beingHit.removeHitListener(this);
            beingHit.removeFromGame(gameLevel);
        } else {
            changeFillIfNeeded(beingHit);
        }
    }
}