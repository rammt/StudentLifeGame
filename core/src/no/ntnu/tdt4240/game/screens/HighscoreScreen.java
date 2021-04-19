package no.ntnu.tdt4240.game.screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import no.ntnu.tdt4240.game.Player;
import no.ntnu.tdt4240.game.StudentLifeGame;
import no.ntnu.tdt4240.game.components.TextFieldComponent;

public class HighscoreScreen implements Screen {

    final StudentLifeGame game;


    public HighscoreScreen(StudentLifeGame game) {
        this.game = game;
        Player user = game.getUser();
        game.getStage().clear();

        Label title = new TextFieldComponent().create(null, "Highscores", game.getSkin(), 6, false).getTextFieldComponent();
        title.setPosition(Gdx.graphics.getWidth()/2.2f,
                Gdx.graphics.getHeight()/1.2f);

        String[] users = {"holde", "flatner"};
        String[] stats = {"2", "4"};

        Table table = new Table();
        table.setFillParent(true);
        table.defaults().minWidth(400).minHeight(100).pad(1);
        table.add(new TextFieldComponent().create(null, "User", game.getSkin(), 4, true).getTextFieldComponent());
        table.add(new TextFieldComponent().create(null, "Clicks", game.getSkin(), 4, true).getTextFieldComponent());
        table.add(new TextFieldComponent().create(null, "Kok", game.getSkin(), 4, true).getTextFieldComponent());
        table.row();

        for(int i = 0; i < users.length; i++) {
            table.add(new TextFieldComponent().create(null, users[i], game.getSkin(), 3, true).getTextFieldComponent());
            table.add(new TextFieldComponent().create(null, stats[i], game.getSkin(), 3, true).getTextFieldComponent());
            table.add(new TextFieldComponent().create(null, "23" , game.getSkin(), 3, true).getTextFieldComponent());
            table.row();
        }

        game.getStage().addActor(title);
        game.getStage().addActor(table);
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0.2f, 1);

        game.getStage().act();
        game.getStage().draw();

        game.getEngine().getEngine().update(Gdx.graphics.getDeltaTime());

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

