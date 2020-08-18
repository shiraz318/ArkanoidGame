package components;

import biuoop.DialogManager;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import interfaces.Animation;
import interfaces.LevelInformation;
import sprites.Block;
import sprites.GameOverScreen;
import sprites.KeyPressStoppableAnimation;
import sprites.YouWinScreen;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static biuoop.KeyboardSensor.SPACE_KEY;


/**
 * The type Game flow.
 */
public class GameFlow {
    /**
     * The constant NUMBER_OF_LIVES.
     */
// static variables
    public static final int NUMBER_OF_LIVES = 7;
    /**
     * The constant FINISH_LEVEL_SCORE.
     */
    public static final int FINISH_LEVEL_SCORE = 100;
    /**
     * The constant SIZE.
     */
    public static final int SIZE = 4;
    /**
     * The constant HEAD_FOLDER.
     */
    public static final String HEAD_FOLDER = "resources/";
    // fields
    private Counter livesCounter;
    private Counter scoreCounter;
    private KeyboardSensor keyboardSensor;
    private AnimationRunner animationRunner;
    private GUI gui;
    private HighScoresTable highScoresTable;
    private Animation highScoresAnimation;
    private File file;
    private int score;
    private GameLevel le;
    private LevelInformation levelInformation;


    /**
     * Instantiates a new Game flow.
     *
     * @param ar                  the ar
     * @param ks                  the ks
     * @param gui                 the gui
     * @param highScoresTable     the high scores table
     * @param highScoresAnimation the high scores animation
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks, GUI gui, HighScoresTable highScoresTable,
                    Animation highScoresAnimation) {
        this.animationRunner = ar;
        this.keyboardSensor = ks;
        this.gui = gui;
        this.scoreCounter = new Counter(0);
        this.livesCounter = new Counter(NUMBER_OF_LIVES);
        this.highScoresTable = highScoresTable;
        this.highScoresAnimation = highScoresAnimation;
        this.file = new File("highscores");
        try {
            highScoresTable.load(file);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException n) {
            try {
                highScoresTable.save(file);
            } catch (IOException j) {
                System.out.println(j.getMessage());
            }
        }
    }

    /**
     * Run levels.
     *
     * @param levels the levels
     */
    public void runLevels(List<LevelInformation> levels) {
        List<LevelInformation> list = new ArrayList<LevelInformation>(levels);
        for (LevelInformation levelInfo : list) {
            this.le = new GameLevel(levelInfo, this.animationRunner, this.keyboardSensor,
                    this.scoreCounter, this.livesCounter);
            this.levelInformation = levelInfo;
            this.le.initialize();
            // while the player has more lives and blocks to remove
            while ((this.le.getCounterLives().getValue() > 0) && (this.le.getCounterBlocks().getValue() > 0)) {

                this.le.playOneTurn();
                // all the balls fall
                if (this.le.getCounterBlocks().getValue() > 0) {
                    this.le.getCounterLives().decrease(1);
                }
            }
            resetLevel(levelInfo, this.le);
            if (this.le.getCounterLives().getValue() <= 0) {
                break;
            }
            this.scoreCounter.increase(FINISH_LEVEL_SCORE);
        }
        endGameAnimations();
    }

    /**
     * End game.
     */
    public void endGameAnimations() {
        // there are no more lives - you lost
        if (this.livesCounter.getValue() <= 0) {
            Animation gameOver = new GameOverScreen(this.scoreCounter);
            animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor, SPACE_KEY, gameOver));
            livesCounter.increase(NUMBER_OF_LIVES);
            // ther are more lives - you win
        } else if (scoreCounter.getValue() > 0) {
            Animation youWin = new YouWinScreen(this.scoreCounter);
            animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor, SPACE_KEY, youWin));
        }
        //add the player to the highscorse table if needed
        if (highScoresTable.getRank(scoreCounter.getValue()) <= highScoresTable.size()) {
            DialogManager dialog = gui.getDialogManager();
            String name = dialog.showQuestionDialog("Name", "What is your name?", "");
            ScoreInfo scoreInfo = new ScoreInfo(name, scoreCounter.getValue());
            highScoresTable.add(scoreInfo);
            try {
                highScoresTable.save(this.file);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        animationRunner.run(this.highScoresAnimation);
        this.score = this.scoreCounter.getValue();
        this.scoreCounter.decrease(score);

    }

    /**
     * Reset level.
     *
     * @param lI    the l i
     * @param level the level
     */
    public void resetLevel(LevelInformation lI, GameLevel level) {
        for (Block b: lI.blocks()) {
            b.setHitPoints(b.getOriginalHitPoints());
            b.resetHitByNow();
            b.removeHitListener(level.getBlockRemover());
            b.removeHitListener(level.getScoreTrackingListener());
            b.getCollisionRectangle().setFillColor(b.getCollisionRectangle().getOriginalFillColor());
            b.setImages(b.getOriginalImage());
        }
    }
}