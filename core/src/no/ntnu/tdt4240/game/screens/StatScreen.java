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

    final float BUTTONHEIGHTGUI;
    final float BUTTONWIDTHGUI;
    final int SCREENHEIGTH;
    final int SCREENWIDTH;
    final int buttonPadding;
    private TextButton.TextButtonStyle textButtonStyleDOWN;
    private TextButton.TextButtonStyle textButtonStyleUP;
    private Button copyButton, pasteButton, deliverButton, gameButton;


    final StudentLifeGame game;

    public StatScreen(final StudentLifeGame game) {

        this.game = game;

        game.getStage().clear();

        SCREENHEIGTH = Gdx.graphics.getHeight();
        SCREENWIDTH = Gdx.graphics.getWidth();
        BUTTONHEIGHTGUI = SCREENHEIGTH/8f;
        BUTTONWIDTHGUI = SCREENWIDTH/4f;
        buttonPadding = 10;


        Entity homeButton = game.getEngine().createEntity();
        homeButton.add(new ButtonComponent().create(
                BUTTONWIDTHGUI,BUTTONHEIGHTGUI,
                (SCREENWIDTH/4f)-BUTTONWIDTHGUI/2-buttonPadding,50,
                "Home",game.getSkin(), new InputListener(){
                    @Override
                    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                        game.setScreen(new StartScreen(game));
                        return true;
                    }}));
        game.getEngine().addEntity(homeButton);

        Entity settingsButton = game.getEngine().createEntity();
        settingsButton.add(new ButtonComponent().create(
                BUTTONWIDTHGUI,BUTTONHEIGHTGUI,
                (SCREENWIDTH/2f)-BUTTONWIDTHGUI/2,50,
                "Shop",game.getSkin(), new InputListener(){
                    @Override
                    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                        game.setScreen(new ShopScreen(game));
                        return true;
                    }}));
        game.getEngine().addEntity(settingsButton);

        Entity ShopButton = game.getEngine().createEntity();
        ShopButton.add(new ButtonComponent().create(
                BUTTONWIDTHGUI,BUTTONHEIGHTGUI,
                (SCREENWIDTH*3/4f)-BUTTONWIDTHGUI/2+buttonPadding,50,
                "Stats",game.getSkin(), new InputListener(){
                    @Override
                    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                        game.setScreen(new StatScreen(game));
                        return true;
                    }}));
        game.getEngine().addEntity(ShopButton);

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
                "Kokt : " + String.valueOf(game.getKokCounter()),
                Gdx.graphics.getWidth()/3f,
                Gdx.graphics.getHeight()/1.2f
        );
        game.getBatch().end();

        game.getEngine().update(Gdx.graphics.getDeltaTime());

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
