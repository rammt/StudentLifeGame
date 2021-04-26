package no.ntnu.tdt4240.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;

import java.text.DecimalFormat;

import no.ntnu.tdt4240.game.StudentLifeGame;
import no.ntnu.tdt4240.game.guiElements.ButtonElement;
import no.ntnu.tdt4240.game.guiElements.NavbarElement;
import no.ntnu.tdt4240.game.systems.AudioSystem;
import no.ntnu.tdt4240.game.systems.SavingSystem;

public class SettingScreen implements Screen {

    private final float BUTTONHEIGHTGUI;
    private final float BUTTONWIDTHGUI;
    private final int SCREENWIDTH;;
    private final int SCREENHEIGTH;
    private final StudentLifeGame game;
    private ButtonElement saveOfflineBtn, saveOnlineBtn, musicBtn, muteBtn, highscoreBtn, statsBtn, tutorialBtn;
    private NavbarElement navbar;
    private AudioSystem as;

    public SettingScreen(final StudentLifeGame game) {

        this.game = game;
        this.game.getStage().clear();
        this.as = game.getEngine().getSystem(AudioSystem.class);
        as.startGameMusic();

        SCREENHEIGTH = Gdx.graphics.getHeight();
        SCREENWIDTH = Gdx.graphics.getWidth();
        BUTTONHEIGHTGUI = SCREENHEIGTH/8f;
        BUTTONWIDTHGUI = SCREENWIDTH/4f;

        highscoreBtn = new ButtonElement(
                BUTTONWIDTHGUI*3, BUTTONHEIGHTGUI,
                SCREENWIDTH/2f-BUTTONWIDTHGUI*3/2,SCREENHEIGTH*5/8f+BUTTONHEIGHTGUI+10,
                "Highscore", game.getSkin(),
                new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        game.setScreen(new HighscoreScreen(game));
                        return true;
                    }

                }
        );
        game.getStage().addActor(highscoreBtn);

        saveOfflineBtn = new ButtonElement(
                BUTTONWIDTHGUI*3/2, BUTTONHEIGHTGUI,
                SCREENWIDTH/2f,  SCREENHEIGTH*5/8f-BUTTONHEIGHTGUI*3-10*3,
                "Save offline", game.getSkin(),
                new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        SavingSystem savingSystem = game.getEngine().getSystem(SavingSystem.class);
                        savingSystem.saveOffline();
                        return true;
                    }

                }
        );
        game.getStage().addActor(saveOfflineBtn);

        if (game.firebase.isLoggedIn()) {
            saveOnlineBtn = new ButtonElement(
                BUTTONWIDTHGUI*3/2, BUTTONHEIGHTGUI,
                    SCREENWIDTH/2f-BUTTONWIDTHGUI*3/2,  SCREENHEIGTH*5/8f-BUTTONHEIGHTGUI*3-10*3,
                "Save online", game.getSkin(),
                new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        game.firebase.savePlayerStats(game.getPlayer());
                        return true;
                    }

                }
            );
            game.getStage().addActor(saveOnlineBtn);
        } else {
            saveOnlineBtn = new ButtonElement(
                    BUTTONWIDTHGUI*3/2, BUTTONHEIGHTGUI,
            SCREENWIDTH/2f-BUTTONWIDTHGUI*3/2,  SCREENHEIGTH*5/8f-BUTTONHEIGHTGUI*3-10*3,
                    "Login to save online", game.getSkin(),
                    new InputListener() {
                        @Override
                        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                            game.firebase.startSignInActivity();
                            game.setScreen(new StatScreen(game));
                            return true;
                        }

                    }
            );
            game.getStage().addActor(saveOnlineBtn);
        }

        musicBtn = new ButtonElement(
                BUTTONWIDTHGUI*3/2, BUTTONHEIGHTGUI,
                SCREENWIDTH/2f, SCREENHEIGTH*5/8f-BUTTONHEIGHTGUI*2-10*2,
                "mute sounds", game.getSkin(),
                new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        as.muteSound();
                        game.setScreen(new SettingScreen(game));
                        return true;
                    }

                }
        );
        musicBtn.toggleButton(as.isSoundPlaying());
        game.getStage().addActor(musicBtn);

        muteBtn = new ButtonElement(
                BUTTONWIDTHGUI*3/2, BUTTONHEIGHTGUI,
                SCREENWIDTH/2f-BUTTONWIDTHGUI*3/2, SCREENHEIGTH*5/8f-BUTTONHEIGHTGUI*2-10*2,
                "mute music", game.getSkin(),
                new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        as.muteMusic();
                        game.setScreen(new SettingScreen(game));
                        return true;
                    }

                }
        );
        muteBtn.toggleButton(as.isMusicPlaying());
        game.getStage().addActor(muteBtn);

        statsBtn = new ButtonElement(
                BUTTONWIDTHGUI*3, BUTTONHEIGHTGUI,
                SCREENWIDTH/2f-BUTTONWIDTHGUI*3/2, SCREENHEIGTH*5/8f,
                "Statistics", game.getSkin(),
                new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        game.setScreen(new StatScreen(game));
                        return true;
                    }

                }
        );
        game.getStage().addActor(statsBtn);

        tutorialBtn = new ButtonElement(
                BUTTONWIDTHGUI*3, BUTTONHEIGHTGUI,
                SCREENWIDTH/2f-BUTTONWIDTHGUI*3/2, SCREENHEIGTH*5/8f-BUTTONHEIGHTGUI-10,
                "Tutorial", game.getSkin(),
                new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        game.setScreen(new TutorialScreen(game,0,true));
                        return true;
                    }

                }
        );
        game.getStage().addActor(tutorialBtn);

        navbar = new NavbarElement(game,BUTTONWIDTHGUI,BUTTONHEIGHTGUI,SCREENWIDTH);

        for(Button btn : navbar.getActors()){
            game.getStage().addActor(btn);
        }

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(57/255f, 72f/255f, 85f/255f, 1);

        // stage tegner aktorsa
        game.getStage().act();
        game.getStage().draw();

        game.getEngine().update(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
