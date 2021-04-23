package no.ntnu.tdt4240.game.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.Font;
import java.util.List;
import java.util.Map;

import no.ntnu.tdt4240.game.StudentLifeGame;
import no.ntnu.tdt4240.game.components.PlayerComponent;
import no.ntnu.tdt4240.game.components.TextFieldComponent;
import no.ntnu.tdt4240.game.guiElements.ButtonElement;
import no.ntnu.tdt4240.game.guiElements.NavbarElement;
import no.ntnu.tdt4240.game.systems.SavingSystem;

public class StatScreen implements Screen{

    private Label kokCount;
    private Label antLevert;
    private Label leaderboard;
    private Label aiKok;
    private Label hackerKok;
    private Label professorKok;

    final float BUTTONHEIGHTGUI;
    final float BUTTONWIDTHGUI;
    final int SCREENHEIGTH;
    final int SCREENWIDTH;
    final int buttonPadding;

    private Button highscoreButton;

    private Button saveStatsButton, saveOffline;

    final StudentLifeGame game;

    public StatScreen(final StudentLifeGame game) {

        this.game = game;
        this.game.getStage().clear();
        Entity player = game.getPlayer();
        PlayerComponent pc = player.getComponent(PlayerComponent.class);

        SCREENHEIGTH = Gdx.graphics.getHeight();
        SCREENWIDTH = Gdx.graphics.getWidth();
        BUTTONHEIGHTGUI = SCREENHEIGTH/8f;
        BUTTONWIDTHGUI = SCREENWIDTH/4f;
        buttonPadding = 10;

        kokCount = new TextFieldComponent().create((pc.getClickCount().intValue()), "Antall Klikk:", game.getSkin(), 3, true).getTextFieldComponent();
        antLevert = new TextFieldComponent().create((int) pc.getKokCount(), "Antall Levert:", game.getSkin(),3, true).getTextFieldComponent();
        leaderboard = new TextFieldComponent().create(1, "Leaderboard: ", game.getSkin(),3, true).getTextFieldComponent();
        aiKok = new TextFieldComponent().create(0, "AI som koker:", game.getSkin(),3, true).getTextFieldComponent();
        hackerKok = new TextFieldComponent().create(0, "Hacker som koker:", game.getSkin(),3, true).getTextFieldComponent();
        professorKok = new TextFieldComponent().create(0, "Professor som koker:", game.getSkin(),3, true).getTextFieldComponent();


        Table table = new Table();
        table.setFillParent(true);
        table.defaults().minWidth(500).minHeight(300).pad(40);
        table.add(kokCount);
        table.add(antLevert);
        table.row();
        table.add(leaderboard);
        table.add(aiKok);
        table.row();
        table.add(hackerKok);
        table.add(professorKok);

        highscoreButton = new ButtonElement(
                Gdx.graphics.getWidth()/3f,Gdx.graphics.getHeight()/20f,
                Gdx.graphics.getWidth()/2f - Gdx.graphics.getWidth()/6f, 50 + BUTTONHEIGHTGUI + 50,
                "Highscores", game.getSkin(), new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new HighscoreScreen(game));
                return true;
            }
        });

        saveStatsButton = new ButtonElement(
                Gdx.graphics.getWidth()/3f,Gdx.graphics.getHeight()/20f,
                Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/1.1f,
                "Save game", game.getSkin(), new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.firebase.savePlayerStats(game.getPlayer());
                return true;
            }
        });

        saveOffline = new ButtonElement(
                Gdx.graphics.getWidth()/3f,Gdx.graphics.getHeight()/20f,
                (Gdx.graphics.getWidth()/2f) - (saveStatsButton.getWidth()), Gdx.graphics.getHeight()/1.1f,
                "Save offline", game.getSkin(), new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                SavingSystem savingSystem = game.getEngine().getSystem(SavingSystem.class);
                savingSystem.saveOffline();
                return true;
            }
        });

        //navbarkode
        NavbarElement navbar = new NavbarElement(game, BUTTONWIDTHGUI, BUTTONHEIGHTGUI, SCREENWIDTH );

        for(Button btn : navbar.getActors()){
            System.out.println("add button");
            game.getStage().addActor(btn);
        }

        game.getStage().addActor(highscoreButton);
        game.getStage().addActor(table);
        game.getStage().addActor(saveStatsButton);
        game.getStage().addActor(saveOffline);
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(57/255f, 72f/255f, 85f/255f, 1);

        // stage tegner aktorsa
        game.getStage().act();
        game.getStage().draw();
        //batch tegner vi resten på
        game.getBatch().begin();
        GlyphLayout layout = new GlyphLayout();
        Entity player = game.getPlayer();
        PlayerComponent pc = player.getComponent(PlayerComponent.class);

        layout.setText(game.getFont(), pc.getName());
        game.getFont().draw(
                game.getBatch(),
                pc.getName(),
                Gdx.graphics.getWidth()/2f - (layout.width/2),
                Gdx.graphics.getHeight()/1.15f
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
