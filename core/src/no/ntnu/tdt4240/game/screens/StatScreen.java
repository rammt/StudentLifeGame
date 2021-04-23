package no.ntnu.tdt4240.game.screens;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
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
import no.ntnu.tdt4240.game.systems.AudioSystem;
import no.ntnu.tdt4240.game.systems.ResourceGainSystem;
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

    private int kokere = 0;
    private int hackere = 0;
    private int studass = 0;
    private int scripts = 0;
    private int rank;

    private Button highscoreButton, musicButton;

    private Button saveStatsButton, saveOffline;

    final StudentLifeGame game;
    private PlayerComponent pc;
    private ImmutableArray<Entity> rg;
    private ComponentMapper<ResourceGainerComponent> rgm;
    private ResourceGainSystem rgs;

    private AudioSystem as;

    public StatScreen(final StudentLifeGame game) {

        this.game = game;
        this.game.getStage().clear();
        Entity player = game.getPlayer();
        pc = player.getComponent(PlayerComponent.class);
        rgs = game.getEngine().getSystem(ResourceGainSystem.class);
        rg = game.getEngine().getEntitiesFor(Family.all(ResourceGainerComponent.class).get());
        rgm = ComponentMapper.getFor(ResourceGainerComponent.class);
        rgs.countResourceGainers(rgm.get(rg.get(0)));

        this.as = game.getEngine().getSystem(AudioSystem.class);

        SCREENHEIGTH = Gdx.graphics.getHeight();
        SCREENWIDTH = Gdx.graphics.getWidth();
        BUTTONHEIGHTGUI = SCREENHEIGTH/8f;
        BUTTONWIDTHGUI = SCREENWIDTH/4f;
        buttonPadding = 10;

        // Get rank of player
        rank = game.firebase.getRank(pc);

        // Resourcegainers for the user
        kokere = rgs.countResourceGainers(rgm.get(rg.get(0)));
        hackere = rgs.countResourceGainers(rgm.get(rg.get(1)));
        studass = rgs.countResourceGainers(rgm.get(rg.get(2)));
        scripts = rgs.countResourceGainers(rgm.get(rg.get(3)));

        // Labelelements for stats in table
        kokCount = new TextFieldComponent().create((pc.getClickCount().intValue()), "Antall Klikk:", game.getSkin(), 3, true).getTextFieldComponent();
        antLevert = new TextFieldComponent().create((int) pc.getKokCount(), "Antall Levert:", game.getSkin(),3, true).getTextFieldComponent();
        leaderboard = new TextFieldComponent().create(kokere, "Betalte kokere: ", game.getSkin(),3, true).getTextFieldComponent();
        aiKok = new TextFieldComponent().create(hackere, "Studasser som koker:", game.getSkin(),3, true).getTextFieldComponent();
        hackerKok = new TextFieldComponent().create(studass, "Kokscripts", game.getSkin(),3, true).getTextFieldComponent();
        professorKok = new TextFieldComponent().create(scripts, "Hackere som koker:", game.getSkin(),3, true).getTextFieldComponent();

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



        musicButton = new ButtonElement(
                Gdx.graphics.getWidth()/6f,Gdx.graphics.getHeight()/25f,
                Gdx.graphics.getWidth() - Gdx.graphics.getWidth()/6f, Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/25f,
                "Music", game.getSkin(), new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                as.startBackgroundMusic(game.getEngine());
                return true;
            }
        });

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

        if (game.firebase.isLoggedIn()) {
            saveStatsButton = new ButtonElement(
                    Gdx.graphics.getWidth()/3f,Gdx.graphics.getHeight()/20f,
                    Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/1.1f,
                    "Save to cloud", game.getSkin(), new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    game.firebase.savePlayerStats(game.getPlayer());
                    return true;
                }
            });
        } else {
            saveStatsButton = new ButtonElement(
                    Gdx.graphics.getWidth()/3f,Gdx.graphics.getHeight()/20f,
                    Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/1.1f,
                    "Log in to save to cloud", game.getSkin(), new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    game.firebase.startSignInActivity();
                    game.setScreen(new StatScreen(game));
                    return true;
                }
            });
        }



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

        game.getStage().addActor(musicButton);
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
