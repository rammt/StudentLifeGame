package no.ntnu.tdt4240.game.components;

import com.badlogic.ashley.core.Component;

public class ResourceGainerComponent implements Component {
    private float gainPerSecond;

    public ResourceGainerComponent create(float gainPerSecond){
        this.gainPerSecond = gainPerSecond;
        return this;
    }

    public float getGainPerSecond() {
        return gainPerSecond;
    }
}
