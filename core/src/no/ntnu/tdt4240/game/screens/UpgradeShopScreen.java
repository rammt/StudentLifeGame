package no.ntnu.tdt4240.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Arrays;

import no.ntnu.tdt4240.game.StudentLifeGame;
import no.ntnu.tdt4240.game.components.PlayerComponent;
import no.ntnu.tdt4240.game.guiElements.ButtonElement;
import no.ntnu.tdt4240.game.guiElements.NavbarElement;
import no.ntnu.tdt4240.game.systems.AudioSystem;
import no.ntnu.tdt4240.game.systems.ResourceGainSystem;

public class UpgradeShopScreen implements Screen {

    final StudentLifeGame game;

    final float BUTTONHEIGHTGUI, BUTTONWIDTHGUI;
    final int SCREENHEIGTH, SCREENWIDTH;

    final ButtonElement combineButton, increaseClickValueButton;
    private final PlayerComponent pc;
    private final AudioSystem as;
    private final NavbarElement navbar;

    public UpgradeShopScreen(final StudentLifeGame game) {

        this.game = game;
        game.getStage().clear();

        pc = game.getPlayer().getComponent(PlayerComponent.class);
        as = game.getEngine().getSystem(AudioSystem.class);
        as.setSound("music/combine_buttons.mp3");

        SCREENHEIGTH = Gdx.graphics.getHeight();
        SCREENWIDTH = Gdx.graphics.getWidth();
        BUTTONHEIGHTGUI = SCREENHEIGTH / 8f;
        BUTTONWIDTHGUI = SCREENWIDTH / 4f;

        combineButton = new ButtonElement(
                BUTTONWIDTHGUI * 3, BUTTONHEIGHTGUI,
                SCREENWIDTH / 2f - BUTTONWIDTHGUI * 3 / 2, SCREENHEIGTH * 5 / 8f,
                "Combine Buttons: 100 000,-", game.getSkin(),
                new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                        if (pc.getKokCount() >= 100000f && !pc.getCombinedButtons()) {
                            pc.setKokCount(pc.getKokCount() - 100000);
                            pc.setCombinedButtons(true);
                            as.playSound();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    long time = System.currentTimeMillis();
                                    while (System.currentTimeMillis() < time + 4000) {
                                    }
                                    Gdx.app.postRunnable(new Runnable() {
                                        @Override
                                        public void run() {
                                            game.setScreen(new GameScreen(game));
                                        }
                                    });
                                }
                            }).start();
                        }
                        return true;
                    }

                }
        );

        int baseClickValuePrice = 200;

        ResourceGainSystem rgs = game.getEngine().getSystem(ResourceGainSystem.class);


        final double calculatedClickValuePrice = Math.floor(baseClickValuePrice * Math.pow(1.10, pc.getClickValue()));

        final double kokGainFromClick = Math.floor(1 + (rgs.getResourceGainPerSecond() / 10f) * Math.pow(1.02, pc.getClickValue()));


        Label description = new Label("Increase kok delivery value", game.getSkin());
        description.setFontScale(3);
        Label price = new Label("Price: " + game.formatMillions(calculatedClickValuePrice) + " kok", game.getSkin());
        price.setFontScale(2);
        Label clickVal = new Label("New click value: " + game.formatMillions(kokGainFromClick) + " kok", game.getSkin());
        clickVal.setFontScale(2);
        ArrayList<Label> buttonLabels = new ArrayList<>(Arrays.asList(description, price, clickVal));

        increaseClickValueButton = new ButtonElement(
                BUTTONWIDTHGUI * 3, BUTTONHEIGHTGUI,
                SCREENWIDTH / 2f - BUTTONWIDTHGUI * 3 / 2, SCREENHEIGTH * 5 / 8f - BUTTONHEIGHTGUI - 50,
                buttonLabels, game.getSkin(),
                new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if (pc.getKokCount() >= calculatedClickValuePrice) {
                            pc.setKokCount((float) (pc.getKokCount() - calculatedClickValuePrice));
                            pc.setClickValue(pc.getClickValue() + 1);
                            System.out.println(pc.getClickValue());
                            as.playSound();
                            game.setScreen(new UpgradeShopScreen(game));
                        }
                        return true;
                    }

                }
        );


        increaseClickValueButton.disableButton(pc.getKokCount() < calculatedClickValuePrice);

        combineButton.disableButton(pc.getCombinedButtons());


        game.getStage().addActor(combineButton);
        game.getStage().addActor(increaseClickValueButton);

        navbar = new NavbarElement(game, BUTTONWIDTHGUI, BUTTONHEIGHTGUI, SCREENWIDTH, 1);
        for (Button btn : navbar.getActors()) {
            game.getStage().addActor(btn);
        }
    }


    @Override
    public void render(float delta) {
        ScreenUtils.clear(57 / 255f, 72f / 255f, 85f / 255f, 1);

        game.getStage().act();
        game.getStage().draw();
        game.getBatch().begin();
        String kokAmount = game.formatMillions(pc.getKokCount());
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
