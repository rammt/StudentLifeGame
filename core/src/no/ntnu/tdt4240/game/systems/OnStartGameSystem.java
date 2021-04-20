package no.ntnu.tdt4240.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;

import no.ntnu.tdt4240.game.FirebaseInterface;
import no.ntnu.tdt4240.game.components.PlayerComponent;
import no.ntnu.tdt4240.game.components.ResourceGainerComponent;

public class OnStartGameSystem extends EntitySystem {
    private FirebaseInterface firebase;
    private ImmutableArray<Entity> player;

    private ComponentMapper<PlayerComponent> pcm = ComponentMapper.getFor(PlayerComponent.class);
    private ComponentMapper<ResourceGainerComponent> rgcm = ComponentMapper.getFor(ResourceGainerComponent.class);


    public OnStartGameSystem(FirebaseInterface firebase) {
        this.firebase = firebase;
    }

    @Override
    public void addedToEngine(Engine engine) {

    }

    @Override
    public void removedFromEngine(Engine engine) {

    }

    public void offlineStart(Engine engine) {
        Entity player = engine.createEntity();
        SavingSystem ss = engine.getSystem(SavingSystem.class);
        PlayerComponent pc;

        if (ss.isLocalData()) {
            pc = ss.getDataFromStorage();
        } else {
            pc = new PlayerComponent().create();
        }

        player.add(pc);
        engine.addEntity(player);
        ss.addPlayer(player);
    }

    public void onlineStart(Engine engine) {
        Entity player = engine.createEntity();
        player.add(new PlayerComponent().create());
        SavingSystem ss = engine.getSystem(SavingSystem.class);

        firebase.getPlayerStats(player);
        engine.addEntity(player);
        ss.addPlayer(player);
    }


    /*private boolean isOfflineStart() {
        Entity player =
        firebase.getStats(Pla);

        return true;
    }*/

    /*private void addOfflineResources() {
        long secondsSinceSave = (new Date().getTime() - player.getLastSave())/1000;

        if (secondsSinceSave > 10) {
            engine.getSystem(ResourceGainSystem.class).addOfflineResource(secondsSinceSave);
        }
    }*/
}




