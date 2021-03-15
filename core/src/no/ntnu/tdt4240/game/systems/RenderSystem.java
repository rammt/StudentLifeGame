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

import no.ntnu.tdt4240.game.components.ButtonComponent;
import no.ntnu.tdt4240.game.components.PlayerComponent;
import no.ntnu.tdt4240.game.components.ResourceGainer;

public class RenderSystem extends EntitySystem {
    private ImmutableArray<Entity> playerEntities;
    private ImmutableArray<Entity> resourceGainers;
    private ImmutableArray<Entity> buttons;

    private final BitmapFont fontRenderer;
    private final SpriteBatch spriteBatch;
    private final ShapeRenderer shapeRenderer;

    private ComponentMapper<PlayerComponent> pm = ComponentMapper.getFor(PlayerComponent.class);
    private ComponentMapper<ResourceGainer> rgm = ComponentMapper.getFor(ResourceGainer.class);


    public RenderSystem(ShapeRenderer sr, BitmapFont fr, SpriteBatch sb){
        spriteBatch = sb;
        fontRenderer = fr;
        shapeRenderer = sr;
    }

    @Override
    public void addedToEngine(Engine engine){
        playerEntities = engine.getEntitiesFor(Family.all(PlayerComponent.class).get());
        resourceGainers = engine.getEntitiesFor(Family.all(ResourceGainer.class).get());
        buttons = engine.getEntitiesFor(Family.all(ButtonComponent.class).get());
    }

    @Override
    public void removedFromEngine (Engine engine){

    }

    @Override
    public void update(float deltaTime){
        super.update(deltaTime);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for(Entity button : buttons){
            Rectangle bt = button.getComponent(ButtonComponent.class).button;
            shapeRenderer.rect(bt.x,bt.y,bt.width,bt.height);
        }
        //ResourceGainer rg1 = rgm.get(resourceGainers.get(0));

        float p1Score = 0;
        float p2Score = 0;

        for(Entity player : playerEntities){
            PlayerComponent pc = pm.get(player);
            if(pc.playerNum == 1) p1Score = pc.getScore();
            if(pc.playerNum == 2) p2Score = pc.getScore();
        }
        String p1NumberAsString = String.format("%.2f",p1Score);
        String p2NumberAsString = String.format("%.2f",p2Score);

        shapeRenderer.end();
        spriteBatch.begin();
        fontRenderer.draw(spriteBatch, "$ " + p1NumberAsString + ",  $ " + p2NumberAsString, 250,250);
        spriteBatch.end();
    }

}
