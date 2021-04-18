package no.ntnu.tdt4240.game.screens;
import com.badlogic.gdx.Gdx;
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

        String[] users = {"holde", "flatner"};
        String[] stats = {"2", "4"};

        Table table = new Table();
        table.setFillParent(true);
        table.defaults().minWidth(500).expandX().minHeight(300).pad(30);
        for(int i = 0; i < users.length; i++) {
            table.add(new TextFieldComponent().create((int) (6), users[i], game.getSkin()).getTextFieldComponent());
            table.add(new TextFieldComponent().create((int) (user.getKokCount()*3), stats[i], game.getSkin()).getTextFieldComponent());
            table.row();
        }

        game.getStage().addActor(table);
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0.2f, 1);

        // stage tegner aktorsa
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

