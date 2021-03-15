package no.ntnu.tdt4240.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

import no.ntnu.tdt4240.game.components.GameComponent;
import no.ntnu.tdt4240.game.components.PlayerComponent;

public class GameSystem extends EntitySystem {
    private ImmutableArray<Entity> players;
    private ImmutableArray<Entity> games;

    private Engine engine;

    private ComponentMapper<PlayerComponent> pm = ComponentMapper.getFor(PlayerComponent.class);
    private ComponentMapper<GameComponent> gm = ComponentMapper.getFor(GameComponent.class);


    public void addedToEngine(Engine engine){
        players = engine.getEntitiesFor(Family.all(PlayerComponent.class).get());
        games = engine.getEntitiesFor(Family.all(GameComponent.class).get());
    }

    public void update(float deltaTime){

    }
}
