package no.ntnu.tdt4240.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Array;


public class PlayerComponent implements Component {
    private String name;
    private float score = 0;
    public int playerNum;
    private Array<ResourceGainer> resourceGainers = new Array<ResourceGainer>();

    public PlayerComponent create(String name, int num){
        this.name = name;
        playerNum = num;
        return this;
    }
    public PlayerComponent create(String name, int num, ResourceGainer rg){
        this.name = name;
        playerNum = num;
        resourceGainers.add(rg);
        return this;
    }

    public String getName(){
        return name;
    }
    public float getScore(){
        return score;
    }
    public PlayerComponent updateScore(float num){
        if(!resourceGainers.isEmpty()){
            for(ResourceGainer rg : resourceGainers){
                num += rg.getGainPerSecond();
            }
        }
        score += num;
        return this;
    }

    public void addResourceGainer(ResourceGainer rg){
        if(score >= rg.getPrice()){
            score -= rg.getPrice();
            resourceGainers.add(rg);
        }
    }

    public void buttonPress(){
        score += 3f;
    }
}
