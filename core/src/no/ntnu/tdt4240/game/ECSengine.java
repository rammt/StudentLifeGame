package no.ntnu.tdt4240.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import no.ntnu.tdt4240.game.components.GameComponent;
import no.ntnu.tdt4240.game.components.ResourceGainerComponent;
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
    private Entity gainerKoker, gainerStudass, gainerHacker, gainerScripts;

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

        gainerKoker = engine.createEntity();
        gainerKoker.add(new ResourceGainerComponent().create("Kokere","Random kokere",50,2));
        engine.addEntity(gainerKoker);

        gainerStudass = engine.createEntity();
        gainerStudass.add(new ResourceGainerComponent().create("Studass","Studass kokere",200,10));
        engine.addEntity(gainerStudass);

        gainerScripts = engine.createEntity();
        gainerScripts.add(new ResourceGainerComponent().create("Scripts","Kok script",500,25));
        engine.addEntity(gainerScripts);

        gainerHacker = engine.createEntity();
        gainerHacker.add(new ResourceGainerComponent().create("Hackere","Hacker kokere",1000,100));
        engine.addEntity(gainerHacker);

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
