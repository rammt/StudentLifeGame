package no.ntnu.tdt4240.game.systems;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import no.ntnu.tdt4240.game.components.GameComponent;
import no.ntnu.tdt4240.game.components.PlayerComponent;

public class RenderSystem extends EntitySystem {
    private ImmutableArray<Entity> playerEntities;
    private ImmutableArray<Entity> games;

    private final BitmapFont fontRenderer;
    private final SpriteBatch spriteBatch;

    private ComponentMapper<PlayerComponent> pm = ComponentMapper.getFor(PlayerComponent.class);
    private ComponentMapper<GameComponent> gm = ComponentMapper.getFor(GameComponent.class);

    public RenderSystem(SpriteBatch sb, BitmapFont fr){
        spriteBatch = sb;
        fontRenderer = fr;
    }

    @Override
    public void addedToEngine(Engine engine){
        playerEntities = engine.getEntitiesFor(Family.all(PlayerComponent.class).get());
        games = engine.getEntitiesFor(Family.all(GameComponent.class).get());
    }

    @Override
    public void removedFromEngine (Engine engine){

    }

    @Override
    public void update(float deltaTime){
        super.update(deltaTime);

        float playerScore = 0;
        for(Entity player : playerEntities){
            PlayerComponent pc = pm.get(player);
            pc.updateScore(0.2f);
            playerScore = pc.getScore();
        }
        String numberAsString = String.format("%.2f",playerScore);

        spriteBatch.begin();
        fontRenderer.draw(spriteBatch, "$ " + numberAsString, 250,250);
        spriteBatch.end();
    }

}
