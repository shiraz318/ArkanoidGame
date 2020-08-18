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

    private int width = -1;
    private int defaultWidth = -1;
    private int defaultHitPoints = -1;
    private int height = -1;
    private int defaultHeight = -1;
    private String symbol = null;
    private String defaultSymbol = null;
    private int hitPoints = -1;
    private String fill = null;
    private String defaultFill = null;
    private String fillK = null;
    private String stroke = null;
    private String defaultStroke = null;
    private int k = -1;
    private boolean flag = true;
    private boolean color = false;
    private boolean image = false;
    private List<Integer> kListColor = new ArrayList<Integer>();
    private List<Integer> savekListColor = new ArrayList<Integer>();
    private List<Integer> savekListImage = new ArrayList<Integer>();
    private List<Integer> kListImage = new ArrayList<Integer>();
    private List<String> fillKColorList = new ArrayList<String>();
    private List<String> fillKImageList = new ArrayList<String>();

    /**
     * Sets width.
     *
     * @param w the w
     */
    public void setWidth(int w) {
        this.width = w;
    }

    /**
     * Sets height.
     *
     * @param h the h
     */
    public void setHeight(int h) {
        this.height = h;
    }

    /**
     * Gets width.
     *
     * @return the width
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Gets height.
     *
     * @return the height
     */
    public int getHeight() {
        return this.height;
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

        BlocksDefinitionReader bdr = new BlocksDefinitionReader();
        BufferedReader br = null;
        try {
            br = new BufferedReader(reader);
            String line;
            while ((line = br.readLine()) != null) {
                bdr.setText(line, spacerWidths, blockCreators);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return new BlocksFromSymbolsFactory(spacerWidths, blockCreators);
    }

    /**
     * Sets text.
     *
     * @param line          the line
     * @param spacerWidths  the spacer widths
     * @param blockCreators the block creators
     */
    public void setText(String line, Map<String, Integer> spacerWidths, Map<String, BlockCreator> blockCreators) {
        String[] info = line.split(" ");
        switch (info[0]) {
            case "#":
                break;
            case "sdef":
                setSpacerWidth(info, spacerWidths);
                if (!checkValidity()) {
                    try {
                        throw new Exception();
                    } catch (Exception e) {
                        System.exit(0);
                    }
                }
                break;
            case "default":
                for (int i = 0; i < info.length - 1; i++) {
                    String[] def = info[i + 1].split(":");
                    setValue(def);
                    checkDefault(def[0]);
                }
                break;
            case "bdef":
                String[] part = new String[2];
                setDefaultValues();
                for (int i = 1; i < info.length; i++) {
                    part = info[i].split(":");
                    setValue(part);
                }
                BlockCreator blockCreator = new BlocksCreate(this.width, this.height, this.hitPoints, this.stroke,
                        this.fill, this.k);
                ((BlocksCreate) blockCreator).setOther(this.fillK, this.kListColor, this.fillKColorList,
                        this.kListImage, this.fillKImageList);
                blockCreators.put(this.symbol, blockCreator);
                this.savekListColor = this.kListColor;
                this.savekListImage = this.kListImage;
                this.kListImage = new ArrayList<>();
                this.kListColor = new ArrayList<>();
                this.fillKImageList = new ArrayList<>();
                this.fillKColorList = new ArrayList<>();
                break;
            default:
                break;
        }
    }

    /**
     * Sets default values.
     */
    public void setDefaultValues() {
        this.hitPoints = this.defaultHitPoints;
        this.width = this.defaultWidth;
        this.height = this.defaultHeight;
        this.fill = this.defaultFill;
        this.stroke = this.defaultStroke;
        this.symbol = this.defaultSymbol;
    }

    /**
     * Check default.
     *
     * @param defaultValue the default value
     */
    public void checkDefault(String defaultValue) {
        switch (defaultValue) {
            case "hit_points":
                this.defaultHitPoints = this.hitPoints;
                break;
            case "width":
                this.defaultWidth = this.width;
                break;
            case "height":
                this.defaultHeight = this.height;
                break;
            case "symbol":
                this.defaultSymbol = this.symbol;
                break;
            case "fill":
                this.defaultFill = this.fill;
                break;
            case "stroke":
                this.defaultStroke = this.stroke;
                break;
            default:
                break;
        }

    }

    /**
     * Check validity boolean.
     *
     * @return the boolean
     */
    public boolean checkValidity() {
        boolean valid = true;
        if (width < 0) {
            System.out.println("invalid wide");
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
        if (fill == null) {
            valid = false;
            for (int i: this.savekListColor) {
                if (i == hitPoints) {
                    valid = true;
                }
            }
            for (int i: this.savekListImage) {
                if (i == hitPoints) {
                    valid = true;
                }
            }
            if (!valid) {
                System.out.println("invalid fill");
            }
        }
        return valid;
    }

    /**
     * Sets value.
     *
     * @param def the def
     */
    public void setValue(String[] def) {
        switch (def[0]) {
            case "width":
                this.width = Integer.parseInt(def[1]);
                break;
            case "height":
                this.height = Integer.parseInt(def[1]);
                break;
            case "symbol":
                this.symbol = def[1];
                break;
            case "hit_points":
                this.hitPoints = Integer.parseInt(def[1]);
                break;
            case "fill":
                this.fill = def[1];
                flag = false;
                break;
            case "stroke":
                this.stroke = def[1];
                break;
            default:
                //set fill-k
                if (def[0].startsWith("fill")) {
                    String[] s = def[0].split("-");
                    this.k = Integer.parseInt(s[1]);
                    if (def[1].startsWith("image")) {
                        this.image = true;
                        this.kListImage.add(this.k);
                        this.fillKImageList.add(def[1]);

                    } else {
                        this.color = true;
                        this.kListColor.add(this.k);
                        this.fillK = def[1];
                        this.fillKColorList.add(this.fillK);
                    }
                }
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

