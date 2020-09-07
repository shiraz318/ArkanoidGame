import biuoop.KeyboardSensor;
import components.AnimationRunner;
import components.GameFlow;

import components.HighScoresTable;
import sprites.KeyPressStoppableAnimation;
import interfaces.Animation;
import interfaces.LevelInformation;
import interfaces.Menu;
import interfaces.Task;
import sprites.MenuAnimation;
import sprites.HighScoresAnimation;
import components.LevelSpecificationReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import static biuoop.KeyboardSensor.SPACE_KEY;
import static components.GameFlow.SIZE;

/**
 * The type Ass6game - simulate ball game.
 */
public class Ass7Game {

    private List<LevelInformation> levels;
    private GameFlow gameFlow;
    private List<String> symbols;
    private List<String> names;
    private List<String> path;

    public Ass7Game() {
        levels =  new ArrayList<LevelInformation>();
        symbols = new ArrayList<String>();
        names = new ArrayList<String>();
        path = new ArrayList<String>();
    }
    /**
     * Animation of ball game.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Ass7Game game = new Ass7Game();
        if (args.length == 0) {
            // Default file.
            game.readLevelSets("level_sets.txt");
        } else {
            game.readLevelSets(args[0]);
        }
        game.setAndApplyMenu(args);
    }

    /**
     * Sets symbol and name.
     *
     * @param line the line
     */
    public void setSymbolAndName(String line) {
        String[] components = line.split(":");
        symbols.add(components[0]);
        names.add(components[1]);
    }

    /**
     * Sets file path.
     *
     * @param line the line
     */
    public void setFilePath(String line) {
        path.add(line);
    }

    /**
     * Read level sets.
     *
     * @param fileName the file name
     */
    public void readLevelSets(String fileName) {
        try {
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName);
            Reader reader = new InputStreamReader(is);
            try {
                LineNumberReader br = new LineNumberReader(reader);
                String line;
                while ((line = br.readLine()) != null) {
                    if (br.getLineNumber() % 2 == 1) {
                        setSymbolAndName(line);
                    } else if (!line.equals("")) {
                        setFilePath(line);
                    }
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }

    /**
     * Sets and apply menu.
     *
     * @param args the args
     */
    public void setAndApplyMenu(String[] args) {

        AnimationRunner an = new AnimationRunner();
        KeyboardSensor sensor = an.getGui().getKeyboardSensor();

        // High scores animation setting.
        HighScoresTable highScoresTable = new HighScoresTable(SIZE);
        Animation highScoresAnimation = new KeyPressStoppableAnimation(
                sensor, SPACE_KEY, new HighScoresAnimation(highScoresTable));
        gameFlow = new GameFlow(an, sensor, an.getGui(), highScoresTable, highScoresAnimation);

        // Menu setting.
        Menu<Task<Void>> menu = setMenu(an, sensor, highScoresAnimation);

        // Apply.
        applyMenu(an, menu);

    }

    // Apply the given menu.
    private void applyMenu(AnimationRunner an, Menu<Task<Void>> menu) {
        while (true) {
            an.run(menu);
            Task<Void> task = menu.getStatus();
            task.run();
        }
    }

    // Set a menu definitions.
    private Menu<Task<Void>> setMenu(AnimationRunner an, KeyboardSensor sensor, Animation highScoresAnimation) {
        Menu<Task<Void>> menu = new MenuAnimation<Task<Void>>(sensor, an);
        Menu<Task<Void>> subMenu = new MenuAnimation<Task<Void>>(sensor, an);

        for (int i = 0; i < symbols.size(); i++) {
            // Create level for each symbol and add it to the submenu selections.
            List<LevelInformation> l = setLevels(path.get(i));
            subMenu.addSelection(symbols.get(i), "(" + symbols.get(i) + ") " + names.get(i), new StartGame(l));
        }
        menu.addSubMenu("s", "(s) Start game", subMenu);
        menu.addSelection("h", "(h) High scores", new ShowHiScoresTask(an, highScoresAnimation));
        menu.addSelection("q", "(q) Quit", new Quit());
        return menu;
    }

    /**
     * The type Show high scores task.
     */
    public class ShowHiScoresTask implements Task<Void> {
        private Animation highScoresAnimation;
        private AnimationRunner animationRunner;

        /**
         * Instantiates a new Show high scores task.
         *
         * @param runner              the runner
         * @param highScoresAnimation the high scores animation
         */
        public ShowHiScoresTask(AnimationRunner runner, Animation highScoresAnimation) {
            animationRunner = runner;
            this.highScoresAnimation = highScoresAnimation;
        }
        @Override
        public Void run() {
            animationRunner.run(highScoresAnimation);
            return null;
        }
    }

    /**
     * The type Quit.
     */
    public class Quit implements Task<Void> {
        @Override
        public Void run() {
            System.exit(0);
            return null;
        }
    }

    /**
     * The type Start game.
     */
    public class StartGame implements Task<Void> {
        private List<LevelInformation> levelInformations = new ArrayList<LevelInformation>();

        /**
         * Instantiates a new Start game.
         *
         * @param levelInformations the level informations
         */
        public StartGame(List<LevelInformation> levelInformations) {
            this.levelInformations = levelInformations;
        }
        @Override
        public Void run() {
            gameFlow.runLevels(levelInformations);
            return null;
        }
    }


    /**
     * Sets levels.
     *
     * @param pathArgs the path
     * @return the levels
     */
    public List<LevelInformation> setLevels(String pathArgs) {
        LevelSpecificationReader levelSpecificationReader = new LevelSpecificationReader();
        try {
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(pathArgs);
            Reader reader = new InputStreamReader(is);
            return levelSpecificationReader.fromReader(reader);
        } catch (Exception e) {
            System.out.println("error reading file1");
            System.exit(0);
        }
        return new ArrayList<LevelInformation>();
    }
}
