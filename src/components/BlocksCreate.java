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
    private String stroke = null;
    private String fill;
    private String fillK;
    private int k;
    private Map<Integer, Color> fillKMapColor = new HashMap<Integer, Color>();
    private Map<Integer, Image> fillKMapImage = new HashMap<Integer, Image>();

    private List<Integer> kListColor = new ArrayList<Integer>();
    private List<Integer> kListImage = new ArrayList<Integer>();
    private List<String> fillKListColor = new ArrayList<String>();
    private List<String> fillKImageList = new ArrayList<String>();


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
        this.fill = fill;
        this.height = height;
        this.hitPoints = hitPoints;
        this.stroke = stroke;
        this.width = width;
        this.k = k;
    }

    /**
     * Sets other.
     *
     * @param fillK1          the fill k
     * @param kListColor1     the k list color
     * @param fillKListColor1 the fill k list color
     * @param kListImage1     the k list image
     * @param fillKImageList1 the fill k image list
     */
    public void setOther(String fillK1, List<Integer> kListColor1, List<String> fillKListColor1,
                         List<Integer> kListImage1, List<String> fillKImageList1) {
        this.fillK = fillK1;
        this.kListColor = kListColor1;
        this.fillKListColor = fillKListColor1;
        this.fillKImageList = fillKImageList1;
        this.kListImage = kListImage1;
    }
    @Override
    public Block create(int xpos, int ypos) {
        Block b = new Block(new Point(xpos, ypos), width, height);
        b.setHitPoints(hitPoints);
        //set the fill field - check there is a k that equals to the hit points
        if (fill == null) {
            for (int i: kListColor) {
                if (i == hitPoints) {
                    fill = fillKListColor.get(kListColor.indexOf(i));
                }
            }
            for (int i: kListImage) {
                if (i == hitPoints) {
                    fill = fillKImageList.get(kListImage.indexOf(i));
                }
            }
        }
        if (stroke != null) {
            //isolate the color string
            String color = stroke.substring(6, stroke.length() - 1);
            if (stroke.startsWith("color")) {
                if (!color.startsWith("RGB")) {
                    Color strokeColor = new ColorsParser().colorFromString(color);
                    b.getCollisionRectangle().setFrameColor(strokeColor);
                } else {
                    //isolate the components of the RGB
                    String w = color.substring(4, color.length() - 1);
                    Color strokeColor = new ColorsParser().colorFromString(w);
                    b.getCollisionRectangle().setFrameColor(strokeColor);
                }
            }
        }
        String color;
        if (fill.startsWith("color")) {
            //isolate the color string
            color = fill.substring(6, fill.length() - 1);
            if (!color.startsWith("RGB")) {
                Color fillColor = new ColorsParser().colorFromString(color);
                b.getCollisionRectangle().setColors(fillColor);
            } else {
                //isolate the components of the RGB
                String w = color.substring(4, color.length() - 1);
                Color fillColor = new ColorsParser().colorFromString(w);
                b.getCollisionRectangle().setColors(fillColor);
            }
        } else {
            //isolate the image path
            String image = fill.substring(6, fill.length() - 1);
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(image);
            Image img = null;
            try {
                img = ImageIO.read(is);
                b.setImages(img);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        //set the fillMapColor
        for (String s: this.fillKListColor) {
            if (s.startsWith("color")) {
                color = s.substring(6, s.length() - 1);
                if (!color.startsWith("RGB")) {
                    Color fillKColor = new ColorsParser().colorFromString(color);
                    this.fillKMapColor.put(kListColor.get(this.fillKListColor.indexOf(s)), fillKColor);
                } else {
                    String w = color.substring(4, color.length() - 1);
                    Color fillKColor = new ColorsParser().colorFromString(w);
                    this.fillKMapColor.put(kListColor.get(fillKListColor.indexOf(s)), fillKColor);
                }
            }
        }
        //set the fillMapColor
        for (String s: this.fillKImageList) {
            String image = s.substring(6, s.length() - 1);
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(image);
            Image img = null;
            try {
                img = ImageIO.read(is);
                this.fillKMapImage.put(kListImage.get(fillKImageList.indexOf(s)), img);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        b.setImageMap(this.fillKMapImage);
        b.setkListImage(this.kListImage);
        b.setMapKFillColor(this.fillKMapColor);
        b.setKListColor(kListColor);
        b.setHitPoints(hitPoints);
        b.setOriginalHitPoints(hitPoints);
        b.setHitByNow(0);
        return b;

    }
}