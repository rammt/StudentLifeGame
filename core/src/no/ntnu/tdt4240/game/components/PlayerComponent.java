package no.ntnu.tdt4240.game.components;

import com.badlogic.ashley.core.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class PlayerComponent implements Component {
    private String name;
    private long lastSave;
    private float kokCount;
    private Long clickCount;
    private List<Map<String, Object>> firebaseResourceGainers;
    private ArrayList<ResourceGainerComponent> resourceGainers;


    public PlayerComponent create(String name, long lastSave, float kokCount,float clickCount, List<Map<String, Object>> firebaseResourceGainers) {
        this.name = name;
        this.lastSave = lastSave;
        this.kokCount = kokCount;
        this.firebaseResourceGainers = firebaseResourceGainers;
        this.resourceGainers = new ArrayList<>();
        for(Map<String, Object> map : firebaseResourceGainers) {
            for(Map.Entry<String, Object> innerEntry : map.entrySet()) {
                this.resourceGainers.add((ResourceGainerComponent) innerEntry.getValue());
            }
        }

        return this;
    }

    public PlayerComponent create() {
        this.name = "NoName";
        this.lastSave = new Date().getTime();
        this.kokCount = 0;
        this.clickCount = 0L;
        this.resourceGainers = new ArrayList<>();
        this.firebaseResourceGainers = Collections.emptyList();

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

    public Long getClickCount() {return clickCount;}

    public void setName(String name) {
        this.name = name;
    }

    public void setLastSave(long lastSave) {
        this.lastSave = lastSave;
    }

    public void setKokCount(float kokCount) {
        this.kokCount = kokCount;
    }

    public void setClickCount(Long clickCount) {this.clickCount = clickCount;}

    public void setResourceGainers(List<Map<String, Object>> resourceGainers) {
        this.firebaseResourceGainers = resourceGainers;
    }

    public ArrayList<ResourceGainerComponent> getResourceGainers() {
        return resourceGainers;
    }

    public void addResourceGainers(ResourceGainerComponent resourceGainerComponent) {
        resourceGainers.add(resourceGainerComponent);
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