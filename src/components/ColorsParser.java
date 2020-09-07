package components;


import java.awt.Color;

/**
 * The type Colors parser.
 */
public class ColorsParser {

    // Parse color definition and return the specified color.
    public java.awt.Color colorFromString(String s) {
        switch (s) {
            case "black":
                return Color.BLACK;
            case "blue":
                return Color.BLUE;
            case "cyan":
                return Color.CYAN;
            case "gray":
                return Color.GRAY;
            case "lightGray":
                return Color.LIGHT_GRAY;
            case "green":
                return Color.GREEN;
            case "orange":
                return Color.ORANGE;
            case "pink":
                return Color.PINK;
            case "red":
                return Color.RED;
            case "white":
                return Color.WHITE;
            case "yellow":
                return Color.YELLOW;
            default:
                return checkRGB(s);
        }
    }

    /**
     * Check rgb color.
     *
     * @param s the string color.
     * @return the matching color.
     */
    private Color checkRGB(String s) {
        String[] c = s.split(",");
        return new Color(Integer.parseInt(c[0]), Integer.parseInt(c[1]), Integer.parseInt(c[2]));
    }
}