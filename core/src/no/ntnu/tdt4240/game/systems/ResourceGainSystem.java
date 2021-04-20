package no.ntnu.tdt4240.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Timer;

import no.ntnu.tdt4240.game.components.PlayerComponent;
import no.ntnu.tdt4240.game.components.ResourceComponent;
import no.ntnu.tdt4240.game.components.ResourceGainerComponent;

public class ResourceGainSystem extends EntitySystem {
    private ImmutableArray<Entity> player;
    private ImmutableArray<Entity> resourceGainers;

    private ComponentMapper<PlayerComponent> pm = ComponentMapper.getFor(PlayerComponent.class);
    private ComponentMapper<ResourceGainerComponent> rgm = ComponentMapper.getFor(ResourceGainerComponent.class);

    public void addedToEngine(Engine engine) {
        player = engine.getEntitiesFor(Family.all(PlayerComponent.class).get());
        resourceGainers = engine.getEntitiesFor(Family.all(ResourceGainerComponent.class).get());
    }

    public void update(float deltaTime) {
        addResourcesByTime(deltaTime);
    }

    private float calculateNewResourceGain(float gainIncrement) {
        float addedResources = 0;

        for (Entity resourceGainer : resourceGainers) {
            ResourceGainerComponent rgc = rgm.get(resourceGainer);
            addedResources += rgc.getGainPerSecond() * gainIncrement;
        }

        return addedResources;
    }


    private void addResourcesByTime(float time) {
        for (Entity player : player) {
            PlayerComponent pc = pm.get(player);

            pc.setKokCount(pc.getKokCount() + calculateNewResourceGain(time));
        }
    }


    public void addOfflineResource(float secondsSinceSave) {
        addResourcesByTime(secondsSinceSave);
    }
}
