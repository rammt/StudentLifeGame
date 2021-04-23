package no.ntnu.tdt4240.game.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import no.ntnu.tdt4240.game.StudentLifeGame;
import no.ntnu.tdt4240.game.components.PlayerComponent;
import no.ntnu.tdt4240.game.guiElements.ButtonElement;
import no.ntnu.tdt4240.game.systems.OnStartGameSystem;

public class StartScreen implements Screen {

    private ButtonElement cloudLogInBtn, localLogInBtn;
    private Table layout, localData, cloudData;
    private Label cloudTitle, cloudLastSave, cloudKokCount;

    final StudentLifeGame game;
    private Entity onlinePlayer;
    private final OnStartGameSystem startingSystem;
    public StartScreen(final StudentLifeGame game, final Entity onlinePlayer) {
        this.game = game;
        this.onlinePlayer = onlinePlayer;
        this.startingSystem = game.getEngine().getSystem(OnStartGameSystem.class);
        this.game.getStage().clear();


        layout = new Table();

        final Entity offlinePlayer = startingSystem.getOfflinePlayer(game.getEngine());
        PlayerComponent offlinePC = offlinePlayer.getComponent(PlayerComponent.class);
        Label title = new Label("Use Local Data", game.getSkin());
        title.setFontScale(3);
        Label lastSave = new Label("Last save: " + new Date(offlinePC.getLastSave()).toString(), game.getSkin());
        lastSave.setFontScale(2);
        Label kokCount = new Label("KokCount: " + offlinePC.getKokCount(), game.getSkin());
        kokCount.setFontScale(2);

        localData = new Table();
        localData.add(title);
        localData.row();
        localData.add(lastSave).pad(30, 10, 30, 10);
        localData.row();
        localData.add(kokCount).pad(30, 10, 30, 10);

        localData.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                startingSystem.startGameWithPlayer(game.getEngine(), offlinePlayer);
                game.setScreen(new GameScreen(game));
                return true;
            }
        });

        layout.add(localData).padBottom(100);
        layout.row();

        //layout.setDebug(true);
        layout.setFillParent(true);

        if (onlinePlayer == null) {
            //TODO Set new screen after player is found. Create prettier button for login/use label with onClick.
            float buttonWidth = Gdx.graphics.getWidth()/2f;
            float buttonHeight = Gdx.graphics.getHeight()/8f;

            cloudLogInBtn = new ButtonElement(
                    buttonWidth, buttonHeight, Gdx.graphics.getWidth()/2f - buttonWidth/2,
                    Gdx.graphics.getHeight()/2f + buttonHeight/2, "Cloud Login", game.getSkin(), new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    game.firebase.onSignInButtonClicked();
                    Entity player = startingSystem.getOnlinePlayer(game.getEngine());
                    game.setScreen(new StartScreen(game, player));
                    return true;
                }
            });

            layout.add(cloudLogInBtn);
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(57/255f, 72f/255f, 85f/255f, 1);



        if (onlinePlayer != null) {
            PlayerComponent onlinePC = onlinePlayer.getComponent(PlayerComponent.class);

            layout.removeActor(cloudLogInBtn);
            layout.removeActor(cloudData);

            cloudTitle = new Label("Use Cloud Data", game.getSkin());
            cloudTitle.setFontScale(3);
            cloudLastSave = new Label("Last save: " + new Date(onlinePC.getLastSave()).toString(), game.getSkin());
            cloudLastSave.setFontScale(2);
            cloudKokCount = new Label("KokCount: " + onlinePC.getKokCount(), game.getSkin());
            cloudKokCount.setFontScale(2);

            cloudData = new Table();
            cloudData.add(cloudTitle);
            cloudData.row();
            cloudData.add(cloudLastSave).pad(30, 10, 30, 10);
            cloudData.row();
            cloudData.add(cloudKokCount).pad(30, 10, 30, 10);

            cloudData.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    startingSystem.startGameWithPlayer(game.getEngine(), onlinePlayer);
                    game.setScreen(new GameScreen(game));
                    return true;
                }
            });

            layout.row();
            layout.add(cloudData);
        }

        // stage tegner aktorsa
        game.getStage().addActor(layout);
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
