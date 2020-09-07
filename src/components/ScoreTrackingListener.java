package components;

import interfaces.HitListener;
import sprites.Ball;
import sprites.Block;

/**
 * The type Score tracking listener.
 */
public class ScoreTrackingListener implements HitListener {
    public static final int HIT_SCORE = 5;
    public static final int DESTROYING_SCORE = 10;

    private Counter currentScore;

    /**
     * Construct a new Score tracking listener.
     *
     * @param scoreCounter the score counter
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        currentScore.increase(HIT_SCORE);
       if (beingHit.getHitPoints() == 0) {
           currentScore.increase(DESTROYING_SCORE);
           beingHit.removeHitListener(this);
       }
    }
}