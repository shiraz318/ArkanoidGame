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

    private int numberOfBalls;
    private List<Velocity> initialBallVelocities;
    private int paddleSpeed;
    private int paddleWidth;
    private String levelName;
    private Sprite background;
    private List<Block> blocks;
    private int numberOfBlocksToRemove;
    private int blocksStartX;
    private int blocksStartY;
    private int rowHeight;
    private String blockDefinitions;
    private List<LevelInformation> levelInformations;

    public LevelSpecificationReader() {
        this.numberOfBalls = -1;
        this.paddleSpeed = -1;
        this.paddleWidth = -1;
        this.numberOfBlocksToRemove = -1;
        this.blocksStartX = -1;
        this.blocksStartY = -1;
        this.rowHeight = -1;
        this.background = null;
        this.blocks = new ArrayList<Block>();
        this.initialBallVelocities = new ArrayList<Velocity>();
        this.levelInformations = new ArrayList<LevelInformation>();
    }
    /**
     * From reader list.
     *
     * @param reader the reader
     * @return the list
     */
    public List<LevelInformation> fromReader(java.io.Reader reader) {
        readLevel(reader);
        return levelInformations;
    }

    // Set the correct definition accordingly to the given info.
    private void switchField(String[] info, List<String> blocksList) {
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
            // Line that starts with # is a note so we can ignore.
            if (info[0].startsWith("#")) continue;
            switchField(info, blocksList);
        }
        if (!checkValidity()) {
            System.exit(0);
        }
        Level l = set();
        levelInformations.add(l);
    }

    /**
     * Check validation.
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
        return blockValidation(valid);
    }

    // Check a block related information validation.
    private boolean blockValidation(boolean valid) {
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

    // Set the background by a given string.
    public void setBackground(String s) {
        String color = s.substring(6, s.length() - 1);

        // The background is a color.
        if (s.startsWith("color")) {
            String tempColor = (!color.startsWith("RGB")) ? color : color.substring(4, color.length() - 1);
            Color backgroundColor = new ColorsParser().colorFromString(tempColor);
            background = new Background(backgroundColor, null);
            // The background is an image.
        } else {
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(color);
            BufferedImage img = null;
            try {
               img = ImageIO.read(is);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            background = new Background(null, img);
        }
    }

    /**
     * Set level.
     *
     * @return the level
     */
    public Level set() {
        Level l = new Level();
        l.setBlocks(blocks);
        l.setNumberOfBlocksToRemove(numberOfBlocksToRemove);
        l.setGetBackground(background);
        l.setInitialBallVelocities(initialBallVelocities);
        l.setLevelName(levelName);
        l.setNumberOfBalls(numberOfBalls);
        l.setPaddleSpeed(paddleSpeed);
        l.setPaddleWidth(paddleWidth);
        clear();
        return l;
    }

    /**
     * Clear.
     */
    public void clear() {
        paddleSpeed = -1;
        paddleWidth = -1;
        levelName = null;
        background = null;
        numberOfBlocksToRemove = -1;
        blocksStartX = -1;
        blocksStartY = -1;
        rowHeight = -1;
        blockDefinitions = null;
        initialBallVelocities = new ArrayList<Velocity>();
        blocks = new ArrayList<Block>();
    }

    // Set one block definitions.
    private int[] setOneBlock(char[] details, int[] starts, BlocksFromSymbolsFactory bdf, BlocksDefinitionReader bdr,
                              int size) {
        boolean flg = false;
        String string;
        int startX = starts[0];
        int startY = starts[1];
        for (int i = 0; i < size; i++) {
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
        return new int[]{startX, startY};
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
            int[] starts = new int[]{blocksStartX, blocksStartY};
            for (String s: info) {
                if (s.equals(" ")) break;
                starts[0] = this.blocksStartX;
                starts = setOneBlock(s.toCharArray(), starts, bdf, bdr, s.length());
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
        numberOfBalls = initialBallVelocities.size();
    }
    /**
     * Read level.
     *
     * @param reader the reader
     */
    public void readLevel(java.io.Reader reader) {
        List<String> level = new ArrayList<String>();
        BufferedReader br = new BufferedReader(reader);
        String line;
        try {
            // Read line by line and process the information of each line.
            while ((line = br.readLine()) != null) {
                if (line.equals("") || line.startsWith("#")) continue;
                if (line.equals("START_LEVEL")) level = new ArrayList<String>();
                else if (line.equals("END_LEVEL")) setFields(level);
                else level.add(line);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}