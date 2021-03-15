package no.ntnu.tdt4240.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;

import no.ntnu.tdt4240.game.StudentLifeGame;

public class StartScreen implements Screen{

    private Button copyButton, pasteButton, deliverButton;
    private int counter;

    final StudentLifeGame game;

	public StartScreen(final StudentLifeGame game) {
		this.game = game;
		counter = 0;

		copyButton = new TextButton("COPYBUTTON",game.getSkin());
		copyButton.setSize(Gdx.graphics.getWidth()/2f,Gdx.graphics.getHeight()/8f);
		copyButton.setPosition(Gdx.graphics.getWidth()/2f - copyButton.getWidth()/2,Gdx.graphics.getHeight()/2f + copyButton.getHeight()/2);
		copyButton.addListener(new InputListener(){
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				counter++;
				return true;
			}
		});
		pasteButton = new TextButton("PASTEBUTTON",game.getSkin());
		pasteButton.setSize(Gdx.graphics.getWidth()/2f,Gdx.graphics.getHeight()/8f);
		pasteButton.setPosition(Gdx.graphics.getWidth()/2f - pasteButton.getWidth()/2,Gdx.graphics.getHeight()/2f - pasteButton.getHeight()/2);
		pasteButton.addListener(new InputListener(){
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				counter++;
				return true;
			}
		});
		deliverButton = new TextButton("DELIVERBUTTON",game.getSkin());
		deliverButton.setSize(Gdx.graphics.getWidth()/2f,Gdx.graphics.getHeight()/8f);
		deliverButton.setPosition(Gdx.graphics.getWidth()/2f - deliverButton.getWidth()/2,Gdx.graphics.getHeight()/2f - deliverButton.getHeight()*1.5f);
		deliverButton.addListener(new InputListener(){
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				counter++;
				return true;
			}
		});
		game.getStage().addActor(copyButton);
		game.getStage().addActor(pasteButton);
		game.getStage().addActor(deliverButton);

	}

	@Override
	public void render(float delta) {

        ScreenUtils.clear(0, 0, 0.2f, 1);

		game.getStage().act();
		game.getStage().draw();

		game.getBatch().begin();
		game.getFont().draw(game.getBatch(), String.valueOf(counter) , Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/1.2f);
		game.getBatch().end();

	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}
    
}
