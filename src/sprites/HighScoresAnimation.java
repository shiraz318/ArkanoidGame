package sprites;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import components.HighScoresTable;
import components.ScoreInfo;
import interfaces.Animation;

import java.awt.Color;
import java.util.List;

/**
 * The type High scores animation.
 */
public class HighScoresAnimation implements Animation {

    private boolean stop;
    private HighScoresTable scores;
    private KeyboardSensor sensor;
    private boolean isAlreadyPressed = true;

    /**
     * Instantiates a new High scores animation.
     *
     * @param scores the scores
     */
    public HighScoresAnimation(HighScoresTable scores) {
        this.stop = false;
        this.scores = scores;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        int y = 0;
        for (int color = 8; y <= 600; color += 5) {
            d.setColor(new Color(7, color, 150));
            d.fillRectangle(0, y, 800, y + 20);
            y += 20;
        }
        d.setColor(Color.WHITE);
        d.drawText(90, 150, "High scores", 58);
        int xLocation = 120;
        int yLocation = 220;
        List<ScoreInfo> scoreInfos = this.scores.getHighScores();
        int size = scoreInfos.size();
        for (int i = 0; i < size; i++) {
            String name = scoreInfos.get(i).getName();
            int score = scoreInfos.get(i).getScore();
            d.setColor(Color.WHITE);
            d.drawCircle(xLocation - 20, yLocation - 10, 8);
            d.drawCircle(xLocation - 20, yLocation - 10, 7);
            d.drawCircle(xLocation - 20, yLocation - 10, 6);
            d.drawText(xLocation, yLocation, name, 30);
            d.drawText(500, yLocation,  "" + score, 30);
            yLocation += 50;
        }
        d.fillRectangle(710, 0, 8, 600);
        d.fillRectangle(690, 0, 15, 600);
        d.fillRectangle(0, 520, 800, 15);
        d.fillRectangle(0, 540, 800, 8);
    }
    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
