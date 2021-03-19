package no.ntnu.tdt4240.game.components;

import com.badlogic.ashley.core.Component;

public class ResourceComponent implements Component {
    private String name;
    private int price;

    public ResourceComponent create(String name, int price) {
        this.name = name;
        this.price = price;
        return this;
    }

    public String getName() {
        return name;
    }
    public int getPrice() {
        return price;
    }
}
