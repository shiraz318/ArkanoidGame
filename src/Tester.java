import components.HighScoresTable;
import components.ScoreInfo;

import java.io.File;

/**
 * The type Tester.
 */
public class Tester {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        HighScoresTable h = new HighScoresTable(4);
        for (int i = 0; i < 9; i++) {
            h.add(new ScoreInfo("score" + (i + 1), (i + 1) * 50));
        }

        try {
            h.save(new File("highscores"));
            h.clear();
            h.load(new File("highscores"));
            for (ScoreInfo s : h.getHighScores()) {
                System.out.println("name: " + s.getName() + ", score: " + s.getScore());
                System.out.println("rank: " + h.getRank(s.getScore()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something went wrong:(");
            return;
        }
    }
}
