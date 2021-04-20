package no.ntnu.tdt4240.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.ScreenUtils;

import no.ntnu.tdt4240.game.StudentLifeGame;
import no.ntnu.tdt4240.game.guiElements.ButtonElement;
import no.ntnu.tdt4240.game.systems.OnStartGameSystem;

public class StartScreen implements Screen {

    private ButtonElement cloudLogInBtn, localLogInBtn;

    final StudentLifeGame game;

    public StartScreen(final StudentLifeGame game) {
        this.game = game;

        float buttonWidth = Gdx.graphics.getWidth()/2f;
        float buttonHeight = Gdx.graphics.getHeight()/8f;
        final OnStartGameSystem startingSystem = game.getEngine().getSystem(OnStartGameSystem.class);

        cloudLogInBtn = new ButtonElement(
                buttonWidth, buttonHeight, Gdx.graphics.getWidth()/2f - buttonWidth/2,
            Gdx.graphics.getHeight()/2f + buttonHeight/2, "Cloud Login", game.getSkin(), new InputListener() {
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            game.firebase.onSignInButtonClicked();
            startingSystem.onlineStart(game.getEngine());
            game.setScreen(new GameScreen(game));
            return true;
            }
        });

        localLogInBtn = new ButtonElement(
            buttonWidth, buttonHeight,
                Gdx.graphics.getWidth()/2f - buttonWidth/2, Gdx.graphics.getHeight()/2f - buttonHeight/2,
                "Local Login", game.getSkin(), new InputListener() {
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            startingSystem.offlineStart(game.getEngine());
            game.setScreen(new GameScreen(game));
            return true;
        }
        }
        );



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
