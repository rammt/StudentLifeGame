package no.ntnu.tdt4240.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import no.ntnu.tdt4240.game.components.PlayerComponent;
import no.ntnu.tdt4240.game.systems.RenderSystem;

public class StudentLifeGame extends Game {
	private float SCREEN_WIDTH;
	private float SCREEN_HEIGHT;

	private PooledEngine engine;

	private Entity game;

	private BitmapFont font;
	private SpriteBatch batch;
	
	@Override
	public void create(){
		SCREEN_WIDTH = Gdx.graphics.getWidth();
		SCREEN_HEIGHT = Gdx.graphics.getHeight();

		engine = new PooledEngine();

		batch = new SpriteBatch();
		font = new BitmapFont();

		engine.addSystem(new RenderSystem(batch, font));

		//game = engine.createEntity();

		Entity player = engine.createEntity();
		player.add(new PlayerComponent().create("name1"));
		engine.addEntity(player);
	}

	@Override
	public void render(){
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		engine.update(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void dispose(){
		batch.dispose();
		font.dispose();
	}


}
