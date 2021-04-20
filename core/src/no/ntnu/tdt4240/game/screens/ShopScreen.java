package no.ntnu.tdt4240.game.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.ScreenUtils;

import no.ntnu.tdt4240.game.StudentLifeGame;
import no.ntnu.tdt4240.game.components.PlayerComponent;
import no.ntnu.tdt4240.game.guiElements.ButtonElement;


public class ShopScreen implements Screen {

    private Button statButton, gameButton, shopButton;
    private Button buy1Button, buy2Button, buy3Button, buy4Button;
    final StudentLifeGame game;
    final float BUTTONHEIGHTGUI;
    final float BUTTONWIDTHGUI;
    final int SCREENHEIGTH;
    final int SCREENWIDTH;
    final int buttonPadding;
    int counterEidBuy1,counterEidBuy2,counterEidBuy3,counterEidBuy4;
    String buy1String,buy2String,buy3String,buy4String;
    PlayerComponent pc;


    public ShopScreen(final StudentLifeGame game) {

        this.game = game;
        this.game.getStage().clear();
        Entity player = game.getPlayer();
        pc = player.getComponent(PlayerComponent.class);

        SCREENHEIGTH = Gdx.graphics.getHeight();
        SCREENWIDTH = Gdx.graphics.getWidth();
        BUTTONHEIGHTGUI = SCREENHEIGTH/8f;
        BUTTONWIDTHGUI = SCREENWIDTH/4f;
        buttonPadding = 10;



        //TODO lmao, burde hente alle resourcecompontene og bygge et listview

        counterEidBuy1 = 0;
        final int prisBuy1 = 100;
        buy1String = "Betalte kokere "+prisBuy1+",-";

        buy1Button = new ButtonElement(
                BUTTONWIDTHGUI*3,BUTTONHEIGHTGUI,
                (SCREENWIDTH/2f)-BUTTONWIDTHGUI*3/2, SCREENHEIGTH*5/8f,
                buy1String, game.getSkin(), new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(pc.getKokCount() >= prisBuy1){
                    counterEidBuy1++;

                    pc.setKokCount(pc.getKokCount()-prisBuy1);
                    System.out.println("KJØPTE KOKERE 1");
                }
                return true;
            }
        });

        counterEidBuy2= 0;
        final int prisBuy2 = 200;
        buy2String = "Studass som koker "+prisBuy2+",-";

        buy2Button = new ButtonElement(
                BUTTONWIDTHGUI*3,BUTTONHEIGHTGUI,
                (SCREENWIDTH/2f)-BUTTONWIDTHGUI*3/2, SCREENHEIGTH*5/8f-BUTTONHEIGHTGUI-buttonPadding,
                buy2String, game.getSkin(), new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(pc.getKokCount() >= prisBuy2){
                    counterEidBuy2++;
                    pc.setKokCount(pc.getKokCount()-prisBuy2);
                    System.out.println("KJØPTE KOKERE 2");
                }
                return true;
            }
        });

        counterEidBuy3= 0;
        final int prisBuy3 = 500;
        buy3String = "kok scripts "+prisBuy3+",-";

        buy3Button = new ButtonElement(
                BUTTONWIDTHGUI*3,BUTTONHEIGHTGUI,
                (SCREENWIDTH/2f)-BUTTONWIDTHGUI*3/2, SCREENHEIGTH*5/8f-BUTTONHEIGHTGUI*2-buttonPadding*2,
                buy3String, game.getSkin(), new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(pc.getKokCount() >= prisBuy3){
                    counterEidBuy3++;
                    pc.setKokCount(pc.getKokCount()-prisBuy3);
                    System.out.println("KJØPTE KOKERE 3");
                }
                return true;
            }
        });

        counterEidBuy4 = 0;
        final int prisBuy4 = 1000;
        buy4String = "Kok Hackere "+prisBuy4+",-";

        buy4Button = new ButtonElement(
                BUTTONWIDTHGUI*3,BUTTONHEIGHTGUI,
                (SCREENWIDTH/2f)-BUTTONWIDTHGUI*3/2, SCREENHEIGTH*5/8f-BUTTONHEIGHTGUI*3-buttonPadding*3,
                buy4String, game.getSkin(), new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(pc.getKokCount() >= prisBuy4){
                    counterEidBuy4++;
                    pc.setKokCount(pc.getKokCount()-prisBuy4);
                    System.out.println("KJØPTE KOKERE 4");
                }
                return true;
            }
        });

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

        //add actors til stagen
        game.getStage().addActor(buy1Button);
        game.getStage().addActor(buy2Button);
        game.getStage().addActor(buy3Button);
        game.getStage().addActor(buy4Button);
        game.getStage().addActor(statButton);
        game.getStage().addActor(gameButton);
        game.getStage().addActor(shopButton);
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(57/255f, 72f/255f, 85f/255f, 1);

        // stage tegner aktorsa
        game.getStage().act();
        game.getStage().draw();
        //batch tegner vi resten på
        game.getBatch().begin();
        game.getFont().draw(
                game.getBatch(),
                "Kokt : " + String.valueOf(pc.getKokCount()),
                Gdx.graphics.getWidth()/3f,
                Gdx.graphics.getHeight()/1.2f
        );
        game.getFont().draw(
                game.getBatch(),
                ""+counterEidBuy1,
                25,
                SCREENHEIGTH*5/8f+BUTTONHEIGHTGUI/2
        );
        game.getFont().draw(
                game.getBatch(),
                ""+counterEidBuy2,
                25,
                SCREENHEIGTH*5/8f-BUTTONHEIGHTGUI+BUTTONHEIGHTGUI/2
        );
        game.getFont().draw(
                game.getBatch(),
                ""+counterEidBuy3,
                25,
                SCREENHEIGTH*5/8f-BUTTONHEIGHTGUI*2+BUTTONHEIGHTGUI/2
        );
        game.getFont().draw(
                game.getBatch(),
                "" + counterEidBuy4,
                25,
                SCREENHEIGTH*5/8f-BUTTONHEIGHTGUI*3+BUTTONHEIGHTGUI/2
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
