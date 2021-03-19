package no.ntnu.tdt4240.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

import no.ntnu.tdt4240.game.components.PlayerComponent;
import no.ntnu.tdt4240.game.components.ResourceGainerComponent;

public class ResourceGainSystem extends EntitySystem {
    private ImmutableArray<Entity> playerEntities;
    private ImmutableArray<Entity> resourceGainers;

    private Engine engine;

    private ComponentMapper<PlayerComponent> pm = ComponentMapper.getFor(PlayerComponent.class);
    private ComponentMapper<ResourceGainerComponent> rgm = ComponentMapper.getFor(ResourceGainerComponent.class);

    public void addedToEngine(Engine engine) {
        playerEntities = engine.getEntitiesFor(Family.all(PlayerComponent.class).get());
        resourceGainers = engine.getEntitiesFor(Family.all(ResourceGainerComponent.class).get());
    }

    public void update(float deltaTime) {
        for (Entity player : playerEntities) {
            PlayerComponent pc = pm.get(player);
            //pc.updateScore(1f * deltaTime);
        }
    }

    public void addResourceGainerToPlayer(ResourceGainerComponent rg, PlayerComponent player) {
        //player.addResourceGainer(rg);
    }

}
