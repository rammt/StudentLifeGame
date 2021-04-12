package no.ntnu.tdt4240.game;

public interface FirebaseInterface {
    void onSignInButtonClicked(Player user);
    void saveStats(Player user);
    void getStats(Player user);
    /*
    public void onSignOutButtonClicked();
    public boolean isSignedIn();
    public void signInSilently();
    public void submitScore(String leaderboardId, int highScore);
    public void showLeaderboard(String leaderboardId);
    public void setTrackerScreenName(String screenName);
    */
}
