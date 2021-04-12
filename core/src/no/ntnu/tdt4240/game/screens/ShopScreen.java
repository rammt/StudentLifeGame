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
import no.ntnu.tdt4240.game.components.ButtonComponent;


public class ShopScreen implements Screen {

    final StudentLifeGame game;
    final float BUTTONHEIGHTGUI;
    final float BUTTONWIDTHGUI;
    final int SCREENHEIGTH;
    final int SCREENWIDTH;
    final int buttonPadding;
    int counterEidBuy1,counterEidBuy2,counterEidBuy3,counterEidBuy4;
    String buy1String,buy2String,buy3String,buy4String;


    public ShopScreen(final StudentLifeGame game) {

        this.game = game;
        SCREENHEIGTH = Gdx.graphics.getHeight();
        SCREENWIDTH = Gdx.graphics.getWidth();
        BUTTONHEIGHTGUI = SCREENHEIGTH/8f;
        BUTTONWIDTHGUI = SCREENWIDTH/4f;
        buttonPadding = 10;
        final int prisBuy1 = 100;
        final int prisBuy2 = 200;
        final int prisBuy3 = 500;
        final int prisBuy4 = 1000;
        buy1String = "Betalte kokere ( " + counterEidBuy1 + " ) "+prisBuy1+",-";
        buy2String = "Studass som koker ( " + counterEidBuy2 + " ) "+prisBuy2+",-";
        buy3String = "kok scripts ( " + counterEidBuy3 + " ) "+prisBuy3+",-";
        buy4String = "Kok Hackere ( " + counterEidBuy4 + " ) "+prisBuy4+",-";

        game.getStage().clear();

        //shop elementer

        Entity BUY1 = game.getEngine().createEntity();
        counterEidBuy1 = 0;
        BUY1.add(new ButtonComponent().create(
            BUTTONWIDTHGUI*3,BUTTONHEIGHTGUI,
            (SCREENWIDTH/2f)-BUTTONWIDTHGUI*3/2,SCREENHEIGTH*5/8f,
            buy1String,game.getSkin(), new InputListener(){
                @Override
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    if(game.getKokCounter() >= prisBuy1){
                        counterEidBuy1++;
                        game.setKokCounter(-prisBuy1);
                        System.out.println("KJØPTE KOKERE 1");
                    }
                    return true;
                }}));
        game.getEngine().addEntity(BUY1);

        Entity BUY2 = game.getEngine().createEntity();
        counterEidBuy2= 0;
        BUY2.add(new ButtonComponent().create(
            BUTTONWIDTHGUI*3,BUTTONHEIGHTGUI,
            (SCREENWIDTH/2f)-BUTTONWIDTHGUI*3/2,SCREENHEIGTH*5/8f-BUTTONHEIGHTGUI-buttonPadding,
            buy2String,game.getSkin(), new InputListener(){
                @Override
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    if(game.getKokCounter() >= prisBuy2){
                        counterEidBuy2++;
                        game.setKokCounter(-prisBuy2);
                        System.out.println("KJØPTE KOKERE 2");
                    }
                    return true;
                }}));
        game.getEngine().addEntity(BUY2);


        Entity BUY3 = game.getEngine().createEntity();
        counterEidBuy3= 0;
        BUY3.add(new ButtonComponent().create(
        BUTTONWIDTHGUI*3,BUTTONHEIGHTGUI,
        (SCREENWIDTH/2f)-BUTTONWIDTHGUI*3/2,SCREENHEIGTH*5/8f-BUTTONHEIGHTGUI*2-buttonPadding*2,
         buy3String,game.getSkin(), new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if(game.getKokCounter() >= prisBuy3){
                    counterEidBuy3++;
                    game.setKokCounter(-prisBuy3);
                    System.out.println("KJØPTE KOKERE 3");
                }
                return true;
            }}));
        game.getEngine().addEntity(BUY3);


        Entity BUY4 = game.getEngine().createEntity();
        counterEidBuy4 = 0;
        BUY4.add(new ButtonComponent().create(
            BUTTONWIDTHGUI*3,BUTTONHEIGHTGUI,
            (SCREENWIDTH/2f)-BUTTONWIDTHGUI*3/2,SCREENHEIGTH*5/8f-BUTTONHEIGHTGUI*3-buttonPadding*3,
            buy4String,game.getSkin(), new InputListener(){
                @Override
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    if(game.getKokCounter() >= prisBuy4){
                        counterEidBuy4++;
                        game.setKokCounter(-prisBuy4);
                        System.out.println("KJØPTE KOKERE 4");
                    }
                    return true;
                }}));
        game.getEngine().addEntity(BUY4);

        //TODO Meny gui, kanskje ha dette i en egen element klasse?

        Entity homeButton = game.getEngine().createEntity();
        homeButton.add(new ButtonComponent().create(
            BUTTONWIDTHGUI,BUTTONHEIGHTGUI,
            (SCREENWIDTH/4f)-BUTTONWIDTHGUI/2-buttonPadding,50,
            "Home",game.getSkin(), new InputListener(){
                @Override
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    game.setScreen(new StartScreen(game));
                    return true;
                }}));
        game.getEngine().addEntity(homeButton);

        Entity settingsButton = game.getEngine().createEntity();
        settingsButton.add(new ButtonComponent().create(
                BUTTONWIDTHGUI,BUTTONHEIGHTGUI,
                (SCREENWIDTH/2f)-BUTTONWIDTHGUI/2,50,
                "Shop",game.getSkin(), new InputListener(){
                    @Override
                    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                        //game.setScreen(new ShopScreen(game));
                        return true;
                    }}));
        game.getEngine().addEntity(settingsButton);

        Entity ShopButton = game.getEngine().createEntity();
        ShopButton.add(new ButtonComponent().create(
                BUTTONWIDTHGUI,BUTTONHEIGHTGUI,
                (SCREENWIDTH*3/4f)-BUTTONWIDTHGUI/2+buttonPadding,50,
                "Stats",game.getSkin(), new InputListener(){
                    @Override
                    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                        game.setScreen(new StatScreen(game));
                        return true;
                    }}));
        game.getEngine().addEntity(ShopButton);
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0.2f, 1);

        // stage tegner aktorsa
        game.getStage().act();
        game.getStage().draw();
        //batch tegner vi resten på
        game.getBatch().begin();
        game.getFont().draw(
                game.getBatch(),
                "Kokt : " + String.valueOf(game.getKokCounter()),
                Gdx.graphics.getWidth()/3f,
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
