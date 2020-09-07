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
    private boolean isAlreadyPressed;

    /**
     * Instantiates a new High scores animation.
     *
     * @param scores the scores
     */
    public HighScoresAnimation(HighScoresTable scores) {
        this.isAlreadyPressed = true;
        this.stop = false;
        this.scores = scores;
    }

    // Draw the background.
    private void drawBackground(DrawSurface d) {
        int y = 0;
        for (int color = 8; y <= d.getHeight(); color += 5) {
            d.setColor(new Color(7, color, 150));
            d.fillRectangle(0, y, d.getWidth(), y + 20);
            y += 20;
        }
    }

    // Draw one line of score.
    private int drawOneTextLine(DrawSurface d, int i, List<ScoreInfo> scoreInfos, int xLocation, int yLocation) {
        String name = scoreInfos.get(i).getName();
        int score = scoreInfos.get(i).getScore();
        d.setColor(Color.WHITE);
        d.drawCircle(xLocation - 20, yLocation - 10, 8);
        d.drawCircle(xLocation - 20, yLocation - 10, 7);
        d.drawCircle(xLocation - 20, yLocation - 10, 6);
        d.drawText(xLocation, yLocation, name, 30);
        d.drawText(500, yLocation,  "" + score, 30);
        yLocation += 50;
        return yLocation;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        // Draw changed color background.
        drawBackground(d);

        d.setColor(Color.WHITE);
        d.drawText(90, 150, "High scores", 58);
        int xLocation = 120;
        int yLocation = 220;
        List<ScoreInfo> scoreInfos = scores.getHighScores();
        int size = scoreInfos.size();
        for (int i = 0; i < size; i++) {
            yLocation = drawOneTextLine(d, i, scoreInfos, xLocation, yLocation);
        }
        d.fillRectangle(710, 0, 8, d.getHeight());
        d.fillRectangle(690, 0, 15, d.getHeight());
        d.fillRectangle(0, 520, d.getWidth(), 15);
        d.fillRectangle(0, 540, d.getWidth(), 8);
    }
    @Override
    public boolean shouldStop() {
        return stop;
    }
}
