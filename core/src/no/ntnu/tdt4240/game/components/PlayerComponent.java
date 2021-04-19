package no.ntnu.tdt4240.game.components;

import com.badlogic.ashley.core.Component;

import no.ntnu.tdt4240.game.Player;


public class PlayerComponent implements Component {
    private Player player;

    public PlayerComponent create(Player player) {
        this.player = player;
        return this;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
