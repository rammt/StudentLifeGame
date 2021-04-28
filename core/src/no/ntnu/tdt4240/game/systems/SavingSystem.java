package no.ntnu.tdt4240.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

import java.util.Date;

import no.ntnu.tdt4240.game.Firebase;
import no.ntnu.tdt4240.game.components.PlayerComponent;

public class SavingSystem extends EntitySystem {
    private Firebase firebase;
    private ImmutableArray<Entity> player;
    private float counter;
    private final Preferences offlinePlayer = Gdx.app.getPreferences("offlineStorage");
    private final Json json = new Json();

    private final ComponentMapper<PlayerComponent> pcm = ComponentMapper.getFor(PlayerComponent.class);

    @Override
    public void addedToEngine(Engine engine) {
        //player = engine.getEntitiesFor(Family.all(PlayerComponent.class).get());
    }

    @Override
    public void removedFromEngine(Engine engine) {

    }

    public void addPlayer(Entity playerToAdd) {
        Array<Entity> entityArray = new Array<>();
        entityArray.add(playerToAdd);
        player = new ImmutableArray<>(entityArray);
    }

    public void update(float deltaTime) {
        counter += deltaTime;

        if (counter > 60) {
            saveOffline();
            counter = 0;
        }
    }

    public void saveOffline() {
        PlayerComponent player_pc = pcm.get(player.get(0));

        if (player_pc != null) {
            player_pc.setLastSave(new Date().getTime());
            String playerData = json.toJson(player_pc);

            offlinePlayer.putString("playerData", playerData);
            offlinePlayer.flush();
        }
    }

    public PlayerComponent getDataFromStorage() {
        String offlinePlayerJson = offlinePlayer.getString("playerData");

        return json.fromJson(PlayerComponent.class, offlinePlayerJson);

    }

    public boolean isLocalData() {
        return !offlinePlayer.getString("playerData", "noData").equals("noData");
    }
}




