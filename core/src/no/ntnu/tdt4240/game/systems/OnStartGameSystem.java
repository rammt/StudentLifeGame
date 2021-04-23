package no.ntnu.tdt4240.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.async.AsyncExecutor;
import com.badlogic.gdx.utils.async.AsyncResult;
import com.badlogic.gdx.utils.async.AsyncTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import no.ntnu.tdt4240.game.Firebase;
import no.ntnu.tdt4240.game.StudentLifeGame;
import no.ntnu.tdt4240.game.components.PlayerComponent;
import no.ntnu.tdt4240.game.components.ResourceGainerComponent;

public class OnStartGameSystem extends EntitySystem {
    private Firebase firebase;
    private ImmutableArray<Entity> player;

    private ComponentMapper<PlayerComponent> pcm = ComponentMapper.getFor(PlayerComponent.class);
    private ComponentMapper<ResourceGainerComponent> rgcm = ComponentMapper.getFor(ResourceGainerComponent.class);


    public OnStartGameSystem(Firebase firebase) {
        this.firebase = firebase;
    }

    @Override
    public void addedToEngine(Engine engine) {

    }

    @Override
    public void removedFromEngine(Engine engine) {

    }

    public Entity getOfflinePlayer(Engine engine) {
        Entity player = engine.createEntity();
        SavingSystem ss = engine.getSystem(SavingSystem.class);
        PlayerComponent pc;

        if (ss.isLocalData()) {
            pc = ss.getDataFromStorage();
        } else {
            pc = new PlayerComponent().create();
        }

        player.add(pc);

        return player;
    }

    public Entity getOnlinePlayer(Engine engine) {
        Entity player = engine.createEntity();
        player.add(new PlayerComponent().create());
        SavingSystem ss = engine.getSystem(SavingSystem.class);

        firebase.getPlayerStats(player);

        return player;
    }

    public void startGameWithPlayer(final StudentLifeGame game, Entity player) {
        final List<Map<String, Object>> resourceGainers = new ArrayList<>();

        firebase.getResourceGainers(resourceGainers);

        for (Map<String, Object> gainer : resourceGainers) {
            System.out.println("Adding gainer: " + gainer.get("id"));
            Entity newGainer = game.getEngine().createEntity();
            newGainer.add(new ResourceGainerComponent().create((String) gainer.get("id"), (String) gainer.get("name"), (int) gainer.get("price"), (float) gainer.get("gain_per_second")));
            game.getEngine().addEntity(newGainer);
        }

        SavingSystem ss = game.getEngine().getSystem(SavingSystem.class);
        game.getEngine().addEntity(player);
        ss.addPlayer(player);
        addOfflineResources(player.getComponent(PlayerComponent.class), game.getEngine());

    }

    private void addOfflineResources(PlayerComponent player_pc, Engine engine) {
        long secondsSinceSave = (new Date().getTime() - player_pc.getLastSave())/1000;

        if (secondsSinceSave > 10) {
            engine.getSystem(ResourceGainSystem.class).addResourcesByTime(secondsSinceSave);
        }
    }
}




