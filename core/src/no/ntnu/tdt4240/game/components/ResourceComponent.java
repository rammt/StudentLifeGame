package no.ntnu.tdt4240.game.components;

import com.badlogic.ashley.core.Component;

public class ResourceComponent implements Component {
    private String name;
    private int count;

    public ResourceComponent create(String name, int count) {
        this.name = name;
        this.count = count;
        return this;
    }

    public String getName() {
        return name;
    }
    public int getCount() {
        return count;
    }
}
