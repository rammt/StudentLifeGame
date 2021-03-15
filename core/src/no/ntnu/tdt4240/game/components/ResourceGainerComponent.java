package no.ntnu.tdt4240.game.components;

import com.badlogic.ashley.core.Component;

public class ResourceGainerComponent implements Component {
    private ResourceComponent resource;
    private float gainPerSecond;
    private float price;
    public ResourceGainerComponent create(float gps, int num){
        gainPerSecond = gps;
        price = num;
        return this;
    }

    public float getGainPerSecond() {
        return gainPerSecond;
    }
    public float getPrice() {
        return price;
    }
}
