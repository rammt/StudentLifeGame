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

    public OnStartGameSystem(Firebase firebase) {
        this.firebase = firebase;
    }

    @Override
    public void addedToEngine(Engine engine) { }

    @Override
    public void removedFromEngine(Engine engine) { }

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
        //TODO make the commented code work instead
        /*final List<Map<String, Object>> resourceGainers = new ArrayList<>();

        firebase.getResourceGainers(resourceGainers);

        for (Map<String, Object> gainer : resourceGainers) {
            System.out.println("Adding gainer: " + gainer.get("id"));
            Entity newGainer = game.getEngine().createEntity();
            newGainer.add(new ResourceGainerComponent().create((String) gainer.get("id"), (String) gainer.get("name"), (int) gainer.get("price"), (float) gainer.get("gain_per_second")));
            game.getEngine().addEntity(newGainer);
        }*/

        Entity studass = game.getEngine().createEntity();
        studass.add(new ResourceGainerComponent().create("rg_studass", "Koking Student Assistant", 20, 1));
        game.getEngine().addEntity(studass);

        Entity professor = game.getEngine().createEntity();
        professor.add(new ResourceGainerComponent().create("rg_prof", "Professor Koker", 250, 10));
        game.getEngine().addEntity(professor);

        Entity script = game.getEngine().createEntity();
        script.add(new ResourceGainerComponent().create("rg_script", "Kok-generating Script", 1000, 50));
        game.getEngine().addEntity(script);

        Entity ai = game.getEngine().createEntity();
        ai.add(new ResourceGainerComponent().create("rg_ai", "Kok-generating AI", 10000, 200));
        game.getEngine().addEntity(ai);

        Entity alien = game.getEngine().createEntity();
        alien.add(new ResourceGainerComponent().create("rg_alien", "Alien Koking Lifeform", 50000, 1000));
        game.getEngine().addEntity(alien);

        Entity nuclear = game.getEngine().createEntity();
        nuclear.add(new ResourceGainerComponent().create("rg_nuclear", "Nuclear Powered Koking Plant", 250000, 2500));
        game.getEngine().addEntity(nuclear);

        Entity dyson = game.getEngine().createEntity();
        dyson.add(new ResourceGainerComponent().create("rg_dyson", "Kok-generating Dyson Sphere", 1000000000, 50000));
        game.getEngine().addEntity(dyson);

        SavingSystem ss = game.getEngine().getSystem(SavingSystem.class);
        game.getEngine().addEntity(player);
        ss.addPlayer(player);
        addOfflineResources(player.getComponent(PlayerComponent.class), game.getEngine());

    }

    private void addOfflineResources(PlayerComponent player_pc, Engine engine) {
        long playerLastSave = player_pc.getLastSave();
        if (playerLastSave > 10000) { //Not first startup, avoids new players getting a lot of kok
            long secondsSinceSave = (new Date().getTime() - playerLastSave) / 1000;

            if (secondsSinceSave > 10) {
                engine.getSystem(ResourceGainSystem.class).addResourcesByTime(secondsSinceSave);
            }
        }
    }
}




