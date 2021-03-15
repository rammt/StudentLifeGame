package no.ntnu.tdt4240.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

import no.ntnu.tdt4240.game.components.ButtonComponent;
import no.ntnu.tdt4240.game.components.PlayerComponent;
import no.ntnu.tdt4240.game.components.ResourceGainerComponent;

public class ControlSystem extends EntitySystem {
    private ImmutableArray<Entity> playerEntities;
    private ImmutableArray<Entity> resourceGainers;
    private ImmutableArray<Entity> buttons;

    private ComponentMapper<PlayerComponent> pm = ComponentMapper.getFor(PlayerComponent.class);
    private ComponentMapper<ButtonComponent> bm = ComponentMapper.getFor(ButtonComponent.class);

    public void addedToEngine(Engine engine){
        playerEntities = engine.getEntitiesFor(Family.all(PlayerComponent.class).get());
        resourceGainers = engine.getEntitiesFor(Family.all(ResourceGainerComponent.class).get());
        buttons = engine.getEntitiesFor(Family.all(ButtonComponent.class).get());
    }

    public void update(float deltaTime){
        if(Gdx.input.justTouched()){
            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(),0);
            for( Entity button : buttons){
                ButtonComponent btn = bm.get(button);
                if(btn.button.contains(touch.x,Gdx.graphics.getHeight()-touch.y)){
                    for (Entity player : playerEntities){
                        PlayerComponent p = pm.get(player);
                        p.buttonPress();
                    }
                }
            }

        }
    }
}
