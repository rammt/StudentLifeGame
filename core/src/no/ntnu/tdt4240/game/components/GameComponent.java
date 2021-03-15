package no.ntnu.tdt4240.game.components;

import com.badlogic.ashley.core.Component;

public class GameComponent implements Component {
    public enum GameState{
        GAME_START,
        GAME_STOP
    }
    public GameState gameState;

    public GameComponent create(){
        this.gameState = GameState.GAME_START;
        return this;
    }
    public GameComponent setState(GameState state){
        this.gameState = state;
        return this;
    }
}
