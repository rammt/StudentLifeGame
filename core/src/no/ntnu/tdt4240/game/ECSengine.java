package no.ntnu.tdt4240.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Date;

import no.ntnu.tdt4240.game.components.ButtonComponent;
import no.ntnu.tdt4240.game.components.GameComponent;
import no.ntnu.tdt4240.game.components.PlayerComponent;
import no.ntnu.tdt4240.game.components.ResourceComponent;
import no.ntnu.tdt4240.game.components.ResourceGainerComponent;
import no.ntnu.tdt4240.game.components.StatsComponent;
import no.ntnu.tdt4240.game.systems.AudioSystem;
import no.ntnu.tdt4240.game.systems.ControlSystem;
import no.ntnu.tdt4240.game.systems.OnStartGameSystem;
import no.ntnu.tdt4240.game.systems.RenderSystem;
import no.ntnu.tdt4240.game.systems.ResourceGainSystem;
import no.ntnu.tdt4240.game.screens.StartScreen;

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
        engine.addSystem(new ControlSystem());
        engine.addSystem(new OnStartGameSystem(firebase));

        game = engine.createEntity();
        game.add(new GameComponent().create());
        engine.addEntity(game);

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
