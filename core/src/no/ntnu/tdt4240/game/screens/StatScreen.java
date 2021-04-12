package no.ntnu.tdt4240.game.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.Font;

import no.ntnu.tdt4240.game.StudentLifeGame;
import no.ntnu.tdt4240.game.components.ButtonComponent;
import no.ntnu.tdt4240.game.components.ResourceGainerComponent;

public class StatScreen implements Screen{

    private TextButton.TextButtonStyle textButtonStyleDOWN;
    private TextButton.TextButtonStyle textButtonStyleUP;
    private Button gameButton, saveStatsButton, saveOffline;

    final StudentLifeGame game;

    public StatScreen(final StudentLifeGame game) {

        this.game = game;

        game.getStage().clear();


        gameButton = new TextButton("back",game.getSkin());
        gameButton.setSize(Gdx.graphics.getWidth()/2f,Gdx.graphics.getHeight()/8f);
        gameButton.setPosition(
                Gdx.graphics.getWidth()/2f - gameButton.getWidth()/2,
                0);
        gameButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                game.setScreen(new StartScreen(game));

                return true;
            }
        });

        textButtonStyleDOWN = new TextButton.TextButtonStyle(
                gameButton.getStyle().down,
                gameButton.getStyle().down,
                gameButton.getStyle().down,
                game.getFont()

        );
        textButtonStyleUP = new TextButton.TextButtonStyle(
                gameButton.getStyle().up,
                gameButton.getStyle().down,
                gameButton.getStyle().checked,
                game.getFont()
        );


        saveStatsButton = new TextButton("Save Game", game.getSkin());
        saveStatsButton.setSize(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/8f);
        saveStatsButton.setPosition(Gdx.graphics.getWidth()/2f - gameButton.getWidth()/2, Gdx.graphics.getHeight()/2f - gameButton.getHeight()/2);
        saveStatsButton.addListener(new InputListener() {
                                   @Override
                                   public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
               game.firebase.saveStats(game.getUser());
               return true;
           }
       });


        saveOffline = new TextButton("Save offline", game.getSkin());
        saveOffline.setSize(Gdx.graphics.getWidth()/4f, Gdx.graphics.getHeight()/16f);
        saveOffline.setPosition(Gdx.graphics.getWidth() - gameButton.getWidth()/2, 0);
        saveOffline.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.getUser().saveOffline();
                return true;
            }
        });

        game.getStage().addActor(gameButton);
        game.getStage().addActor(saveStatsButton);
        game.getStage().addActor(saveOffline);
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0.2f, 1);

        // stage tegner aktorsa
        game.getStage().act();
        game.getStage().draw();
        //batch tegner vi resten på
        game.getBatch().begin();
        game.getFont().draw(
                game.getBatch(),
                "Kokt : " + game.getUser().getKokCount(),
                Gdx.graphics.getWidth()/3f,
                Gdx.graphics.getHeight()/1.2f
        );
        game.getBatch().end();

        game.getEngine().getEngine().update(Gdx.graphics.getDeltaTime());

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
        game.getUser().saveOffline();
    }

}
