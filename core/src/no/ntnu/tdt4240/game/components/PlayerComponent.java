package no.ntnu.tdt4240.game.components;

import com.badlogic.ashley.core.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerComponent implements Component {
    private String name;
    private long lastSave;
    private float kokCount;
    private Long clickCount;
    private List<Map<String, Object>> resourceGainers;
    private boolean combinedButtons;
    private float clickValue;


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
        this.clickCount = 0L;
        this.resourceGainers = new ArrayList<>();
        this.combinedButtons = false;
        this.clickValue = 0.05f;

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

    public Long getClickCount() {
        return clickCount;
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

    public void setClickCount(Long clickCount) {
        this.clickCount = clickCount;
    }

    public void setResourceGainers(List<Map<String, Object>> resourceGainers) {
        this.resourceGainers = resourceGainers;
    }

    public List<Map<String, Object>> getResourceGainers() {
        return resourceGainers;
    }

    public void addResourceGainer(ResourceGainerComponent rgc) {
        boolean hasAddedGainer = false;

        for (Map<String, Object> gainer : resourceGainers) {

            int rg_amount = (int) gainer.get("amount");

            if (rgc.getId().equals((String) gainer.get("id"))) {
                gainer.put("amount", rg_amount + 1);
                hasAddedGainer = true;
                break;
            }
        }

        if (!hasAddedGainer) {
            Map<String, Object> newGainer = new HashMap<>();
            newGainer.put("id", rgc.getId());
            newGainer.put("amount", 1);
            newGainer.put("level", 1);
            resourceGainers.add(newGainer);
        }
    }

    public void setCombinedButtons(boolean b) {
        combinedButtons = b;
    }

    public boolean getCombinedButtons() {
        return combinedButtons;
    }

    public void setClickValue(float amount) {
        clickValue = amount;
    }

    public float getClickValue(){
        return clickValue;
    }


}