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
import no.ntnu.tdt4240.game.components.ButtonComponent;
import no.ntnu.tdt4240.game.components.GameComponent;
import no.ntnu.tdt4240.game.components.PlayerComponent;
import no.ntnu.tdt4240.game.components.ResourceComponent;
import no.ntnu.tdt4240.game.components.ResourceGainerComponent;
import no.ntnu.tdt4240.game.components.StatsComponent;
import no.ntnu.tdt4240.game.systems.AudioSystem;
import no.ntnu.tdt4240.game.systems.ControlSystem;
import no.ntnu.tdt4240.game.systems.RenderSystem;
import no.ntnu.tdt4240.game.systems.ResourceGainSystem;
import no.ntnu.tdt4240.game.screens.StartScreen;

//Skal bare inneholde ting som skal kjøres fra start, knapper ogsånt legges til i screens
public class ECSengine{

    private PooledEngine engine;
    private Entity game;

    public ECSengine(ShapeRenderer shapeRenderer, BitmapFont font, SpriteBatch batch, Stage stage){
        super();

        engine = new PooledEngine();

        engine.addSystem(new AudioSystem());
        engine.addSystem(new RenderSystem(shapeRenderer, font, batch, stage));
        engine.addSystem(new ResourceGainSystem());
        engine.addSystem(new ControlSystem());

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

        Entity resourceGainer = engine.createEntity();
        resourceGainer.add(new ResourceGainerComponent().create(3));
        engine.addEntity(resourceGainer);

        Entity localPlayer = engine.createEntity();
        localPlayer.add(new PlayerComponent().create("Insert name", 1));
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

}
