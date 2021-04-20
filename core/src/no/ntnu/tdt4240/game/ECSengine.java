package no.ntnu.tdt4240.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import no.ntnu.tdt4240.game.components.GameComponent;
import no.ntnu.tdt4240.game.systems.AudioSystem;
import no.ntnu.tdt4240.game.systems.OnStartGameSystem;
import no.ntnu.tdt4240.game.systems.RenderSystem;
import no.ntnu.tdt4240.game.systems.ResourceGainSystem;
import no.ntnu.tdt4240.game.systems.SavingSystem;

//Skal bare inneholde ting som skal kjøres fra start, knapper ogsånt legges til i screens
public class ECSengine{

    private PooledEngine engine;
    private Entity game;
    private FirebaseInterface firebase;

    public ECSengine(ShapeRenderer shapeRenderer, BitmapFont font, SpriteBatch batch, Stage stage, FirebaseInterface firebase){
        super();

        this.firebase = firebase;

        engine = new PooledEngine();

        engine.addSystem(new AudioSystem());
        engine.addSystem(new RenderSystem(shapeRenderer, font, batch, stage));
        engine.addSystem(new ResourceGainSystem());
        engine.addSystem(new SavingSystem());
        engine.addSystem(new OnStartGameSystem(firebase));

        game = engine.createEntity();
        game.add(new GameComponent().create());
        engine.addEntity(game);

        Entity resourceKok = engine.createEntity();
        resourceKok.add(new ResourceComponent().create("kokere",10));
        engine.addEntity(resourceKok);

        Entity resourceStud = engine.createEntity();
        resourceStud.add(new ResourceComponent().create("kokere",50));
        engine.addEntity(resourceStud);

        Entity resourceScripter = engine.createEntity();
        resourceScripter.add(new ResourceComponent().create("kokere",100));
        engine.addEntity(resourceScripter);

        Entity resourceHacker = engine.createEntity();
        resourceHacker.add(new ResourceComponent().create("kokere",500));
        engine.addEntity(resourceHacker);


        Entity localPlayer = engine.createEntity();
        localPlayer.add(new PlayerComponent().create());
        localPlayer.add(new StatsComponent().create(1));
        engine.addEntity(localPlayer);


        game.getComponent(GameComponent.class).setState(GameComponent.GameState.GAME_PLAYING);
    }

    public PooledEngine getEngine(){
        return engine;
    }

    public Entity getGame(){
        return game;
    }

    public FirebaseInterface getFirebase() {
        return firebase;
    }
}
