package sprites;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import components.AnimationRunner;
import interfaces.Menu;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Menu animation.
 *
 * @param <T> the type parameter
 */
public class MenuAnimation<T> implements Menu<T> {
    private List<String> keysSelection = new ArrayList<String>();
    private List<String> keysSubMenu = new ArrayList<String>();
    private List<String> messagesSelection = new ArrayList<String>();
    private List<String> messagesSubMenu = new ArrayList<String>();
    private List<T> returnVals = new ArrayList<T>();
    private List<Menu<T>> subMenus = new ArrayList<Menu<T>>();
    private boolean stop;
    private T status;
    private KeyboardSensor sensor;
    private boolean isAlreadyPressed = true;
    private AnimationRunner an;

    /**
     * Instantiates a new Menu animation.
     *
     * @param sensor the sensor
     * @param an     the an
     */
    public MenuAnimation(KeyboardSensor sensor, AnimationRunner an) {
        this.status = null;
        this.stop = false;
        this.sensor = sensor;
        this.an = an;

    }
    @Override
    public void addSelection(String key, String message, T returnVal) {
        this.keysSelection.add(key);
        this.messagesSelection.add(message);
        this.returnVals.add(returnVal);
    }

    @Override
    public T getStatus() {
        this.stop = false;
        return this.status;

    }

    @Override
    public void addSubMenu(String key, String message, Menu<T> subMenu) {
        this.keysSubMenu.add(key);
        this.messagesSubMenu.add(message);
        this.subMenus.add(subMenu);
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        int y = 0;
        for (int color = 100; y <= 600; color += 3) {
            d.setColor(new Color(80, 220, color));
            d.fillRectangle(0, y, 800, y + 20);
            y += 20;
        }
        d.setColor(Color.WHITE);
        d.drawText(100, 150, "Arkanoid", 60);
        int xLocation = 120;
        int yLocation = 200;

        for (String s: this.messagesSubMenu) {
            d.setColor(Color.WHITE);
            d.drawCircle(xLocation - 15, yLocation, 10);
            d.drawCircle(xLocation - 15, yLocation, 9);
            d.drawCircle(xLocation - 15, yLocation, 8);
            d.drawText(xLocation + 10, yLocation + 10, s, 35);
            yLocation += 60;
        }
        for (String s: this.messagesSelection) {
            d.setColor(Color.WHITE);
            d.drawCircle(xLocation - 15, yLocation, 10);
            d.drawCircle(xLocation - 15, yLocation, 9);
            d.drawCircle(xLocation - 15, yLocation, 8);
            d.drawText(xLocation + 10, yLocation + 10, s, 35);
            yLocation += 60;
        }

        d.fillRectangle(710, 0, 8, 600);
        d.fillRectangle(690, 0, 15, 600);
        d.fillRectangle(0, 520, 800, 15);
        d.fillRectangle(0, 540, 800, 8);
        checkIfPressed();
    }

    /**
     * Check if pressed.
     */
    public void checkIfPressed() {
        int index = 0;
        //go through the keys of selection
        for (String k: this.keysSelection) {
            if (this.sensor.isPressed(k)) {
                if (this.isAlreadyPressed) {
                    return;
                } else {
                    stop = true;
                    index = this.keysSelection.indexOf(k);
                    this.status = this.returnVals.get(index);
                    isAlreadyPressed = true;
                    break;
                }
            } else {
                this.isAlreadyPressed = false;
            }
        }
        //go through the keys of menus
        for (String k: this.keysSubMenu) {
            if (this.sensor.isPressed(k)) {
                if (this.isAlreadyPressed) {
                    return;
                } else {
                    stop = true;
                    index = this.keysSubMenu.indexOf(k);
                    Menu<T> m = this.subMenus.get(index);
                    an.run(m);
                    this.status = m.getStatus();
                    isAlreadyPressed = true;
                    break;
                }
            } else {
                this.isAlreadyPressed = false;
            }
        }
    }
    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
