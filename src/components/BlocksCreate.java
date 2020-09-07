package components;

import graphics.Point;
import interfaces.BlockCreator;
import sprites.Block;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The type Blocks create.
 */
public class BlocksCreate implements BlockCreator {
    private int width;
    private int height;
    private int hitPoints;
    private String stroke;
    private String fill;
    private String fillK;
    private int k;
    private Map<Integer, Color> fillKMapColor;
    private Map<Integer, Image> fillKMapImage;

    private List<Integer> kListColor;
    private List<Integer> kListImage;
    private List<String> fillKListColor;
    private List<String> fillKImageList;


    /**
     * Instantiates a new Blocks create.
     *
     * @param width     the width
     * @param height    the height
     * @param hitPoints the hit points
     * @param stroke    the stroke
     * @param fill      the fill
     * @param k         the k
     */
    public BlocksCreate(int width, int height, int hitPoints, String stroke, String fill, int k) {
        this.stroke = null;
        this.fillKMapColor = new HashMap<Integer, Color>();
        this.fillKMapImage = new HashMap<Integer, Image>();
        this.kListColor = new ArrayList<Integer>();
        this.kListImage = new ArrayList<Integer>();
        this.fillKListColor = new ArrayList<String>();
        this.fillKImageList = new ArrayList<String>();

        this.fill = fill;
        this.height = height;
        this.hitPoints = hitPoints;
        this.stroke = stroke;
        this.width = width;
        this.k = k;

    }

    /**
     * Sets other block.
     *
     * @param fillK1          the fill k
     * @param kListColor1     the k list color
     * @param fillKListColor1 the fill k list color
     * @param kListImage1     the k list image
     * @param fillKImageList1 the fill k image list
     */
    public void setOther(String fillK1, List<Integer> kListColor1, List<String> fillKListColor1,
                         List<Integer> kListImage1, List<String> fillKImageList1) {
        fillK = fillK1;
        kListColor = kListColor1;
        fillKListColor = fillKListColor1;
        fillKImageList = fillKImageList1;
        kListImage = kListImage1;
    }

    // Set the stroke color of a given block.
    private void setStroke(Block block) {
        if (stroke != null) {
            // Isolate the color string.
            String color = stroke.substring(6, stroke.length() - 1);
            if (stroke.startsWith("color")) {
                String tempColor = (!color.startsWith("RGB")) ? color : color.substring(4, color.length() - 1);
                Color strokeColor = new ColorsParser().colorFromString(tempColor);
                block.getCollisionRectangle().setFrameColor(strokeColor);
            }
        }
    }

    // Set the fill color or image of a given block.
    private String setFill(Block block) {
        String color = null;
        if (fill.startsWith("color")) {
            // Isolate the color string
            color = fill.substring(6, fill.length() - 1);
            String tempColor = (!color.startsWith("RGB")) ? color : color.substring(4, color.length() - 1);
            Color fillColor = new ColorsParser().colorFromString(tempColor);
            block.getCollisionRectangle().setColors(fillColor);
        } else {
            // Isolate the image path
            String image = fill.substring(6, fill.length() - 1);
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(image);
            try {
                Image img = ImageIO.read(is);
                block.setImages(img);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return color;
    }

    // Set the fill map color.
    private void setFillMapColor(String color) {
        for (String s: fillKListColor) {
            if (s.startsWith("color")) {
                color = s.substring(6, s.length() - 1);
                String tempColor = (!color.startsWith("RGB")) ? color : color.substring(4, color.length() - 1);
                Color fillKColor = new ColorsParser().colorFromString(tempColor);
                fillKMapColor.put(kListColor.get(fillKListColor.indexOf(s)), fillKColor);
            }
        }
    }

    // Set the fill image list.
    private void setFillImageList(String color) {
        for (String s: fillKImageList) {
            String image = s.substring(6, s.length() - 1);
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(image);
            try {
                Image img = ImageIO.read(is);
                fillKMapImage.put(kListImage.get(fillKImageList.indexOf(s)), img);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public Block create(int xpos, int ypos) {
        Block b = new Block(new Point(xpos, ypos), width, height);
        b.setHitPoints(hitPoints);
        // Set the fill field - check if there is a k that equals to the hit points.
        if (fill == null) {
            for (int i: kListColor) {
                if (i == hitPoints) fill = fillKListColor.get(kListColor.indexOf(i));
            }
            for (int i: kListImage) {
                if (i == hitPoints) fill = fillKImageList.get(kListImage.indexOf(i));
            }
        }
        setStroke(b);
        String color = setFill(b);
        // Set the fillMapColor.
        setFillMapColor(color);
        // Set the fillImageList.
        setFillImageList(color);
        // Other setting.
        b.setImageMap(fillKMapImage);
        b.setkListImage(kListImage);
        b.setMapKFillColor(fillKMapColor);
        b.setKListColor(kListColor);
        b.setHitPoints(hitPoints);
        b.setOriginalHitPoints(hitPoints);
        b.setHitByNow(0);
        return b;

    }
}