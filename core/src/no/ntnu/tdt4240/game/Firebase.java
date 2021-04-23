package no.ntnu.tdt4240.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Screen;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import no.ntnu.tdt4240.game.components.HighscoreComponent;
import no.ntnu.tdt4240.game.components.PlayerComponent;
import no.ntnu.tdt4240.game.screens.HighscoreScreen;

public interface Firebase {
    void startSignInActivity();
    void savePlayerStats(Entity player);
    void getPlayerStats(Entity player);
    List<Map<String,Object>> getHighscore();
    int getRank(PlayerComponent pc);
    boolean isLoggedIn();
    void getResourceGainers(List<Map<String, Object>> resourceGainers);

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
