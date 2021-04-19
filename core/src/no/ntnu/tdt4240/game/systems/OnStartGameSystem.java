package no.ntnu.tdt4240.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

import java.util.Date;

import no.ntnu.tdt4240.game.FirebaseInterface;
import no.ntnu.tdt4240.game.components.ButtonComponent;
import no.ntnu.tdt4240.game.components.GameComponent;
import no.ntnu.tdt4240.game.components.PlayerComponent;
import no.ntnu.tdt4240.game.components.ResourceGainerComponent;
import no.ntnu.tdt4240.game.components.TextFieldComponent;

public class OnStartGameSystem extends EntitySystem {
    private FirebaseInterface firebase;
    private ImmutableArray<Entity> players;
    private ImmutableArray<Entity> resourceGainers;

    private ComponentMapper<PlayerComponent> pcm = ComponentMapper.getFor(PlayerComponent.class);
    private ComponentMapper<ResourceGainerComponent> rgcm = ComponentMapper.getFor(ResourceGainerComponent.class);


    public OnStartGameSystem(FirebaseInterface firebase) {
        this.firebase = firebase;
    }

    @Override
    public void addedToEngine(Engine engine) {

        Entity player = engine.createEntity();

        /*
        if (existsLocalData()) {
            float kokCount = prefs.get("kokCount");
            player.add(new PlayerComponent().create(kokCount));
        }*/

        player.add(new PlayerComponent().create());
        engine.addEntity(player);

        /*if (isOfflineStart()) {

        }*/

    }

    @Override
    public void removedFromEngine(Engine engine) {

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




