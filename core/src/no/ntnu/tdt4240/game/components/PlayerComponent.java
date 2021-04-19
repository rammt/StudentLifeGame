package no.ntnu.tdt4240.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;



public class PlayerComponent implements Component {
    private String name;
    private long lastSave;
    private float kokCount;
    private List<Map<String, Object>> resourceGainers;


    public PlayerComponent create(String name, long lastSave, float kokCount, List<Map<String, Object>> resourceGainers) {
        this.name = name;
        this.lastSave = lastSave;
        this.kokCount = kokCount;
        this.resourceGainers = resourceGainers;

        return this;
    }

    public PlayerComponent create() {
        this.name = "NoName";
        this.lastSave = new Date().getTime();
        this.kokCount = 0;
        this.resourceGainers = Collections.emptyList();

        return this;
    }

    public String getName() {
        return name;
    }

    public long getLastSave() {
        return lastSave;
    }

    public float getKokCount() {
        return kokCount;
    }

    public List<Map<String, Object>> getResourceGainers() {
        return resourceGainers;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastSave(long lastSave) {
        this.lastSave = lastSave;
    }

    public void setKokCount(float kokCount) {
        this.kokCount = kokCount;
    }

    public void setResourceGainers(List<Map<String, Object>> resourceGainers) {
        this.resourceGainers = resourceGainers;
    }
}


/*long lastSave = resourcePrefs.getLong("lastSave");
        int minimumMSSinceEpoch = 10000; //Safe test for time since epoch, if more than 10000ms has gone they probably have a save.
        if (lastSave > minimumMSSinceEpoch) {
            this.lastSave =  new Date(resourcePrefs.getLong("lastSave")).getTime();
        } else {
            this.lastSave = new Date().getTime();
        }
 */