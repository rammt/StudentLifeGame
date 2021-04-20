package no.ntnu.tdt4240.game.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;

import no.ntnu.tdt4240.game.StudentLifeGame;
import no.ntnu.tdt4240.game.components.PlayerComponent;
import no.ntnu.tdt4240.game.guiElements.ButtonElement;
import no.ntnu.tdt4240.game.systems.OnStartGameSystem;

public class LoginScreen implements Screen {

    private ButtonElement cloudLogInBtn, localLogInBtn;

    final StudentLifeGame game;

    public LoginScreen(final StudentLifeGame game) {
        this.game = game;

        float buttonWidth = Gdx.graphics.getWidth()/2f;
        float buttonHeight = Gdx.graphics.getHeight()/8f;
        final OnStartGameSystem startingSystem = game.getEngine().getSystem(OnStartGameSystem.class);

        cloudLogInBtn = new ButtonElement(buttonWidth, buttonHeight, Gdx.graphics.getWidth()/2f - buttonWidth/2,
                Gdx.graphics.getHeight()/2f + buttonHeight/2, "Cloud Login", game.getSkin(), new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.firebase.onSignInButtonClicked();
                startingSystem.onlineStart(game.getEngine());
                game.setScreen(new StartScreen(game));
                return true;
            }
        });

        localLogInBtn = new ButtonElement(buttonWidth, buttonHeight, Gdx.graphics.getWidth()/2f - buttonWidth/2,
                Gdx.graphics.getHeight()/2f - buttonHeight/2, "Local Login", game.getSkin(), new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                startingSystem.offlineStart(game.getEngine());
                game.setScreen(new StartScreen(game));
                return true;
            }
        });



        //legger til aktors
        game.getStage().addActor(cloudLogInBtn);
        game.getStage().addActor(localLogInBtn);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(57/255f, 72f/255f, 85f/255f, 1);

        // stage tegner aktorsa
        game.getStage().act();
        game.getStage().draw();


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
