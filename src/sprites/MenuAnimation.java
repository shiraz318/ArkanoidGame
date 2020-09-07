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
    private List<String> keysSelection;
    private List<String> keysSubMenu;
    private List<String> messagesSelection;
    private List<String> messagesSubMenu;
    private List<T> returnVals;
    private List<Menu<T>> subMenus;
    private boolean stop;
    private T status;
    private KeyboardSensor sensor;
    private boolean isAlreadyPressed;
    private AnimationRunner an;

    /**
     * Instantiates a new Menu animation.
     *
     * @param sensor the sensor
     * @param an     the an
     */
    public MenuAnimation(KeyboardSensor sensor, AnimationRunner an) {

        this.keysSelection = new ArrayList<String>();
        this.keysSubMenu = new ArrayList<String>();
        this.messagesSelection = new ArrayList<String>();
        this.messagesSubMenu = new ArrayList<String>();
        this.returnVals = new ArrayList<T>();
        this.subMenus = new ArrayList<Menu<T>>();
        this.isAlreadyPressed = true;

        this.status = null;
        this.stop = false;
        this.sensor = sensor;
        this.an = an;

    }
    @Override
    public void addSelection(String key, String message, T returnVal) {
        keysSelection.add(key);
        messagesSelection.add(message);
        returnVals.add(returnVal);
    }

    @Override
    public T getStatus() {
        stop = false;
        return status;

    }

    @Override
    public void addSubMenu(String key, String message, Menu<T> subMenu) {
        keysSubMenu.add(key);
        messagesSubMenu.add(message);
        subMenus.add(subMenu);
    }

    // Draw the background.
    private void drawBackground(DrawSurface d) {
        int y = 0;
        for (int color = 100; y <= d.getHeight(); color += 3) {
            d.setColor(new Color(80, 220, color));
            d.fillRectangle(0, y, d.getWidth(), y + 20);
            y += 20;
        }
    }

    // Draw one line of the menu.
    private int drawOneTextLine(DrawSurface d, int xLocation, int yLocation, String text) {
        d.setColor(Color.WHITE);
        d.drawCircle(xLocation - 15, yLocation, 10);
        d.drawCircle(xLocation - 15, yLocation, 9);
        d.drawCircle(xLocation - 15, yLocation, 8);
        d.drawText(xLocation + 10, yLocation + 10, text, 35);
        yLocation += 60;
        return  yLocation;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        // Draw a changed color background.
        drawBackground(d);

        d.setColor(Color.WHITE);
        d.drawText(100, 150, "Arkanoid", 60);
        int xLocation = 120;
        int yLocation = 200;

        for (String s: messagesSubMenu) {
            yLocation = drawOneTextLine(d, xLocation, yLocation, s);
        }
        for (String s: messagesSelection) {
            yLocation = drawOneTextLine(d, xLocation, yLocation, s);
        }

        d.fillRectangle(710, 0, 8, d.getHeight());
        d.fillRectangle(690, 0, 15, d.getHeight());
        d.fillRectangle(0, 520, d.getWidth(), 15);
        d.fillRectangle(0, 540, d.getWidth(), 8);
        checkIfPressed();
    }

    // Check if a key of the selection symbols is pressed.
    private void checkInSelection() {
        int index;
        for (String k: keysSelection) {
            if (sensor.isPressed(k)) {
                if (isAlreadyPressed) return;
                else {
                    stop = true;
                    index = keysSelection.indexOf(k);
                    status = returnVals.get(index);
                    isAlreadyPressed = true;
                    break;
                }
            } else {
                isAlreadyPressed = false;
            }
        }
    }

    // Check if a key of the menu symbols is pressed.
    private void checkInMenu() {
        int index;
        for (String k: keysSubMenu) {
            if (sensor.isPressed(k)) {
                if (isAlreadyPressed) return;
                else {
                    stop = true;
                    index = keysSubMenu.indexOf(k);
                    Menu<T> m = subMenus.get(index);
                    an.run(m);
                    status = m.getStatus();
                    isAlreadyPressed = true;
                    break;
                }
            } else {
                isAlreadyPressed = false;
            }
        }
    }

    /**
     * Check if pressed.
     */
    public void checkIfPressed() {
        // Go through the keys of selection.
        checkInSelection();
        // Go through the keys of menus.
        checkInMenu();
    }
    @Override
    public boolean shouldStop() {
        return stop;
    }
}
