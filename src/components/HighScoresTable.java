package components;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.FileNotFoundException;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The type High scores table.
 */
public class HighScoresTable implements Serializable {
    private int size;
    private List<ScoreInfo> scores = new ArrayList<ScoreInfo>();

    /**
     * Instantiates a new High scores table.
     *
     * @param size the size
     */
    public HighScoresTable(int size) {
        this.size = size;
    }


    /**
     * Return table size.
     *
     * @return the table size
     */
    public int size() {
        return this.size;
    }

    /**
     * Return the current high scores.
     *
     * @return the high scores
     */
    public List<ScoreInfo> getHighScores() {
        return this.scores;
    }

    /**
     * Return the rank of the current score.
     *
     * @param score the score
     * @return the rank
     */
    public int getRank(int score) {
        for (ScoreInfo s: this.scores) {
            if (score > s.getScore()) {
                return this.scores.indexOf(s) + 1;
            }
        }
        return this.scores.size() + 1;
    }

    /**
     * Clear the table.
     */
    public void clear() {
        for (int i = 0; i > this.scores.size(); i++) {
            ScoreInfo s = this.scores.get(0);
            this.scores.remove(0);
        }
    }

    /**
     * Load table data from file.
     *
     * @param filename the filename
     * @throws IOException the io exception
     */
    public void load(File filename) throws IOException {
        clear();
        HighScoresTable h = loadFromFile(filename);
        this.scores = h.scores;
        this.size = h.size;
    }

    /**
     * Save table data to the specified file.
     *
     * @param filename the filename
     * @throws IOException the io exception
     */
    public void save(File filename) throws IOException {
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(
                    new FileOutputStream(filename));
            objectOutputStream.writeObject(this);
        } catch (IOException e) {
            System.err.println("Failed saving object");
            e.printStackTrace(System.err);
        } finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file: " + filename);
            }
        }
    }

    /**
     * Load from file high scores table.
     *
     * @param filename the filename
     * @return the high scores table
     */
    public static HighScoresTable loadFromFile(File filename) {
        HighScoresTable highScoresTable = null;
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(
                    new FileInputStream(filename));

            // unsafe down casting, we better be sure that the stream really contains a Person!
            highScoresTable = (HighScoresTable) objectInputStream.readObject();
            // Can't find file to open
        } catch (FileNotFoundException e) {
            highScoresTable.doNothing();
            //System.err.println("Unable to find file: " + filename);
            // The class in the stream is unknown to the JVM
        } catch (ClassNotFoundException e) {
            System.err.println("Unable to find class for object in file: " + filename);
            // Some other problem
        } catch (IOException e) {
            System.err.println("Failed reading object");
            e.printStackTrace(System.err);
        } finally {
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file: " + filename);
            }
        }
        return highScoresTable;
    }

    /**
     * Do nothing.
     */
    public void doNothing() {
        return;
    }

    /**
     * Add a high-score.
     *
     * @param score the score
     */
    public void add(ScoreInfo score) {
        //check if the player should get in the table
        if (!(getRank(score.getScore()) > this.size)) {
            this.scores.add(score);
            sort();
            //check if a player should be removed
            if (this.scores.size() > this.size) {
                this.scores.remove(this.scores.size() - 1);
            }
        }
    }

    /**
     * Sort.
     */
    public void sort() {
        for (int i = 0; i < scores.size() - 1; i++) {
            for (int j = 0; j < scores.size() - 1 - i; j++) {
                if (scores.get(j).getScore() < scores.get(j + 1).getScore()) {
                    Collections.swap(this.scores, j, j + 1);
                }
            }
        }
    }
}