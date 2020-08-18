package components;

import interfaces.LevelInformation;
import interfaces.Sprite;
import sprites.Background;
import sprites.Block;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.BufferedReader;


import java.util.ArrayList;
import java.util.List;


/**
 * The type Level specification reader.
 */
public class LevelSpecificationReader {

    private int numberOfBalls = -1;
    private List<Velocity> initialBallVelocities = new ArrayList<Velocity>();
    private int paddleSpeed = -1;
    private int paddleWidth = -1;
    private String levelName = null;
    private Sprite background = null;
    private List<Block> blocks = new ArrayList<Block>();
    private int numberOfBlocksToRemove = -1;
    private int blocksStartX = -1;
    private int blocksStartY = -1;
    private int rowHeight = -1;
    private String blockDefinitions = null;
    private List<LevelInformation> levelInformations = new ArrayList<LevelInformation>();
    /**
     * From reader list.
     *
     * @param reader the reader
     * @return the list
     */
    public List<LevelInformation> fromReader(java.io.Reader reader) {
        readLevel(reader);
        return this.levelInformations;
    }

    /**
     * Sets fields.
     *
     * @param level the level
     */
    public void setFields(List<String> level) {
        String[] info;
        List<String> blocksList = new ArrayList<String>();
        for (String s: level) {
            info = s.split(":");
            if (info[0].startsWith("#")) {
                continue;
            }
            switch (info[0]) {
                case "#":
                    break;
                case "level_name":
                    levelName = info[1];
                    break;
                case "paddle_speed":
                    paddleSpeed = Integer.parseInt(info[1]);
                    break;
                case "paddle_width":
                    paddleWidth = Integer.parseInt(info[1]);
                    break;
                case "num_blocks":
                    numberOfBlocksToRemove = Integer.parseInt(info[1]);
                    break;
                case "blocks_start_x":
                    blocksStartX = Integer.parseInt(info[1]);
                    break;
                case "blocks_start_y":
                    blocksStartY = Integer.parseInt(info[1]);
                    break;
                case "row_height":
                    rowHeight = Integer.parseInt(info[1]);
                    break;
                case "ball_velocities":
                    setVelocities(info[1]);
                    break;
                case "block_definitions":
                    blockDefinitions = info[1];
                    break;
                case "background":
                    setBackground(info[1]);
                    break;
                case "START_BLOCKS":
                    break;
                case "END_BLOCKS":
                    setBlocks(blocksList);
                    break;
                default:
                    blocksList.add(info[0]);
                    break;
            }
        }
        if (!checkValidity()) {
            try {
                throw new Exception();
            } catch (Exception e) {
                System.exit(0);
            }
        }
        Level l = set();
        this.levelInformations.add(l);
    }

    /**
     * Check validity boolean.
     *
     * @return the boolean
     */
    public boolean checkValidity() {
        boolean valid = true;

        if (numberOfBalls == -1) {
            System.out.println("invalid numberOfBalls");
            valid = false;
        }
        if (initialBallVelocities.isEmpty()) {
            System.out.println("invalid initialBallVelocities");
            valid = false;
        }
        if (paddleSpeed == -1) {
            System.out.println("invalid paddleSpeed");
            valid = false;
        }
        if (paddleWidth == -1) {
            System.out.println("invalid paddleWidth");
            valid = false;
        }
        if (levelName == null) {
            System.out.println("invalid levelName");
            valid = false;
        }
        if (background == null) {
            System.out.println("invalid background");
            valid = false;
        }
        if (blocks.isEmpty()) {
            System.out.println("invalid blocks");
            valid = false;
        }
        if (numberOfBlocksToRemove == -1) {
            System.out.println("invalid numberOfBlocksToRemove");
            valid = false;
        }
        if (blocksStartX == -1) {
            System.out.println("invalid blocksStartX");
            valid = false;
        }
        if (blocksStartY == -1) {
            System.out.println("invalid blocksStartY");
            valid = false;
        }
        if (rowHeight == -1) {
            System.out.println("invalid rowHeight");
            valid = false;
        }
        if (blockDefinitions == null) {
            System.out.println("invalid blockDefinitions");
            valid = false;
        }
    return valid;
    }

    /**
     * Sets background.
     *
     * @param s the s
     */
    public void setBackground(String s) {
        String color = s.substring(6, s.length() - 1);
        if (s.startsWith("color")) {
            if (!color.startsWith("RGB")) {
                Color backgroundColor = new ColorsParser().colorFromString(color);
                this.background = new Background(backgroundColor, null);
            } else {
                String w = color.substring(4, color.length() - 1);
                Color backgroundColor = new ColorsParser().colorFromString(w);
                this.background = new Background(backgroundColor, null);
            }
        } else {

            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(color);
            BufferedImage img = null;
            try {
               img = ImageIO.read(is);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            this.background = new Background(null, img);
        }
    }

    /**
     * Set level.
     *
     * @return the level
     */
    public Level set() {
        Level l = new Level();
        l.setBlocks(this.blocks);
        l.setNumberOfBlocksToRemove(this.numberOfBlocksToRemove);
        l.setGetBackground(this.background);
        l.setInitialBallVelocities(this.initialBallVelocities);
        l.setLevelName(this.levelName);
        l.setNumberOfBalls(this.numberOfBalls);
        l.setPaddleSpeed(this.paddleSpeed);
        l.setPaddleWidth(this.paddleWidth);
        clear();
        return l;
    }

    /**
     * Clear.
     */
    public void clear() {
        this.paddleSpeed = -1;
        this.paddleWidth = -1;
        this.levelName = null;
        this.background = null;
        this.numberOfBlocksToRemove = -1;
        this.blocksStartX = -1;
        this.blocksStartY = -1;
        this.rowHeight = -1;
        this.blockDefinitions = null;
        this.initialBallVelocities = new ArrayList<Velocity>();
        this.blocks = new ArrayList<Block>();
    }

    /**
     * Sets blocks.
     *
     * @param info the info
     */
    public void setBlocks(List<String> info) {
        try {
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(blockDefinitions);
            Reader reader = new InputStreamReader(is);
            BlocksDefinitionReader bdr = new BlocksDefinitionReader();
            BlocksFromSymbolsFactory bdf = BlocksDefinitionReader.fromReader(reader);
            int startX = this.blocksStartX;
            int startY = this.blocksStartY;
            char[] details;
            boolean flg = false;
            for (String s: info) {
                if (s.equals(" ")) {
                    break;
                }
                details = s.toCharArray();
                String string;
                startX = this.blocksStartX;
                for (int i = 0; i < s.length(); i++) {
                    flg = true;
                    string = "" + details[i];
                    if (bdf.isSpaceSymbol(string)) {
                        if (details.length == 1) {
                            flg = false;
                            startY += this.rowHeight;
                        } else {
                            startX += bdf.getSpaceWidth(string);
                        }

                    } else if (bdf.isBlockSymbol(string)) {
                        Block newBlock = bdf.getBlock(string, startX, startY);
                        bdr.setHeight((int) newBlock.getCollisionRectangle().getHeight());
                        bdr.setWidth((int) newBlock.getCollisionRectangle().getWidth());
                        this.blocks.add(newBlock);
                        startX += newBlock.getCollisionRectangle().getWidth();
                    }
                }
                if (flg) {
                    startY += this.rowHeight;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Sets velocities.
     *
     * @param info the info
     */
    public void setVelocities(String info) {
        String[] balls = info.split(" ");
        String[] velocity = new String[2];
        for (int i = 0; i < balls.length; i++) {
            String ball = balls[i];
            velocity = ball.split(",");
            double angle = Double.parseDouble(velocity[0]);
            double speed = Double.parseDouble(velocity[1]);
            initialBallVelocities.add((Velocity.fromAngleAndSpeed(angle, speed)));
        }
        this.numberOfBalls = this.initialBallVelocities.size();
    }
    /**
     * Read level.
     *
     * @param reader the reader
     */
    public void readLevel(java.io.Reader reader) {
        List<String> level = new ArrayList<String>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(reader);
            String line;
            while ((line = br.readLine()) != null) {
                if (line.equals("")) {
                    continue;
                }
                if (line.startsWith("#")) {
                    continue;
                }
                if (line.equals("START_LEVEL")) {
                    level = new ArrayList<String>();
                } else if (line.equals("END_LEVEL")) {
                    setFields(level);
                } else {
                    level.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}