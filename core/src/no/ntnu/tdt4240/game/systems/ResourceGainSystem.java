package no.ntnu.tdt4240.game.systems;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;

import no.ntnu.tdt4240.game.components.PlayerComponent;
import no.ntnu.tdt4240.game.components.ResourceGainerComponent;

public class ResourceGainSystem extends EntitySystem {

    private ImmutableArray<Entity> player;
    private ComponentMapper<PlayerComponent> pm = ComponentMapper.getFor(PlayerComponent.class);

    public void addedToEngine(Engine engine) {
        player = engine.getEntitiesFor(Family.all(PlayerComponent.class).get());
    }

    public void update(float deltaTime) {
        addResourcesByTime(deltaTime);
    }

    private float calculateNewResourceGain(float gainIncrement) {
        float addedResources = 0;
        PlayerComponent pc = pm.get(player.get(0));

        for (ResourceGainerComponent rg :  pc.getResourceGainers()) {
            addedResources += rg.getGainPerSecond() * gainIncrement;
        }

        return addedResources;
    }

    public float addResourcesByTime(float time) {
        PlayerComponent pc = pm.get(player.get(0));
        float newResources = calculateNewResourceGain(time);
        pc.setKokCount(pc.getKokCount() + newResources);
        return newResources;
    }

    public int countResourceGainers(ResourceGainerComponent rg) {

        int resourceGainerCounter = 0;
        PlayerComponent pc = pm.get(player.get(0));

        for (ResourceGainerComponent p_rg : pc.getResourceGainers()) {
            if (p_rg.getName().equals(rg.getName())) {
                resourceGainerCounter++;
            }
        }

        return resourceGainerCounter;
    }

}
