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
import com.badlogic.gdx.scenes.scene2d.Stage;

import no.ntnu.tdt4240.game.components.GameComponent;
import no.ntnu.tdt4240.game.components.PlayerComponent;
import no.ntnu.tdt4240.game.components.ResourceGainerComponent;
import no.ntnu.tdt4240.game.components.TextFieldComponent;

public class RenderSystem extends EntitySystem {
    private ImmutableArray<Entity> playerEntities;
    private ImmutableArray<Entity> resourceGainers;
    private ImmutableArray<Entity> buttonEntities;
    private ImmutableArray<Entity> games;
    private ImmutableArray<Entity> textFieldComponents;

    private final BitmapFont fontRenderer;
    private final SpriteBatch spriteBatch;
    private final ShapeRenderer shapeRenderer;
    private final Stage stage;

    private ComponentMapper<PlayerComponent> pm = ComponentMapper.getFor(PlayerComponent.class);
    private ComponentMapper<ResourceGainerComponent> rgm = ComponentMapper.getFor(ResourceGainerComponent.class);
    private ComponentMapper<GameComponent> gm = ComponentMapper.getFor(GameComponent.class);
    private ComponentMapper<TextFieldComponent> tf = ComponentMapper.getFor(TextFieldComponent.class);


    public RenderSystem(ShapeRenderer sr, BitmapFont fr, SpriteBatch sb, Stage stage) {
        spriteBatch = sb;
        fontRenderer = fr;
        shapeRenderer = sr;
        this.stage = stage;
    }

    @Override
    public void addedToEngine(Engine engine) {
        playerEntities = engine.getEntitiesFor(Family.all(PlayerComponent.class).get());
        resourceGainers = engine.getEntitiesFor(Family.all(ResourceGainerComponent.class).get());
        games = engine.getEntitiesFor(Family.all(GameComponent.class).get());
        textFieldComponents = engine.getEntitiesFor(Family.all(TextFieldComponent.class).get());
    }

    @Override
    public void removedFromEngine(Engine engine) {

    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        for (Entity game : games) {
            GameComponent gameComponent = gm.get(game);
            if (gameComponent.gameState == GameComponent.GameState.GAME_PLAYING) {

                for (Entity textField : textFieldComponents) {
                    stage.addActor(textField.getComponent(TextFieldComponent.class).getTextFieldComponent());
                }

                shapeRenderer.end();
                spriteBatch.begin();
                spriteBatch.end();

                stage.act();
                stage.draw();
            }



        }
    }
}




