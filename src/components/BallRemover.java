package components;

import interfaces.HitListener;
import sprites.Ball;
import sprites.Block;

/**
 * The type Ball remover.
 * remove the ball from the gameLevel
 */
public class BallRemover implements HitListener {
    // Fields
    private GameLevel gameLevel;
    private Counter remainingBalls;

    /**
     * Construct a new Ball remover.
     *
     * @param gameLevel the gameLevel
     * @param removedBalls the number of removed balls
     */
    public BallRemover(GameLevel gameLevel, Counter removedBalls) {
        this.gameLevel = gameLevel;
        this.remainingBalls = removedBalls;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        remainingBalls.decrease(1);
        hitter.removeFromGame(gameLevel);
    }
}
