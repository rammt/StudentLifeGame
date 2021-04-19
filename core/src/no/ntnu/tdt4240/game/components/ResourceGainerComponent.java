package no.ntnu.tdt4240.game.components;

import com.badlogic.ashley.core.Component;

public class ResourceGainerComponent implements Component {
    private String name;
    private int price;
    private float gainPerSecond;

    public ResourceGainerComponent create(String name, int price, float gainPerSecond){
        this.name = name;
        this.price = price;
        this.gainPerSecond = gainPerSecond;
        return this;
    }

    public float getGainPerSecond() {
        return gainPerSecond;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}
