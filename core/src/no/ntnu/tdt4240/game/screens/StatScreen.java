package no.ntnu.tdt4240.game.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;

import no.ntnu.tdt4240.game.StudentLifeGame;
import no.ntnu.tdt4240.game.components.PlayerComponent;
import no.ntnu.tdt4240.game.guiElements.NavbarElement;
import no.ntnu.tdt4240.game.guiElements.TextFieldElement;

public class StatScreen implements Screen {

    private final Label kokCount;
    private final Label antLevert;

    final float BUTTONHEIGHTGUI;
    final float BUTTONWIDTHGUI;
    final int SCREENHEIGTH;
    final int SCREENWIDTH;
    final int buttonPadding;
    private final int rank;

    final StudentLifeGame game;
    private final PlayerComponent pc;

    public StatScreen(final StudentLifeGame game) {

        this.game = game;
        this.game.getStage().clear();
        Entity player = game.getPlayer();
        pc = player.getComponent(PlayerComponent.class);

        SCREENHEIGTH = Gdx.graphics.getHeight();
        SCREENWIDTH = Gdx.graphics.getWidth();
        BUTTONHEIGHTGUI = SCREENHEIGTH / 8f;
        BUTTONWIDTHGUI = SCREENWIDTH / 4f;
        buttonPadding = 10;

        // Get rank of player
        rank = game.getFirebase().getRank(pc);

        // Labelelements for stats in table
        kokCount = new TextFieldElement((game.formatMillions(pc.getClickCount())), "Antall Klikk:", game.getSkin(), 3, true).getActor();
        antLevert = new TextFieldElement((game.formatMillions(pc.getKokCount())), "Antall Levert:", game.getSkin(), 3, true).getActor();

        // Table
        Table table = new Table();
        table.setFillParent(true);
        table.defaults().minWidth(500).minHeight(300).pad(40);
        table.add(kokCount);
        table.add(antLevert);

        //navbarkode
        NavbarElement navbar = new NavbarElement(game, BUTTONWIDTHGUI, BUTTONHEIGHTGUI, SCREENWIDTH, 2);

        for (Button btn : navbar.getActors()) {
            game.getStage().addActor(btn);
        }

        game.getStage().addActor(table);
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(57 / 255f, 72f / 255f, 85f / 255f, 1);

        // stage tegner aktorsa
        game.getStage().act();
        game.getStage().draw();
        //batch tegner vi resten på
        game.getBatch().begin();
        GlyphLayout layout = new GlyphLayout();
        layout.setText(game.getFont(), pc.getName());
        game.getFont().draw(
                game.getBatch(),
                pc.getName() + "\n Rank: " + rank,
                Gdx.graphics.getWidth() / 2f - (layout.width / 2),
                Gdx.graphics.getHeight() / 1.15f
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
