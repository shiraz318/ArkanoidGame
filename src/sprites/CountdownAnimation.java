package sprites;

import biuoop.DrawSurface;
import components.SpriteCollection;
import interfaces.Animation;

import java.awt.Color;

/**
 * The type Countdown animation.
 * display the given gameScreen,
 * for numOfSeconds seconds, and on top of them it will show
 * a countdown from countFrom back to 1
 */
public class CountdownAnimation implements Animation {
    private double numOfSeconds;
    private int countFrom;
    private SpriteCollection gameScreen;
    private boolean stop;
    private double framesPerNumber;
    private double appearances;

    /**
     * Instantiates a new Countdown animation.
     *
     * @param numOfSeconds the num of seconds
     * @param countFrom    the number to count from
     * @param gameScreen   the game screen
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.countFrom = countFrom;
        this.gameScreen = gameScreen;
        this.numOfSeconds = numOfSeconds;
        this.framesPerNumber = 1000 * (this.numOfSeconds / this.countFrom);
        this.appearances = this.framesPerNumber;
    }
    @Override
    public void doOneFrame(DrawSurface d) {
        stop = false;
        gameScreen.drawAllOn(d);
        d.setColor(Color.GRAY.brighter());
        d.drawText((d.getWidth() / 2) - 15, d.getHeight() / 2, Integer.toString(countFrom), 70);
        framesPerNumber = framesPerNumber - (double) (1000 / 60);
        if ((framesPerNumber <= 0) && (countFrom != 0)) {
            countFrom = countFrom - 1;
            framesPerNumber = appearances;
        }
        if (countFrom == 0) stop = true;
    }
    @Override
    public boolean shouldStop() {
        return stop;
    }
}