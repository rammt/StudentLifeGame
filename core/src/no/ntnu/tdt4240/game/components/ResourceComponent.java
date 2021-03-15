package no.ntnu.tdt4240.game.components;

import com.badlogic.ashley.core.Component;

public class ResourceComponent implements Component {
    private String name;

    public ResourceComponent create(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }
}
