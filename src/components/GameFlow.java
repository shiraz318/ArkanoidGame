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
        loadHighscores(highScoresTable);
    }

    // Load information from the highscores file.
    private void loadHighscores(HighScoresTable highScoresTable) {
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
            le = new GameLevel(levelInfo, animationRunner, keyboardSensor, scoreCounter, livesCounter);
            levelInformation = levelInfo;
            le.initialize();
            // While the player has more lives and blocks to remove.
            while ((le.getCounterLives().getValue() > 0) && (le.getCounterBlocks().getValue() > 0)) {
                le.playOneTurn();
                // All the balls fall.
                if (le.getCounterBlocks().getValue() > 0) le.getCounterLives().decrease(1);
            }
            resetLevel(levelInfo, le);
            // No more lives left.
            if (le.getCounterLives().getValue() <= 0) break;
            // Add the finish level score.
            scoreCounter.increase(FINISH_LEVEL_SCORE);
        }
        endGameAnimations();
    }

    // Add a player to the highscores table.
    private void addToHighScores() {
        // If the player have enough points, add it to the table.
        if (highScoresTable.getRank(scoreCounter.getValue()) <= highScoresTable.size()) {
            // Ask the player for his name.
            DialogManager dialog = gui.getDialogManager();
            String name = dialog.showQuestionDialog("Name", "What is your name?", "");
            ScoreInfo scoreInfo = new ScoreInfo(name, scoreCounter.getValue());
            highScoresTable.add(scoreInfo);
            try {
                highScoresTable.save(file);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * End game.
     */
    public void endGameAnimations() {
        // There are no more lives - the player lost.
        if (livesCounter.getValue() <= 0) {
            Animation gameOver = new GameOverScreen(scoreCounter);
            animationRunner.run(new KeyPressStoppableAnimation(keyboardSensor, SPACE_KEY, gameOver));
            livesCounter.increase(NUMBER_OF_LIVES);
            // There are more lives - the player win.
        } else if (scoreCounter.getValue() > 0) {
            Animation youWin = new YouWinScreen(scoreCounter);
            animationRunner.run(new KeyPressStoppableAnimation(keyboardSensor, SPACE_KEY, youWin));
        }
        // Add the player to the high scores table if needed.
        addToHighScores();
        animationRunner.run(highScoresAnimation);
        score = scoreCounter.getValue();
        scoreCounter.decrease(score);
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