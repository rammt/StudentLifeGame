package no.ntnu.tdt4240.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;


public class StudentLifeGame extends ApplicationAdapter {
	Stage stage;
	SpriteBatch batch;
	Texture img;
	TextButton signInBtn;

	public FirebaseInterface firebase;

	public StudentLifeGame(FirebaseInterface firebase) {
		this.firebase = firebase;
	}
	
	@Override
	public void create () {
		stage = new Stage();
		batch = new SpriteBatch();

		Gdx.input.setInputProcessor(stage);
		BitmapFont font = new BitmapFont();
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.font = font;
		signInBtn = new TextButton("SIGN IN BUTTON PLEASE CLICK ME", textButtonStyle);
		stage.addActor(signInBtn);

		signInBtn.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				firebase.onSignInButtonClicked();
			}
		});
	}



	@Override
	public void render () {
		stage.draw();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}