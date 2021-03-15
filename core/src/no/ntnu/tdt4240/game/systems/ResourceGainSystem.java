package no.ntnu.tdt4240.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

import no.ntnu.tdt4240.game.components.PlayerComponent;
import no.ntnu.tdt4240.game.components.ResourceGainer;

public class ResourceGainSystem extends EntitySystem {
    private ImmutableArray<Entity> playerEntities;
    private ImmutableArray<Entity> resourceGainers;

    private Engine engine;

    private ComponentMapper<PlayerComponent> pm = ComponentMapper.getFor(PlayerComponent.class);
    private ComponentMapper<ResourceGainer> rgm = ComponentMapper.getFor(ResourceGainer.class);

    public void addedToEngine(Engine engine){
        playerEntities = engine.getEntitiesFor(Family.all(PlayerComponent.class).get());
        resourceGainers = engine.getEntitiesFor(Family.all(ResourceGainer.class).get());
    }

    public void update(float deltaTime){
        for(Entity player : playerEntities){
            PlayerComponent pc = pm.get(player);
            //ResourceGainer rg1 = rgm.get(resourceGainers.get(0));

            /*ResourceGainer rg = new ResourceGainer();
            for(Entity res : resourceGainers){
                rg = rgm.get(res);
            }*/

            //addResourceGainerToPlayer(rg1, pc);
            pc.updateScore(0.2f);
        }
    }

    public void addResourceGainerToPlayer(ResourceGainer rg, PlayerComponent player){
        player.addResourceGainer(rg);
    }

}
