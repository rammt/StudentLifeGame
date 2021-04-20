package no.ntnu.tdt4240.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Screen;

import java.util.List;
import java.util.Map;

import no.ntnu.tdt4240.game.components.HighscoreComponent;
import no.ntnu.tdt4240.game.screens.HighscoreScreen;

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
