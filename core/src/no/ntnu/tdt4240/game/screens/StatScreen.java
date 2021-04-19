package no.ntnu.tdt4240.game.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.Font;
import java.util.List;
import java.util.Map;

import no.ntnu.tdt4240.game.StudentLifeGame;
import no.ntnu.tdt4240.game.components.PlayerComponent;
import no.ntnu.tdt4240.game.components.ButtonComponent;
import no.ntnu.tdt4240.game.components.ResourceGainerComponent;
import no.ntnu.tdt4240.game.components.TextFieldComponent;

public class StatScreen implements Screen{

    private Label kokCount;
    private Label antLevert;
    private Label leaderboard;
    private Label aiKok;
    private Label hackerKok;
    private Label professorKok;



    private Button gameButton, saveStatsButton, saveOffline;

    final StudentLifeGame game;

    public StatScreen(final StudentLifeGame game) {

        this.game = game;

        game.getStage().clear();
        Entity player = game.getPlayer();
        PlayerComponent pc = player.getComponent(PlayerComponent.class);

        kokCount = new TextFieldComponent().create((int) (pc.getKokCount()*3), "Antall Klikk:", game.getSkin()).getTextFieldComponent();
        antLevert = new TextFieldComponent().create((int) pc.getKokCount(), "Antall Levert:", game.getSkin()).getTextFieldComponent();
        leaderboard = new TextFieldComponent().create(1, "Leaderboard: ", game.getSkin()).getTextFieldComponent();
        aiKok = new TextFieldComponent().create(0, "AI som koker:", game.getSkin()).getTextFieldComponent();
        hackerKok = new TextFieldComponent().create(0, "Hacker som koker:", game.getSkin()).getTextFieldComponent();
        professorKok = new TextFieldComponent().create(0, "Professor som koker:", game.getSkin()).getTextFieldComponent();


        Table table = new Table();
        table.setFillParent(true);
        table.defaults().minWidth(500).expandX().minHeight(300).pad(40);
        table.add(kokCount);
        table.add(antLevert);
        table.row();
        table.add(leaderboard);
        table.add(aiKok);
        table.row();
        table.add(hackerKok);
        table.add(professorKok);



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

        saveStatsButton = new TextButton("Save Game", game.getSkin());
        saveStatsButton.setSize(Gdx.graphics.getWidth()/4f, Gdx.graphics.getHeight()/16f);
        saveStatsButton.setPosition(Gdx.graphics.getWidth() - gameButton.getWidth()/2, Gdx.graphics.getHeight()/16f);
        saveStatsButton.addListener(new InputListener() {
                                   @Override
                                   public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
               game.firebase.saveStats(game.getPlayer());
               return true;
           }
       });


        saveOffline = new TextButton("Save offline", game.getSkin());
        saveOffline.setSize(Gdx.graphics.getWidth()/4f, Gdx.graphics.getHeight()/16f);
        saveOffline.setPosition(Gdx.graphics.getWidth() - gameButton.getWidth()/2, 0);
        saveOffline.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //game.getUser().saveOffline();
                game.firebase.getHighscore();
                List<Map<String,Object>> l = game.firebase.getHighscore();
                System.out.println(l);
                return true;
            }
        });

        game.getStage().addActor(gameButton);
        game.getStage().addActor(table);
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
        GlyphLayout layout = new GlyphLayout();
        Entity player = game.getPlayer();
        PlayerComponent pc = player.getComponent(PlayerComponent.class);

        layout.setText(game.getFont(), pc.getName());
        //GlyphLayout layout = new GlyphLayout();
        //layout.setText(game.getFont(), game.getUser().getName());
        game.getFont().draw(
                game.getBatch(),
                pc.getName(),
                Gdx.graphics.getWidth()/2f - (layout.width/2),
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
