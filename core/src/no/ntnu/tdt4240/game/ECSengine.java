package no.ntnu.tdt4240.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.List;
import java.util.Map;

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

    public ECSengine(ShapeRenderer shapeRenderer, BitmapFont font, SpriteBatch batch, Stage stage, Firebase firebase){
        super();

        engine = new PooledEngine();

        engine.addSystem(new AudioSystem());
        engine.addSystem(new RenderSystem(shapeRenderer, font, batch, stage));
        engine.addSystem(new ResourceGainSystem());
        engine.addSystem(new SavingSystem());
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
}
