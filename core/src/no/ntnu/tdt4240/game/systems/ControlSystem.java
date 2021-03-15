package no.ntnu.tdt4240.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;

import no.ntnu.tdt4240.game.components.ButtonComponent;
import no.ntnu.tdt4240.game.components.PlayerComponent;
import no.ntnu.tdt4240.game.components.ResourceGainer;

public class ControlSystem extends EntitySystem {
    private ImmutableArray<Entity> playerEntities;
    private ImmutableArray<Entity> resourceGainers;
    private ImmutableArray<Entity> buttons;

    private ComponentMapper<PlayerComponent> pm = ComponentMapper.getFor(PlayerComponent.class);
    private ComponentMapper<ButtonComponent> bm = ComponentMapper.getFor(ButtonComponent.class);

    public void addedToEngine(Engine engine){
        playerEntities = engine.getEntitiesFor(Family.all(PlayerComponent.class).get());
        resourceGainers = engine.getEntitiesFor(Family.all(ResourceGainer.class).get());
        buttons = engine.getEntitiesFor(Family.all(ButtonComponent.class).get());
    }

    public void update(float deltaTime){
        if(Gdx.input.justTouched()){
            for (Entity player : playerEntities){
                PlayerComponent p = pm.get(player);
                p.buttonPress();
            }
        }
    }
}
