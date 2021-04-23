package no.ntnu.tdt4240.game.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import no.ntnu.tdt4240.game.StudentLifeGame;
import no.ntnu.tdt4240.game.guiElements.ButtonElement;
import no.ntnu.tdt4240.game.components.PlayerComponent;

public class TutorialScreen implements Screen{

    private Button statButton, gameButton, shopButton;
    private Button previousButton, nextButton;
    private int SCREENWIDTH, SCREENHEIGHT,BUTTONHEIGHTGUI,BUTTONWIDTHGUI,SMALLBUTTONHEIGHTGUI;
    private int page;

    private List<String> title = Arrays.asList("How to KOK", "Buy upgrade", "Save game", "Need some inspiration?");
    private List<String> description1 = Arrays.asList("1. Click copy", "1. Kok a lot", "Save online if you", "Den koker vi");
    private List<String> description2 = Arrays.asList("2. Click paste", "2. Click SHOP", "want to continue", "Og den koker vi");
    private List<String> description3 = Arrays.asList("3. Click deliver", "3. Buy helpers", "on another device", "Ja, den koker vi");
    private List<Texture> images = Arrays.asList(
        new Texture("images/kok.png"),
        new Texture("images/upgrade.png"),
        new Texture("images/save.png"),
        new Texture("images/inspiration.png")
    );

    final StudentLifeGame game;
    public TutorialScreen(final StudentLifeGame game, final int page) {

        this.game = game;
        this.game.getStage().clear();
        this.page = page;

        SCREENHEIGHT = Gdx.graphics.getHeight();
        SCREENWIDTH = Gdx.graphics.getWidth();
        BUTTONHEIGHTGUI = SCREENHEIGHT/8;
        BUTTONWIDTHGUI = SCREENWIDTH/4;
        SMALLBUTTONHEIGHTGUI = SCREENHEIGHT/18;

        //TODO æsj fiks dette her
        float width = Gdx.graphics.getWidth()/2f;
        float height = Gdx.graphics.getHeight()/8f;
        float x = Gdx.graphics.getWidth()/2f - width/2;
        float copyY = Gdx.graphics.getHeight()/2f + height/2;
        float pasteY = Gdx.graphics.getHeight()/2f - height/2;
        float deliverY = Gdx.graphics.getHeight()/2f - height*1.5f;
        //kode for knappene pluss logikk når knappen trykkes


        gameButton = new ButtonElement(
                BUTTONWIDTHGUI,BUTTONHEIGHTGUI,
                (SCREENWIDTH/4f)-SCREENWIDTH/4f/2-10, 50,
                "GAME", game.getSkin(), new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game));
                return true;
            }
        });

        shopButton = new ButtonElement(
                BUTTONWIDTHGUI,BUTTONHEIGHTGUI,
                (SCREENWIDTH*3/4f)-SCREENWIDTH/4f/2+10, 50,
                "SHOP", game.getSkin(), new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new ShopScreen(game, 0));
                return true;
            }
        });

        statButton = new ButtonElement(
                BUTTONWIDTHGUI,BUTTONHEIGHTGUI,
                (SCREENWIDTH/2f)-SCREENWIDTH/4f/2, 50,
                "STATS", game.getSkin(), new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new StatScreen(game));
                return true;
            }
        });

        previousButton = new ButtonElement(
                Gdx.graphics.getWidth()/3f,Gdx.graphics.getHeight()/20f,
                Gdx.graphics.getWidth()/2f - Gdx.graphics.getWidth()/3f, statButton.getY() + statButton.getHeight() + 50,
                "PREVIOUS", game.getSkin(), new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new TutorialScreen(game, page - 1));
                return true;
            }
        });

        nextButton = new ButtonElement(
                Gdx.graphics.getWidth()/3f,Gdx.graphics.getHeight()/20f,
                Gdx.graphics.getWidth()/2f, statButton.getY() + statButton.getHeight() + 50,
                "NEXT", game.getSkin(), new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new TutorialScreen(game, page + 1));
                return true;
            }
        });


        game.getStage().addActor(statButton);
        game.getStage().addActor(gameButton);
        game.getStage().addActor(shopButton);
        // Has previous page
        if (page > 0) {
            game.getStage().addActor(previousButton);
        }
        // Has next page
        if (page < title.size()-1) {
            game.getStage().addActor(nextButton);
        }

    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(57/255f, 72f/255f, 85f/255f, 1);

        // stage tegner aktorsa
        game.getStage().act();
        game.getStage().draw();
        //batch tegner vi resten på
        game.getBatch().begin();
        Entity player = game.getPlayer();
        PlayerComponent pc = player.getComponent(PlayerComponent.class);
        game.getFont().draw(
                game.getBatch(),
                title.get(page),
                Gdx.graphics.getWidth()/5f,
                Gdx.graphics.getHeight()/1.05f
        );
        game.getFont().draw(
                game.getBatch(),
                description1.get(page),
                Gdx.graphics.getWidth()/3.5f,
                statButton.getY() + statButton.getHeight() + (Gdx.graphics.getHeight()/30f)*6
        );
        game.getFont().draw(
                game.getBatch(),
                description2.get(page),
                Gdx.graphics.getWidth()/3.5f,
                statButton.getY() + statButton.getHeight() + (Gdx.graphics.getHeight()/30f)*5
        );
        game.getFont().draw(
                game.getBatch(),
                description3.get(page),
                Gdx.graphics.getWidth()/3.5f,
                statButton.getY() + statButton.getHeight() + (Gdx.graphics.getHeight()/30f)*4
        );

        game.getBatch().draw(images.get(page), 0, statButton.getY() + statButton.getHeight() + (Gdx.graphics.getHeight()/30f)*7, Gdx.graphics.getWidth(), Gdx.graphics.getWidth());

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
