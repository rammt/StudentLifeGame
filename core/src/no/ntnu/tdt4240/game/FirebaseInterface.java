package no.ntnu.tdt4240.game;

import com.badlogic.ashley.core.Entity;

public interface FirebaseInterface {
    void onSignInButtonClicked();
    void savePlayerStats(Entity player);
    void getPlayerStats(Entity player);
    /*
    public void onSignOutButtonClicked();
    public boolean isSignedIn();
    public void signInSilently();
    public void submitScore(String leaderboardId, int highScore);
    public void showLeaderboard(String leaderboardId);
    public void setTrackerScreenName(String screenName);
    */
}
