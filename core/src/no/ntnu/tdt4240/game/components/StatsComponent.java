package no.ntnu.tdt4240.game.components;

import com.badlogic.ashley.core.Component;

import java.time.LocalDate;

public class StatsComponent implements Component {
    public int playerId;
    private int clicks;
    private int currentScore;
    private int overallScore;
    private LocalDate startDate;

    //når det er et ny spiller
    @SuppressWarnings("NewApi")
    public StatsComponent create(int playerId) {
        this.playerId = playerId;
        currentScore = 0;
        overallScore = 0;
        clicks = 0;
        startDate = LocalDate.now();
        return this;
    }
    //når man laster fra fil
    public StatsComponent create(int playerId, int currentScore, int overallScore, LocalDate startDate, int clicks) {
        this.playerId = playerId;
        this.currentScore = currentScore;
        this.overallScore = overallScore;
        this.startDate = startDate;
        this.clicks = clicks;
        return this;
    }

    public int getClicks() {
        return clicks;
    }

    public float getOverallScore() {
        return overallScore;
    }

    public float getCurrentScore() {
        return currentScore;
    }

    public LocalDate getStartDate(){
        return startDate;
    }

    public void updateScores(int num) {
        currentScore += num;
        overallScore += num;
    }

    public void updateCurrentScore(int price) {
        currentScore -= price;
    }
}
