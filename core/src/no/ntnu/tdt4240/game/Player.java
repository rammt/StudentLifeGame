package no.ntnu.tdt4240.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Player {
    private int kokCount;
    private String name;

    private int amtResourceGainers;

    private Preferences resourcePrefs;

    public Player() {
        this.amtResourceGainers = 0;
        this.resourcePrefs = Gdx.app.getPreferences("resourcePrefs");
    }

    public int getKokCount() {
        return kokCount;
    }

    public void setKokCount(int kokCount) {
        this.kokCount = kokCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmtResourceGainers() {
        return amtResourceGainers;
    }

    public void setAmtResourceGainers(int amtResourceGainers) {
        this.amtResourceGainers = amtResourceGainers;
    }

    public void saveOffline() {
        resourcePrefs.putInteger("amtResourceGainers", amtResourceGainers);
        resourcePrefs.flush();
    }
}
