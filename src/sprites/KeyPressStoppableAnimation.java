package sprites;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import interfaces.Animation;

/**
 * The type Key press stoppable animation.
 */
public class KeyPressStoppableAnimation implements Animation {
    // fields
    private KeyboardSensor sensor;
    private String key;
    private Animation animation;
    private boolean stop = false;
    private boolean isAlreadyPressed;

    /**
     * Instantiates a new Key press stoppable animation.
     *
     * @param sensor    the keyboard sensor
     * @param key       the key that makes the animation stop
     * @param animation the animation
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.sensor = sensor;
        this.animation = animation;
        this.key = key;
        this.isAlreadyPressed = true;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        animation.doOneFrame(d);
        if (this.sensor.isPressed(this.key)) {
            if (this.isAlreadyPressed) {
                return;
            } else {
                stop = true;
            }
        } else {
            this.isAlreadyPressed = false;
        }
    }

    @Override
    public boolean shouldStop() {
        if (this.stop) {
            this.stop = false;
            this.isAlreadyPressed = true;
            return !this.stop;
        }
        return this.stop;
    }
}