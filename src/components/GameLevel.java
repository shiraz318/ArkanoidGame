package components;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import graphics.Point;
import interfaces.Animation;
import interfaces.HitListener;
import interfaces.LevelInformation;
import interfaces.Sprite;
import interfaces.Collidable;
import sprites.Ball;
import sprites.Block;
import sprites.CountdownAnimation;
import sprites.Paddle;
import sprites.LivesIndicator;
import sprites.ScoreIndicator;
import sprites.NameIndicator;
import sprites.KeyPressStoppableAnimation;
import sprites.PauseScreen;


import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import static biuoop.KeyboardSensor.SPACE_KEY;

/**
 * The type GameLevel has sprites and game environment.
 *
 * @author Shiraz Berger
 */
public class GameLevel implements Animation {
    /**
     * The constant WIDTH_GAME.
     */
    public static final int WIDTH_GAME = 800;
    /**
     * The constant HEIGHT_GAME.
     */
    public static final int HEIGHT_GAME = 600;
    /**
     * The constant RADIUS_GAME.
     */
    public static final int RADIUS_GAME = 6;
    /**
     * The constant FRAME_WIDTH.
     */
    public static final int FRAME_WIDTH = 25;
    /**
     * The constant BLOCKS_WIDTH.
     */
    public static final int BLOCKS_WIDTH = 51;
    /**
     * The constant BLOCKS_HEIGHT.
     */
    public static final int BLOCKS_HEIGHT = 22;
    /**
     * The constant PADDLE_HEIGHT.
     */
    public static final int PADDLE_HEIGHT = 15;
    /**
     * The constant BACKGRAUND_COLOR.
     */
    public static final Color BACKGRAUND_COLOR = Color.BLUE.darker().darker().darker();
    /**
     * The constant NUM_OF_SECONDS.
     */
    public static final double NUM_OF_SECONDS = 2;
    /**
     * The constant COUNT_FROM.
     */
    public static final int COUNT_FROM = 3;
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private Counter blocksCounter;
    private Counter ballsCounter;
    private Counter scoreCounter;
    private Counter livesCounter;
    private HitListener blockRemover;
    private HitListener ballRemover;
    private HitListener scoreTrackingListener;
    private Paddle paddle;
    private Block deathRegion;
    private Ball[] balls;
    private AnimationRunner runner;
    private boolean running;
    private KeyboardSensor keyboard;
    private LevelInformation level;
    private Sprite nameIndicator;
    private List<Block> bCopy;
    private List<Block> blocks;

    /**
     * Create a new GameLevel.
     *
     * @param level           the information about the level
     * @param animationRunner the animation
     * @param keyboardSensor  the keyboard sensor
     * @param scoreCounter    the score counter
     * @param livesCounter    the lives counter
     */
    public GameLevel(LevelInformation level, AnimationRunner animationRunner,
                     KeyboardSensor keyboardSensor, Counter scoreCounter, Counter livesCounter) {
        this.level = level;
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.blocksCounter = new Counter(level.numberOfBlocksToRemove());
        this.ballsCounter = new Counter(0);
        this.blockRemover = new BlockRemover(this, blocksCounter);
        this.ballRemover = new BallRemover(this, ballsCounter);
        this.scoreCounter = scoreCounter;
        this.scoreTrackingListener = new ScoreTrackingListener(this.scoreCounter);
        this.livesCounter = livesCounter;
        this.runner = animationRunner;
        this.running = true;
        this.keyboard = keyboardSensor;
        this.nameIndicator = new NameIndicator(level.levelName());
        this.blocks = this.level.blocks();
    }

    /**
     * Gets balls count.
     *
     * @return the balls count
     */
    public int getBallsCount() {
        return ballsCounter.getValue();
    }

    /**
     * Gets block remover.
     *
     * @return the block remover
     */
    public HitListener getBlockRemover() {
        return blockRemover;
    }

    /**
     * Gets score tracking listener.
     *
     * @return the score tracking listener
     */
    public HitListener getScoreTrackingListener() {
        return scoreTrackingListener;
    }

    /**
     * Gets blocks counter.
     *
     * @return the blocks counter
     */
    public Counter getCounterBlocks() {
        return blocksCounter;
    }

    /**
     * Gets lives counter.
     *
     * @return the lives counter
     */
    public Counter getCounterLives() {
        return livesCounter;
    }

    /**
     * Add the given collidable to the environment.
     *
     * @param c the collidable to add
     */
    public void addCollidable(Collidable c) {
        environment.addCollidable(c);
    }

    /**
     * Add the given sprite.
     *
     * @param s the sprite to add
     */
    public void addSprite(Sprite s) {
        sprites.addSprite(s);
    }

    /**
     * Initialize the level.
     */
    public void initialize() {
        drawBackground();
        createFrame();
        createScoreIndicator();
        createLivesIndicator();
        createBlocks();
        createPaddle();
    }

    // Set the death region block.
    private void setDeathRegion() {
        deathRegion = new Block(new Point(0, HEIGHT_GAME), WIDTH_GAME, FRAME_WIDTH);
        deathRegion.getCollisionRectangle().setFillColor(BACKGRAUND_COLOR);
        deathRegion.getCollisionRectangle().setFrameColor(BACKGRAUND_COLOR);
        deathRegion.getCollisionRectangle().setTextColor(deathRegion.getCollisionRectangle().getFillColor());
        deathRegion.setHitPoints(0);
        deathRegion.addHitListener(ballRemover);
        deathRegion.addToGame(this);
    }

    /**
     * Create a frame and add it to the game.
     */
    public void createFrame() {
        Block[] frame = new Block[3];
        int high = ScoreIndicator.INDICATOR_HEIGHT;
        // Down - death region.
        setDeathRegion();
        // Up.
        frame[0] = new Block(new Point(0, high), WIDTH_GAME, FRAME_WIDTH);
        // Right.
        frame[1] = new Block(new Point(WIDTH_GAME - FRAME_WIDTH, high), FRAME_WIDTH, HEIGHT_GAME);
        // Left.
        frame[2] = new Block(new Point(0, high), FRAME_WIDTH, HEIGHT_GAME);
        // Set color, hit points and add to the game.
        for (int i = 0; i < frame.length; i++) {
            frame[i].getCollisionRectangle().setColors(Color.GRAY);
            frame[i].setHitPoints(0);
            frame[i].addToGame(this);
        }
    }

    /**
     * Gets death region.
     *
     * @return the death region
     */
    public Block getDeathRegion() {
        return deathRegion;
    }

    /**
     * Create score indicator.
     */
    public void createScoreIndicator() {
        Sprite indicator = new ScoreIndicator(scoreCounter);
        sprites.addSprite(indicator);
    }

    /**
     * Create lives indicator.
     */
    public void createLivesIndicator() {
        Sprite indicator = new LivesIndicator(livesCounter);
        sprites.addSprite(indicator);
    }

    /**
     * Create blocks and add them to the game.
     */
    public void createBlocks() {
        bCopy = new ArrayList<>(blocks);
        for (Block b: bCopy) {
           // b.setHitPoints();
             b.addHitListener(blockRemover);
             b.addHitListener(scoreTrackingListener);
             b.addToGame(this);
        }
    }

    /**
     * Create balls and add them to the game.
     */
    public void createBall() {
        balls = new Ball[level.numberOfBalls()];
        int wide = level.paddleWidth() / 2;
        for (int i = 0; i < balls.length; i++) {
            int x = (int) paddle.getCollisionRectangle().getUpperLeft().getX() + wide;
            int y = (int) paddle.getCollisionRectangle().getUpperLeft().getY() - RADIUS_GAME;
            balls[i] = new Ball(new Point(x, y), RADIUS_GAME, Color.WHITE);
            balls[i].setVelocity(level.initialBallVelocities().get(i));
            balls[i].setTrajectory();
            balls[i].setGame(environment);
            balls[i].addToGame(this);
        }
    }

    /**
     * Create paddle and add it to the game.
     */
    public void createPaddle() {
        int x = (WIDTH_GAME / 2) - (level.paddleWidth() / 2);
        int y = HEIGHT_GAME - FRAME_WIDTH - PADDLE_HEIGHT;
        Point upperLeft = new Point(x, y);
        paddle = new Paddle(upperLeft, level.paddleWidth(), PADDLE_HEIGHT, keyboard);
        paddle.getCollisionRectangle().setFillColor(Color.GRAY.darker());

        paddle.setSpeed(level.paddleSpeed());
        paddle.addToGame(this);
    }

    /**
     * Update the paddle - locate it in the middle of the screen.
     */
    public void updatePaddle() {
        removeCollidable(paddle);
        removeSprite(paddle);
        createPaddle();
    }

    /**
     * Play one turn of the game.
     */
    public void playOneTurn() {
        updatePaddle();
        if (ballsCounter.getValue() == 0) {
            resetBallCounter();
            createBall();
        }
        // Countdown before turn starts.
        runner.run(new CountdownAnimation(NUM_OF_SECONDS, COUNT_FROM, sprites));
        // Use our runner to run the current animation -- which is one turn of
        // the game.
        running = true;
        runner.run(this);
    }

    /**
     * Reset the ball counter.
     */
    public void resetBallCounter() {
        ballsCounter = new Counter(level.numberOfBalls());
        deathRegion.removeHitListener(ballRemover);
        ballRemover = new BallRemover(this, ballsCounter);
        deathRegion.addHitListener(ballRemover);
    }

    /**
     * Sets ball counter.
     *
     * @param count the count
     */
    public void setBallCounter(int count) {
        ballsCounter = new Counter(count);
    }

    /**
     * Sets ball remover.
     */
    public void setBallRemover() {
        ballRemover = new BallRemover(this, ballsCounter);
    }

    /**
     * Draw the background on a given surface.
     */
    public void drawBackground() {
        sprites.addSprite(level.getBackground());
        Block block = new Block(new Point(0, 0), 800, 25);
        block.getCollisionRectangle().setFillColor(Color.WHITE);
        block.getCollisionRectangle().setColors(Color.WHITE);
        sprites.addSprite(block);
        sprites.addSprite(nameIndicator);
    }

    /**
     * Remove a collidable from the game.
     *
     * @param c a collidable
     */
    public void removeCollidable(Collidable c) {
        if (environment.getCollidables().contains(c)) {
            environment.getCollidables().remove(c);
        }
    }

    /**
     * Remove a sprite from the game.
     *
     * @param s a sprite
     */
    public void removeSprite(Sprite s) {
        if (sprites.getSpriteCollection().contains(s)) {
            sprites.getSpriteCollection().remove(s);
        }
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        // No more blocks or no more balls left.
        if ((blocksCounter.getValue() <= 0) || (ballsCounter.getValue() <= 0)) {
            running = false;
        }
        sprites.drawAllOn(d);
        sprites.notifyAllTimePassed();
        // If the player wants to pause the game.
        if (keyboard.isPressed("p")) {
            runner.run(new KeyPressStoppableAnimation(keyboard, SPACE_KEY, new PauseScreen(keyboard)));
        }
    }

    @Override
    public boolean shouldStop() {
        return !running;
    }

}