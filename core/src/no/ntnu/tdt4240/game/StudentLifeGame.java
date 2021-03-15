package no.ntnu.tdt4240.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import no.ntnu.tdt4240.game.components.ButtonComponent;
import no.ntnu.tdt4240.game.components.GameComponent;
import no.ntnu.tdt4240.game.components.PlayerComponent;
import no.ntnu.tdt4240.game.components.ResourceComponent;
import no.ntnu.tdt4240.game.components.ResourceGainerComponent;
import no.ntnu.tdt4240.game.systems.AudioSystem;
import no.ntnu.tdt4240.game.systems.ControlSystem;
import no.ntnu.tdt4240.game.systems.RenderSystem;
import no.ntnu.tdt4240.game.systems.ResourceGainSystem;

public class StudentLifeGame extends Game {
	private float SCREEN_WIDTH;
	private float SCREEN_HEIGHT;

	private PooledEngine engine;

	private Entity game;

	private BitmapFont font;
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	
	@Override
	public void create(){
		SCREEN_WIDTH = Gdx.graphics.getWidth();
		SCREEN_HEIGHT = Gdx.graphics.getHeight();

		engine = new PooledEngine();

		batch = new SpriteBatch();
		font = new BitmapFont();
		shapeRenderer = new ShapeRenderer();

		engine.addSystem(new AudioSystem());
		engine.addSystem(new RenderSystem(shapeRenderer, font, batch));
		engine.addSystem(new ResourceGainSystem());
		engine.addSystem(new ControlSystem());


		game = engine.createEntity();
		game.add(new GameComponent().create());
		engine.addEntity(game);

		Entity resource = engine.createEntity();
		resource.add(new ResourceComponent().create("Studass"));
		engine.addEntity(resource);

		Entity resourceGainer = engine.createEntity();
		resourceGainer.add(new ResourceGainerComponent().create(100f,5));
		engine.addEntity(resourceGainer);

		Entity player1 = engine.createEntity();
		player1.add(new PlayerComponent().create("name1", 1));
		engine.addEntity(player1);

		Entity player2 = engine.createEntity();
		player2.add(new PlayerComponent().create("name2", 2));
		engine.addEntity(player2);

		float buttonHeight = 60f;
		float buttonWidth = 140f;

		float copyButtonX = SCREEN_WIDTH/2;
		float copyButtonY = SCREEN_HEIGHT/2;

		Entity button = engine.createEntity();
		button.add(new ButtonComponent().create(buttonHeight,buttonWidth,copyButtonX,copyButtonY));
		engine.addEntity(button);

		game.getComponent(GameComponent.class).setState(GameComponent.GameState.GAME_PLAYING);

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
