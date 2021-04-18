package no.ntnu.tdt4240.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;

import no.ntnu.tdt4240.game.Player;
import no.ntnu.tdt4240.game.StudentLifeGame;

public class LoginScreen implements Screen {

    private Button logInBtn;

    final StudentLifeGame game;

    public LoginScreen(final StudentLifeGame game) {
        this.game = game;

        //kode for knappene pluss logikk når knappen trykkes
        logInBtn = new TextButton("Log in using Google", game.getSkin());
        logInBtn.setSize(Gdx.graphics.getWidth()/2f,Gdx.graphics.getHeight()/8f);
        logInBtn.setPosition(
                Gdx.graphics.getWidth()/2f - logInBtn.getWidth()/2,
                Gdx.graphics.getHeight()/2f + logInBtn.getHeight()/2);

        //legger til aktors
        game.getStage().addActor(logInBtn);

        logInBtn.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                Player user = new Player();
                game.firebase.onSignInButtonClicked(user);
                game.setUser(user);

                game.setScreen(new StartScreen(game));
            }
        });
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0.2f, 1);

        // stage tegner aktorsa
        game.getStage().act();
        game.getStage().draw();

        /*if (game.getUser() != null) {
            game.setScreen(new StartScreen(game));
        }*/

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
