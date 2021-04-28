package no.ntnu.tdt4240.game.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;

import no.ntnu.tdt4240.game.StudentLifeGame;
import no.ntnu.tdt4240.game.components.PlayerComponent;
import no.ntnu.tdt4240.game.guiElements.ButtonElement;
import no.ntnu.tdt4240.game.guiElements.NavbarElement;
import no.ntnu.tdt4240.game.systems.AudioSystem;
import no.ntnu.tdt4240.game.systems.ResourceGainSystem;

public class GameScreen implements Screen {

    private final ButtonElement copyButton;
    private final ButtonElement pasteButton;
    private final ButtonElement deliverButton;
    private final ButtonElement copyPasteDeliverButton;
    private boolean copied;
    private boolean pasted;
    private final boolean delivered;
    private final boolean upgraded = false;
    private final int SCREENWIDTH;
    private final int SCREENHEIGHT;
    private final int BUTTONHEIGHTGUI;
    private final int BUTTONWIDTHGUI;
    private float gainpersecond;
    private final NavbarElement navbar;
    private final Entity player;
    private final PlayerComponent pc;
    private final AudioSystem as;
    private boolean tutorialMode = false;

    final StudentLifeGame game;

    public GameScreen(final StudentLifeGame game) {
        this.game = game;
        this.game.getStage().clear();
        this.tutorialMode = true;
        SCREENHEIGHT = Gdx.graphics.getHeight();
        SCREENWIDTH = Gdx.graphics.getWidth();
        BUTTONHEIGHTGUI = SCREENHEIGHT / 8;
        BUTTONWIDTHGUI = SCREENWIDTH / 4;

        player = game.getPlayer();
        pc = player.getComponent(PlayerComponent.class);

        copied = false;
        pasted = false;
        delivered = false;

        this.as = game.getEngine().getSystem(AudioSystem.class);
        as.setSound("music/Whoo.mp3");
        as.startGameMusic();

        //TODO æsj fiks dette her
        float width = Gdx.graphics.getWidth() / 2f;
        float height = Gdx.graphics.getHeight() / 8f;
        float x = Gdx.graphics.getWidth() / 2f - width / 2;
        float copyY = Gdx.graphics.getHeight() / 2f + height / 2;
        float pasteY = Gdx.graphics.getHeight() / 2f - height / 2;
        float deliverY = Gdx.graphics.getHeight() / 2f - height * 1.5f;
        //kode for knappene pluss logikk når knappen trykkes

        copyButton = new ButtonElement(
                x, copyY,
                "COPY", game.getSkin(), new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (!copied) {
                    copied = true;
                    copyButton.disableButton(copied);
                    pc.setClickCount(pc.getClickCount() + 1);
                }
                return true;
            }
        });

        pasteButton = new ButtonElement(
                x, pasteY,
                "PASTE", game.getSkin(), new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (copied && !pasted) {
                    pasted = true;
                    pasteButton.disableButton(pasted);
                    pc.setClickCount(pc.getClickCount() + 1);
                    as.setSound("music/Whoo.mp3");
                }
                return true;
            }
        });

        final double kokGainFromClick = (1 + (getGainpersecond() / 10f) * Math.pow(1.02, pc.getClickValue()));
        System.out.println("Gained kok: " + kokGainFromClick);

        deliverButton = new ButtonElement(
                x, deliverY,
                "DELIVER", game.getSkin(), new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (copied && pasted && !delivered) {
                    copied = false;
                    pasted = false;
                    pc.setClickCount(pc.getClickCount() + 1);
                    pc.setKokCount((float) (pc.getKokCount() + kokGainFromClick));
                    copyButton.disableButton(copied);
                    pasteButton.disableButton(pasted);
                    as.playSound();
                }
                return true;
            }
        });

        copyPasteDeliverButton = new ButtonElement(x, pasteY, "KOK", game.getSkin(), new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                pc.setKokCount((float) (pc.getKokCount() + kokGainFromClick));
                pc.setClickCount(pc.getClickCount() + 1);
                as.playSound();
                return true;

            }
        });


        //legger til aktors

        navbar = new NavbarElement(game, BUTTONWIDTHGUI, BUTTONHEIGHTGUI, SCREENWIDTH, 0);
        for (Button btn : navbar.getActors()) {
            game.getStage().addActor(btn);
        }

        if (pc.getCombinedButtons()) {
            game.getStage().addActor(copyPasteDeliverButton);
        } else {
            game.getStage().addActor(copyButton);
            game.getStage().addActor(pasteButton);
            game.getStage().addActor(deliverButton);
        }

    }


    @Override
    public void render(float delta) {

        ScreenUtils.clear(57 / 255f, 72f / 255f, 85f / 255f, 1);

        // Show tutorial on first launch and kok count is zero
        if (game.showTutorialFirstLaunch() && pc.getKokCount() == 0) {
            this.tutorialMode = false;
            game.setScreen(new TutorialScreen(game, 0, false));
        }

        game.getStage().act();
        game.getStage().draw();
        game.getBatch().begin();

        Entity player = game.getPlayer();
        PlayerComponent pc = player.getComponent(PlayerComponent.class);
        //DecimalFormat formatter = new DecimalFormat("#,###");
        game.getFont().draw(
                game.getBatch(),
                //"Kok : " + formatter.format(pc.getKokCount()),
                "Kok: " + game.formatMillions(pc.getKokCount()),
                Gdx.graphics.getWidth() / 3f,
                Gdx.graphics.getHeight() / 1.2f
        );
        game.getFont().draw(
                game.getBatch(),
                game.formatMillions(getGainpersecond()) + " kok/s",
                Gdx.graphics.getWidth() / 3f,
                Gdx.graphics.getHeight() / 1.3f
        );
        game.getBatch().end();

        game.getEngine().update(Gdx.graphics.getDeltaTime());

    }

    private float getGainpersecond() {
        ResourceGainSystem rgs = game.getEngine().getSystem(ResourceGainSystem.class);

        return rgs.getResourceGainPerSecond();
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
