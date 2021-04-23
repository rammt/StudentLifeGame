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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import no.ntnu.tdt4240.game.StudentLifeGame;
import no.ntnu.tdt4240.game.components.PlayerComponent;
import no.ntnu.tdt4240.game.components.ResourceGainerComponent;
import no.ntnu.tdt4240.game.components.TextFieldComponent;
import no.ntnu.tdt4240.game.guiElements.ButtonElement;
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

    private int kokere = 0;
    private int hackere = 0;
    private int studass = 0;
    private int scripts = 0;
    private int rank;

    private List<Map<String,Object>> hl = new ArrayList<Map<String,Object>>();

    private Button statButton, gameButton, shopButton, highscoreButton;

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

        // Updates amount of resourcegainers for the player
        ArrayList<ResourceGainerComponent> rg = pc.getResourceGainers();
        for(int i = 0; i < rg.size(); i++) {
            System.out.println(rg.get(i).getName());
            if(rg.get(i).getName() == "Kokere"){
                kokere += 1;
            }
            else if(rg.get(i).getName() == "Hackere"){
                hackere += 1;
            }
            else if(rg.get(i).getName() == "Studass"){
                studass += 1;
            }
            else{
                scripts += 1;
            }
        }

       // String.valueOf(hl.get(i).get("name");
        hl = game.firebase.getHighscore();

        for(int i = 0; i < hl.size(); i++){
            if(pc.getName() == String.valueOf(hl.get(i).get("name"))){
                rank = i+1;
            }
        }

        // Labelelements for stats in table
        kokCount = new TextFieldComponent().create((int) (pc.getKokCount()*3), "Antall Klikk:", game.getSkin(), 3, true).getTextFieldComponent();
        antLevert = new TextFieldComponent().create((int) pc.getKokCount(), "Antall Levert:", game.getSkin(),3, true).getTextFieldComponent();
        leaderboard = new TextFieldComponent().create(kokere, "Betalte kokere: ", game.getSkin(),3, true).getTextFieldComponent();
        aiKok = new TextFieldComponent().create(hackere, "Hackere som koker:", game.getSkin(),3, true).getTextFieldComponent();
        hackerKok = new TextFieldComponent().create(studass, "Studasser som koker", game.getSkin(),3, true).getTextFieldComponent();
        professorKok = new TextFieldComponent().create(scripts, "Kokescripts:", game.getSkin(),3, true).getTextFieldComponent();

        // Table
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

        // Buttons
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
                game.setScreen(new ShopScreen(game));
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


        highscoreButton = new ButtonElement(
                Gdx.graphics.getWidth()/3f,Gdx.graphics.getHeight()/20f,
                Gdx.graphics.getWidth()/2f - Gdx.graphics.getWidth()/6f, statButton.getY() + statButton.getHeight() + 50,
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

        // Add actors to stage
        game.getStage().addActor(gameButton);
        game.getStage().addActor(statButton);
        game.getStage().addActor(shopButton);
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
                pc.getName() + "\n Rank: " + rank,
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
