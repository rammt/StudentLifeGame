package no.ntnu.tdt4240.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Array;


public class PlayerComponent implements Component {
    private String name;
    public int playerId;

    //burde assigne id på en annen måte, men enn så lenge
    public PlayerComponent create(String name, int id) {
        this.name = name;
        playerId = id;
        return this;
    }

    public String getName() {
        return name;
    }
    public int getPlayerId() {
        return playerId;
    }
}
