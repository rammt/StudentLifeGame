package no.ntnu.tdt4240.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.ScreenUtils;

import no.ntnu.tdt4240.game.StudentLifeGame;
import no.ntnu.tdt4240.game.components.PlayerComponent;
import no.ntnu.tdt4240.game.guiElements.ButtonElement;
import no.ntnu.tdt4240.game.guiElements.NavbarElement;
import no.ntnu.tdt4240.game.systems.AudioSystem;

public class ShopSelectScreen implements Screen {
    final StudentLifeGame game;

    final float BUTTONHEIGHTGUI;
    final float BUTTONWIDTHGUI;
    final int SCREENHEIGTH;
    final int SCREENWIDTH;
    final int buttonPadding;
    private final PlayerComponent player_pc;
    private final AudioSystem as;
    private final Button upgradeScreenButton;
    private final Button upgradeRGButton;

    public ShopSelectScreen(final StudentLifeGame game) {
        this.game = game;
        game.getStage().clear();

        SCREENHEIGTH = Gdx.graphics.getHeight();
        SCREENWIDTH = Gdx.graphics.getWidth();
        BUTTONHEIGHTGUI = SCREENHEIGTH / 8f;
        BUTTONWIDTHGUI = SCREENWIDTH / 4f;
        buttonPadding = 10;

        player_pc = game.getPlayer().getComponent(PlayerComponent.class);
        as = game.getEngine().getSystem(AudioSystem.class);
        as.startShopMusic();

        upgradeScreenButton = new ButtonElement(
                BUTTONWIDTHGUI * 3, BUTTONHEIGHTGUI,
                SCREENWIDTH / 2f - BUTTONWIDTHGUI * 3 / 2, SCREENHEIGTH * 5 / 8f - BUTTONHEIGHTGUI - 50,
                "Upgrades", game.getSkin(),
                new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        game.setScreen(new UpgradeShopScreen(game));
                        return true;
                    }
                }
        );

        upgradeRGButton = new ButtonElement(
                BUTTONWIDTHGUI * 3, BUTTONHEIGHTGUI,
                SCREENWIDTH / 2f - BUTTONWIDTHGUI * 3 / 2, SCREENHEIGTH * 5 / 8f,
                "Resource Gainers", game.getSkin(),
                new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        game.setScreen(new ShopScreen(game, 0));
                        return true;
                    }
                }
        );

        NavbarElement navbar = new NavbarElement(game, BUTTONWIDTHGUI, BUTTONHEIGHTGUI, SCREENWIDTH, 1);
        for (Button btn : navbar.getActors()) {
            game.getStage().addActor(btn);
        }

        game.getStage().addActor(upgradeScreenButton);
        game.getStage().addActor(upgradeRGButton);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(57 / 255f, 72f / 255f, 85f / 255f, 1);

        game.getStage().act();
        game.getStage().draw();

        game.getBatch().begin();
        String kokAmount = game.formatMillions(player_pc.getKokCount());
        game.getFont().draw(
                game.getBatch(),
                //"Kokt : " + String.valueOf(formatter.format(pc.getKokCount())),
                "Kok: " + kokAmount,
                Gdx.graphics.getWidth() / 3f,
                Gdx.graphics.getHeight() / 1.2f
        );
        game.getBatch().end();

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
