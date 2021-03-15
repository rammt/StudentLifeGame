package no.ntnu.tdt4240.game.components;

import com.badlogic.ashley.core.Component;

public class PlayerComponent implements Component {
    private String name;
    private float score = 0;

    public PlayerComponent create(String name){
        this.name = name;
        return this;
    }
    public float getScore(){
        return score;
    }
    public PlayerComponent updateScore(float num){
        if(num > 0){
            score += num;
        }
        return this;
    }
}
