package no.ntnu.tdt4240.game;

import com.badlogic.ashley.core.Entity;

import java.util.List;
import java.util.Map;

public interface FirebaseInterface {
    void onSignInButtonClicked(Entity emptyPlayer);
    void saveStats(Entity player);
    List<Map<String,Object>> getHighscore();

    //void getStats(Player user);
    /*
    public void onSignOutButtonClicked();
    public boolean isSignedIn();
    public void signInSilently();
    public void submitScore(String leaderboardId, int highScore);
    public void showLeaderboard(String leaderboardId);
    public void setTrackerScreenName(String screenName);
    */
}
