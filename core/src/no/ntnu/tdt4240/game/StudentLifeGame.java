package no.ntnu.tdt4240.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import no.ntnu.tdt4240.game.screens.StartScreen;

public class StudentLifeGame extends Game {

	private SpriteBatch batch;
	private BitmapFont font;
	private Stage stage;
	private Skin skin;
	private TextureAtlas textureAtlas;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
		this.setScreen(new StartScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
		stage.dispose();
		skin.dispose();
		textureAtlas.dispose();
	}

	public SpriteBatch getBatch(){
		return batch;
	}
	public BitmapFont getFont(){
		return font;
	}
	public Stage getStage(){
		return stage;
	}
	public Skin getSkin(){
		return skin;
	}
	public TextureAtlas getTextureAtlas(){
		return textureAtlas;
	}

}
