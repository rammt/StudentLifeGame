package no.ntnu.tdt4240.game.components;

import com.badlogic.ashley.core.Component;

public class ResourceGainer implements Component {
    private ResourceComponent resource;
    public int resourceGainerNum;
    private float gainPerSecond;
    public ResourceGainer create(float gps, int num){
        gainPerSecond = gps;
        resourceGainerNum = num;
        return this;
    }

    public float getGainPerSecond() {
        return gainPerSecond;
    }
}
