package components;

import interfaces.BlockCreator;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Blocks definition reader.
 */
public class BlocksDefinitionReader {

    private int width;
    private int defaultWidth;
    private int defaultHitPoints;
    private int height;
    private int defaultHeight;
    private int k;
    private int hitPoints;
    private String symbol;
    private String defaultSymbol;
    private String fill;
    private String defaultFill;
    private String fillK;
    private String stroke;
    private String defaultStroke;
    private boolean flag;
    private boolean color;
    private boolean image;
    private List<Integer> kListColor;
    private List<Integer> savekListColor;
    private List<Integer> savekListImage;
    private List<Integer> kListImage;
    private List<String> fillKColorList;
    private List<String> fillKImageList;

    public BlocksDefinitionReader() {
        this.width = -1;
        this.defaultWidth = -1;
        this.defaultHitPoints = -1;
        this.height = -1;
        this.defaultHeight = -1;
        this.hitPoints = -1;
        this.k = -1;
        this.flag = true;
        this.color = false;
        this.image = false;
        this.kListColor = new ArrayList<Integer>();
        this.savekListColor = new ArrayList<Integer>();
        this.savekListImage = new ArrayList<Integer>();
        this.kListImage = new ArrayList<Integer>();
        this.fillKColorList = new ArrayList<String>();
        this.fillKImageList = new ArrayList<String>();
    }

    /**
     * Sets width.
     *
     * @param w the w
     */
    public void setWidth(int w) {
        width = w;
    }

    /**
     * Sets height.
     *
     * @param h the h
     */
    public void setHeight(int h) {
        height = h;
    }

    /**
     * Gets width.
     *
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets height.
     *
     * @return the height
     */
    public int getHeight() {
        return height;
    }


    /**
     * From reader blocks from symbols factory.
     *
     * @param reader the reader
     * @return the blocks from symbols factory
     */
    public static BlocksFromSymbolsFactory fromReader(java.io.Reader reader) {
        Map<String, Integer> spacerWidths = new HashMap<String, Integer>();
        Map<String, BlockCreator> blockCreators = new HashMap<String, BlockCreator>();
        String line;
        BlocksDefinitionReader bdr = new BlocksDefinitionReader();
        BufferedReader br = new BufferedReader(reader);

        try {
            // Read line by line and process each line information.
            while ((line = br.readLine()) != null) {
                bdr.setText(line, spacerWidths, blockCreators);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return new BlocksFromSymbolsFactory(spacerWidths, blockCreators);
    }

    // Create a new block.
    private void createBlock(Map<String, BlockCreator> blockCreators) {
        BlockCreator blockCreator = new BlocksCreate(width, height, hitPoints, stroke, fill, k);
        ((BlocksCreate) blockCreator).setOther(fillK, kListColor, fillKColorList, kListImage, fillKImageList);
        blockCreators.put(symbol, blockCreator);
        savekListColor = kListColor;
        savekListImage = kListImage;
        kListImage = new ArrayList<>();
        kListColor = new ArrayList<>();
        fillKImageList = new ArrayList<>();
        fillKColorList = new ArrayList<>();
    }

    /**
     * Process information of a given line.
     *
     * @param line          the line
     * @param spacerWidths  the spacer widths
     * @param blockCreators the block creators
     */
    public void setText(String line, Map<String, Integer> spacerWidths, Map<String, BlockCreator> blockCreators) {
        String[] info = line.split(" ");
        switch (info[0]) {
            case "sdef":
                setSpacerWidth(info, spacerWidths);
                if (!checkValidity()) System.exit(0);
                break;
                // Default values.
            case "default":
                for (int i = 0; i < info.length - 1; i++) {
                    String[] def = info[i + 1].split(":");
                    setValue(def);
                    checkDefault(def[0]);
                }
                break;
                // Block definition.
            case "bdef":
                String[] part = new String[2];
                setDefaultValues();
                for (int i = 1; i < info.length; i++) {
                    part = info[i].split(":");
                    setValue(part);
                }
                createBlock(blockCreators);
                break;
            default:
                break;
        }
    }

    /**
     * Sets default values.
     */
    public void setDefaultValues() {
        hitPoints = defaultHitPoints;
        width = defaultWidth;
        height = defaultHeight;
        fill = defaultFill;
        stroke = defaultStroke;
        symbol = defaultSymbol;
    }

    /**
     * Check which default value is given and set it's value.
     *
     * @param defaultValue the default value
     */
    public void checkDefault(String defaultValue) {
        switch (defaultValue) {
            case "hit_points":
                defaultHitPoints = hitPoints;
                break;
            case "width":
                defaultWidth = width;
                break;
            case "height":
                defaultHeight = height;
                break;
            case "symbol":
                defaultSymbol = symbol;
                break;
            case "fill":
                defaultFill = fill;
                break;
            case "stroke":
                defaultStroke = stroke;
                break;
            default:
                break;
        }

    }

    // Check validation of a fill color and image.
    private boolean checkFillValidation(boolean valid) {
        if (fill == null) {
            valid = false;
            for (int i: savekListColor) {
                if (i == hitPoints) {
                    valid = true;
                    break;
                }
            }
            for (int i: savekListImage) {
                if (i == hitPoints) {
                    valid = true;
                    break;
                }
            }
            if (!valid) System.out.println("invalid fill");
        }
        return valid;
    }

    /**
     * Check validation.
     *
     * @return the boolean
     */
    public boolean checkValidity() {
        boolean valid = true;
        if (width < 0) {
            System.out.println("invalid width");
            valid = false;
        }
        if (height < 0) {
            System.out.println("invalid height");
            valid = false;
        }
        if (symbol == null || symbol.length() != 1) {
            System.out.println("invalid symbol");
            valid = false;
        }
        if (hitPoints < 0) {
            System.out.println("invalid hit_points");
            valid = false;
        }
        valid = checkFillValidation(valid);
        return valid;
    }

    // Set the fill color or image.
    private void setFillK(String[] def) {
        if (def[0].startsWith("fill")) {
            String[] s = def[0].split("-");
            k = Integer.parseInt(s[1]);
            if (def[1].startsWith("image")) {
                image = true;
                kListImage.add(k);
                fillKImageList.add(def[1]);

            } else {
                color = true;
                kListColor.add(k);
                fillK = def[1];
                fillKColorList.add(fillK);
            }
        }
    }

    /**
     * Sets value by a given definition.
     *
     * @param def the def
     */
    public void setValue(String[] def) {
        switch (def[0]) {
            case "width":
                width = Integer.parseInt(def[1]);
                break;
            case "height":
                height = Integer.parseInt(def[1]);
                break;
            case "symbol":
                symbol = def[1];
                break;
            case "hit_points":
                hitPoints = Integer.parseInt(def[1]);
                break;
            case "fill":
                fill = def[1];
                flag = false;
                break;
            case "stroke":
                stroke = def[1];
                break;
            default:
                //set fill-k
                setFillK(def);
                break;

        }
    }

    /**
     * Sets spacer width.
     *
     * @param info         the info
     * @param spacerWidths the spacer widths
     */
    public void setSpacerWidth(String[] info, Map<String, Integer> spacerWidths) {
        String[] data  = new String[info.length - 1];
        for (int i = 0; i < info.length - 1; i++) {
            data[i] = info[i + 1].split(":")[1];
        }
        for (int i = 0; i < data.length; i++) {
            spacerWidths.put(data[i], Integer.parseInt(data[++i]));
        }
    }
}

