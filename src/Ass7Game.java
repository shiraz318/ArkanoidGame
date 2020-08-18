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

    private List<LevelInformation> levels = new ArrayList<LevelInformation>();
    private GameFlow gameFlow;
    private List<String> symbols = new ArrayList<String>();
    private List<String> names = new ArrayList<String>();
    private List<String> path = new ArrayList<String>();

    /**
     * Animation of ball game.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Ass7Game game = new Ass7Game();
        if (args.length == 0) {
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
        this.symbols.add(components[0]);
        this.names.add(components[1]);
    }

    /**
     * Sets file path.
     *
     * @param line the line
     */
    public void setFilePath(String line) {
        this.path.add(line);
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
            LineNumberReader br = null;
            try {
                br = new LineNumberReader(reader);
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
        HighScoresTable highScoresTable = new HighScoresTable(SIZE);
        Animation highScoresAnimation = new KeyPressStoppableAnimation(
                sensor, SPACE_KEY, new HighScoresAnimation(highScoresTable));
        this.gameFlow = new GameFlow(an, sensor, an.getGui(), highScoresTable, highScoresAnimation);

        Menu<Task<Void>> menu = new MenuAnimation<Task<Void>>(sensor, an);
        Menu<Task<Void>> subMenu = new MenuAnimation<Task<Void>>(sensor, an);
        for (int i = 0; i < this.symbols.size(); i++) {
            List<LevelInformation> l = setLevels(this.path.get(i));
            subMenu.addSelection(symbols.get(i), "(" + symbols.get(i) + ") " + names.get(i), new Ass7Game.StartGame(l));
        }
        menu.addSubMenu("s", "(s) Start game", subMenu);
        menu.addSelection("h", "(h) High scores", new Ass7Game.ShowHiScoresTask(an, highScoresAnimation));
        menu.addSelection("q", "(q) Quit", new Ass7Game.Quit());
        while (true) {
            an.run(menu);
            Task<Void> task = menu.getStatus();
            task.run();
        }

    }

    /**
     * The type Show hi scores task.
     */
    public class ShowHiScoresTask implements Task<Void> {
        private Animation highScoresAnimation;
        private AnimationRunner animationRunner;

        /**
         * Instantiates a new Show hi scores task.
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
            animationRunner.run(this.highScoresAnimation);
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
            gameFlow.runLevels(this.levelInformations);
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
