package no.ntnu.tdt4240.game.screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.async.AsyncExecutor;
import com.badlogic.gdx.utils.async.AsyncResult;
import com.badlogic.gdx.utils.async.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import no.ntnu.tdt4240.game.StudentLifeGame;
import no.ntnu.tdt4240.game.components.HighscoreComponent;
import no.ntnu.tdt4240.game.components.TextFieldComponent;

public class HighscoreScreen implements Screen {

    final StudentLifeGame game;
    private Button gameButton;
    private List<Map<String,Object>> hl = new ArrayList<Map<String,Object>>();

    private AsyncExecutor executor = new AsyncExecutor(4);
    private AsyncResult<Void> task;
    private float taskProgressValue = 0;

    private boolean updated = false;

    String[] users = {"","","","",""};
    String[] stats = {"0", "0","0","0","0"};

    private Table table;


    public HighscoreScreen(final StudentLifeGame game) {
        this.game = game;

        game.getStage().clear();

        task = executor.submit(new AsyncTask<Void>() {
            @Override
            public Void call() throws Exception {
                hl = game.firebase.getHighscore();
                taskProgressValue = 100;
                return null;
            }
        });


        Label title = new TextFieldComponent().create(null, "Highscores", game.getSkin(), 6, false).getTextFieldComponent();
        title.setPosition(Gdx.graphics.getWidth()/3f,
                Gdx.graphics.getHeight()/1.2f);



        table = new Table();
        table.setFillParent(true);
        table.defaults().minWidth(400).minHeight(100).pad(1);
        table.add(new TextFieldComponent().create(null, "User", game.getSkin(), 4, true).getTextFieldComponent());
        //table.add(new TextFieldComponent().create(null, "Clicks", game.getSkin(), 4, true).getTextFieldComponent());
        table.add(new TextFieldComponent().create(null, "Kok", game.getSkin(), 4, true).getTextFieldComponent());
        table.row();
/*
        for(int i = 0; i < users.length; i++) {
            table.add(new TextFieldComponent().create(null, users[i], game.getSkin(), 3, true).getTextFieldComponent());
            table.add(new TextFieldComponent().create(null, stats[i], game.getSkin(), 3, true).getTextFieldComponent());
            table.add(new TextFieldComponent().create(null, "23" , game.getSkin(), 3, true).getTextFieldComponent());
            table.row();
        }*/

        gameButton = new TextButton("back",game.getSkin());
        gameButton.setSize(Gdx.graphics.getWidth()/2f,Gdx.graphics.getHeight()/8f);
        gameButton.setPosition(
                Gdx.graphics.getWidth()/2f - gameButton.getWidth()/2,
                0);
        gameButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                game.setScreen(new StatScreen(game));

                return true;
            }
        });


        game.getStage().addActor(title);
        game.getStage().addActor(table);

        game.getStage().addActor(gameButton);
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0.2f, 1);

        if(task.isDone() && !updated && !hl.isEmpty()){
            for(int i = 0; i<users.length; i++){
                users[i] = String.valueOf(hl.get(i).get("name"));
                stats[i] = String.valueOf(hl.get(i).get("kokCount"));
            }
            System.out.println(hl);
            System.out.println(hl.get(0).get("name"));
            System.out.println(hl.get(0).get("kokCount"));
            users[0] = String.valueOf(hl.get(0).get("name"));
           /* table.add(new TextFieldComponent().create(null, "User", game.getSkin(), 4, true).getTextFieldComponent());
            table.add(new TextFieldComponent().create(null, "Clicks", game.getSkin(), 4, true).getTextFieldComponent());
            table.add(new TextFieldComponent().create(null, "Kok", game.getSkin(), 4, true).getTextFieldComponent());
            table.row();*/
            for(int i = 0; i < users.length; i++) {
                table.add(new TextFieldComponent().create(null, users[i], game.getSkin(), 3, true).getTextFieldComponent());
               // table.add(new TextFieldComponent().create(null, "value", game.getSkin(), 3, true).getTextFieldComponent());
                table.add(new TextFieldComponent().create(null, stats[i] , game.getSkin(), 3, true).getTextFieldComponent());
                table.row();
            }
            updated = true;
        }

        game.getStage().act();
        game.getStage().draw();

        game.getEngine().update(Gdx.graphics.getDeltaTime());

    }

    @Override
    public void show() {

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

